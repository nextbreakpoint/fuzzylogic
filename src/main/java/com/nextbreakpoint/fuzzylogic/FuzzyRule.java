package com.nextbreakpoint.fuzzylogic;

import java.util.Objects;

public class FuzzyRule {
	private final FuzzyExpression when;
	private final FuzzyInference then;
	
	private FuzzyRule(FuzzyExpression when, FuzzyInference then) {
		Objects.requireNonNull(when);
		Objects.requireNonNull(then);
		this.when = when;
		this.then = then;
	}
	
	public FuzzySet[] evaluate(double value) {
		return then.apply(when.apply(value));
	}
	
	public static FuzzyRule of(FuzzyExpression when, FuzzyInference then) {
		return new FuzzyRule(when, then);
	}
}
