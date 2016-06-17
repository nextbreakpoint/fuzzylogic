package com.nextbreakpoint.fuzzylogic;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class FuzzyValueTest {
	private static final double PRECISION = 0.001;
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Test
	public void should_throw_exception_when_value_is_less_than_zero() {
		exception.expect(IllegalArgumentException.class);
		FuzzyValue.of(-0.1);
	}
	
	@Test
	public void should_throw_exception_when_value_is_greater_than_one() {
		exception.expect(IllegalArgumentException.class);
		FuzzyValue.of(1.1);
	}

	@Test
	public void should_not_throw_exception_when_value_is_equals_to_zero() {
		FuzzyValue.of(0.0);
	}

	@Test
	public void should_not_throw_exception_when_value_is_equals_to_one() {
		FuzzyValue.of(1.0);
	}

	@Test
	public void should_return_value() {
		FuzzyValue value = FuzzyValue.of(0.5);
		assertEquals(0.5, value.get(), PRECISION);
	}
}
