package com.nextbreakpoint.fuzzylogic;

import java.util.Objects;

public class FuzzyOutput {
	private FuzzySet[] sets;

	public FuzzyOutput(FuzzySet[] sets) {
		Objects.requireNonNull(sets);
		this.sets = sets;
	}

	public int numberOfSets() {
		return sets.length;
	}
}
