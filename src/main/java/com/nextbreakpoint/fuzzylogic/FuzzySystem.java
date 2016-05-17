package com.nextbreakpoint.fuzzylogic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class FuzzySystem {
	private final List<FuzzyRule> rules;
	private final List<FuzzyInput> inputs;
	private final List<FuzzyOutput> outputs;

	public FuzzySystem() {
		this(Collections.emptyList(), Collections.emptyList(), Collections.emptyList());
	}
	
	public FuzzySystem(List<FuzzyInput> inputs, List<FuzzyOutput> outputs, List<FuzzyRule> rules) {
		Objects.requireNonNull(rules);
		Objects.requireNonNull(inputs);
		Objects.requireNonNull(outputs);
		this.rules = rules;
		this.inputs = inputs;
		this.outputs = outputs;
	}
	
	public int inputSize() {
		return inputs.size();
	}

	public int outputSize() {
		return outputs.size();
	}

	public int numberOfRules() {
		return rules.size();
	}

	public FuzzySystem addInput(FuzzySet setA, FuzzySet setB, FuzzySet... otherSets) {
		List<FuzzyInput> newInputs = new ArrayList<>();
		newInputs.addAll(inputs);
		FuzzySet[] sets = new FuzzySet[otherSets.length + 2];
		sets[0] = setA;
		sets[1] = setB;
		System.arraycopy(otherSets, 0, sets, 2, otherSets.length);
		newInputs.add(new FuzzyInput(sets));
		return new FuzzySystem(newInputs, outputs, rules);
	}

	public FuzzySystem addOutput(FuzzySet setA, FuzzySet setB, FuzzySet... otherSets) {
		List<FuzzyOutput> newOutputs = new ArrayList<>();
		newOutputs.addAll(outputs);
		FuzzySet[] sets = new FuzzySet[otherSets.length + 2];
		sets[0] = setA;
		sets[1] = setB;
		System.arraycopy(otherSets, 0, sets, 2, otherSets.length);
		newOutputs.add(new FuzzyOutput(sets));
		return new FuzzySystem(inputs, newOutputs, rules);
	}

	public FuzzyInput input(int index) {
		return inputs.get(index);
	}

	public FuzzyOutput output(int index) {
		return outputs.get(index);
	}

	public FuzzySystem addRule(FuzzySet[] outputSets, FuzzySet set) {
		List<FuzzyRule> newRules = new ArrayList<>();
		newRules.addAll(rules);
		newRules.add(FuzzyRule.of(set));
		// TODO verify set belongs to at least one input
		return new FuzzySystem(inputs, outputs, newRules);
	}

	public FuzzySystem addRuleAnd(FuzzySet[] outputSets, FuzzySet setA, FuzzySet setB, FuzzySet... otherSets) {
		List<FuzzyRule> newRules = new ArrayList<>();
		newRules.addAll(rules);
		newRules.add(FuzzyRule.and(setA, setB, otherSets));
		// TODO verify set belongs to at least one input
		return new FuzzySystem(inputs, outputs, newRules);
	}

	public FuzzySystem addRuleOr(FuzzySet[] outputSets, FuzzySet setA, FuzzySet setB, FuzzySet... otherSets) {
		List<FuzzyRule> newRules = new ArrayList<>();
		newRules.addAll(rules);
		newRules.add(FuzzyRule.or(setA, setB, otherSets));
		// TODO verify set belongs to at least one input
		return new FuzzySystem(inputs, outputs, newRules);
	}
}
