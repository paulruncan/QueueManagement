package View;

import javax.swing.*;
import java.awt.*;

public class OutputFrame extends JFrame{
    private JTextArea outputTextArea;
    private JScrollPane pane;
    public OutputFrame(String name){
        super(name);
        this.prepareGui();
    }

    private void prepareGui() {
        this.setSize(800,200);
        this.outputTextArea=new JTextArea();
        this.pane = new JScrollPane(outputTextArea);
        add(pane);
    }

    public JTextArea getOutputTextArea() {
        return outputTextArea;
    }
}
