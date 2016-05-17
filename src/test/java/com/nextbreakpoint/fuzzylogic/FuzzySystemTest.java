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

		FuzzySet fuzzyRuleA = FuzzyExpression.of(fuzzySetInputA);
		FuzzySet fuzzyRuleB = FuzzyExpression.of(fuzzySetInputB);
		
		double value = -1.5;
		
		FuzzyValue valueA = fuzzyRuleA.apply(value);
		FuzzyValue valueB = fuzzyRuleB.apply(value);
		
		double result = FuzzyExpression.or(fuzzySetOutputA.limit(valueA), fuzzySetOutputB.limit(valueB)).centroid(-1.5 + 0.3, 1.5 + 0.3, 25);

		assertEquals(-0.5 + 0.3, result, PRECISION);
	}

	@Test
	public void system_shouldReturnValue_whenInputActivatesRuleB() {
		FuzzySystem system = new FuzzySystem();
		
		FuzzySet fuzzySetInputA = FuzzySet.triangle(-2.5, 1.5);
		FuzzySet fuzzySetInputB = FuzzySet.triangle(-1.5, 2.5);
		
		FuzzySet fuzzySetOutputA = FuzzySet.triangle(-1.5 + 0.3, 0.5 + 0.3);
		FuzzySet fuzzySetOutputB = FuzzySet.triangle(-0.5 + 0.3, 1.5 + 0.3);

		FuzzySet fuzzyRuleA = FuzzyExpression.of(fuzzySetInputA);
		FuzzySet fuzzyRuleB = FuzzyExpression.of(fuzzySetInputB);
		
		double value = 1.5;
		
		FuzzyValue valueA = fuzzyRuleA.apply(value);
		FuzzyValue valueB = fuzzyRuleB.apply(value);
		
		double result = FuzzyExpression.or(fuzzySetOutputA.limit(valueA), fuzzySetOutputB.limit(valueB)).centroid(-1.5 + 0.3, 1.5 + 0.3, 25);

		assertEquals(0.5 + 0.3, result, PRECISION);
	}

	@Test
	public void emptySystem_shouldReturnInputSizeIsZero() {
		FuzzySystem system = new FuzzySystem();
		assertEquals(0, system.inputSize());
	}

	@Test
	public void emptySystem_shouldReturnOutputSizeIsZero() {
		FuzzySystem system = new FuzzySystem();
		assertEquals(0, system.outputSize());
	}

	@Test
	public void systemWithOneInput_shouldReturnInputSizeIsOne() {
		FuzzySystem system = new FuzzySystem();
		FuzzySet fuzzySet1 = FuzzySet.triangle(-1.5, 0.5);
		FuzzySet fuzzySet2 = FuzzySet.triangle(-0.5, 1.5);
		assertEquals(1, system.addInput(fuzzySet1, fuzzySet2).inputSize());
	}

	@Test
	public void systemWithOneOutput_shouldReturnOutputSizeIsOne() {
		FuzzySystem system = new FuzzySystem();
		FuzzySet fuzzySet1 = FuzzySet.triangle(-1.5, 0.5);
		FuzzySet fuzzySet2 = FuzzySet.triangle(-0.5, 1.5);
		assertEquals(1, system.addOutput(fuzzySet1, fuzzySet2).outputSize());
	}

	@Test
	public void systemWithOneInput_shouldReturnCorrectNumberOfFuzzySets() {
		FuzzySystem system = new FuzzySystem();
		FuzzySet fuzzySet1 = FuzzySet.triangle(-1.5, 0.5);
		FuzzySet fuzzySet2 = FuzzySet.triangle(-0.5, 1.5);
		assertEquals(2, system.addInput(fuzzySet1, fuzzySet2).input(0).numberOfSets());
	}

	@Test
	public void systemWithOneOutput_shouldReturnCorrectNumberOfFuzzySets() {
		FuzzySystem system = new FuzzySystem();
		FuzzySet fuzzySet1 = FuzzySet.triangle(-1.5, 0.5);
		FuzzySet fuzzySet2 = FuzzySet.triangle(-0.5, 1.5);
		assertEquals(2, system.addOutput(fuzzySet1, fuzzySet2).output(0).numberOfSets());
	}

	@Test
	public void emptySystem_shouldReturnNumberOfRulesIsZero() {
		FuzzySystem system = new FuzzySystem();
		assertEquals(0, system.numberOfRules());
	}

	@Test
	public void systemWithRules_shouldReturnCorrectNumberOfFuzzyRules() {
		FuzzySystem system = new FuzzySystem();
		FuzzySet fuzzySetIn1 = FuzzySet.triangle(-2.5, 1.5);
		FuzzySet fuzzySetIn2 = FuzzySet.triangle(-1.5, 2.5);
		FuzzySet fuzzySetOut1 = FuzzySet.triangle(-1.5 + 0.3, 0.5 + 0.3);
		FuzzySet fuzzySetOut2 = FuzzySet.triangle(-0.5 + 0.3, 1.5 + 0.3);
		assertEquals(2, system.addInput(fuzzySetIn1, fuzzySetIn2).addOutput(fuzzySetOut1, fuzzySetOut2)
				.addRule(FuzzyRule.of(FuzzyExpression.of(fuzzySetIn1), FuzzyInference.of(fuzzySetOut1)))
				.addRule(FuzzyRule.of(FuzzyExpression.of(fuzzySetIn2), FuzzyInference.of(fuzzySetOut2)))
				.numberOfRules());
	}
}
