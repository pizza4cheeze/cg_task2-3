package ru.vsu.cs.Grushevskaya;

import ru.vsu.cs.Grushevskaya.lineDrawers.PixelDrawer;
import ru.vsu.cs.Grushevskaya.screenWork.ScreenPoint;

import java.util.ArrayList;

public class Curve {
    private ArrayList<ScreenPoint> points;

    public Curve() {
    }

    public Curve(ArrayList<ScreenPoint> points) {
        this.points = points;
    }

    public ArrayList<ScreenPoint> getPoints() {
        return points;
    }

    public void setPoints(ArrayList<ScreenPoint> points) {
        this.points = points;
    }

    public void drawCurve(PixelDrawer pd) {
        ArrayList<ScreenPoint> currPoints = points;
        ArrayList<ScreenPoint> decreasedPoints;
        ArrayList<ScreenPoint> resPoints = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            decreasedPoints = points;
            while (decreasedPoints.size() > 1) {
                currPoints = new ArrayList<>();
                for (int j = 0; j < decreasedPoints.size() - 1; j++) {
                    ScreenPoint newPointA = decreasedPoints.get(j);
                    ScreenPoint newPointB = decreasedPoints.get(j + 1);
                    int newX = (newPointB.getC() - newPointA.getC()) * i + newPointA.getC();
                    int newY = (newPointB.getR() - newPointA.getR()) * i + newPointA.getR();
                    ScreenPoint newPoint = new ScreenPoint(newX, newY);
                    currPoints.add(newPoint);
                }
                decreasedPoints = currPoints;
            }
            resPoints.add(decreasedPoints.get(0));
        }
        for (ScreenPoint s : resPoints) {
            pd.drawPixel(s.getC(), s.getR());
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
