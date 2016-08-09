package com.nextbreakpoint.fuzzylogic;

public class FuzzyDomain {
    private double min;
    private double max;

    private FuzzyDomain(double min, double max) {
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

    public static FuzzyDomain of(double min, double max) {
        return new FuzzyDomain(min, max);
    }

    public static FuzzyDomain merge(FuzzyDomain left, FuzzyDomain right) {
        return FuzzyDomain.of(Math.min(left.min(), right.min()), Math.max(left.max(), right.max()));
    }
}
