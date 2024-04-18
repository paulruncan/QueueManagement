package View;

import BussinesLogic.SimulationManager;
import Enums.SelectionPolicy;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.InputMismatchException;
import java.util.SortedMap;

public class SimulationController implements ActionListener {
    private SimulationFrame simulationFrame;

    public SimulationController( SimulationFrame simulationFrame ) {
        this.simulationFrame = simulationFrame;
    }

    @Override
    public void actionPerformed( ActionEvent e ) {
        try {
            String command = e.getActionCommand();
            if (command.equals("START")) {
                int nrOfClients = Integer.parseInt(simulationFrame.getNrOfClientsTextField().getText());
                int nrOfQueues = Integer.parseInt(simulationFrame.getNrOfQueuesTextField().getText());
                int minArrival = Integer.parseInt(simulationFrame.getMinArrivalTimeTextField().getText());
                int maxArrival = Integer.parseInt(simulationFrame.getMaxArrivalTimeTextField().getText());
                int minService = Integer.parseInt(simulationFrame.getMinServiceTimeTextField().getText());
                int maxService = Integer.parseInt(simulationFrame.getMaxServiceTimeTextField().getText());
                int simulationTime = Integer.parseInt(simulationFrame.getSimulationTimeTextField().getText());
                String strategy = String.valueOf(simulationFrame.getStrategyComboBox().getSelectedItem());
                if (nrOfClients < 0 || nrOfQueues < 0 || minArrival < 0 || maxArrival < 0 || minService < 0 || maxService < 0 || simulationTime < 0 || maxArrival<minArrival || maxService < minService)
                    throw new InputMismatchException();
                SimulationManager simulationManager = new SimulationManager(simulationTime, maxService, minService, minArrival, maxArrival, nrOfQueues, nrOfClients);

                if (strategy.equals("Time Strategy"))
                    simulationManager.setSelectionPolicy(SelectionPolicy.SHORTEST_TIME);
                else simulationManager.setSelectionPolicy(SelectionPolicy.SHORTEST_QUEUE);

                simulationManager.getScheduler().changeStrategy(simulationManager.getSelectionPolicy());
                Thread t = new Thread(simulationManager);
                t.start();

            }
        } catch (InputMismatchException exception) {
            OutputFrame frame = new OutputFrame("da");
            frame.getOutputTextArea().setText("Invalid Input");
            frame.setVisible(true);
        }
    }
}
