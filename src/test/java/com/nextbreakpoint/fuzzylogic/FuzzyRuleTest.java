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
	public void apply_shouldCallApplyOnSet() {
		FuzzySet fuzzySet = mock(FuzzySet.class);
		when(fuzzySet.apply(any(Double.class))).thenReturn(FuzzyValue.of(0.5));
		FuzzyRule rule = FuzzyRule.of(fuzzySet);
		rule.apply(5);
		verify(fuzzySet, times(1)).apply(any(Double.class));
	}
	
	@Test
	public void apply_shouldCallApplyOnAllSets_whenRuleIsAnd() {
		FuzzySet fuzzySet1 = mock(FuzzySet.class);
		FuzzySet fuzzySet2 = mock(FuzzySet.class);
		when(fuzzySet1.apply(any(Double.class))).thenReturn(FuzzyValue.of(0.5));
		when(fuzzySet2.apply(any(Double.class))).thenReturn(FuzzyValue.of(0.2));
		FuzzyRule rule = FuzzyRule.and(fuzzySet1, fuzzySet2);
		rule.apply(5);
		verify(fuzzySet1, times(1)).apply(any(Double.class));
		verify(fuzzySet2, times(1)).apply(any(Double.class));
	}
	
	@Test
	public void apply_shouldCallApplyOnAllSets_whenRuleIsOr() {
		FuzzySet fuzzySet1 = mock(FuzzySet.class);
		FuzzySet fuzzySet2 = mock(FuzzySet.class);
		when(fuzzySet1.apply(any(Double.class))).thenReturn(FuzzyValue.of(0.5));
		when(fuzzySet2.apply(any(Double.class))).thenReturn(FuzzyValue.of(0.2));
		FuzzyRule rule = FuzzyRule.or(fuzzySet1, fuzzySet2);
		rule.apply(5);
		verify(fuzzySet1, times(1)).apply(any(Double.class));
		verify(fuzzySet2, times(1)).apply(any(Double.class));
	}
	
	@Test
	public void apply_shouldCallApplyOnAllRules_whenRuleIsAnd() {
		FuzzyRule apply1 = mock(FuzzyRule.class);
		FuzzyRule apply2 = mock(FuzzyRule.class);
		when(apply1.apply(any(Double.class))).thenReturn(FuzzyValue.of(0.5));
		when(apply2.apply(any(Double.class))).thenReturn(FuzzyValue.of(0.2));
		FuzzyRule rule = FuzzyRule.and(apply1, apply2);
		rule.apply(5);
		verify(apply1, times(1)).apply(any(Double.class));
		verify(apply2, times(1)).apply(any(Double.class));
	}
	
	@Test
	public void apply_shouldCallApplyOnAllRules_whenRuleIsOr() {
		FuzzyRule apply1 = mock(FuzzyRule.class);
		FuzzyRule apply2 = mock(FuzzyRule.class);
		when(apply1.apply(any(Double.class))).thenReturn(FuzzyValue.of(0.5));
		when(apply2.apply(any(Double.class))).thenReturn(FuzzyValue.of(0.2));
		FuzzyRule rule = FuzzyRule.or(apply1, apply2);
		rule.apply(5);
		verify(apply1, times(1)).apply(any(Double.class));
		verify(apply2, times(1)).apply(any(Double.class));
	}
	
	@Test
	public void apply_shouldReturnValue_whenRuleIsAnd() {
		FuzzySet fuzzySet1 = mock(FuzzySet.class);
		FuzzySet fuzzySet2 = mock(FuzzySet.class);
		when(fuzzySet1.apply(any(Double.class))).thenReturn(FuzzyValue.of(0.5));
		when(fuzzySet2.apply(any(Double.class))).thenReturn(FuzzyValue.of(0.2));
		FuzzyRule rule = FuzzyRule.and(fuzzySet1, fuzzySet2);
		FuzzyValue result = rule.apply(5);
		assertEquals(0.1, result.get(), PRECISION);
	}
	
	@Test
	public void apply_shouldReturnValue_whenRuleIsOr() {
		FuzzySet fuzzySet1 = mock(FuzzySet.class);
		FuzzySet fuzzySet2 = mock(FuzzySet.class);
		when(fuzzySet1.apply(any(Double.class))).thenReturn(FuzzyValue.of(0.5));
		when(fuzzySet2.apply(any(Double.class))).thenReturn(FuzzyValue.of(0.2));
		FuzzyRule rule = FuzzyRule.or(fuzzySet1, fuzzySet2);
		FuzzyValue result = rule.apply(5);
		assertEquals(0.6, result.get(), PRECISION);
	}
}
