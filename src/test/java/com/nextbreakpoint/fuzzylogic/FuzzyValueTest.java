package com.nextbreakpoint.fuzzylogic;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class FuzzyValueTest {
	private static final double PRECISION = 0.001;
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Test
	public void fuzzyValue_shouldThrowException_whenValueIsLessThan0() {
		exception.expect(IllegalArgumentException.class);
		new FuzzyValue(-0.1);
	}
	
	@Test
	public void fuzzyValue_shouldThrowException_whenValueIsGreatThan1() {
		exception.expect(IllegalArgumentException.class);
		new FuzzyValue(+1.1);
	}

	@Test
	public void fuzzyValue_shouldNotThrowException_whenValueIs0() {
		FuzzyValue value = new FuzzyValue(0.0);
		assertEquals(value.get(), 0.0, PRECISION);
	}
	
	@Test
	public void fuzzyValue_shouldNotThrowException_whenValueIs1() {
		FuzzyValue value = new FuzzyValue(1.0);
		assertEquals(value.get(), 1.0, PRECISION);
	}
	
	@Test
	public void fuzzyValue_shouldNotThrowException_whenValueIs0Point5() {
		FuzzyValue value = new FuzzyValue(0.5);
		assertEquals(value.get(), 0.5, PRECISION);
	}
}
