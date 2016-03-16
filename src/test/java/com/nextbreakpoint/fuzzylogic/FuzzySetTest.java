package com.nextbreakpoint.fuzzylogic;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class FuzzySetTest {
	private static final double PRECISION = 0.001;

	@Test
	public void fuzzySet_shouldReturn0_whenInputValueIs5_andFuzzySetIsConstant0() {
		FuzzySet fuzzySet = (value) -> FuzzyValue.of(0);
		FuzzyValue value = fuzzySet.apply(5);
		assertEquals(0.0, value.get(), PRECISION);
	}

	@Test
	public void fuzzySet_shouldReturn1_whenInputValueIs5_andFuzzySetIsConstant1() {
		FuzzySet fuzzySet = (value) -> FuzzyValue.of(1);
		FuzzyValue value = fuzzySet.apply(5);
		assertEquals(1.0, value.get(), PRECISION);
	}

	@Test
	public void fuzzySet_shouldReturnDot5_whenInputValueIs5_andFuzzySetIsLinearInRange0To10() {
		FuzzySet fuzzySet = (value) -> FuzzyValue.of(value < 0 ? 0 : value > 10 ? 1 : value / 10);
		FuzzyValue value = fuzzySet.apply(5);
		assertEquals(0.5, value.get(), PRECISION);
	}

	@Test
	public void fuzzySet_shouldReturnDot5_whenInputValueIs10_andFuzzySetIsMaxOfTwoSetsLinearInRange0To20() {
		FuzzySet fuzzySetA = (value) -> FuzzyValue.of(value < 0 ? 0 : value > 20 ? 1 : value / 20);
		FuzzySet fuzzySetB = (value) -> FuzzyValue.of(value < 0 ? 0 : value > 20 ? 1 : value / 20);
		FuzzySet union = FuzzySet.max(fuzzySetA, fuzzySetB);
		FuzzyValue value = union.apply(10);
		assertEquals(0.5, value.get(), PRECISION);
	}
}
