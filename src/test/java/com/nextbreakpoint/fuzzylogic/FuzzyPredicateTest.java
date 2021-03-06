package com.nextbreakpoint.fuzzylogic;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class FuzzyPredicateTest {
	private static final double PRECISION = 0.001;

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void should_throw_exception_when_variables_is_null() {
		exception.expect(NullPointerException.class);
		FuzzyPredicate.of(null);
	}

	@Test
	public void should_return_value_when_expression_is_AND_of_triangle_sets() {
		FuzzyPredicate fuzzyPredicate = FuzzyPredicate.and(FuzzyVariable.of("test", FuzzyRange.of(-1.5, 0.5)), FuzzyVariable.of("test", FuzzyRange.of(-0.5, 1.5)));
		FuzzyValue value = fuzzyPredicate.evaluate(getInputs());
		assertEquals(0.25, value.get(), PRECISION);
	}

	@Test
	public void should_return_value_when_expression_is_OR_of_triangle_sets() {
		FuzzyPredicate fuzzyPredicate = FuzzyPredicate.or(FuzzyVariable.of("test", FuzzyRange.of(-1.5, 0.5)), FuzzyVariable.of("test", FuzzyRange.of(-0.5, 1.5)));
		FuzzyValue value = fuzzyPredicate.evaluate(getInputs());
		assertEquals(0.75, value.get(), PRECISION);
	}

	private Map<String, Double> getInputs() {
		Map<String,Double> inputs = new HashMap<>();
		inputs.put("test", 0.0);
		return inputs;
	}
}
