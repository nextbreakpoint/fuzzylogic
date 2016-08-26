package com.nextbreakpoint.fuzzylogic.playground;

import java.awt.geom.Point2D;

public class DummyGraphSource extends GraphSource {
    public DummyGraphSource(int frames) {
        super(frames);
        double x = 0;
        double y = 0;
        for (int i = 0; i < frames; i++) {
            y = Math.random();
            x = i;
            addPoint(new Point2D.Double(x, y));
        }
    }
}
