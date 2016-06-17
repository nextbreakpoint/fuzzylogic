package com.nextbreakpoint.fuzzylogic;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class FuzzyOutputTest {
	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void should_throw_exception_when_output_is_null() {
		exception.expect(NullPointerException.class);
		FuzzyOutput.of(null);
	}

	@Test
	public void should_return_number_of_sets_is_one_when_number_of_outputs_is_one() {
		assertEquals(1, FuzzyOutput.of(FuzzySet.constant(0.5)).numberOfSets());
	}

	@Test
	public void should_return_number_of_sets_is_two_when_number_of_outputs_is_two() {
		assertEquals(2, FuzzyOutput.of(FuzzySet.constant(0.5), FuzzySet.constant(0.6)).numberOfSets());
	}
}
