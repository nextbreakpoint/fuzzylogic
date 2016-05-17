package com.nextbreakpoint.fuzzylogic;

import java.util.Objects;

public class FuzzyOutput {
	private FuzzySet[] sets;

	private FuzzyOutput(FuzzySet[] sets) {
		Objects.requireNonNull(sets);
		this.sets = sets;
	}

	public int numberOfSets() {
		return sets.length;
	}

	public static FuzzyOutput of(FuzzySet set, FuzzySet... otherSets) {
		FuzzySet[] sets = new FuzzySet[otherSets.length + 1];
		sets[0] = set;
		System.arraycopy(otherSets, 0, sets, 1, otherSets.length);
		return new FuzzyOutput(sets);
	}
}
