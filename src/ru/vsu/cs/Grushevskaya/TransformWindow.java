package ru.vsu.cs.Grushevskaya;

import ru.vsu.cs.Grushevskaya.screenWork.ScreenPoint;

import javax.swing.*;
import java.util.ArrayList;

public class TransformWindow extends JFrame {
    public TransformWindow(ArrayList<ScreenPoint> startPoints, ArrayList<ScreenPoint> resultPoints, int time) {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //setTitle("Превращение кривых");

        add(new TransformPanel(startPoints, resultPoints, time));
        pack();

        setLocationRelativeTo(null);
        setVisible(true);
    }
}
