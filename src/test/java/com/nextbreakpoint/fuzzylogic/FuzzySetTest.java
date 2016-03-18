package com.nextbreakpoint.fuzzylogic;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class FuzzySetTest {
	private static final double PRECISION = 0.001;

	@Test
	public void apply_shouldReturnValue_whenSetIsConstant() {
		FuzzySet fuzzySet = (value) -> FuzzyValue.of(0.5);
		FuzzyValue value = fuzzySet.apply(5);
		assertEquals(0.5, value.get(), PRECISION);
	}

	@Test
	public void apply_shouldReturnValue_whenSetIsTriangle() {
		FuzzySet fuzzySet = FuzzySet.triangle(-1, 1);
		FuzzyValue value = fuzzySet.apply(0.5);
		assertEquals(0.5, value.get(), PRECISION);
	}

	@Test
	public void apply_shouldReturnValue_whenSetIsMaxOfTriangles() {
		FuzzySet fuzzySetA = FuzzySet.triangle(-1.5, 0.5);
		FuzzySet fuzzySetB = FuzzySet.triangle(-0.5, 1.5);
		FuzzySet fuzzySet = FuzzySet.max(fuzzySetA, fuzzySetB);
		FuzzyValue value = fuzzySet.apply(0);
		assertEquals(0.5, value.get(), PRECISION);
	}

	@Test
	public void apply_shouldReturnValue_whenSetIsMaxOfTrapezoids() {
		FuzzySet fuzzySetA = FuzzySet.trapezoid(-1.5, 0.5, 0.5);
		FuzzySet fuzzySetB = FuzzySet.trapezoid(-0.5, 1.5, 0.5);
		FuzzySet fuzzySet = FuzzySet.max(fuzzySetA, fuzzySetB);
		FuzzyValue value = fuzzySet.apply(0);
		assertEquals(1.0, value.get(), PRECISION);
	}
}
