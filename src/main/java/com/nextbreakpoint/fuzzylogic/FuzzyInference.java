package com.nextbreakpoint.fuzzylogic;

import java.util.Arrays;
import java.util.Objects;

public class FuzzyInference {
	private final FuzzyVariable[] variables;

	private FuzzyInference(FuzzyVariable[] variables) {
		Objects.requireNonNull(variables);
		if (Arrays.stream(variables).anyMatch(set -> set == null)) {
			throw new NullPointerException("Variable can't be null");
		}
		this.variables = variables;
	}

	public int numberOfSets() {
		return variables.length;
	}

	public FuzzySet[] apply(FuzzyValue value) {
		FuzzySet[] outSets = new FuzzySet[variables.length];
		for (int i = 0; i < variables.length; i++) {
			outSets[i] = variables[i].set().limit(value);
		}
		return outSets;
	}
	
	public static FuzzyInference of(FuzzyVariable set, FuzzyVariable... otherVariables) {
		FuzzyVariable[] variables = new FuzzyVariable[otherVariables.length + 1];
		variables[0] = set;
		System.arraycopy(otherVariables, 0, variables, 1, otherVariables.length);
		return new FuzzyInference(variables);
	}
}
