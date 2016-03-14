package com.nextbreakpoint.fuzzylogic;

@FunctionalInterface
public interface FuzzySet {
	public FuzzyValue apply(double value);
}
