package com.nextbreakpoint.fuzzylogic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.HashMap;
import java.util.Map;

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
		FuzzyPredicate when = FuzzyPredicate.of(FuzzyVariable.of("input", FuzzySet.constant(1.0)));
		FuzzyInference then = FuzzyInference.of(FuzzyVariable.of("output", FuzzySet.constant(0.5)));
		FuzzyRule rule = FuzzyRule.of(when, then);
		FuzzySet[] sets = rule.evaluate(getInputs());
		assertEquals(1, sets.length);
	}

	@Test
	public void should_return_value_when_expression_is_not_limiting_output() {
		FuzzyPredicate when = FuzzyPredicate.of(FuzzyVariable.of("input", FuzzySet.constant(0.5)));
		FuzzyInference then = FuzzyInference.of(FuzzyVariable.of("output", FuzzySet.constant(0.3)));
		FuzzyRule rule = FuzzyRule.of(when, then);
		FuzzySet[] sets = rule.evaluate(getInputs());
		assertEquals(0.3, sets[0].apply(0).get(), PRECISION);
	}

	@Test
	public void should_return_limit_value_when_expression_is_limiting_output() {
		FuzzyPredicate when = FuzzyPredicate.of(FuzzyVariable.of("input", FuzzySet.constant(0.5)));
		FuzzyInference then = FuzzyInference.of(FuzzyVariable.of("output", FuzzySet.constant(0.8)));
		FuzzyRule rule = FuzzyRule.of(when, then);
		FuzzySet[] sets = rule.evaluate(getInputs());
		assertEquals(0.5, sets[0].apply(0).get(), PRECISION);
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
