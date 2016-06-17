package com.nextbreakpoint.fuzzylogic;

import java.util.Map;
import java.util.Objects;

public class FuzzyVariable implements FuzzyExpression {
	protected final String name;
	protected final FuzzySet set;

	private FuzzyVariable(String name, FuzzySet set) {
		Objects.requireNonNull(name);
		Objects.requireNonNull(set);
		this.name = name;
		this.set = set;
	}

	public String name() {
		return name;
	}

	public FuzzySet set() {
		return set;
	}

	public static FuzzyVariable of(String name, FuzzySet set) {
		return new FuzzyVariable(name, set);
	}

	@Override
	public FuzzyValue evaluate(Map<String, Double> inputs) {
		return set.apply(inputs.get(name));
	}
}
