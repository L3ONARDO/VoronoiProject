package com.GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by littl on 10/12/2017.
 */
public class VoronoiGUI extends javax.swing.JFrame{
    private JButton startButton;
    private JPanel appPanel;
    private JComboBox strategyPicker;
    private JButton simulationButton;
    private JLabel display;

    public VoronoiGUI() {
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Yo");
            }
        });
        simulationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        strategyPicker.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("VoronoiGUI");
        frame.setContentPane(new VoronoiGUI().appPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
