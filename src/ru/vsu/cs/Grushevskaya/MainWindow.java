package ru.vsu.cs.Grushevskaya;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    SetPointsPanel setPointsPanel = new SetPointsPanel(this);

    public MainWindow() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
        setTitle("Превращение кривых");

        add(setPointsPanel);
        pack();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new MainWindow();
    }
}