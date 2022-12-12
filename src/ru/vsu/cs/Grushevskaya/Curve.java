package ru.vsu.cs.Grushevskaya;

import ru.vsu.cs.Grushevskaya.lineDrawers.LineDrawer;
import ru.vsu.cs.Grushevskaya.lineDrawers.PixelDrawer;
import ru.vsu.cs.Grushevskaya.screenWork.RealPoint;
import ru.vsu.cs.Grushevskaya.screenWork.ScreenPoint;

import java.util.ArrayList;
import java.util.List;

public class Curve {
    private List<ScreenPoint> points;

    public Curve() {
    }

    public Curve(List<ScreenPoint> points) {
        this.points = points;
    }

    public List<ScreenPoint> getPoints() {
        return points;
    }

    public void setPoints(List<ScreenPoint> points) {
        this.points = points;
    }

    public void drawCurve(LineDrawer ld) {
        List<RealPoint> currPoints;
        List<RealPoint> decreasedPoints;
        List<ScreenPoint> resPoints = new ArrayList<>();
        for (int i = 0; i <= 1000; i++) {
            decreasedPoints = new ArrayList<>();
            for (ScreenPoint s : points) {
                decreasedPoints.add(new RealPoint(s.getC(), s.getR()));
            }
            while (decreasedPoints.size() > 1) {
                currPoints = new ArrayList<>();
                for (int j = 0; j < decreasedPoints.size() - 1; j++) {
                    RealPoint newPointA = decreasedPoints.get(j);
                    RealPoint newPointB = decreasedPoints.get(j + 1);
                    double newX = (newPointB.getX() - newPointA.getX()) * i * 0.001 + newPointA.getX();
                    double newY = (newPointB.getY() - newPointA.getY()) * i * 0.001 + newPointA.getY();
                    RealPoint newPoint = new RealPoint(newX, newY);
                    currPoints.add(newPoint);
                }
                decreasedPoints = currPoints;
            }
            resPoints.add(new ScreenPoint((int) decreasedPoints.get(0).getX(), (int) decreasedPoints.get(0).getY()));
        }
        for (int i = 0; i < resPoints.size() - 1; i++) {
            ld.drawLine(resPoints.get(i).getC(), resPoints.get(i).getR(), resPoints.get(i+1).getC(), resPoints.get(i+1).getR());

        }
    }

//    public Curve middlePoints() {
//        ArrayList<ScreenPoint> newPoints = new ArrayList<>();
//        for (int i = 0; i < points.size() - 1; i++) {
//            ScreenPoint newPointA = points.get(i);
//            ScreenPoint newPointB = points.get(i + 1);
//            int newX = (int) ((newPointB.getC() - newPointA.getC()) * t + newPointA.getC());
//            int newY = (int) ((newPointB.getR() - newPointA.getR()) * t + newPointA.getR());
//            ScreenPoint newPoint = new ScreenPoint(newX, newY);
//            newPoints.add(newPoint);
//        }
//        return new Curve(newPoints);
//    }
}
