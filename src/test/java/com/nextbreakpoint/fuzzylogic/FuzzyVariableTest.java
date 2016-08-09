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
		FuzzyVariable.of(null, FuzzyDomain.of(-2.5, 2.5), null);
	}

	@Test
	public void should_throw_exception_when_domain_is_null() {
		exception.expect(NullPointerException.class);
		FuzzyVariable.of("", null, FuzzyMembership.constant(0));
	}

	@Test
	public void should_throw_exception_when_membership_is_null() {
		exception.expect(NullPointerException.class);
		FuzzyVariable.of("", FuzzyDomain.of(-2.5, 2.5), null);
	}

	@Test
	public void should_return_name() {
		assertEquals("test", FuzzyVariable.of("test", FuzzyDomain.of(-2.5, 2.5), FuzzyMembership.constant(0)).name());
	}

	@Test
	public void should_return_domain() {
		FuzzyDomain fuzzyDomain = FuzzyDomain.of(-2.5, 2.5);
		assertEquals(fuzzyDomain, FuzzyVariable.of("test", fuzzyDomain, FuzzyMembership.constant(0)).domain());
	}

	@Test
	public void should_return_membership() {
		FuzzyMembership fuzzyMembership = FuzzyMembership.constant(0);
		assertEquals(fuzzyMembership, FuzzyVariable.of("test", FuzzyDomain.of(-2.5, 2.5), fuzzyMembership).membership());
	}
}
