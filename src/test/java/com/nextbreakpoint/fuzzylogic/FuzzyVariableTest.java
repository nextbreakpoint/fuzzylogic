package com.nextbreakpoint.fuzzylogic;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class FuzzyVariableTest {
	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void should_throw_exception_when_name_is_null() {
		exception.expect(NullPointerException.class);
		FuzzyVariable.of(null, FuzzyDomain.of(-2.5, 2.5));
	}

	@Test
	public void should_throw_exception_when_domain_is_null() {
		exception.expect(NullPointerException.class);
		FuzzyVariable.of("", null);
	}

	@Test
	public void should_return_name() {
		assertEquals("test", FuzzyVariable.of("test", FuzzyDomain.of(-2.5, 2.5)).name());
	}

	@Test
	public void should_return_domain() {
		FuzzyDomain fuzzyDomain = FuzzyDomain.of(-2.5, 2.5);
		assertEquals(fuzzyDomain, FuzzyVariable.of("test", fuzzyDomain).domain());
	}

	@Test
	public void should_return_membership() {
		assertNotNull(FuzzyVariable.of("test", FuzzyDomain.of(-2.5, 2.5)).membership());
	}
}
