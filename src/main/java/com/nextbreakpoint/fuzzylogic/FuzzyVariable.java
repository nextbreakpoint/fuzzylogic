package com.nextbreakpoint.fuzzylogic;

import java.util.Map;
import java.util.Objects;

public class FuzzyVariable implements FuzzyExpression {
	protected final String name;
	protected final FuzzyMembership set;

	private FuzzyVariable(String name, FuzzyMembership set) {
		Objects.requireNonNull(name);
		Objects.requireNonNull(set);
		this.name = name;
		this.set = set;
	}

	public String name() {
		return name;
	}

	public FuzzyMembership set() {
		return set;
	}

	public static FuzzyVariable of(String name, FuzzyMembership set) {
		return new FuzzyVariable(name, set);
	}

	@Override
	public FuzzyValue evaluate(Map<String, Double> inputs) {
		return set.apply(inputs.get(name));
	}
}
