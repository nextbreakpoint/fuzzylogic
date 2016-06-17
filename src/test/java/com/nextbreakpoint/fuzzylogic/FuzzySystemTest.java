package com.nextbreakpoint.fuzzylogic;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class FuzzySystemTest {
	private static final double PRECISION = 0.001;

	private FuzzySystem system;

	@Before
	public void setUp() throws Exception {
		system = FuzzySystem.empty();
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
	public void should_return_number_of_rules_is_zero_when_system_is_empty() {
		assertEquals(0, system.numberOfRules());
	}

	@Test
	public void should_return_input_size_is_one_when_system_has_one_input() {
		FuzzySet fuzzySet1 = FuzzySet.triangle(-1.5, 0.5);
		FuzzySet fuzzySet2 = FuzzySet.triangle(-0.5, 1.5);
		assertEquals(1, system.addInput(FuzzyInput.of("input", fuzzySet1, fuzzySet2)).inputSize());
	}

	@Test
	public void should_return_output_size_is_one_when_system_has_one_output() {
		FuzzySet fuzzySet1 = FuzzySet.triangle(-1.5, 0.5);
		FuzzySet fuzzySet2 = FuzzySet.triangle(-0.5, 1.5);
		assertEquals(1, system.addOutput(FuzzyOutput.of("input", fuzzySet1, fuzzySet2)).outputSize());
	}

	@Test
	public void should_return_number_of_fuzzy_sets_when_system_has_one_input() {
		FuzzySet fuzzySet1 = FuzzySet.triangle(-1.5, 0.5);
		FuzzySet fuzzySet2 = FuzzySet.triangle(-0.5, 1.5);
		assertEquals(2, system.addInput(FuzzyInput.of("input", fuzzySet1, fuzzySet2)).input(0).numberOfSets());
	}

	@Test
	public void should_return_number_of_fuzzy_sets_when_system_has_one_output() {
		FuzzySet fuzzySet1 = FuzzySet.triangle(-1.5, 0.5);
		FuzzySet fuzzySet2 = FuzzySet.triangle(-0.5, 1.5);
		assertEquals(2, system.addOutput(FuzzyOutput.of("input", fuzzySet1, fuzzySet2)).output(0).numberOfSets());
	}

	@Test
	public void should_return_number_of_rules_is_two_when_system_has_two_rules() {
		FuzzySystem fuzzySystem = getFuzzySystem();
		assertEquals(2, fuzzySystem.numberOfRules());
	}

	@Test
	public void should_return_array_with_same_size_as_number_of_outputs() {
		FuzzySystem fuzzySystem = getFuzzySystem();
		Map<String, Double> outputs = fuzzySystem.evaluate(getInputs1(), -1.5 + 0.3, 1.5 + 0.3, 25);
		assertEquals(1, outputs.size());
	}

	@Test
	public void should_return_value_when_input_activates_rule_1() {
		FuzzySystem fuzzySystem = getFuzzySystem();
		Map<String, Double> outputs = fuzzySystem.evaluate(getInputs1(), -1.5 + 0.3, 1.5 + 0.3, 25);
		assertEquals(-0.5 + 0.3, outputs.get("output"), PRECISION);
	}

	@Test
	public void should_return_value_when_input_activates_rule_B() {
		FuzzySystem fuzzySystem = getFuzzySystem();
		Map<String, Double> outputs = fuzzySystem.evaluate(getInputs2(), -1.5 + 0.3, 1.5 + 0.3, 25);
		assertEquals(0.5 + 0.3, outputs.get("output"), PRECISION);
	}

	private FuzzySystem getFuzzySystem() {
		FuzzySet fuzzySetIn1 = FuzzySet.triangle(-2.5, 1.5);
		FuzzySet fuzzySetIn2 = FuzzySet.triangle(-1.5, 2.5);
		FuzzySet fuzzySetOut1 = FuzzySet.triangle(-1.5 + 0.3, 0.5 + 0.3);
		FuzzySet fuzzySetOut2 = FuzzySet.triangle(-0.5 + 0.3, 1.5 + 0.3);
		FuzzyRule rule1 = FuzzyRule.of(FuzzyPredicate.of(FuzzyVariable.of("input", fuzzySetIn1)), FuzzyInference.of(FuzzyVariable.of("output", fuzzySetOut1)));
		FuzzyRule rule2 = FuzzyRule.of(FuzzyPredicate.of(FuzzyVariable.of("input", fuzzySetIn2)), FuzzyInference.of(FuzzyVariable.of("output", fuzzySetOut2)));
		return system.addInput(FuzzyInput.of("input", fuzzySetIn1, fuzzySetIn2)).addOutput(FuzzyOutput.of("output", fuzzySetOut1, fuzzySetOut2)).addRule(rule1).addRule(rule2);
	}

	private Map<String, Double> getInputs1() {
		Map<String,Double> inputs = new HashMap<>();
		inputs.put("input", -1.5);
		return inputs;
	}

	private Map<String, Double> getInputs2() {
		Map<String,Double> inputs = new HashMap<>();
		inputs.put("input", +1.5);
		return inputs;
	}
}
