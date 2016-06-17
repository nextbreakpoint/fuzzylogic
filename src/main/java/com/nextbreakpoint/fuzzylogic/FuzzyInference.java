package com.nextbreakpoint.fuzzylogic;

import java.util.Arrays;
import java.util.Objects;

public class FuzzyInference {
	private final FuzzySet[] sets;

	private FuzzyInference(FuzzySet[] sets) {
		Objects.requireNonNull(sets);
		if (Arrays.stream(sets).anyMatch(set -> set == null)) {
			throw new NullPointerException("Set can't be null");
		}
		this.sets = sets;
	}

	public int numberOfSets() {
		return sets.length;
	}

	public FuzzySet[] apply(FuzzyValue value) {
		FuzzySet[] outSets = new FuzzySet[sets.length];
		for (int i = 0; i < sets.length; i++) {
			outSets[i] = sets[i].limit(value);
		}
		return outSets;
	}
	
	public static FuzzyInference of(FuzzySet set, FuzzySet... otherSets) {
		FuzzySet[] sets = new FuzzySet[otherSets.length + 1];
		sets[0] = set;
		System.arraycopy(otherSets, 0, sets, 1, otherSets.length);
		return new FuzzyInference(sets);
	}
}
