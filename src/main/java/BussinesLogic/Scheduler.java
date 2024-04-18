package BussinesLogic;

import Model.Server;
import Enums.SelectionPolicy;
import Model.Task;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class Scheduler {
    private CopyOnWriteArrayList<Server> servers;
    private int maxNoServers;
    private int maxTasksPerServer;
    private Strategy strategy;

    public Scheduler(int maxNoServers, int maxTasksPerServer){
        this.servers=new CopyOnWriteArrayList<>();
        this.maxNoServers=maxNoServers;
        this.maxTasksPerServer=maxTasksPerServer;
        for(int i=0;i<maxNoServers;i++){
            Server server = new Server(maxTasksPerServer);
            Thread t = new Thread(server);
            t.start();
            this.servers.add(server);
        }
    }

    public void changeStrategy(SelectionPolicy policy){
        if(policy == SelectionPolicy.SHORTEST_TIME)
            strategy = new TimeStrategy();
        if(policy == SelectionPolicy.SHORTEST_QUEUE)
            strategy = new ShortestQueueStrategy();
    }
    public int dispatchTask( Task task){
        return strategy.addTask(servers,task);
    }

    public CopyOnWriteArrayList<Server> getServers() {
        return servers;
    }

    public void setServers( CopyOnWriteArrayList<Server> servers ) {
        this.servers = servers;
    }

    public int getMaxNoServers() {
        return maxNoServers;
    }

    public void setMaxNoServers( int maxNoServers ) {
        this.maxNoServers = maxNoServers;
    }

    public int getMaxTasksPerServer() {
        return maxTasksPerServer;
    }

    public void setMaxTasksPerServer( int maxTasksPerServer ) {
        this.maxTasksPerServer = maxTasksPerServer;
    }

    public Strategy getStrategy() {
        return strategy;
    }

    public void setStrategy( Strategy strategy ) {
        this.strategy = strategy;
    }
}
