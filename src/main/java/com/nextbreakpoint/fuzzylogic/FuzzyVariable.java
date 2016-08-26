package com.nextbreakpoint.fuzzylogic;

import java.util.Map;
import java.util.Objects;

public class FuzzyVariable implements FuzzyExpression {
	protected final String name;
	protected final FuzzyRange domain;
	protected final FuzzyMembership membership;

	private FuzzyVariable(String name, FuzzyRange domain, FuzzyMembership membership) {
		Objects.requireNonNull(name);
		Objects.requireNonNull(domain);
		Objects.requireNonNull(membership);
		this.name = name;
		this.domain = domain;
		this.membership = membership;
	}

	public String name() {
		return name;
	}

	public FuzzyRange domain() {
		return domain;
	}

	public FuzzyMembership membership() {
		return membership;
	}

	public static FuzzyVariable of(String name, FuzzyRange domain) {
		return new FuzzyVariable(name, domain, FuzzyMembership.triangle().scale(domain.max() - domain.min()).translate(domain.center()));
	}

	public static FuzzyVariable of(String name, FuzzyRange domain, FuzzyValue limit) {
		return new FuzzyVariable(name, domain, FuzzyMembership.triangle().scale(domain.max() - domain.min()).translate(domain.center()).limit(limit));
	}

	@Override
	public FuzzyValue evaluate(Map<String, Double> inputs) {
		Double value = inputs.get(name);
		if (value != null && domain.contains(value)) {
			return membership.apply(inputs.get(name));
		}
		return FuzzyValue.of(0);
	}
}
