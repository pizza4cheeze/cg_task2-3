package ru.vsu.cs.Grushevskaya;

import ru.vsu.cs.Grushevskaya.lineDrawers.*;
import ru.vsu.cs.Grushevskaya.screenWork.RealPoint;
import ru.vsu.cs.Grushevskaya.screenWork.ScreenPoint;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class TransformPanel extends JPanel {
    private final double timeToTransform;
    private double currTime = 0;

    private final Timer t;
    int delay;

    CurveConverter curveConverter;

    public TransformPanel(ArrayList<ScreenPoint> startPoints, ArrayList<ScreenPoint> resultPoints, int time) {
        setPreferredSize(new Dimension(800, 600));

        delay = time;
        timeToTransform = time * 1000;
        repaint();

        curveConverter = new CurveConverter(startPoints, resultPoints);

        t = new Timer(delay - 1, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currTime += delay;
                repaint();
                if (currTime > timeToTransform) {
                    t.stop();
                }
            }
        });
        t.start();

//        t = new javax.swing.Timer(delay, new AbstractAction() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                while (currTime <= timeToTransform) {
//                    currTime += timeToTransform / 1000;
//                }
//                repaint();
//            }
//        });
//        t.start();
    }

    @Override
    public void paintComponent(Graphics origG) {
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

        g.setColor(Color.BLACK);
        g.drawString("time remaining: " + currTime, 50, 50);

        g.setColor(Color.MAGENTA);
        Curve curve = curveConverter.middle(currTime / timeToTransform);
        curve.drawCurve(ldDDA);

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
