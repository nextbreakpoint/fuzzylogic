package com.nextbreakpoint.fuzzylogic;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

public abstract class FuzzyPredicate implements FuzzyExpression {
	protected final FuzzyExpression[] expressions;

	private FuzzyPredicate(FuzzyExpression[] expressions) {
		Objects.requireNonNull(expressions);
		if (Arrays.stream(expressions).anyMatch(exp -> exp == null)) {
			throw new NullPointerException("Expression can't be null");
		}
		this.expressions = expressions;
	}

	public static FuzzyPredicate of(FuzzyVariable variable) {
		return new SimplePredicate(variable);
	}
	
	public static FuzzyPredicate and(FuzzyExpression expA, FuzzyExpression expB, FuzzyExpression... otherExps) {
		FuzzyExpression[] exps = new FuzzyExpression[otherExps.length + 2];
		exps[0] = expA;
		exps[1] = expB;
		System.arraycopy(otherExps, 0, exps, 2, otherExps.length);
		return new AndPredicate(exps);
	}

	public static FuzzyPredicate or(FuzzyExpression expA, FuzzyExpression expB, FuzzyExpression... otherExps) {
		FuzzyExpression[] exps = new FuzzyExpression[otherExps.length + 2];
		exps[0] = expA;
		exps[1] = expB;
		System.arraycopy(otherExps, 0, exps, 2, otherExps.length);
		return new OrPredicate(exps);
	}

	private static class SimplePredicate extends FuzzyPredicate {
		private SimplePredicate(FuzzyVariable variable) {
			super(new FuzzyExpression[] { variable });
		}

		@Override
		public FuzzyValue evaluate(Map<String, Double> inputs) {
			return expressions[0].evaluate(inputs);
		}
	}

	private static class AndPredicate extends FuzzyPredicate {
		private AndPredicate(FuzzyExpression[] exps) {
			super(exps);
		}

		@Override
		public FuzzyValue evaluate(Map<String, Double> inputs) {
			if (expressions.length == 0) {
				return FuzzyValue.of(0);
			} else {
				double result = 1;
				for (int i = 0; i < expressions.length; i++) {
					result *= expressions[i].evaluate(inputs).get();
				}
				return FuzzyValue.of(result);
			}
		}
	}

	private static class OrPredicate extends FuzzyPredicate {
		private OrPredicate(FuzzyExpression[] exps) {
			super(exps);
		}

		@Override
		public FuzzyValue evaluate(Map<String, Double> inputs) {
			if (expressions.length == 0) {
				return FuzzyValue.of(1);
			} else {
				double result = 1;
				for (int i = 0; i < expressions.length; i++) {
					result *= 1 - expressions[i].evaluate(inputs).get();
				}
				return FuzzyValue.of(1 - result);
			}
		}
	}
}
