package com.nextbreakpoint.fuzzylogic.playground;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class GraphSource {
    private List<Point2D.Double> points = new ArrayList<>();
    private double origin = 0;
    private int frames = 0;

    public GraphSource(int frames) {
        this.frames = frames;
    }

    public List<Point2D.Double> points() {
        return points;
    }

    public double getOrigin() {
        return origin;
    }

    protected void addPoint(Point2D.Double point) {
        if (points.size() > frames) {
            points.remove(0);
            origin = points.get(0).getX();
        }
        points.add(point);
        points.stream().forEach(p -> p.setLocation(p.getX() - origin, p.getY()));
    }
}
