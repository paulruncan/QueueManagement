import BussinesLogic.Scheduler;
import BussinesLogic.ShortestQueueStrategy;
import BussinesLogic.SimulationManager;
import BussinesLogic.TimeStrategy;
import Enums.SelectionPolicy;
import Model.Server;
import Model.Task;

import javax.swing.*;
import java.util.ArrayList;

public class Main {
    public static void main( String[] args ) {
        JFrame frame = new View.SimulationFrame("da");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}