package ru.vsu.cs.Grushevskaya;

import java.util.List;
import java.util.Random;

public class CurveConverter {
    Curve departureCurve;
    Curve resultingCurve;

    public CurveConverter(Curve departureCurve, Curve resultingCurve) {
        convertAmountOfPoints(departureCurve, resultingCurve);
    }

    public Curve getDepartureCurve() {
        return departureCurve;
    }

    public Curve getResultingCurve() {
        return resultingCurve;
    }

    public void setDepartureCurve(Curve departureCurve) {
        this.departureCurve = departureCurve;
    }

    public void setResultingCurve(Curve resultingCurve) {
        this.resultingCurve = resultingCurve;
    }

    public void convertAmountOfPoints(Curve departureCurve, Curve resultingCurve) {
        List<RealPoint> departurePoints = departureCurve.getPoints();
        List<RealPoint> resultingPoints = resultingCurve.getPoints();

        if (departurePoints.size() > resultingPoints.size()) {
            resultingPoints = addPoints(resultingPoints, departurePoints.size());
        }
        else if (resultingPoints.size() > departurePoints.size()) {
            departurePoints = addPoints(departurePoints, resultingPoints.size());
        }
        this.setDepartureCurve(departureCurve);
        this.setResultingCurve(resultingCurve);
    }

    public List<RealPoint> addPoints(List<RealPoint> resultingPoints, int sizeDep) {
        int sizeRez = resultingPoints.size();
        Random rndIdx = new Random();
        for (int i = 0; i < sizeDep - sizeRez; i++) {
            int index = rndIdx.nextInt(resultingPoints.size() - 1) + 1;
            RealPoint newPoint = resultingPoints.get(index);
            resultingPoints.add(index, newPoint);
        }
        return resultingPoints;
    }
}
