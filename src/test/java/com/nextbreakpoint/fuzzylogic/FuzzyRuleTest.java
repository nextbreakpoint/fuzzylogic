package com.nextbreakpoint.fuzzylogic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

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
		FuzzyExpression when = mock(FuzzyExpression.class);
		FuzzyRule.of(when, null);
	}

	@Test
	public void should_call_apply_on_expression() {
		FuzzyExpression when = mock(FuzzyExpression.class);
		FuzzyInference then = mock(FuzzyInference.class);
		FuzzyRule rule = FuzzyRule.of(when, then);
		rule.evaluate(0.5);
		verify(when, times(1)).apply(any(Double.class));
	}

	@Test
	public void should_call_apply_on_inference() {
		FuzzyExpression when = mock(FuzzyExpression.class);
		FuzzyInference then = mock(FuzzyInference.class);
		FuzzyRule rule = FuzzyRule.of(when, then);
		rule.evaluate(0.5);
		verify(then, times(1)).apply(any(FuzzyValue.class));
	}

	@Test
	public void should_return_one_set_when_number_of_inputs_is_one() {
		FuzzyExpression when = FuzzyExpression.of(FuzzySet.constant(1.0));
		FuzzyInference then = FuzzyInference.of(FuzzySet.constant(0.5));
		FuzzyRule rule = FuzzyRule.of(when, then);
		FuzzySet[] sets = rule.evaluate(0.5);
		assertEquals(1, sets.length);
	}

	@Test
	public void should_return_value_when_expression_is_not_limiting_output() {
		FuzzyExpression when = FuzzyExpression.of(FuzzySet.constant(0.5));
		FuzzyInference then = FuzzyInference.of(FuzzySet.constant(0.3));
		FuzzyRule rule = FuzzyRule.of(when, then);
		FuzzySet[] sets = rule.evaluate(0.5);
		assertEquals(0.3, sets[0].apply(0).get(), PRECISION);
	}

	@Test
	public void should_return_limit_value_when_expression_is_limiting_output() {
		FuzzyExpression when = FuzzyExpression.of(FuzzySet.constant(0.5));
		FuzzyInference then = FuzzyInference.of(FuzzySet.constant(0.8));
		FuzzyRule rule = FuzzyRule.of(when, then);
		FuzzySet[] sets = rule.evaluate(0.5);
		assertEquals(0.5, sets[0].apply(0).get(), PRECISION);
	}
}
