package ru.vsu.cs.Grushevskaya;

import ru.vsu.cs.Grushevskaya.screenWork.ScreenPoint;

import javax.swing.*;
import java.awt.*;
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

//    public static void main(String[] args) {
//        ArrayList<ScreenPoint> s = new ArrayList<>();
//        s.add(new ScreenPoint(40, 70));
//        s.add(new ScreenPoint(424, 120));
//        s.add(new ScreenPoint(391, 56));
//
//        ArrayList<ScreenPoint> r = new ArrayList<>();
//        r.add(new ScreenPoint(78, 560));
//        r.add(new ScreenPoint(100, 467));
//        r.add(new ScreenPoint(74, 367));
//
//        new TransformWindow(s, r, 5);
//    }
}
