package com.nextbreakpoint.fuzzylogic.playground;

import com.nextbreakpoint.fuzzylogic.FuzzyRange;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class GraphSource {
    private List<Point2D.Double> points = new ArrayList<>();
    private double origin = 0;
    private String name = "";
    private int frames = 0;
    private FuzzyRange range;

    public GraphSource(String name, int frames, FuzzyRange range) {
        this.name = name;
        this.frames = frames;
        this.range = range;
    }

    public List<Point2D.Double> points() {
        return points;
    }

    public String getName() {
        return name;
    }

    public double getOrigin() {
        return origin;
    }

    protected void addPoint(Point2D.Double point) {
        if (points.size() >= frames) {
            points.remove(0);
            origin = points.get(0).getX();
        }
        points.add(point);
    }

    public void update(int time, double value) {
        addPoint(new Point2D.Double(time, (value - range.min()) / (range.max() - range.min())));
    }
}
