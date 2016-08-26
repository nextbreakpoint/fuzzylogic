package com.nextbreakpoint.fuzzylogic;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class FuzzySystemTest {
	private static final double PRECISION = 0.001;

	private FuzzySystem system;

	@Before
	public void setUp() throws Exception {
		system = FuzzySystem.empty();
	}

	@Test
	public void should_return_number_of_rules_is_zero_when_system_is_empty() {
		assertEquals(0, system.numberOfRules());
	}

	@Test
	public void should_return_number_of_rules_is_two_when_system_has_two_rules() {
		FuzzySystem fuzzySystem = createFuzzySystem();
		assertEquals(2, fuzzySystem.numberOfRules());
	}

	@Test
	public void should_return_map_with_same_size_as_number_of_outputs() {
		FuzzySystem fuzzySystem = createFuzzySystem().withSteps(25);
		Map<String, Double> outputs = fuzzySystem.evaluate(createInputs0());
		assertEquals(1, outputs.size());
	}

	@Test
	public void should_return_value_when_input_activates_rule_1() {
		FuzzySystem fuzzySystem = createFuzzySystem().withSteps(25);
		Map<String, Double> outputs = fuzzySystem.evaluate(createInputs1());
		assertEquals(-0.2, outputs.get("output"), PRECISION);
	}

	@Test
	public void should_return_value_when_input_activates_rule_2() {
		FuzzySystem fuzzySystem = createFuzzySystem().withSteps(25);
		Map<String, Double> outputs = fuzzySystem.evaluate(createInputs2());
		assertEquals(+0.8, outputs.get("output"), PRECISION);
	}

	private FuzzySystem createFuzzySystem() {
		FuzzyDomain domainIn = FuzzyDomain.of(-0.5, 0.5).scale(4);
		FuzzyDomain domainOut = FuzzyDomain.of(-0.5, 0.5).scale(2);
		FuzzyRule rule1 = FuzzyRule.of(FuzzyPredicate.of(FuzzyVariable.of("input", domainIn.translate(-0.5))), FuzzyInference.of(FuzzyVariable.of("output", domainOut.translate(-0.2))));
		FuzzyRule rule2 = FuzzyRule.of(FuzzyPredicate.of(FuzzyVariable.of("input", domainIn.translate(+0.5))), FuzzyInference.of(FuzzyVariable.of("output", domainOut.translate(+0.8))));
		return system.addRule(rule1).addRule(rule2);
	}

	private Map<String, Double> createInputs0() {
		Map<String,Double> inputs = new HashMap<>();
		inputs.put("input", 0.0);
		return inputs;
	}

	private Map<String, Double> createInputs1() {
		Map<String,Double> inputs = new HashMap<>();
		inputs.put("input", -1.5);
		return inputs;
	}

	private Map<String, Double> createInputs2() {
		Map<String,Double> inputs = new HashMap<>();
		inputs.put("input", +1.5);
		return inputs;
	}
}
