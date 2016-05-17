package com.nextbreakpoint.fuzzylogic;

import java.util.Objects;

public class FuzzyInput {
	private FuzzySet[] sets;
	
	public FuzzyInput(FuzzySet[] sets) {
		Objects.requireNonNull(sets);
		this.sets = sets;
	}

	public int numberOfSets() {
		return sets.length;
	}
}
