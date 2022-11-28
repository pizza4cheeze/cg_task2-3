package ru.vsu.cs.Grushevskaya;

import ru.vsu.cs.Grushevskaya.LineDrawers.PixelDrawer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Curve {
    private List<RealPoint> points;
    private PixelDrawer pd;

    public Curve() {
    }

    public Curve(List<RealPoint> points, PixelDrawer pd) {
        this.points = points;
        this.pd = pd;
    }

    public List<RealPoint> getPoints() {
        return points;
    }

    public void setPoints(List<RealPoint> points) {
        this.points = points;
    }

    public void drawCurve() {
        List<RealPoint> currPoints = points;
        while (currPoints.size() > 2) {
            for (int i = 0; i < currPoints.size() - 1; i++) {
                RealPoint pointA = new RealPoint(currPoints.get(i).getX(), currPoints.get(i).getY());
            }
        }
    }

    public Curve middle(Curve a, Curve b, double t) {
        List<RealPoint> points = new ArrayList<>();
        for (int i = 0; i < a.points.size(); i++) {
            RealPoint newPointA = a.points.get(i);
            RealPoint newPointB = b.points.get(i);
            double newX = Math.abs(newPointA.getX() - newPointB.getX()) * t + newPointA.getX();
            double newY = Math.abs(newPointA.getY() - newPointB.getY()) * t + newPointA.getY();
            RealPoint newPoint = new RealPoint(newX, newY);
            points.add(newPoint);
        }
        return new Curve(points, pd);
    }
}
