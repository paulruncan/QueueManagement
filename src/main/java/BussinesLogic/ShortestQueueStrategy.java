package BussinesLogic;

import Model.Server;
import Model.Task;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class ShortestQueueStrategy implements Strategy{
    @Override
    public void addTask( CopyOnWriteArrayList<Server> servers, Task task ) {
        Server shortQueueServer=new Server();
        int shortestSize=10000;
        for(Server server:servers){
            if(shortestSize>server.getTasks().size()){
                shortQueueServer=server;
                shortestSize=server.getTasks().size();
            }
        }
        shortQueueServer.addTask(task);
    }
}
