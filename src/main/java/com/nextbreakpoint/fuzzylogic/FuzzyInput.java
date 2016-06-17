package com.nextbreakpoint.fuzzylogic;

import java.util.Arrays;
import java.util.Objects;

public class FuzzyInput {
	private final String name;
	private final FuzzySet[] sets;
	
	private FuzzyInput(String name, FuzzySet[] sets) {
		Objects.requireNonNull(name);
		Objects.requireNonNull(sets);
		if (Arrays.stream(sets).anyMatch(set -> set == null)) {
			throw new NullPointerException("Set can't be null");
		}
		this.name = name;
		this.sets = sets;
	}

	public String name() {
		return name;
	}

	public int numberOfSets() {
		return sets.length;
	}

	public static FuzzyInput of(String name, FuzzySet set, FuzzySet... otherSets) {
		FuzzySet[] sets = new FuzzySet[otherSets.length + 1];
		sets[0] = set;
		System.arraycopy(otherSets, 0, sets, 1, otherSets.length);
		return new FuzzyInput(name, sets);
	}
}
