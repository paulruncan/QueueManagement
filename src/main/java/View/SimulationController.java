package View;

import BussinesLogic.SimulationManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.SortedMap;

public class SimulationController implements ActionListener {
    private SimulationFrame simulationFrame;

    public SimulationController( SimulationFrame simulationFrame){this.simulationFrame=simulationFrame;}
    @Override
    public void actionPerformed( ActionEvent e ) {
        String command = e.getActionCommand();
        if(command.equals("START")){
            int nrOfClients = Integer.parseInt(simulationFrame.getNrOfClientsTextField().getText());
            int nrOfQueues = Integer.parseInt(simulationFrame.getNrOfQueuesTextField().getText());
            int minArrival = Integer.parseInt(simulationFrame.getMinArrivalTimeTextField().getText());
            int maxArrival = Integer.parseInt(simulationFrame.getMaxArrivalTimeTextField().getText());
            int minService = Integer.parseInt(simulationFrame.getMinServiceTimeTextField().getText());
            int maxService = Integer.parseInt(simulationFrame.getMaxServiceTimeTextField().getText());
            int simulationTime = Integer.parseInt(simulationFrame.getSimulationTimeTextField().getText());
            String strategy=String.valueOf(simulationFrame.getStrategyComboBox().getSelectedItem());
            SimulationManager simulationManager = new SimulationManager(simulationTime,maxService,minService,minArrival,maxArrival,nrOfQueues,nrOfClients,strategy);
            Thread t = new Thread(simulationManager);
            t.start();

        }
    }
}
