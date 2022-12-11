package ru.vsu.cs.Grushevskaya;

import ru.vsu.cs.Grushevskaya.LineDrawers.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class SetPointsPanel extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener {
    private ScreenConverter sc;
    private Line ox, oy;
    ArrayList<RealPoint> pointsStart;
    ArrayList<RealPoint> pointsResult;


    public SetPointsPanel() {
        sc = new ScreenConverter(-2, 2, 4, 4, 800, 600);
        ox = new Line(new RealPoint(-2, 0), new RealPoint(2, 0));
        oy = new Line(new RealPoint(0, -2), new RealPoint(0, 2));

        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.addMouseWheelListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        sc.setsWidth(getWidth());
        sc.setsHeight(getHeight());

        BufferedImage bi = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D imageGraphics = bi.createGraphics();
        imageGraphics.setColor(Color.WHITE);
        imageGraphics.fillRect(0, 0, getWidth(), getHeight());

        PixelDrawer pixelDrawer = new GraphicsPixelDrawer(imageGraphics);
        LineDrawer ddaLineDrawer = new DDALineDrawer(pixelDrawer);
        LineDrawer graphicsLineDrawer = new GraphicsLineDrawer(imageGraphics);
        LineDrawer bresenhamLineDrawer = new BresenhamLineDrawer(pixelDrawer);
        LineDrawer wuLineDrawer = new WuLineDrawer(pixelDrawer);

        imageGraphics.setColor(Color.BLUE);
        drawLine(ddaLineDrawer, sc, ox);
        drawLine(ddaLineDrawer, sc, oy);

        imageGraphics.setColor(Color.BLACK);
        graphicsLineDrawer.drawLine(0, 0, 800, 600);
        wuLineDrawer.drawLine(5, 9, 45, 600);

        g.drawImage(bi, 0, 0, null);
        imageGraphics.dispose();

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

    private static void deletePoint(ScreenPoint p) {

    }


    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        ScreenPoint p = new ScreenPoint(x, y);
        if (SwingUtilities.isLeftMouseButton(e)) {
            pointsStart.add(sc.s2r(p));
        } else if (SwingUtilities.isRightMouseButton(e)) {
            pointsResult.add(sc.s2r(p));
        } else if (SwingUtilities.isMiddleMouseButton(e)) {
            deletePoint(p);
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

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    private static final double SCALE_STEP = 0.1;

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int clicks = e.getWheelRotation();
        double coef = 1 + SCALE_STEP * (clicks < 0 ? -1 : 1);
        double scale = 1;
        for (int i = Math.abs(clicks); i > 0; i--) {
            scale *= coef;
        }
        sc.changeScale(scale);
        repaint();

    }
}
