package com.nextbreakpoint.fuzzylogic;

import java.util.Objects;

public class FuzzyRule {
	private final FuzzySetFunction[] functions;
	
	private FuzzyRule(FuzzySetFunction[] functions) {
		Objects.requireNonNull(functions);
		this.functions = functions;
	}

	public FuzzyValue apply(double value) {
		if (functions.length == 0) {
			return FuzzyValue.of(0);
		} else {
			double result = 1;
			for (FuzzySetFunction function : functions) {
				result *= function.apply(value).get();
			}
			return FuzzyValue.of(result);
		}
	}
	
	public static FuzzyRule of(FuzzySetFunction set, FuzzySetFunction... otherSets) {
		FuzzySetFunction[] sets = new FuzzySetFunction[otherSets.length + 1];
		sets[0] = set;
		System.arraycopy(otherSets, 0, sets, 1, otherSets.length);
		return new FuzzyRule(sets);
	}
}
