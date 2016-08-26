package com.nextbreakpoint.fuzzylogic;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;

public class FuzzyInferenceTest {
	private static final double PRECISION = 0.001;

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void should_throw_exception_when_set_is_null() {
		exception.expect(NullPointerException.class);
		FuzzyInference.of(null);
	}

	@Test
	public void should_return_number_of_variables_is_one_when_number_of_sets_is_one() {
		assertEquals(1, FuzzyInference.of(FuzzyVariable.of("test", FuzzyDomain.of(0, 1))).numberOfVariables());
	}

	@Test
	public void should_return_number_of_sets_is_two_when_number_of_sets_is_two() {
		assertEquals(2, FuzzyInference.of(FuzzyVariable.of("test1", FuzzyDomain.of(0, 1)), FuzzyVariable.of("test2", FuzzyDomain.of(0, 1))).numberOfVariables());
	}

	@Test
	public void should_return_name() {
		assertEquals("test", FuzzyInference.of(FuzzyVariable.of("test", FuzzyDomain.of(0, 1))).apply(FuzzyValue.of(0.3))[0].name());
	}

	@Test
	public void should_return_set_when_value_is_not_limiting_set() {
		assertEquals(0.3, FuzzyInference.of(FuzzyVariable.of("test", FuzzyDomain.of(0, 1))).apply(FuzzyValue.of(0.3))[0].membership().apply(0.5).get(), PRECISION);
	}

	@Test
	public void should_return_limit_set_when_value_is_limiting_set() {
		assertEquals(0.8, FuzzyInference.of(FuzzyVariable.of("test", FuzzyDomain.of(0, 1))).apply(FuzzyValue.of(0.8))[0].membership().apply(0.5).get(), PRECISION);
	}
}
