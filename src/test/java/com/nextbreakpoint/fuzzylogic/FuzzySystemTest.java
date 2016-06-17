package com.nextbreakpoint.fuzzylogic;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class FuzzySystemTest {
	private static final double PRECISION = 0.001;

	private FuzzySystem system;

	@Before
	public void setUp() throws Exception {
		system = FuzzySystem.empty();
	}

	@Test
	public void should_return_value_when_input_activates_rule_A() {
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
	public void should_return_value_when_input_activates_rule_B() {
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
	public void should_return_input_size_is_zero_when_system_is_empty() {
		assertEquals(0, system.inputSize());
	}

	@Test
	public void should_return_output_size_is_zero_when_system_is_empty() {
		assertEquals(0, system.outputSize());
	}

	@Test
	public void should_return_input_size_is_one_when_system_has_one_input() {
		FuzzySet fuzzySet1 = FuzzySet.triangle(-1.5, 0.5);
		FuzzySet fuzzySet2 = FuzzySet.triangle(-0.5, 1.5);
		assertEquals(1, system.addInputs(fuzzySet1, fuzzySet2).inputSize());
	}

	@Test
	public void should_return_output_size_is_one_when_system_has_one_output() {
		FuzzySet fuzzySet1 = FuzzySet.triangle(-1.5, 0.5);
		FuzzySet fuzzySet2 = FuzzySet.triangle(-0.5, 1.5);
		assertEquals(1, system.addOutputs(fuzzySet1, fuzzySet2).outputSize());
	}

	@Test
	public void should_return_number_of_fuzzy_sets_when_system_has_one_input() {
		FuzzySet fuzzySet1 = FuzzySet.triangle(-1.5, 0.5);
		FuzzySet fuzzySet2 = FuzzySet.triangle(-0.5, 1.5);
		assertEquals(2, system.addInputs(fuzzySet1, fuzzySet2).input(0).numberOfSets());
	}

	@Test
	public void should_return_number_of_fuzzy_sets_when_system_has_one_output() {
		FuzzySet fuzzySet1 = FuzzySet.triangle(-1.5, 0.5);
		FuzzySet fuzzySet2 = FuzzySet.triangle(-0.5, 1.5);
		assertEquals(2, system.addOutputs(fuzzySet1, fuzzySet2).output(0).numberOfSets());
	}

	@Test
	public void should_return_number_of_rules_is_zero_when_system_is_empty() {
		assertEquals(0, system.numberOfRules());
	}

	@Test
	public void should_return_number_of_rules_is_two_when_system_has_two_rules() {
		FuzzySet fuzzySetIn1 = FuzzySet.triangle(-2.5, 1.5);
		FuzzySet fuzzySetIn2 = FuzzySet.triangle(-1.5, 2.5);
		FuzzySet fuzzySetOut1 = FuzzySet.triangle(-1.5 + 0.3, 0.5 + 0.3);
		FuzzySet fuzzySetOut2 = FuzzySet.triangle(-0.5 + 0.3, 1.5 + 0.3);
		FuzzySystem fuzzySystem = system
				.addInputs(fuzzySetIn1, fuzzySetIn2)
				.addOutputs(fuzzySetOut1, fuzzySetOut2)
				.addRules(FuzzyRule.of(FuzzyExpression.of(fuzzySetIn1), FuzzyInference.of(fuzzySetOut1)))
				.addRules(FuzzyRule.of(FuzzyExpression.of(fuzzySetIn2), FuzzyInference.of(fuzzySetOut2)));
		assertEquals(2, fuzzySystem.numberOfRules());
	}
}
