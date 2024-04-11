package BussinesLogic;

import Enums.SelectionPolicy;
import Model.Server;
import Model.Task;
import View.OutputFrame;
import View.SimulationFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.FileWriter;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class SimulationManager implements Runnable {
    private int timeLimit = 60;
    private int maxProcessingTime = 4;
    private int minProcessingTime = 2;
    private int minArriveTime=0;
    private int maxArriveTime=10;
    private int numberOfServers = 2;
    private int numberOfClients = 4;
    private SelectionPolicy selectionPolicy = SelectionPolicy.SHORTEST_TIME;

    private Scheduler scheduler;
    private CopyOnWriteArrayList<Task> generatedTasks;
    private StringBuilder stringBuilder;
    private OutputFrame frame;

    public SimulationManager() {
        generatedTasks = generateNRandomTasks();
        for( Task task : generatedTasks ) {
            System.out.println(task.getId() + " " + task.getArrivalTime() + " " + task.getServiceTime());
        }
    }
    public SimulationManager(int timeLimit,int maxProcessingTime,int minProcessingTime,int minArriveTime,int maxArriveTime,int numberOfServers,int numberOfClients,String selectionPolicy){
        this.timeLimit=timeLimit;
        this.maxProcessingTime=maxProcessingTime;
        this.minProcessingTime=minProcessingTime;
        this.minArriveTime=minArriveTime;
        this.maxArriveTime=maxArriveTime;
        this.numberOfServers=numberOfServers;
        this.numberOfClients=numberOfClients;
        if(!selectionPolicy.equals("Time Strategy"))
            this.selectionPolicy=SelectionPolicy.SHORTEST_QUEUE;
        scheduler = new Scheduler(numberOfServers,100,this.selectionPolicy);
        generatedTasks=generateNRandomTasks();
        frame=new OutputFrame("da");
        frame.setVisible(true);
        //frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private CopyOnWriteArrayList<Task> generateNRandomTasks() {
        Random random = new Random();
        CopyOnWriteArrayList<Task> tasks = new CopyOnWriteArrayList<>();
        for( int i = 1; i <= numberOfClients; i++ ) {
            Task task = new Task(random.nextInt(minArriveTime, maxArriveTime), random.nextInt(minProcessingTime, maxProcessingTime), i);
            tasks.add(task);
        }
        Collections.sort(tasks, new Comparator<Task>() {
            @Override
            public int compare( Task o1, Task o2 ) {
                if (o1.getArrivalTime() == o2.getArrivalTime())
                    return 0;
                return o1.getArrivalTime() < o2.getArrivalTime() ? -1 : 1;
            }
        });
        return tasks;
    }

    @Override
    public void run() {
        AtomicInteger averageWaiting=new AtomicInteger(0);
        AtomicInteger averageProcess=new AtomicInteger(0);
        AtomicInteger peekHour=new AtomicInteger(0);
        AtomicInteger biggestSize=new AtomicInteger(0);
        this.stringBuilder=new StringBuilder();
        int currentTime = 0;
        try {
            FileWriter myWriter = new FileWriter("output.txt");
            while (currentTime <= timeLimit) {
                this.stringBuilder.delete(0,this.stringBuilder.length());
                this.stringBuilder.append("STEP:").append(currentTime).append("\n");
                for( Task task : generatedTasks )
                    if (task.getArrivalTime() == currentTime) {
                        averageProcess.addAndGet(task.getServiceTime());
                        averageWaiting.addAndGet(scheduler.dispatchTask(task));
                        generatedTasks.remove(task);
                    }
                this.stringBuilder.append("Waiting:");
                for( Task task : generatedTasks )
                {
                    this.stringBuilder.append("{").append(task.getId()).append(",").append(task.getArrivalTime()).append(",").append(task.getServiceTime()).append("}");
                }
                this.stringBuilder.append("\n");
                currentTime++;
                int i = 1;
                AtomicInteger currentSize = new AtomicInteger(0);
                for( Server server : scheduler.getServers() ) {
                    currentSize.addAndGet(server.getTasks().size());
                    if(currentSize.get()>biggestSize.get()){
                        biggestSize.set(currentSize.get());
                        peekHour.set(currentTime-1);
                    }
                    stringBuilder.append("Queue ").append(i++).append(":");
                    if (server.getTasks().isEmpty()){
                        stringBuilder.append("closed\n");
                        }
                    else {
                        for( Task task : server.getTasks() ) {
                            stringBuilder.append("{").append(task.getId()).append(",").append(task.getArrivalTime()).append(",").append(task.getServiceTime()).append("}");
                        }
                        stringBuilder.append("\n");
                    }
                }
                System.out.println(stringBuilder);
                System.out.println(averageWaiting);
                System.out.println(averageProcess);
                myWriter.write(stringBuilder.toString());
                //myWriter.write(averageWaiting.toString());
                //myWriter.write("\n");
                frame.getOutputTextArea().setText(stringBuilder.toString());

                for( Server server : scheduler.getServers() ) {
                    if (!server.getTasks().isEmpty())
                        server.getTasks().peek().setServiceTime(server.getTasks().peek().getServiceTime() - 1);
                    if (server.getWaitingPeriod().get() > 0)
                        server.getWaitingPeriod().decrementAndGet();
                }
                Thread.sleep(1000);
            }
            System.out.println("peel:" +peekHour.get());
            System.out.println(averageProcess.get()/numberOfClients);
            System.out.println(averageWaiting.get()/numberOfClients);
            Double x=1.0*averageWaiting.get()/numberOfClients;
            myWriter.write("Average Waiting: " + x.toString());
            myWriter.write("\n");
            x=1.0*averageProcess.get()/numberOfClients;
            myWriter.write("Average Proccesing: " +x.toString());
            myWriter.write("\n");
            myWriter.write("Peek Hour: " +peekHour.toString());
            myWriter.write("\n");
            myWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public StringBuilder getStringBuilder() {
        return stringBuilder;
    }
}
