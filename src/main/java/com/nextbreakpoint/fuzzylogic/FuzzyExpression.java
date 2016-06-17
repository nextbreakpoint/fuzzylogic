package com.nextbreakpoint.fuzzylogic;

import java.util.Objects;

public abstract class FuzzyExpression implements FuzzySet {
	protected final FuzzySet[] sets;
	
	private FuzzyExpression(FuzzySet[] sets) {
		Objects.requireNonNull(sets);
		this.sets = sets;
	}

	public static FuzzyExpression of(FuzzySet set) {
		return new FuzzyExpressionSingle(set);
	}
	
	public static FuzzyExpression and(FuzzySet setA, FuzzySet setB, FuzzySet... otherSets) {
		FuzzySet[] sets = new FuzzySet[otherSets.length + 2];
		sets[0] = setA;
		sets[1] = setB;
		System.arraycopy(otherSets, 0, sets, 2, otherSets.length);
		return new FuzzyExpressionAnd(sets);
	}

	public static FuzzyExpression or(FuzzySet setA, FuzzySet setB, FuzzySet... otherSets) {
		FuzzySet[] sets = new FuzzySet[otherSets.length + 2];
		sets[0] = setA;
		sets[1] = setB;
		System.arraycopy(otherSets, 0, sets, 2, otherSets.length);
		return new FuzzyExpressionOr(sets);
	}

	private static class FuzzyExpressionSingle extends FuzzyExpression {
		private FuzzyExpressionSingle(FuzzySet function) {
			super(new FuzzySet[] { function });
		}

		@Override
		public FuzzyValue apply(double value) {
			return sets[0].apply(value);
		}
	}

	private static class FuzzyExpressionAnd extends FuzzyExpression {
		private FuzzyExpressionAnd(FuzzySet[] functions) {
			super(functions);
		}

		@Override
		public FuzzyValue apply(double value) {
			if (sets.length == 0) {
				return FuzzyValue.of(0);
			} else {
				double result = 1;
				for (FuzzySet function : sets) {
					result *= function.apply(value).get();
				}
				return FuzzyValue.of(result);
			}
		}
	}

	private static class FuzzyExpressionOr extends FuzzyExpression {
		private FuzzyExpressionOr(FuzzySet[] functions) {
			super(functions);
		}

		@Override
		public FuzzyValue apply(double value) {
			if (sets.length == 0) {
				return FuzzyValue.of(1);
			} else {
				double result = 1;
				for (FuzzySet function : sets) {
					result *= (1 - function.apply(value).get());
				}
				return FuzzyValue.of(1 - result);
			}
		}
	}
}
