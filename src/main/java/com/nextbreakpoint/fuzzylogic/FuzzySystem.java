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

	public FuzzySystem addInput(FuzzySet set, FuzzySet... otherSets) {
		List<FuzzyInput> newInputs = new ArrayList<>();
		newInputs.addAll(inputs);
		newInputs.add(FuzzyInput.of(set, otherSets));
		return new FuzzySystem(newInputs, outputs, rules);
	}

	public FuzzySystem addOutput(FuzzySet set, FuzzySet... otherSets) {
		List<FuzzyOutput> newOutputs = new ArrayList<>();
		newOutputs.addAll(outputs);
		newOutputs.add(FuzzyOutput.of(set, otherSets));
		return new FuzzySystem(inputs, newOutputs, rules);
	}

	public FuzzySystem addRule(FuzzyRule rule) {
		List<FuzzyRule> newRules = new ArrayList<>();
		newRules.addAll(rules);
		newRules.add(rule);
		// TODO verify set belongs to at least one input
		return new FuzzySystem(inputs, outputs, newRules);
	}

	public FuzzyInput input(int index) {
		return inputs.get(index);
	}

	public FuzzyOutput output(int index) {
		return outputs.get(index);
	}
}
