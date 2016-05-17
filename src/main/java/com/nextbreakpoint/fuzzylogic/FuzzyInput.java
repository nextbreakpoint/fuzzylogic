package com.nextbreakpoint.fuzzylogic;

import java.util.Objects;

public class FuzzyInput {
	private FuzzySet[] sets;
	
	private FuzzyInput(FuzzySet[] sets) {
		Objects.requireNonNull(sets);
		this.sets = sets;
	}

	public int numberOfSets() {
		return sets.length;
	}

	public static FuzzyInput of(FuzzySet set, FuzzySet... otherSets) {
		FuzzySet[] sets = new FuzzySet[otherSets.length + 1];
		sets[0] = set;
		System.arraycopy(otherSets, 0, sets, 1, otherSets.length);
		return new FuzzyInput(sets);
	}
}
