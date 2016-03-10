package com.nextbreakpoint.fuzzylogic;

import org.junit.Assert;
import org.junit.Test;

public class FuzzySetFunctionTest {
	@Test
	public void fuzzySet_shouldReturn0_whenSetIsConstant0() {
		FuzzySetFunction fuzzySet = (value) -> FuzzyValue.of(0);
		FuzzyValue value = fuzzySet.apply(5);
		Assert.assertEquals(value.get(), 0.0, 0.001);
	}

	@Test
	public void fuzzySet_shouldReturn1_whenSetIsConstant1() {
		FuzzySetFunction fuzzySet = (value) -> FuzzyValue.of(1);
		FuzzyValue value = fuzzySet.apply(5);
		Assert.assertEquals(value.get(), 1.0, 0.001);
	}
}
