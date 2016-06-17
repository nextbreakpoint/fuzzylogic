package com.nextbreakpoint.fuzzylogic;

import java.util.Arrays;
import java.util.Objects;

public class FuzzySet {
	private final String name;
	private final FuzzyVariable[] variables;

	private FuzzySet(String name, FuzzyVariable[] variables) {
		Objects.requireNonNull(name);
		Objects.requireNonNull(variables);
		if (Arrays.stream(variables).anyMatch(variable -> variable == null)) {
			throw new NullPointerException("Variable can't be null");
		}
		this.name = name;
		this.variables = variables;
	}

	public String name() {
		return name;
	}

	public int numberOfVariables() {
		return variables.length;
	}

	public static FuzzySet of(String name, FuzzyVariable variable, FuzzyVariable... otherVariables) {
		FuzzyVariable[] variables = new FuzzyVariable[otherVariables.length + 1];
		variables[0] = variable;
		System.arraycopy(otherVariables, 0, variables, 1, otherVariables.length);
		return new FuzzySet(name, variables);
	}
}
