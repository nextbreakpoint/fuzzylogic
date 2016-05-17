package com.nextbreakpoint.fuzzylogic;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class FuzzySystemTest {
	private static final double PRECISION = 0.001;

	@Test
	public void system_shouldReturnValue_whenInputActivatesRuleA() {
		FuzzySystem system = new FuzzySystem();
		
		FuzzySet fuzzySetInputA = FuzzySet.triangle(-2.5, 1.5);
		FuzzySet fuzzySetInputB = FuzzySet.triangle(-1.5, 2.5);
		
		FuzzySet fuzzySetOutputA = FuzzySet.triangle(-1.5 + 0.3, 0.5 + 0.3);
		FuzzySet fuzzySetOutputB = FuzzySet.triangle(-0.5 + 0.3, 1.5 + 0.3);

		FuzzySet fuzzyRuleA = FuzzyRule.of(fuzzySetInputA);
		FuzzySet fuzzyRuleB = FuzzyRule.of(fuzzySetInputB);
		
		double value = -1.5;
		
		FuzzyValue valueA = fuzzyRuleA.apply(value);
		FuzzyValue valueB = fuzzyRuleB.apply(value);
		
		double result = FuzzyRule.or(fuzzySetOutputA.limit(valueA), fuzzySetOutputB.limit(valueB)).centroid(-1.5 + 0.3, 1.5 + 0.3, 25);

		assertEquals(-0.5 + 0.3, result, PRECISION);
	}

	@Test
	public void system_shouldReturnValue_whenInputActivatesRuleB() {
		FuzzySystem system = new FuzzySystem();
		
		FuzzySet fuzzySetInputA = FuzzySet.triangle(-2.5, 1.5);
		FuzzySet fuzzySetInputB = FuzzySet.triangle(-1.5, 2.5);
		
		FuzzySet fuzzySetOutputA = FuzzySet.triangle(-1.5 + 0.3, 0.5 + 0.3);
		FuzzySet fuzzySetOutputB = FuzzySet.triangle(-0.5 + 0.3, 1.5 + 0.3);

		FuzzySet fuzzyRuleA = FuzzyRule.of(fuzzySetInputA);
		FuzzySet fuzzyRuleB = FuzzyRule.of(fuzzySetInputB);
		
		double value = 1.5;
		
		FuzzyValue valueA = fuzzyRuleA.apply(value);
		FuzzyValue valueB = fuzzyRuleB.apply(value);
		
		double result = FuzzyRule.or(fuzzySetOutputA.limit(valueA), fuzzySetOutputB.limit(valueB)).centroid(-1.5 + 0.3, 1.5 + 0.3, 25);

		assertEquals(0.5 + 0.3, result, PRECISION);
	}
}
