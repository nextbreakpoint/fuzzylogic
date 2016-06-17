package com.nextbreakpoint.fuzzylogic;

public final class FuzzyValue {
	private final double value;

	private FuzzyValue(double value) {
		if (value < 0 || value > 1) {
			throw new IllegalArgumentException("Value must be >= 0 and <= 1. Value is " + value);
		}
		this.value = value;
	}

	public static FuzzyValue of(double value) {
		return new FuzzyValue(value);
	}

	public double get() {
		return value;
	}

	@Override
	public String toString() {
		return "" + value + "";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		FuzzyValue that = (FuzzyValue) o;

		return Double.compare(that.value, value) == 0;

	}

	@Override
	public int hashCode() {
		long temp = Double.doubleToLongBits(value);
		return (int) (temp ^ (temp >>> 32));
	}
}
