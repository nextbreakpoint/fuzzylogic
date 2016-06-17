package com.nextbreakpoint.fuzzylogic;

import java.util.Map;
import java.util.Objects;

public class FuzzyVariable implements FuzzyExpression {
	protected final String name;
	protected final FuzzyMembership membership;

	private FuzzyVariable(String name, FuzzyMembership membership) {
		Objects.requireNonNull(name);
		Objects.requireNonNull(membership);
		this.name = name;
		this.membership = membership;
	}

	public String name() {
		return name;
	}

	public FuzzyMembership membership() {
		return membership;
	}

	public static FuzzyVariable of(String name, FuzzyMembership membership) {
		return new FuzzyVariable(name, membership);
	}

	@Override
	public FuzzyValue evaluate(Map<String, Double> inputs) {
		return membership.apply(inputs.get(name));
	}
}
