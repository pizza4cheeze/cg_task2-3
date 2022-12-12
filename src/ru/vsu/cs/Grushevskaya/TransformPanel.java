package ru.vsu.cs.Grushevskaya;

import ru.vsu.cs.Grushevskaya.lineDrawers.*;
import ru.vsu.cs.Grushevskaya.screenWork.RealPoint;
import ru.vsu.cs.Grushevskaya.screenWork.ScreenConverter;
import ru.vsu.cs.Grushevskaya.screenWork.ScreenPoint;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class TransformPanel extends JPanel {
//    private final double timeToTransform;
    private double currTime = 0;

    CurveConverter curveConverter;

    public TransformPanel(ArrayList<ScreenPoint> startPoints, ArrayList<ScreenPoint> resultPoints, int time) {
        setPreferredSize(new Dimension(800, 600));
        repaint();
        add(new JButton("Проверка"));

//        timeToTransform = time;
//        curveConverter = new CurveConverter(startPoints, resultPoints);
//
//        Thread t = new Thread(new Runnable(){
//            @Override
//            public void run() {
//                while(currTime <= timeToTransform) {
//                    currTime += timeToTransform / 1000;
//                    repaint();
//                    try {
//                        Thread.sleep(4);
//                    } catch (InterruptedException ignored) {}
//                }
//            }
//        });
//        t.start();

    }

    @Override
    public void paintComponents(Graphics origG) {
//        Graphics2D g2d = (Graphics2D) origG;
//        origG.fillOval(0, 0, getWidth(), getHeight());
        BufferedImage bi = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bi.createGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());

        PixelDrawer pd = new GraphicsPixelDrawer(g);
        LineDrawer ldDDA = new DDALineDrawer(pd);
        LineDrawer ldGr = new GraphicsLineDrawer(g);
        LineDrawer ldBres = new BresenhamLineDrawer(pd);
        LineDrawer ldWu = new WuLineDrawer(pd);

        g.setColor(Color.MAGENTA);
        Curve curve = curveConverter.middle(currTime);
        curve.drawCurve(pd);

        origG.drawImage(bi, 0, 0, null);
        g.dispose();
    }

    private static void drawLine(Graphics2D g, Line l) {
        RealPoint p1 = l.getP1();
        RealPoint p2 = l.getP2();
        g.drawLine((int) p1.getY(), (int) p1.getX(), (int) p2.getY(), (int) p2.getX());
    }

    private static void drawLine(LineDrawer ld, Line l) {
        RealPoint p1 = l.getP1();
        RealPoint p2 = l.getP2();
        ld.drawLine((int) p1.getY(), (int) p1.getX(), (int) p2.getY(), (int) p2.getX());
    }
}
