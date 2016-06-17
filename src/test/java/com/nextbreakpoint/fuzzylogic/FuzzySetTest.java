package com.nextbreakpoint.fuzzylogic;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;

public class FuzzySetTest {
	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void should_throw_exception_when_name_is_null() {
		exception.expect(NullPointerException.class);
		FuzzySet.of(null, FuzzyVariable.of("A", FuzzyMembership.constant(0)));
	}

	@Test
	public void should_throw_exception_when_variable_is_null() {
		exception.expect(NullPointerException.class);
		FuzzySet.of("test", null);
	}

	@Test
	public void should_return_number_of_variables_is_one_when_number_of_variables_is_one() {
		assertEquals(1, FuzzySet.of("test", FuzzyVariable.of("A", FuzzyMembership.constant(0.5))).numberOfVariables());
	}

	@Test
	public void should_return_number_of_variables_is_two_when_number_of_variables_is_two() {
		assertEquals(2, FuzzySet.of("test", FuzzyVariable.of("A", FuzzyMembership.constant(0.5)), FuzzyVariable.of("B", FuzzyMembership.constant(0.6))).numberOfVariables());
	}
}
