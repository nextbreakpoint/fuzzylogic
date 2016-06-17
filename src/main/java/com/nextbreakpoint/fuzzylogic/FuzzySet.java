package com.nextbreakpoint.fuzzylogic;

@FunctionalInterface
public interface FuzzySet {
	public FuzzyValue apply(double value);
	
	public default FuzzySet limit(FuzzyValue limit) {
		return value -> FuzzyValue.of(Math.min(apply(value).get(), limit.get()));
	}

	public default double centroid(double begin, double end, int steps) {
		if (steps < 2) {
			return 0;
		}
		double y = 0;
		double yNum = 0;
		double yDen = 0;
		double x = begin;
		double dx = (end - begin) / (steps - 1);
		for (int i = 0; i < steps; i++) {
			y = apply(x).get();
			yNum += y * x;
			yDen += y;
			x += dx;
		}
		return yNum / yDen;
	}

	public static FuzzySet triangle(double begin, double end) {
		return value -> FuzzyValue.of(value < begin ? 0 : value < begin + ((end - begin) / 2) ? (value - begin) / ((end - begin) / 2) : value < end ? (end - value) / ((end - begin) / 2) : 0);
	}

	public static FuzzySet trapezoid(double begin, double end, double delta) {
		return value -> FuzzyValue.of(value < begin ? 0 : value < begin + delta ? (value - begin) / delta : value < end - delta ? 1 : value < end ? (end - value) / delta : 0);
	}

	public static FuzzySet constant(double v) {
		return value -> FuzzyValue.of(v);
	}
}
