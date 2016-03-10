package com.nextbreakpoint.fuzzylogic;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class FuzzySetFunctionTest {
	private static final double PRECISION = 0.001;

	@Test
	public void fuzzySet_shouldReturn0_whenInputValueIs5_andFuzzySetIsConstant0() {
		FuzzySetFunction fuzzySet = (value) -> FuzzyValue.of(0);
		FuzzyValue value = fuzzySet.apply(5);
		assertEquals(0.0, value.get(), PRECISION);
	}

	@Test
	public void fuzzySet_shouldReturn1_whenInputValueIs5_andFuzzySetIsConstant1() {
		FuzzySetFunction fuzzySet = (value) -> FuzzyValue.of(1);
		FuzzyValue value = fuzzySet.apply(5);
		assertEquals(1.0, value.get(), PRECISION);
	}

	@Test
	public void fuzzySet_shouldReturnDot5_whenInputValueIs5_andFuzzySetIsLinearInRange0To10() {
		FuzzySetFunction fuzzySet = (value) -> FuzzyValue.of(value < 0 ? 0 : value > 10 ? 1 : value / 10);
		FuzzyValue value = fuzzySet.apply(5);
		assertEquals(0.5, value.get(), PRECISION);
	}
}
