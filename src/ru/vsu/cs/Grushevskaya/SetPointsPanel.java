package ru.vsu.cs.Grushevskaya;

import ru.vsu.cs.Grushevskaya.LineDrawers.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class SetPointsPanel extends JPanel {
    private ScreenConverter sc;
    private Line ox, oy;
    ArrayList<RealPoint> pointsStart;
    ArrayList<RealPoint> pointsResult;


    public SetPointsPanel() {
        sc = new ScreenConverter(-2, 2, 4, 4, 800, 600);
        ox = new Line(new RealPoint(-1, 0), new RealPoint(1, 0));
        oy = new Line(new RealPoint(0, -1), new RealPoint(0, 1));

        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON2) {
                    double x = e.getX();
                    double y = e.getY();
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        PixelDrawer pixelDrawer = new GraphicsPixelDrawer(g2d);
        LineDrawer ddaLineDrawer = new DDALineDrawer(pixelDrawer);
        LineDrawer graphicsLineDrawer = new GraphicsLineDrawer(g2d);
        LineDrawer bresenhamLineDrawer = new BresenhamLineDrawer(pixelDrawer);
        LineDrawer wuLineDrawer = new WuLineDrawer(pixelDrawer);

        g.setColor(Color.BLUE);
        drawLine(ddaLineDrawer, sc, ox);
        drawLine(ddaLineDrawer, sc, oy);

        g.setColor(Color.BLACK);

        graphicsLineDrawer.drawLine(10, 10, 60, 60);
        ddaLineDrawer.drawLine(70, 70, 150, 220);
        bresenhamLineDrawer.drawLine(167, 45, 250, 300);
        wuLineDrawer.drawLine(400, 368, 0, 500);
    }

    private static void drawLine(Graphics2D g, ScreenConverter sc, Line l) {
        ScreenPoint p1 = sc.r2s(l.getP1());
        ScreenPoint p2 = sc.r2s(l.getP2());
        g.drawLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
    }

    private static void drawLine(LineDrawer ld, ScreenConverter sc, Line l) {
        ScreenPoint p1 = sc.r2s(l.getP1());
        ScreenPoint p2 = sc.r2s(l.getP2());
        ld.drawLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
    }
}
