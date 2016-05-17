package com.nextbreakpoint.fuzzylogic;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;

public class FuzzyOutputTest {
	private static final double PRECISION = 0.001;
	
//	@Test
//	public void apply_shouldCallApplyOnSet() {
//		FuzzySet fuzzySet = mock(FuzzySet.class);
//		when(fuzzySet.apply(any(Double.class))).thenReturn(FuzzyValue.of(0.5));
//		FuzzyExpression exp = FuzzyExpression.of(fuzzySet);
//		exp.apply(5);
//		verify(fuzzySet, times(1)).apply(any(Double.class));
//	}
}
