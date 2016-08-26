package com.nextbreakpoint.fuzzylogic;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

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
		FuzzyMembership fuzzyMembership = FuzzyMembership.triangle().scale(2);
		FuzzyValue value = fuzzyMembership.apply(0.5);
		assertEquals(0.5, value.get(), PRECISION);
	}

	@Test
	public void should_return_centroid_when_sets_are_triangles() {
		FuzzyMembership fuzzyMembershipA = FuzzyMembership.triangle().scale(2).translate(-0.2);
		FuzzyMembership fuzzyMembershipB = FuzzyMembership.triangle().scale(2).translate(+0.8);
		FuzzyMembership fuzzyMembership = FuzzyMembership.of(fuzzyMembershipA, fuzzyMembershipB);
		double value = fuzzyMembership.centroid(-1.5 + 0.3, 1.5 + 0.3, 3);
		assertEquals(0.3, value, PRECISION);
	}
}
