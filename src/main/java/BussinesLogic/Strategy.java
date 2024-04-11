package BussinesLogic;

import Model.Server;
import Model.Task;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public interface Strategy {
     int addTask( CopyOnWriteArrayList<Server> servers, Task task);
}
