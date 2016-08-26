package com.nextbreakpoint.fuzzylogic.playground;

import com.nextbreakpoint.fuzzylogic.FuzzyRange;

import java.awt.geom.Point2D;

public class FuzzyGraphSource extends GraphSource {
    private String name;
    private FuzzyRange range;

    public FuzzyGraphSource(String name, FuzzyRange range, int frames) {
        super(frames);
        this.name = name;
        this.range = range;
    }

    public void update(int time, double value) {
        addPoint(new Point2D.Double(time, (value - range.min()) / (range.max() - range.min())));
    }
}
