package com.nextbreakpoint.fuzzylogic;

public class FuzzyValue {
	private final double value;

	public FuzzyValue(double value) {
		this.value = value;
		if (value < 0 || value > 1) {
			throw new IllegalArgumentException("Value must be >= 0 and <= 1. Value is " + value);
		}
	}

	public static FuzzyValue of(double value) {
		return new FuzzyValue(value);
	}
	
	public double get() {
		return value;
	}
}
