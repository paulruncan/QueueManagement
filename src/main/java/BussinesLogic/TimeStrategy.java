package BussinesLogic;

import BussinesLogic.Strategy;
import Model.Server;
import Model.Task;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class TimeStrategy implements Strategy {

    @Override
    public void addTask( CopyOnWriteArrayList<Server> servers, Task task ) {
        Server minTimeServer=new Server();
        int minWaitingTime=10000;
        for(Server server:servers){
            if(minWaitingTime>server.getWaitingPeriod().get()){
                minTimeServer=server;
                minWaitingTime=server.getWaitingPeriod().get();
            }
        }
        minTimeServer.addTask(task);
    }
}
