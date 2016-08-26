package com.nextbreakpoint.fuzzylogic;

import java.util.function.Function;

public class FuzzyMath {
    private FuzzyMath() {}

    public static Function<Double, Double> rectangle(double begin, double end) {
        return value -> value < begin || value > end ? 0.0 : 1.0;
    }

    public static Function<Double, Double> triangle(double begin, double end) {
        return value -> value < begin ? 0 : value < begin + ((end - begin) / 2) ? (value - begin) / ((end - begin) / 2) : value < end ? (end - value) / ((end - begin) / 2) : 0;
    }

    public static Function<Double, Double> trapezoid(double begin, double end, double delta) {
        return value -> value < begin ? 0 : value < begin + delta ? (value - begin) / delta : value < end - delta ? 1 : value < end ? (end - value) / delta : 0;
    }

    public static Function<Double, Double> line(double begin, double end) {
        return value -> value < begin ? 0 : value < end ? (value - begin) / (end - begin) : 1;
    }
}
