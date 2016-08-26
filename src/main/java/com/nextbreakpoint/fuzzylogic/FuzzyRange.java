package com.nextbreakpoint.fuzzylogic;

public class FuzzyRange {
    private double min;
    private double max;

    private FuzzyRange(double min, double max) {
        this.min = min;
        this.max = max;
    }

    public double min() {
        return min;
    }

    public double max() {
        return max;
    }

    public boolean contains(double value) {
        return value < min ? false : value > max ? false : true;
    }

    public static FuzzyRange of(double min, double max) {
        return new FuzzyRange(min, max);
    }

    public static FuzzyRange merge(FuzzyRange left, FuzzyRange right) {
        return FuzzyRange.of(Math.min(left.min(), right.min()), Math.max(left.max(), right.max()));
    }

    public FuzzyRange translate(double v) {
        return new FuzzyRange(min + v, max + v);
    }

    public FuzzyRange scale(double v) {
        return new FuzzyRange(min * v, max * v);
    }

    public double center() {
        return (max + min) / 2;
    }
}
