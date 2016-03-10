package com.nextbreakpoint.fuzzylogic;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;

public class FuzzyRuleTest {
	private static final double PRECISION = 0.001;
	
	@Test
	public void fuzzyRule_shouldCallApplyOfSingleFuzzySet() {
		FuzzySetFunction fuzzySet = mock(FuzzySetFunction.class);
		when(fuzzySet.apply(any(Double.class))).thenReturn(FuzzyValue.of(0.5));
		FuzzyRule rule = FuzzyRule.of(fuzzySet);
		rule.apply(5);
		verify(fuzzySet, times(1)).apply(any(Double.class));
	}
	
	@Test
	public void fuzzyRule_shouldCallApplyOfMultipleFuzzySet() {
		FuzzySetFunction fuzzySet1 = mock(FuzzySetFunction.class);
		FuzzySetFunction fuzzySet2 = mock(FuzzySetFunction.class);
		when(fuzzySet1.apply(any(Double.class))).thenReturn(FuzzyValue.of(0.5));
		when(fuzzySet2.apply(any(Double.class))).thenReturn(FuzzyValue.of(0.2));
		FuzzyRule rule = FuzzyRule.of(fuzzySet1, fuzzySet2);
		rule.apply(5);
		verify(fuzzySet1, times(1)).apply(any(Double.class));
		verify(fuzzySet2, times(1)).apply(any(Double.class));
	}
	
	@Test
	public void fuzzyRule_shouldReturnExpectedValue() {
		FuzzySetFunction fuzzySet1 = mock(FuzzySetFunction.class);
		FuzzySetFunction fuzzySet2 = mock(FuzzySetFunction.class);
		when(fuzzySet1.apply(any(Double.class))).thenReturn(FuzzyValue.of(0.5));
		when(fuzzySet2.apply(any(Double.class))).thenReturn(FuzzyValue.of(0.2));
		FuzzyRule rule = FuzzyRule.of(fuzzySet1, fuzzySet2);
		FuzzyValue result = rule.apply(5);
		assertEquals(0.1, result.get(), PRECISION);
	}
}
