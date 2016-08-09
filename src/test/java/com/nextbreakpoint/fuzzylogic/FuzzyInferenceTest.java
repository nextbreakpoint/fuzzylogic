package com.nextbreakpoint.fuzzylogic;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

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
		assertEquals(1, FuzzyInference.of(FuzzyVariable.of("test", FuzzyDomain.of(0, 1), FuzzyMembership.constant(0.5))).numberOfVariables());
	}

	@Test
	public void should_return_number_of_sets_is_two_when_number_of_sets_is_two() {
		assertEquals(2, FuzzyInference.of(FuzzyVariable.of("test1", FuzzyDomain.of(0, 1), FuzzyMembership.constant(0.5)), FuzzyVariable.of("test2", FuzzyDomain.of(0, 1), FuzzyMembership.constant(0.6))).numberOfVariables());
	}

	@Test
	public void should_call_limit_on_all_sets() {
		FuzzyMembership fuzzyMembership1 = mock(FuzzyMembership.class);
		FuzzyMembership fuzzyMembership2 = mock(FuzzyMembership.class);
		FuzzyVariable fuzzyVar1 = mock(FuzzyVariable.class);
		FuzzyVariable fuzzyVar2 = mock(FuzzyVariable.class);
		when(fuzzyVar1.name()).thenReturn("test");
		when(fuzzyVar2.name()).thenReturn("test");
		when(fuzzyVar1.domain()).thenReturn(FuzzyDomain.of(0, 1));
		when(fuzzyVar2.domain()).thenReturn(FuzzyDomain.of(0, 1));
		when(fuzzyVar1.membership()).thenReturn(fuzzyMembership1);
		when(fuzzyVar2.membership()).thenReturn(fuzzyMembership2);
		when(fuzzyVar1.membership().limit(any())).thenReturn(fuzzyMembership1);
		when(fuzzyVar2.membership().limit(any())).thenReturn(fuzzyMembership2);
		FuzzyInference.of(fuzzyVar1, fuzzyVar2).apply(FuzzyValue.of(0.5));
		verify(fuzzyMembership1, times(1)).limit(FuzzyValue.of(0.5));
		verify(fuzzyMembership2, times(1)).limit(FuzzyValue.of(0.5));
	}

	@Test
	public void should_return_name() {
		assertEquals("test", FuzzyInference.of(FuzzyVariable.of("test", FuzzyDomain.of(0, 1), FuzzyMembership.constant(0.5))).apply(FuzzyValue.of(0.3))[0].name());
	}

	@Test
	public void should_return_set_when_value_is_not_limiting_set() {
		assertEquals(0.3, FuzzyInference.of(FuzzyVariable.of("test", FuzzyDomain.of(0, 1), FuzzyMembership.constant(0.5))).apply(FuzzyValue.of(0.3))[0].membership().apply(0).get(), PRECISION);
	}

	@Test
	public void should_return_limit_set_when_value_is_limiting_set() {
		assertEquals(0.5, FuzzyInference.of(FuzzyVariable.of("test", FuzzyDomain.of(0, 1), FuzzyMembership.constant(0.5))).apply(FuzzyValue.of(0.8))[0].membership().apply(0).get(), PRECISION);
	}
}
