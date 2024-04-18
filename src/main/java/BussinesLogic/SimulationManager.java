package BussinesLogic;

import Enums.SelectionPolicy;
import Model.Server;
import Model.Task;
import Utils.PrintTxt;
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
    private int minArriveTime = 0;
    private int maxArriveTime = 10;
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

    public SimulationManager( int timeLimit, int maxProcessingTime, int minProcessingTime, int minArriveTime, int maxArriveTime, int numberOfServers, int numberOfClients ) {
        this.timeLimit = timeLimit;
        this.maxProcessingTime = maxProcessingTime;
        this.minProcessingTime = minProcessingTime;
        this.minArriveTime = minArriveTime;
        this.maxArriveTime = maxArriveTime;
        this.numberOfServers = numberOfServers;
        this.numberOfClients = numberOfClients;
        scheduler = new Scheduler(numberOfServers, 100);
        generatedTasks = generateNRandomTasks();
        if(!generatedTasks.isEmpty())
        {frame = new OutputFrame("da");
        frame.setVisible(true);}
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
        if(!this.generatedTasks.isEmpty()) {
            AtomicInteger averageWaiting = new AtomicInteger(0);
            AtomicInteger averageProcess = new AtomicInteger(0);
            AtomicInteger peekHour = new AtomicInteger(0);
            AtomicInteger biggestSize = new AtomicInteger(0);
            this.stringBuilder = new StringBuilder();
            int currentTime = 0;
            PrintTxt.clearTxt();
            try {
                while (currentTime <= timeLimit) {
                    this.stringBuilder.delete(0, this.stringBuilder.length());
                    this.stringBuilder.append("STEP:").append(currentTime).append("\n");
                    for( Task task : generatedTasks )
                        if (task.getArrivalTime() == currentTime) {
                            averageProcess.addAndGet(task.getServiceTime());
                            averageWaiting.addAndGet(scheduler.dispatchTask(task));
                            generatedTasks.remove(task);
                        }
                    this.stringBuilder.append("Waiting:");
                    for( Task task : generatedTasks )
                        this.stringBuilder.append("{").append(task.getId()).append(",").append(task.getArrivalTime()).append(",").append(task.getServiceTime()).append("}");
                    this.stringBuilder.append("\n");
                    currentTime++;
                    int i = 1;
                    AtomicInteger currentSize = new AtomicInteger(0);
                    for( Server server : scheduler.getServers() ) {
                        currentSize.addAndGet(server.getTasks().size());
                        if (currentSize.get() > biggestSize.get()) {
                            biggestSize.set(currentSize.get());
                            peekHour.set(currentTime - 1);
                        }
                        stringBuilder.append("Queue ").append(i++).append(":");
                        if (server.getTasks().isEmpty()) {
                            stringBuilder.append("closed\n");
                        } else {
                            for( Task task : server.getTasks() ) {
                                stringBuilder.append("{").append(task.getId()).append(",").append(task.getArrivalTime()).append(",").append(task.getServiceTime()).append("}");
                            }
                            stringBuilder.append("\n");
                        }
                    }
                    PrintTxt.printTxt(stringBuilder.toString());
                    frame.getOutputTextArea().setText(stringBuilder.toString());
                    updateServers(scheduler);
                    Thread.sleep(1000);
                }
                Double x = 1.0 * averageWaiting.get() / numberOfClients;
                Double y = 1.0 * averageProcess.get() / numberOfClients;
                String outputData = "Average Waiting: " + x.toString() + "\n" + "Average Proccesing: " + y.toString() + "\n" + "Peek Hour: " + peekHour.toString() + "\n";
                PrintTxt.printTxt(outputData);
                frame.getOutputTextArea().setText(outputData);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void updateServers( Scheduler scheduler ) {
        for( Server server : scheduler.getServers() ) {
            if (!server.getTasks().isEmpty())
                server.getTasks().peek().setServiceTime(server.getTasks().peek().getServiceTime() - 1);
            if (server.getWaitingPeriod().get() > 0)
                server.getWaitingPeriod().decrementAndGet();
        }

    }

    public StringBuilder getStringBuilder() {
        return stringBuilder;
    }

    public SelectionPolicy getSelectionPolicy() {
        return selectionPolicy;
    }

    public void setSelectionPolicy( SelectionPolicy selectionPolicy ) {
        this.selectionPolicy = selectionPolicy;
    }

    public Scheduler getScheduler() {
        return scheduler;
    }

    public void setScheduler( Scheduler scheduler ) {
        this.scheduler = scheduler;
    }
}
