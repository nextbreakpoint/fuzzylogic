package com.nextbreakpoint.fuzzylogic;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

public abstract class FuzzyPredicate implements FuzzyExpression {
	protected final FuzzyExpression[] variables;

	private FuzzyPredicate(FuzzyExpression[] variables) {
		Objects.requireNonNull(variables);
		if (Arrays.stream(variables).anyMatch(set -> set == null)) {
			throw new NullPointerException("Variable can't be null");
		}
		this.variables = variables;
	}

	public static FuzzyPredicate of(FuzzyVariable variable) {
		return new SimplePredicate(variable);
	}
	
	public static FuzzyPredicate and(FuzzyExpression variableA, FuzzyExpression variableB, FuzzyExpression... otherVariables) {
		FuzzyExpression[] variables = new FuzzyExpression[otherVariables.length + 2];
		variables[0] = variableA;
		variables[1] = variableB;
		System.arraycopy(otherVariables, 0, variables, 2, otherVariables.length);
		return new AndPredicate(variables);
	}

	public static FuzzyPredicate or(FuzzyExpression variableA, FuzzyExpression variableB, FuzzyExpression... otherVariables) {
		FuzzyExpression[] variables = new FuzzyExpression[otherVariables.length + 2];
		variables[0] = variableA;
		variables[1] = variableB;
		System.arraycopy(otherVariables, 0, variables, 2, otherVariables.length);
		return new OrPredicate(variables);
	}

	private static class SimplePredicate extends FuzzyPredicate {
		private SimplePredicate(FuzzyVariable variable) {
			super(new FuzzyExpression[] { variable });
		}

		@Override
		public FuzzyValue evaluate(Map<String, Double> inputs) {
			return variables[0].evaluate(inputs);
		}
	}

	private static class AndPredicate extends FuzzyPredicate {
		private AndPredicate(FuzzyExpression[] variables) {
			super(variables);
		}

		@Override
		public FuzzyValue evaluate(Map<String, Double> inputs) {
			if (variables.length == 0) {
				return FuzzyValue.of(0);
			} else {
				double result = 1;
				for (int i = 0; i < variables.length; i++) {
					result *= variables[i].evaluate(inputs).get();
				}
				return FuzzyValue.of(result);
			}
		}
	}

	private static class OrPredicate extends FuzzyPredicate {
		private OrPredicate(FuzzyExpression[] variables) {
			super(variables);
		}

		@Override
		public FuzzyValue evaluate(Map<String, Double> inputs) {
			if (variables.length == 0) {
				return FuzzyValue.of(1);
			} else {
				double result = 1;
				for (int i = 0; i < variables.length; i++) {
					result *= 1 - variables[i].evaluate(inputs).get();
				}
				return FuzzyValue.of(1 - result);
			}
		}
	}
}
