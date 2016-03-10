package com.nextbreakpoint.fuzzylogic;

@FunctionalInterface
public interface FuzzySetFunction {
	public FuzzyValue apply(double value);
}
