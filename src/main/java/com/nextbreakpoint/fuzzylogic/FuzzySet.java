package com.nextbreakpoint.fuzzylogic;

import java.util.stream.Stream;

@FunctionalInterface
public interface FuzzySet {
	public FuzzyValue apply(double value);
	
	public default FuzzySet limit(FuzzyValue limit) {
		return value -> FuzzyValue.of(Math.min(apply(value).get(), limit.get()));
	}

	public default double centroid(double begin, double end, int steps) {
		double y = 0;
		double z = 0;
		double yNum = 0;
		double yDen = 0;
		double x = begin;
		double dx = (end - begin) / steps;
		for (int i = 0; i < steps; i++) {
			y = apply(x).get();
			z = x * dx;
			yNum += y * z;
			yDen += z;
			x += dx;
		}
		return yNum / yDen;
	}

	public static FuzzySet max(FuzzySet... sets) {
		return value -> FuzzyValue.of(Stream.of(sets).mapToDouble(set -> set.apply(value).get()).max().orElse(0));
	}

	public static FuzzySet triangle(double begin, double end) {
		double delta = (end - begin) / 2;
		return value -> FuzzyValue.of(value < begin ? 0 : value < begin + delta ? (value - begin) / delta : value < end ? (end - value) / delta : 0);
	}

	public static FuzzySet trapezoid(double begin, double end, double delta) {
		return value -> FuzzyValue.of(value < begin ? 0 : value < begin + delta ? (value - begin) / delta : value < end - delta ? 1 : value < end ? (end - value) / delta : 0);
	}
}
