package com.nextbreakpoint.fuzzylogic;

import java.util.Map;
import java.util.Objects;

public class FuzzyRule {
	private final FuzzyPredicate when;
	private final FuzzyInference then;
	
	private FuzzyRule(FuzzyPredicate when, FuzzyInference then) {
		Objects.requireNonNull(when);
		Objects.requireNonNull(then);
		this.when = when;
		this.then = then;
	}
	
	public FuzzyVariable[] evaluate(Map<String, Double> inputs) {
		return then.apply(when.evaluate(inputs));
	}
	
	public static FuzzyRule of(FuzzyPredicate when, FuzzyInference then) {
		return new FuzzyRule(when, then);
	}
}
