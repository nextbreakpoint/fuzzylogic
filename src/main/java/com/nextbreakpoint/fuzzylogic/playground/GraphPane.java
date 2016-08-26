package com.nextbreakpoint.fuzzylogic.playground;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class GraphPane extends Canvas {
    private List<GraphSource> sources;
    private AffineTransform transform;
    private int frames;
    private int duration;
    private TimeUnit unit;

    public GraphPane(List<GraphSource> sources, int frames, int duration, TimeUnit unit) {
        this.frames = frames;
        this.duration = duration;
        this.unit = unit;
        Objects.requireNonNull(sources);
        this.sources = sources;
        widthProperty().addListener(v -> redraw());
        heightProperty().addListener(v -> redraw());
    }

    public void redraw() {
        transform = AffineTransform.getScaleInstance(getWidth() / (frames - 1), getHeight());
        GraphicsContext g2d = getGraphicsContext2D();
        g2d.setFill(Color.GRAY);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        sources.stream().forEach(source -> drawSource(source));
        g2d.setStroke(Color.YELLOW);
        g2d.setLineWidth(2);
        g2d.stroke();
    }

    private void drawSource(GraphSource source) {
        source.points().stream().findFirst().map(point -> convertPoint(point)).ifPresent(point -> getGraphicsContext2D().moveTo(point.getX(), point.getY()));
        source.points().stream().map(point -> convertPoint(point)).forEach(point -> getGraphicsContext2D().lineTo(point.getX(), point.getY()));
    }

    private Point2D convertPoint(Point2D point) {
        return transform.transform(point, new Point2D.Double());
    }
}
