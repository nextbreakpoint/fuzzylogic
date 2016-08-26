package com.nextbreakpoint.fuzzylogic;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class FuzzyRuleTest {
	private static final double PRECISION = 0.001;

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void should_throw_exception_when_expression_is_null() {
		exception.expect(NullPointerException.class);
		FuzzyInference then = mock(FuzzyInference.class);
		FuzzyRule.of(null, then);
	}

	@Test
	public void should_throw_exception_when_inference_is_null() {
		exception.expect(NullPointerException.class);
		FuzzyPredicate when = mock(FuzzyPredicate.class);
		FuzzyRule.of(when, null);
	}

	@Test
	public void should_call_apply_on_expression() {
		FuzzyPredicate when = mock(FuzzyPredicate.class);
		FuzzyInference then = mock(FuzzyInference.class);
		FuzzyRule rule = FuzzyRule.of(when, then);
		rule.evaluate(getInputs());
		verify(when, times(1)).evaluate(any(Map.class));
	}

	@Test
	public void should_return_one_set_when_number_of_inputs_is_one() {
		FuzzyPredicate when = FuzzyPredicate.of(FuzzyVariable.of("input", FuzzyRange.of(0, 1)));
		FuzzyInference then = FuzzyInference.of(FuzzyVariable.of("output", FuzzyRange.of(0, 1)));
		FuzzyRule rule = FuzzyRule.of(when, then);
		FuzzyVariable[] variables = rule.evaluate(getInputs());
		assertEquals(1, variables.length);
	}

	@Test
	public void should_return_name_when_number_of_outputs_is_one() {
		FuzzyPredicate when = FuzzyPredicate.of(FuzzyVariable.of("input", FuzzyRange.of(0, 1)));
		FuzzyInference then = FuzzyInference.of(FuzzyVariable.of("output", FuzzyRange.of(0, 1)));
		FuzzyRule rule = FuzzyRule.of(when, then);
		FuzzyVariable[] variables = rule.evaluate(getInputs());
		assertEquals("output", variables[0].name());
	}

	@Test
	public void should_return_value_when_expression_is_not_limiting_output() {
		FuzzyPredicate when = FuzzyPredicate.of(FuzzyVariable.of("input", FuzzyRange.of(0, 1).translate(-0.10)));
		FuzzyInference then = FuzzyInference.of(FuzzyVariable.of("output", FuzzyRange.of(0, 1).translate(-0.15)));
		FuzzyRule rule = FuzzyRule.of(when, then);
		FuzzyVariable[] variables = rule.evaluate(getInputs());
		assertEquals(0.7, variables[0].membership().apply(0.5).get(), PRECISION);
	}

	@Test
	public void should_return_limit_value_when_expression_is_limiting_output() {
		FuzzyPredicate when = FuzzyPredicate.of(FuzzyVariable.of("input", FuzzyRange.of(0, 1).translate(-0.10)));
		FuzzyInference then = FuzzyInference.of(FuzzyVariable.of("output", FuzzyRange.of(0, 1).translate(-0.15)));
		FuzzyRule rule = FuzzyRule.of(when, then);
		FuzzyVariable[] variables = rule.evaluate(getInputs());
		assertEquals(0.8, variables[0].membership().apply(0.5 - 0.15).get(), PRECISION);
	}

	@Test
	public void should_call_apply_on_inference() {
		FuzzyPredicate when = mock(FuzzyPredicate.class);
		FuzzyInference then = mock(FuzzyInference.class);
		FuzzyRule rule = FuzzyRule.of(when, then);
		rule.evaluate(getInputs());
		verify(then, times(1)).apply(any(FuzzyValue.class));
	}

	private Map<String, Double> getInputs() {
		Map<String,Double> inputs = new HashMap<>();
		inputs.put("input", 0.5);
		return inputs;
	}
}
