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

	public int numberOfVariables() {
		return variables.length;
	}

	public FuzzyVariable[] apply(FuzzyValue value) {
		FuzzyVariable[] outputs = new FuzzyVariable[variables.length];
		for (int i = 0; i < variables.length; i++) {
			outputs[i] = FuzzyVariable.of(variables[i].name(), variables[i].membership().limit(value));
		}
		return outputs;
	}
	
	public static FuzzyInference of(FuzzyVariable variable, FuzzyVariable... otherVariables) {
		FuzzyVariable[] variables = new FuzzyVariable[otherVariables.length + 1];
		variables[0] = variable;
		System.arraycopy(otherVariables, 0, variables, 1, otherVariables.length);
		return new FuzzyInference(variables);
	}
}
