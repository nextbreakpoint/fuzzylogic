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
		FuzzySystem fuzzySystem = getFuzzySystem();
		assertEquals(2, fuzzySystem.numberOfRules());
	}

	@Test
	public void should_return_array_with_same_size_as_number_of_outputs() {
		FuzzySystem fuzzySystem = getFuzzySystem().withSteps(25);
		Map<String, Double> outputs = fuzzySystem.evaluate(getInputs1());
		assertEquals(1, outputs.size());
	}

	@Test
	public void should_return_value_when_input_activates_rule_1() {
		FuzzySystem fuzzySystem = getFuzzySystem().withSteps(25);
		Map<String, Double> outputs = fuzzySystem.evaluate(getInputs1());
		assertEquals(-0.5 + 0.3, outputs.get("output"), PRECISION);
	}

	@Test
	public void should_return_value_when_input_activates_rule_B() {
		FuzzySystem fuzzySystem = getFuzzySystem().withSteps(25);
		Map<String, Double> outputs = fuzzySystem.evaluate(getInputs2());
		assertEquals(0.5 + 0.3, outputs.get("output"), PRECISION);
	}

	private FuzzySystem getFuzzySystem() {
		FuzzyMembership fuzzyMembershipIn1 = FuzzyMembership.triangle(-2.5, 1.5);
		FuzzyMembership fuzzyMembershipIn2 = FuzzyMembership.triangle(-1.5, 2.5);
		FuzzyMembership fuzzyMembershipOut1 = FuzzyMembership.triangle(-1.5 + 0.3, 0.5 + 0.3);
		FuzzyMembership fuzzyMembershipOut2 = FuzzyMembership.triangle(-0.5 + 0.3, 1.5 + 0.3);
		FuzzyRule rule1 = FuzzyRule.of(FuzzyPredicate.of(FuzzyVariable.of("input", FuzzyDomain.of(-2.5, 1.5), fuzzyMembershipIn1)), FuzzyInference.of(FuzzyVariable.of("output", FuzzyDomain.of(-1.5 + 0.3, 0.5 + 0.3), fuzzyMembershipOut1)));
		FuzzyRule rule2 = FuzzyRule.of(FuzzyPredicate.of(FuzzyVariable.of("input", FuzzyDomain.of(-1.5, 2.5), fuzzyMembershipIn2)), FuzzyInference.of(FuzzyVariable.of("output", FuzzyDomain.of(-0.5 + 0.3, 1.5 + 0.3), fuzzyMembershipOut2)));
		return system.addRule(rule1).addRule(rule2);
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
