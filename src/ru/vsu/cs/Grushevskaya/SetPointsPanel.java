package ru.vsu.cs.Grushevskaya;

import ru.vsu.cs.Grushevskaya.lineDrawers.*;
import ru.vsu.cs.Grushevskaya.screenWork.RealPoint;
import ru.vsu.cs.Grushevskaya.screenWork.ScreenConverter;
import ru.vsu.cs.Grushevskaya.screenWork.ScreenPoint;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class SetPointsPanel extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener {
    private ScreenConverter sc;
    private Line ox, oy;

    private MainWindow mainWindow;

    private ArrayList<RealPoint> startPoints = new ArrayList<>();
    private ArrayList<RealPoint> resultPoints = new ArrayList<>();

    public SetPointsPanel(MainWindow mw) {
        mainWindow = mw;

        setPreferredSize(new Dimension(800, 600));
        sc = new ScreenConverter(-2, 2, 4, 4, 800, 600);
        ox = new Line(new RealPoint(-1, 0), new RealPoint(1, 0));
        oy = new Line(new RealPoint(0, -1), new RealPoint(0, 1));
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.addMouseWheelListener(this);

        JSpinner spinner = new JSpinner();
        spinner.setValue(5);
        add(spinner);

        JButton button = new JButton("Нарисовать превращение");
        button.addActionListener(e -> {
            ArrayList<ScreenPoint> startScreenPoints = new ArrayList<>();
            ArrayList<ScreenPoint> resultScreenPoints = new ArrayList<>();

            for (RealPoint r : startPoints) {
                //startScreenPoints.add(new ScreenPoint((int) r.getX(), (int) r.getY()));
                startScreenPoints.add(sc.r2s(r));
            }

            for (RealPoint r : resultPoints) {
                //resultScreenPoints.add(new ScreenPoint((int) r.getX(), (int) r.getY()));
                resultScreenPoints.add(sc.r2s(r));
            }

            new TransformWindow(startScreenPoints, resultScreenPoints, (int) spinner.getValue());
            mw.setVisible(false);
        });
        add(button);
    }

    @Override
    public void paintComponent(Graphics origG) {
        sc.setSw(getWidth());
        sc.setSh(getHeight());

        BufferedImage bi = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bi.createGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());

        PixelDrawer pd = new GraphicsPixelDrawer(g);
        LineDrawer ldDDA = new DDALineDrawer(pd);
        LineDrawer ldGr = new GraphicsLineDrawer(g);
        LineDrawer ldBres = new BresenhamLineDrawer(pd);
        LineDrawer ldWu = new WuLineDrawer(pd);

        g.setColor(Color.BLUE);
        drawLine(ldDDA, sc, ox);
        drawLine(ldDDA, sc, oy);

        g.setColor(Color.RED);
        List<ScreenPoint> a = new ArrayList<>();
        for (RealPoint r : startPoints) {
            ScreenPoint s = sc.r2s(r);
            a.add(s);
            g.fillOval(s.getC() - 5, s.getR() - 5,  10, 10);
        }
        if (a.size() > 1) {
            Curve cc = new Curve(a);
            cc.drawCurve(ldWu);
        }

        g.setColor(Color.GREEN);
        List<ScreenPoint> b = new ArrayList<>();
        for (RealPoint r : resultPoints) {
            ScreenPoint s = sc.r2s(r);
            b.add(s);
            g.fillOval(s.getC() - 5, s.getR() - 5,  10, 10);
        }
        if (b.size() > 1) {
            Curve cc = new Curve(b);
            cc.drawCurve(ldWu);
        }

        origG.drawImage(bi, 0, 0, null);
        g.dispose();
    }

    private static void drawLine(Graphics2D g, ScreenConverter sc, Line l) {
        ScreenPoint p1 = sc.r2s(l.getP1());
        ScreenPoint p2 = sc.r2s(l.getP2());
        g.drawLine(p1.getC(), p1.getR(), p2.getC(), p2.getR());
    }

    private static void drawLine(LineDrawer ld, ScreenConverter sc, Line l) {
        ScreenPoint p1 = sc.r2s(l.getP1());
        ScreenPoint p2 = sc.r2s(l.getP2());
        ld.drawLine(p1.getC(), p1.getR(), p2.getC(), p2.getR());
    }

    private static boolean isNear(ScreenConverter sc, RealPoint rp, ScreenPoint sp, int eps) {
        ScreenPoint p = sc.r2s(rp);
        return eps * eps > (p.getR() - sp.getR()) * (p.getR() - sp.getR()) + (p.getC() - sp.getC()) * (p.getC() - sp.getC());
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        /*ScreenPoint s = new ScreenPoint(e.getX(), e.getY());
        RealPoint r = sc.s2r(s);
        if (SwingUtilities.isLeftMouseButton(e)) {
            startPoints.add(r);
        } else if (SwingUtilities.isRightMouseButton(e)) {
            resultPoints.add(r);
        } else if (SwingUtilities.isMiddleMouseButton(e)) {
            deletePoint(s);
        }

        repaint();*/
    }

    private void deletePoint(ScreenPoint s) {
        deletePoint(startPoints, s);
        deletePoint(resultPoints, s);
        //startPoints.removeIf(r -> isNear(sc, r, s, 10));
        //resultPoints.removeIf(r -> isNear(sc, r, s, 10));
    }

    private void deletePoint(List<RealPoint> l, ScreenPoint s) {
        l.removeIf(r -> isNear(sc, r, s, 10));
    }
    private ScreenPoint prevPoint = null;

    @Override
    public void mousePressed(MouseEvent e) {
        ScreenPoint s = new ScreenPoint(e.getX(), e.getY());
        RealPoint r = sc.s2r(s);
        if (SwingUtilities.isLeftMouseButton(e)) {
            if (e.isControlDown())
                deletePoint(startPoints, s);
            else
                startPoints.add(r);
        } else if (SwingUtilities.isRightMouseButton(e)) {
            if (e.isControlDown())
                deletePoint(resultPoints, s);
            else
                resultPoints.add(r);
        } else if (SwingUtilities.isMiddleMouseButton(e)) {
            prevPoint = new ScreenPoint(e.getX(), e.getY());
        }

        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (SwingUtilities.isMiddleMouseButton(e)) {
            prevPoint = null;
        }

        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (SwingUtilities.isMiddleMouseButton(e)) {
            ScreenPoint curPoint = new ScreenPoint(e.getX(), e.getY());
            RealPoint p1 = sc.s2r(curPoint);
            RealPoint p2 = sc.s2r(prevPoint);
            RealPoint delta = p2.minus(p1);
            sc.moveCorner(delta);
            prevPoint = curPoint;
        }

        repaint();
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
