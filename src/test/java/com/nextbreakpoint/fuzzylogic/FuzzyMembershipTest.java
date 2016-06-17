package com.nextbreakpoint.fuzzylogic;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class FuzzyMembershipTest {
	private static final double PRECISION = 0.001;

	@Test
	public void should_return_value_when_set_is_constant() {
		FuzzyMembership fuzzyMembership = (value) -> FuzzyValue.of(0.5);
		FuzzyValue value = fuzzyMembership.apply(5);
		assertEquals(0.5, value.get(), PRECISION);
	}

	@Test
	public void should_return_value_when_set_is_triangle() {
		FuzzyMembership fuzzyMembership = FuzzyMembership.triangle(-1, 1);
		FuzzyValue value = fuzzyMembership.apply(0.5);
		assertEquals(0.5, value.get(), PRECISION);
	}

	@Test
	public void should_return_value_when_set_is_trapezoid() {
		FuzzyMembership fuzzyMembership = FuzzyMembership.trapezoid(-1.0, 1.0, 0.5);
		FuzzyValue value = fuzzyMembership.apply(0.75);
		assertEquals(0.5, value.get(), PRECISION);
	}

	@Test
	public void should_return_centroid_when_sets_are_triangles() {
		FuzzyMembership fuzzyMembershipA = FuzzyMembership.triangle(-1.5 + 0.3, 0.5 + 0.3);
		FuzzyMembership fuzzyMembershipB = FuzzyMembership.triangle(-0.5 + 0.3, 1.5 + 0.3);
		FuzzyMembership fuzzyMembership = FuzzyMembership.of(fuzzyMembershipA, fuzzyMembershipB);
		double value = fuzzyMembership.centroid(-1.5 + 0.3, 1.5 + 0.3, 3);
		assertEquals(0.3, value, PRECISION);
	}

	@Test
	public void should_return_centroid_when_sets_are_trapezoids() {
		FuzzyMembership fuzzyMembershipA = FuzzyMembership.trapezoid(-1.0 + 0.3, 0.25 + 0.3, 0.5);
		FuzzyMembership fuzzyMembershipB = FuzzyMembership.trapezoid(-0.25 + 0.3, 1.0 + 0.3, 0.5);
		FuzzyMembership fuzzyMembership = FuzzyMembership.of(fuzzyMembershipA, fuzzyMembershipB);
		double value = fuzzyMembership.centroid(-1.0 + 0.3, 1.0 + 0.3, 3);
		assertEquals(0.3, value, PRECISION);
	}
}
