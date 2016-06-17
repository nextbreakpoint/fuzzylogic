package com.nextbreakpoint.fuzzylogic;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;

public class FuzzyExpressionTest {
	private static final double PRECISION = 0.001;
	
	@Test
	public void should_call_apply_on_set() {
		FuzzySet fuzzySet = mock(FuzzySet.class);
		when(fuzzySet.apply(any(Double.class))).thenReturn(FuzzyValue.of(0.5));
		FuzzyExpression exp = FuzzyExpression.of(fuzzySet);
		exp.apply(5);
		verify(fuzzySet, times(1)).apply(any(Double.class));
	}
	
	@Test
	public void should_call_apply_on_all_sets_when_expression_is_AND() {
		FuzzySet fuzzySet1 = mock(FuzzySet.class);
		FuzzySet fuzzySet2 = mock(FuzzySet.class);
		when(fuzzySet1.apply(any(Double.class))).thenReturn(FuzzyValue.of(0.5));
		when(fuzzySet2.apply(any(Double.class))).thenReturn(FuzzyValue.of(0.2));
		FuzzyExpression exp = FuzzyExpression.and(fuzzySet1, fuzzySet2);
		exp.apply(5);
		verify(fuzzySet1, times(1)).apply(any(Double.class));
		verify(fuzzySet2, times(1)).apply(any(Double.class));
	}
	
	@Test
	public void should_call_apply_on_all_sets_when_expression_is_OR() {
		FuzzySet fuzzySet1 = mock(FuzzySet.class);
		FuzzySet fuzzySet2 = mock(FuzzySet.class);
		when(fuzzySet1.apply(any(Double.class))).thenReturn(FuzzyValue.of(0.5));
		when(fuzzySet2.apply(any(Double.class))).thenReturn(FuzzyValue.of(0.2));
		FuzzyExpression exp = FuzzyExpression.or(fuzzySet1, fuzzySet2);
		exp.apply(5);
		verify(fuzzySet1, times(1)).apply(any(Double.class));
		verify(fuzzySet2, times(1)).apply(any(Double.class));
	}
	
	@Test
	public void should_call_apply_on_all_rules_when_expression_is_AND() {
		FuzzyExpression apply1 = mock(FuzzyExpression.class);
		FuzzyExpression apply2 = mock(FuzzyExpression.class);
		when(apply1.apply(any(Double.class))).thenReturn(FuzzyValue.of(0.5));
		when(apply2.apply(any(Double.class))).thenReturn(FuzzyValue.of(0.2));
		FuzzyExpression exp = FuzzyExpression.and(apply1, apply2);
		exp.apply(5);
		verify(apply1, times(1)).apply(any(Double.class));
		verify(apply2, times(1)).apply(any(Double.class));
	}
	
	@Test
	public void should_call_apply_on_all_rules_when_expression_is_OR() {
		FuzzyExpression apply1 = mock(FuzzyExpression.class);
		FuzzyExpression apply2 = mock(FuzzyExpression.class);
		when(apply1.apply(any(Double.class))).thenReturn(FuzzyValue.of(0.5));
		when(apply2.apply(any(Double.class))).thenReturn(FuzzyValue.of(0.2));
		FuzzyExpression exp = FuzzyExpression.or(apply1, apply2);
		exp.apply(5);
		verify(apply1, times(1)).apply(any(Double.class));
		verify(apply2, times(1)).apply(any(Double.class));
	}
	
	@Test
	public void should_return_value_when_expression_is_AND() {
		FuzzySet fuzzySet1 = mock(FuzzySet.class);
		FuzzySet fuzzySet2 = mock(FuzzySet.class);
		when(fuzzySet1.apply(any(Double.class))).thenReturn(FuzzyValue.of(0.5));
		when(fuzzySet2.apply(any(Double.class))).thenReturn(FuzzyValue.of(0.2));
		FuzzyExpression exp = FuzzyExpression.and(fuzzySet1, fuzzySet2);
		FuzzyValue result = exp.apply(5);
		assertEquals(0.1, result.get(), PRECISION);
	}
	
	@Test
	public void should_return_value_when_expression_is_OR() {
		FuzzySet fuzzySet1 = mock(FuzzySet.class);
		FuzzySet fuzzySet2 = mock(FuzzySet.class);
		when(fuzzySet1.apply(any(Double.class))).thenReturn(FuzzyValue.of(0.5));
		when(fuzzySet2.apply(any(Double.class))).thenReturn(FuzzyValue.of(0.2));
		FuzzyExpression exp = FuzzyExpression.or(fuzzySet1, fuzzySet2);
		FuzzyValue result = exp.apply(5);
		assertEquals(0.6, result.get(), PRECISION);
	}
}
