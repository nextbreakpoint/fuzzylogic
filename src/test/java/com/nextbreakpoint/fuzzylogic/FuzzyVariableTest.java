package com.nextbreakpoint.fuzzylogic;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;

public class FuzzyVariableTest {
	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void should_throw_exception_when_name_is_null() {
		exception.expect(NullPointerException.class);
		FuzzyVariable.of(null, FuzzyMembership.constant(0));
	}

	@Test
	public void should_throw_exception_when_set_is_null() {
		exception.expect(NullPointerException.class);
		FuzzyVariable.of("", null);
	}

	@Test
	public void should_return_name() {
		assertEquals("test", FuzzyVariable.of("test", FuzzyMembership.constant(0)).name());
	}

	@Test
	public void should_return_set() {
		FuzzyMembership fuzzyMembership = FuzzyMembership.constant(0);
		assertEquals(fuzzyMembership, FuzzyVariable.of("test", fuzzyMembership).membership());
	}
}
