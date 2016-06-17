package com.nextbreakpoint.fuzzylogic;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class FuzzySetTest {
	private static final double PRECISION = 0.001;

	@Test
	public void should_return_value_when_set_is_constant() {
		FuzzySet fuzzySet = (value) -> FuzzyValue.of(0.5);
		FuzzyValue value = fuzzySet.apply(5);
		assertEquals(0.5, value.get(), PRECISION);
	}

	@Test
	public void should_return_value_when_set_is_triangle() {
		FuzzySet fuzzySet = FuzzySet.triangle(-1, 1);
		FuzzyValue value = fuzzySet.apply(0.5);
		assertEquals(0.5, value.get(), PRECISION);
	}

	@Test
	public void should_return_value_when_set_is_trapezoid() {
		FuzzySet fuzzySet = FuzzySet.trapezoid(-1.0, 1.0, 0.5);
		FuzzyValue value = fuzzySet.apply(0.75);
		assertEquals(0.5, value.get(), PRECISION);
	}

	@Test
	public void should_return_centroid_when_sets_are_triangles() {
		FuzzySet fuzzySetA = FuzzySet.triangle(-1.5 + 0.3, 0.5 + 0.3);
		FuzzySet fuzzySetB = FuzzySet.triangle(-0.5 + 0.3, 1.5 + 0.3);
		FuzzySet fuzzySet = FuzzySet.of(fuzzySetA, fuzzySetB);
		double value = fuzzySet.centroid(-1.5 + 0.3, 1.5 + 0.3, 3);
		assertEquals(0.3, value, PRECISION);
	}

	@Test
	public void should_return_centroid_when_sets_are_trapezoids() {
		FuzzySet fuzzySetA = FuzzySet.trapezoid(-1.0 + 0.3, 0.25 + 0.3, 0.5);
		FuzzySet fuzzySetB = FuzzySet.trapezoid(-0.25 + 0.3, 1.0 + 0.3, 0.5);
		FuzzySet fuzzySet = FuzzySet.of(fuzzySetA, fuzzySetB);
		double value = fuzzySet.centroid(-1.0 + 0.3, 1.0 + 0.3, 3);
		assertEquals(0.3, value, PRECISION);
	}
}
