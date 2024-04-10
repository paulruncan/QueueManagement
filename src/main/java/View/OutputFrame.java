package View;

import javax.swing.*;
import java.awt.*;

public class OutputFrame extends JFrame{
    private JTextArea outputTextArea;
    private JPanel pane;
    public OutputFrame(String name){
        super(name);
        this.prepareGui();
    }

    private void prepareGui() {
        this.setSize(800,200);
        //this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pane=new JPanel(new GridLayout(1,1));
        this.outputTextArea=new JTextArea();
        this.pane.add(this.outputTextArea);
        this.setContentPane(this.pane);
    }

    public JTextArea getOutputTextArea() {
        return outputTextArea;
    }
}
