package com.nextbreakpoint.fuzzylogic;

import java.util.Objects;

public abstract class FuzzyRule implements FuzzySet {
	protected final FuzzySet[] functions;
	
	private FuzzyRule(FuzzySet[] functions) {
		Objects.requireNonNull(functions);
		this.functions = functions;
	}

	public abstract FuzzyValue apply(double value);
	
	public static FuzzyRule of(FuzzySet set) {
		return new FuzzyRuleSingle(set);
	}
	
	public static FuzzyRule and(FuzzySet setA, FuzzySet setB, FuzzySet... otherSets) {
		FuzzySet[] sets = new FuzzySet[otherSets.length + 2];
		sets[0] = setA;
		sets[1] = setB;
		System.arraycopy(otherSets, 0, sets, 2, otherSets.length);
		return new FuzzyRuleAnd(sets);
	}

	public static FuzzyRule or(FuzzySet setA, FuzzySet setB, FuzzySet... otherSets) {
		FuzzySet[] sets = new FuzzySet[otherSets.length + 2];
		sets[0] = setA;
		sets[1] = setB;
		System.arraycopy(otherSets, 0, sets, 2, otherSets.length);
		return new FuzzyRuleOr(sets);
	}

	public static class FuzzyRuleSingle extends FuzzyRule {
		private FuzzyRuleSingle(FuzzySet function) {
			super(new FuzzySet[] { function });
		}

		@Override
		public FuzzyValue apply(double value) {
			return functions[0].apply(value);
		}
	}

	public static class FuzzyRuleAnd extends FuzzyRule {
		private FuzzyRuleAnd(FuzzySet[] functions) {
			super(functions);
		}

		@Override
		public FuzzyValue apply(double value) {
			if (functions.length == 0) {
				return FuzzyValue.of(0);
			} else {
				double result = 1;
				for (FuzzySet function : functions) {
					result *= function.apply(value).get();
				}
				return FuzzyValue.of(result);
			}
		}
	}

	public static class FuzzyRuleOr extends FuzzyRule {
		private FuzzyRuleOr(FuzzySet[] functions) {
			super(functions);
		}

		@Override
		public FuzzyValue apply(double value) {
			if (functions.length == 0) {
				return FuzzyValue.of(1);
			} else {
				double result = 0;
				for (FuzzySet function : functions) {
					result = Math.max(result, function.apply(value).get());
				}
				return FuzzyValue.of(result);
			}
		}
	}
}
