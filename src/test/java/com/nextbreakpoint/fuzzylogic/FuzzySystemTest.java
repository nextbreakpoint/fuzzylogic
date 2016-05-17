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
	public void emptySystem_shouldReturnRuleSizeIsZero() {
		FuzzySystem system = new FuzzySystem();
		FuzzySet fuzzySet1 = FuzzySet.triangle(-1.5, 0.5);
		FuzzySet fuzzySet2 = FuzzySet.triangle(-0.5, 1.5);
		assertEquals(0, system.addInput(fuzzySet1, fuzzySet2).addOutput(fuzzySet1, fuzzySet2).numberOfRules());
	}

	@Test
	public void systemWithAndRule_shouldReturnCorrectNumberOfFuzzyRules() {
		FuzzySystem system = new FuzzySystem();
		FuzzySet fuzzySet1 = FuzzySet.triangle(-1.5, 0.5);
		FuzzySet fuzzySet2 = FuzzySet.triangle(-0.5, 1.5);
		assertEquals(1, system.addInput(fuzzySet1, fuzzySet2).addOutput(fuzzySet1, fuzzySet2).addRuleAnd(new FuzzySet[] { fuzzySet1 }, fuzzySet1, fuzzySet2).numberOfRules());
	}

	@Test
	public void systemWithOrRule_shouldReturnCorrectNumberOfFuzzyRules() {
		FuzzySystem system = new FuzzySystem();
		FuzzySet fuzzySet1 = FuzzySet.triangle(-1.5, 0.5);
		FuzzySet fuzzySet2 = FuzzySet.triangle(-0.5, 1.5);
		assertEquals(1, system.addInput(fuzzySet1, fuzzySet2).addOutput(fuzzySet1, fuzzySet2).addRuleOr(new FuzzySet[] { fuzzySet1 }, fuzzySet1, fuzzySet2).numberOfRules());
	}
}
