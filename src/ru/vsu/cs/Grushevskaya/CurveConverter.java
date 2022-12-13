package ru.vsu.cs.Grushevskaya;

import ru.vsu.cs.Grushevskaya.screenWork.ScreenPoint;

import java.util.ArrayList;
import java.util.Random;

public class CurveConverter {
    ArrayList<ScreenPoint> departureCurve;
    ArrayList<ScreenPoint> resultingCurve;

    public CurveConverter(ArrayList<ScreenPoint> startPoints, ArrayList<ScreenPoint> resultPoints) {
        convertAmountOfPoints(startPoints, resultPoints);
    }

    public void convertAmountOfPoints(ArrayList<ScreenPoint> departureCurve, ArrayList<ScreenPoint> resultingCurve) {
        if (departureCurve.size() > resultingCurve.size()) {
            resultingCurve = addPoints(resultingCurve, departureCurve.size());
        }
        else if (resultingCurve.size() > departureCurve.size()) {
            departureCurve = addPoints(departureCurve, resultingCurve.size());
        }

        this.departureCurve = departureCurve;
        this.resultingCurve = resultingCurve;
    }

    public ArrayList<ScreenPoint> addPoints(ArrayList<ScreenPoint> resultingPoints, int sizeDep) {
        int sizeRez = resultingPoints.size();
        Random rndIdx = new Random();
        for (int i = 0; i < sizeDep - sizeRez; i++) {
            int index = rndIdx.nextInt(resultingPoints.size() - 1) + 1;
            ScreenPoint newPoint = resultingPoints.get(index);
            resultingPoints.add(index, newPoint);
        }
        return resultingPoints;
    }

    public Curve middle(double t) {
        ArrayList<ScreenPoint> points = new ArrayList<>();
        for (int i = 0; i < departureCurve.size(); i++) {
            ScreenPoint newPointA = departureCurve.get(i);
            ScreenPoint newPointB = resultingCurve.get(i);
            double newX = (newPointB.getC() - newPointA.getC()) * t + newPointA.getC();
            double newY = (newPointB.getR() - newPointA.getR()) * t + newPointA.getR();
            ScreenPoint newPoint = new ScreenPoint((int) newX, (int) newY);
            points.add(newPoint);
        }
        return new Curve(points);
    }
}
