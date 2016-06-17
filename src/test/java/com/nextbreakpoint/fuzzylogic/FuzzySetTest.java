package com.nextbreakpoint.fuzzylogic;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

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
	public void should_return_value_when_expression_is_AND_of_triangle_sets() {
		FuzzySet fuzzySetA = FuzzySet.triangle(-1.5, 0.5);
		FuzzySet fuzzySetB = FuzzySet.triangle(-0.5, 1.5);
		FuzzySet fuzzySet = FuzzyExpression.and(fuzzySetA, fuzzySetB);
		FuzzyValue value = fuzzySet.apply(0);
		assertEquals(0.25, value.get(), PRECISION);
	}

	@Test
	public void should_return_value_when_expression_is_OR_of_triangle_sets() {
		FuzzySet fuzzySetA = FuzzySet.triangle(-1.5, 0.5);
		FuzzySet fuzzySetB = FuzzySet.triangle(-0.5, 1.5);
		FuzzySet fuzzySet = FuzzyExpression.or(fuzzySetA, fuzzySetB);
		FuzzyValue value = fuzzySet.apply(0);
		assertEquals(0.75, value.get(), PRECISION);
	}

	@Test
	public void should_return_value_when_expression_is_AND_of_trapezoid_sets() {
		FuzzySet fuzzySetA = FuzzySet.trapezoid(-1.0, 0.25, 0.5);
		FuzzySet fuzzySetB = FuzzySet.trapezoid(-0.25, 1.0, 0.5);
		FuzzySet fuzzySet = FuzzyExpression.and(fuzzySetA, fuzzySetB);
		FuzzyValue value = fuzzySet.apply(0);
		assertEquals(0.25, value.get(), PRECISION);
	}

	@Test
	public void should_return_value_when_expression_is_OR_of_trapezoid_sets() {
		FuzzySet fuzzySetA = FuzzySet.trapezoid(-1.0, 0.25, 0.5);
		FuzzySet fuzzySetB = FuzzySet.trapezoid(-0.25, 1.0, 0.5);
		FuzzySet fuzzySet = FuzzyExpression.or(fuzzySetA, fuzzySetB);
		FuzzyValue value = fuzzySet.apply(0);
		assertEquals(0.75, value.get(), PRECISION);
	}

	@Test
	public void should_return_centroid_when_sets_are_triangles() {
		FuzzySet fuzzySetA = FuzzySet.triangle(-1.5 + 0.3, 0.5 + 0.3);
		FuzzySet fuzzySetB = FuzzySet.triangle(-0.5 + 0.3, 1.5 + 0.3);
		FuzzySet fuzzySet = FuzzyExpression.or(fuzzySetA, fuzzySetB);
		double value = fuzzySet.centroid(-1.5 + 0.3, 1.5 + 0.3, 3);
		assertEquals(0.3, value, PRECISION);
	}

	@Test
	public void should_return_centroid_when_sets_are_trapezoids() {
		FuzzySet fuzzySetA = FuzzySet.trapezoid(-1.0 + 0.3, 0.25 + 0.3, 0.5);
		FuzzySet fuzzySetB = FuzzySet.trapezoid(-0.25 + 0.3, 1.0 + 0.3, 0.5);
		FuzzySet fuzzySet = FuzzyExpression.or(fuzzySetA, fuzzySetB);
		double value = fuzzySet.centroid(-1.0 + 0.3, 1.0 + 0.3, 3);
		assertEquals(0.3, value, PRECISION);
	}
}
