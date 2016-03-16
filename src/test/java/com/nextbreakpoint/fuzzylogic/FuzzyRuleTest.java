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
		FuzzySet fuzzySet = mock(FuzzySet.class);
		when(fuzzySet.apply(any(Double.class))).thenReturn(FuzzyValue.of(0.5));
		FuzzyRule rule = FuzzyRule.of(fuzzySet);
		rule.apply(5);
		verify(fuzzySet, times(1)).apply(any(Double.class));
	}
	
	@Test
	public void fuzzyRule_shouldCallApplyOnAllSets_whenRuleIsSetsAnd() {
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
	public void fuzzyRule_shouldCallApplyOnAllSets_whenRuleIsSetsOr() {
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
	public void fuzzyRule_shouldReturnAndValue_whenRuleIsSetsAnd() {
		FuzzySet fuzzySet1 = mock(FuzzySet.class);
		FuzzySet fuzzySet2 = mock(FuzzySet.class);
		when(fuzzySet1.apply(any(Double.class))).thenReturn(FuzzyValue.of(0.5));
		when(fuzzySet2.apply(any(Double.class))).thenReturn(FuzzyValue.of(0.2));
		FuzzyRule rule = FuzzyRule.and(fuzzySet1, fuzzySet2);
		FuzzyValue result = rule.apply(5);
		assertEquals(0.1, result.get(), PRECISION);
	}
	
	@Test
	public void fuzzyRule_shouldReturnOrValue_whenRuleIsSetsOr() {
		FuzzySet fuzzySet1 = mock(FuzzySet.class);
		FuzzySet fuzzySet2 = mock(FuzzySet.class);
		when(fuzzySet1.apply(any(Double.class))).thenReturn(FuzzyValue.of(0.5));
		when(fuzzySet2.apply(any(Double.class))).thenReturn(FuzzyValue.of(0.2));
		FuzzyRule rule = FuzzyRule.or(fuzzySet1, fuzzySet2);
		FuzzyValue result = rule.apply(5);
		assertEquals(0.9, result.get(), PRECISION);
	}
	
	@Test
	public void fuzzyRule_shouldCallApplyOnAllRules_whenRuleIsRulesAnd() {
		FuzzyRule fuzzyRule1 = mock(FuzzyRule.class);
		FuzzyRule fuzzyRule2 = mock(FuzzyRule.class);
		when(fuzzyRule1.apply(any(Double.class))).thenReturn(FuzzyValue.of(0.5));
		when(fuzzyRule2.apply(any(Double.class))).thenReturn(FuzzyValue.of(0.2));
		FuzzyRule rule = FuzzyRule.and(fuzzyRule1, fuzzyRule2);
		rule.apply(5);
		verify(fuzzyRule1, times(1)).apply(any(Double.class));
		verify(fuzzyRule2, times(1)).apply(any(Double.class));
	}
	
	@Test
	public void fuzzyRule_shouldCallApplyOnAllRules_whenRuleIsRulesOr() {
		FuzzyRule fuzzyRule1 = mock(FuzzyRule.class);
		FuzzyRule fuzzyRule2 = mock(FuzzyRule.class);
		when(fuzzyRule1.apply(any(Double.class))).thenReturn(FuzzyValue.of(0.5));
		when(fuzzyRule2.apply(any(Double.class))).thenReturn(FuzzyValue.of(0.2));
		FuzzyRule rule = FuzzyRule.or(fuzzyRule1, fuzzyRule2);
		rule.apply(5);
		verify(fuzzyRule1, times(1)).apply(any(Double.class));
		verify(fuzzyRule2, times(1)).apply(any(Double.class));
	}
	
	@Test
	public void fuzzyRule_shouldReturnAndValue_whenRuleIsRulesAnd() {
		FuzzyRule fuzzyRule1 = mock(FuzzyRule.class);
		FuzzyRule fuzzyRule2 = mock(FuzzyRule.class);
		when(fuzzyRule1.apply(any(Double.class))).thenReturn(FuzzyValue.of(0.5));
		when(fuzzyRule2.apply(any(Double.class))).thenReturn(FuzzyValue.of(0.2));
		FuzzyRule rule = FuzzyRule.and(fuzzyRule1, fuzzyRule2);
		FuzzyValue result = rule.apply(5);
		assertEquals(0.1, result.get(), PRECISION);
	}
	
	@Test
	public void fuzzyRule_shouldReturnOrValue_whenRuleIsRulesOr() {
		FuzzyRule fuzzyRule1 = mock(FuzzyRule.class);
		FuzzyRule fuzzyRule2 = mock(FuzzyRule.class);
		when(fuzzyRule1.apply(any(Double.class))).thenReturn(FuzzyValue.of(0.5));
		when(fuzzyRule2.apply(any(Double.class))).thenReturn(FuzzyValue.of(0.2));
		FuzzyRule rule = FuzzyRule.or(fuzzyRule1, fuzzyRule2);
		FuzzyValue result = rule.apply(5);
		assertEquals(0.9, result.get(), PRECISION);
	}
}
