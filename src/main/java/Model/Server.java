package Model;
import BussinesLogic.Scheduler;
import BussinesLogic.TimeStrategy;

import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable {
    private BlockingQueue<Task> tasks;
    private AtomicInteger waitingPeriod;

    public Server(LinkedBlockingQueue<Task> tasks, AtomicInteger waitingPeriod){
        this.tasks=tasks;
        this.waitingPeriod=waitingPeriod;
    }

    public Server(){
        this.waitingPeriod=new AtomicInteger(0);
        this.tasks = new LinkedBlockingQueue<>();
    }
    public Server(int maxTasksPerServer){
        this.waitingPeriod=new AtomicInteger(0);
        this.tasks = new LinkedBlockingQueue<>(maxTasksPerServer);
    }
    public void addTask(Task newTask){
        this.tasks.add(newTask);
        this.waitingPeriod.addAndGet(newTask.getServiceTime());
        //System.out.println(waitingPeriod);
    }
    public void run(){
        while(true){
            try {
                if(!this.tasks.isEmpty()){
                Task nextTask=this.tasks.peek();
                Thread.sleep(nextTask.getServiceTime()*1000);
                nextTask = this.tasks.take();
                this.waitingPeriod.addAndGet(-nextTask.getServiceTime());}
                } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public BlockingQueue<Task> getTasks() {
        return tasks;
    }

    public void setTasks( BlockingQueue<Task> tasks ) {
        this.tasks = tasks;
    }

    public AtomicInteger getWaitingPeriod() {
        return waitingPeriod;
    }

    public void setWaitingPeriod( AtomicInteger waitingPeriod ) {
        this.waitingPeriod = waitingPeriod;
    }
}
