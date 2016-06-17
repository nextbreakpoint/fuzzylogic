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
	public void should_return_number_of_sets_is_one_when_number_of_sets_is_one() {
		assertEquals(1, FuzzyInference.of(FuzzyVariable.of("test", FuzzySet.constant(0.5))).numberOfSets());
	}

	@Test
	public void should_return_number_of_sets_is_two_when_number_of_sets_is_two() {
		assertEquals(2, FuzzyInference.of(FuzzyVariable.of("test1", FuzzySet.constant(0.5)), FuzzyVariable.of("test2", FuzzySet.constant(0.6))).numberOfSets());
	}

	@Test
	public void should_call_limit_on_all_sets() {
		FuzzySet fuzzySet1 = mock(FuzzySet.class);
		FuzzySet fuzzySet2 = mock(FuzzySet.class);
		FuzzyVariable fuzzyVar1 = mock(FuzzyVariable.class);
		FuzzyVariable fuzzyVar2 = mock(FuzzyVariable.class);
		when(fuzzyVar1.set()).thenReturn(fuzzySet1);
		when(fuzzyVar2.set()).thenReturn(fuzzySet2);
		FuzzyInference.of(fuzzyVar1, fuzzyVar2).apply(FuzzyValue.of(0.5));
		verify(fuzzySet1, times(1)).limit(FuzzyValue.of(0.5));
		verify(fuzzySet2, times(1)).limit(FuzzyValue.of(0.5));
	}

	@Test
	public void should_return_set_when_value_is_not_limiting_set() {
		assertEquals(0.3, FuzzyInference.of(FuzzyVariable.of("test", FuzzySet.constant(0.5))).apply(FuzzyValue.of(0.3))[0].apply(0).get(), PRECISION);
	}

	@Test
	public void should_return_limit_set_when_value_is_limiting_set() {
		assertEquals(0.5, FuzzyInference.of(FuzzyVariable.of("test", FuzzySet.constant(0.5))).apply(FuzzyValue.of(0.8))[0].apply(0).get(), PRECISION);
	}
}