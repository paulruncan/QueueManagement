package View;

import View.SimulationController;

import javax.swing.*;
import java.awt.*;

public class SimulationFrame extends JFrame {
    private JPanel contentPane;
    private JPanel dataPane;
    private JPanel outputPane;

    private JLabel nrOfClientsLabel;
    private JLabel nrOfQueuesLabel;
    private JLabel simulationTimeLabel;
    private JLabel strategyLabel;

    private JLabel arrivalTimeIntervalLabel;
    private JLabel serviceTimeIntervalLabel;
    private JTextArea outputTextArea;
    private JComboBox strategyComboBox;

    private TextField nrOfClientsTextField;
    private TextField nrOfQueuesTextField;
    private TextField simulationTimeTextField;
    private TextField minArrivalTimeTextField;
    private TextField maxArrivalTimeTextField;
    private TextField minServiceTimeTextField;
    private TextField maxServiceTimeTextField;

    private JButton startButton;

    SimulationController simulationController = new SimulationController(this);

    public SimulationFrame(String name){
        super(name);
        this.prepareGui();
    }

    private void prepareGui() {
        this.setSize(800,800);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.contentPane=new JPanel(new GridLayout(2,1));
        this.prepareDataPanel();
        this.prepareOutputPanel();
        this.setContentPane(this.contentPane);
    }

    private void prepareOutputPanel() {
        this.outputPane=new JPanel();
        this.outputPane.setLayout(new GridLayout(1,1));
        this.outputTextArea=new JTextArea();
        outputTextArea.setEditable(false);
        this.outputPane.add(this.outputTextArea);
        this.contentPane.add(this.outputPane);
    }

    private void prepareDataPanel() {
        this.dataPane=new JPanel();
        this.dataPane.setLayout(new GridLayout(6,2));
        this.nrOfClientsLabel=new JLabel("<html>Nr. Of<br> Clients:</html>", JLabel.CENTER);
        this.dataPane.add(this.nrOfClientsLabel);
        this.nrOfClientsTextField=new TextField();
        this.dataPane.add(this.nrOfClientsTextField);

        this.dataPane.add(new JLabel(""));
        this.nrOfQueuesLabel=new JLabel("Nr. Of Queues:", JLabel.CENTER);
        this.dataPane.add(this.nrOfQueuesLabel);
        this.nrOfQueuesTextField=new TextField();
        this.dataPane.add(this.nrOfQueuesTextField);
        this.dataPane.add(new JLabel(""));

        this.arrivalTimeIntervalLabel=new JLabel("Arrival", JLabel.CENTER);
        this.dataPane.add(this.arrivalTimeIntervalLabel);
        this.minArrivalTimeTextField=new TextField();
        this.dataPane.add(this.minArrivalTimeTextField);
        this.maxArrivalTimeTextField=new TextField();
        this.dataPane.add(this.maxArrivalTimeTextField);

        this.serviceTimeIntervalLabel=new JLabel("Service", JLabel.CENTER);
        this.dataPane.add(this.serviceTimeIntervalLabel);
        this.minServiceTimeTextField=new TextField();
        this.dataPane.add(this.minServiceTimeTextField);
        this.maxServiceTimeTextField=new TextField();
        this.dataPane.add(this.maxServiceTimeTextField);

        this.simulationTimeLabel=new JLabel("simulatuon time", JLabel.CENTER);
        this.dataPane.add(this.simulationTimeLabel);

        this.simulationTimeTextField=new TextField();
        this.dataPane.add(this.simulationTimeTextField);
        String[] stategies = new String[]{"Time Strategy","Shortest Queue Strategy"};
        this.strategyComboBox=new JComboBox(stategies);

        this.dataPane.add(new JLabel(""));
        this.strategyLabel=new JLabel("Strategy:", JLabel.CENTER);
        this.dataPane.add(this.strategyLabel);
        this.dataPane.add(this.strategyComboBox);

        this.startButton=new JButton("START");
        this.startButton.setActionCommand("START");
        this.startButton.addActionListener(this.simulationController);
        this.dataPane.add(this.startButton);
        this.contentPane.add(this.dataPane,"North");
    }


    public JComboBox getStrategyComboBox() {
        return strategyComboBox;
    }

    public TextField getNrOfClientsTextField() {
        return nrOfClientsTextField;
    }

    public TextField getNrOfQueuesTextField() {
        return nrOfQueuesTextField;
    }

    public TextField getSimulationTimeTextField() {
        return simulationTimeTextField;
    }

    public TextField getMinArrivalTimeTextField() {
        return minArrivalTimeTextField;
    }

    public TextField getMaxArrivalTimeTextField() {
        return maxArrivalTimeTextField;
    }

    public TextField getMinServiceTimeTextField() {
        return minServiceTimeTextField;
    }

    public void setOutputTextArea( JTextArea outputTextArea ) {
        this.outputTextArea = outputTextArea;
    }

    public TextField getMaxServiceTimeTextField() {
        return maxServiceTimeTextField;
    }

    public JTextArea getOutputTextArea() {
        return outputTextArea;
    }
}
