package com.nextbreakpoint.fuzzylogic.playground;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class GraphPane extends Canvas {
    private List<GraphSource> sources;
    private AffineTransform transform;
    private Color lineColor;
    private int frames;
    private int duration;
    private TimeUnit unit;
    private double value;
    private ObjectProperty<Double> valueProperty = new SimpleObjectProperty();

    public GraphPane(List<GraphSource> sources, int frames, int duration, TimeUnit unit) {
        Objects.requireNonNull(sources);
        this.sources = sources;
        this.frames = frames;
        this.duration = duration;
        this.unit = unit;
        widthProperty().addListener(v -> redraw());
        heightProperty().addListener(v -> redraw());
        onMouseClickedProperty().setValue(event -> valueProperty.setValue(1 - event.getY() / getHeight()));
        onMouseDraggedProperty().setValue(event -> valueProperty.setValue(1 - event.getY() / getHeight()));
    }

    public ObjectProperty<Double> getValueProperty() {
        return valueProperty;
    }

    public void redraw() {
        transform = AffineTransform.getScaleInstance(getWidth() / (frames - 1), getHeight());
        GraphicsContext g2d = getGraphicsContext2D();
        g2d.save();
        g2d.setFill(Color.GRAY);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        g2d.beginPath();
        g2d.setStroke(getLineColor());
        sources.stream().forEach(source -> drawSource(source));
        g2d.setLineWidth(2);
        g2d.stroke();
        g2d.restore();
    }

    private void drawSource(GraphSource source) {
        getGraphicsContext2D().strokeText(String.format("%s %.2f", source.getName(), value), 20, 20);
        source.points().stream().findFirst().map(point -> convertPoint(point, source.getOrigin())).ifPresent(point -> getGraphicsContext2D().moveTo(point.getX(), point.getY()));
        source.points().stream().map(point -> convertPoint(point, source.getOrigin())).forEach(point -> getGraphicsContext2D().lineTo(point.getX(), point.getY()));
    }

    private Point2D convertPoint(Point2D point, double origin) {
        Point2D.Double newPoint = new Point2D.Double(point.getX() - origin, 1 - point.getY());
        return transform.transform(newPoint, newPoint);
    }

    public void setLineColor(Color lineColor) {
        this.lineColor = lineColor;
    }

    public Color getLineColor() {
        return lineColor;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
