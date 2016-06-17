package com.nextbreakpoint.fuzzylogic;

import java.util.*;

public class FuzzySystem {
	private final List<FuzzyRule> rules;
	private final List<FuzzyInput> inputs;
	private final List<FuzzyOutput> outputs;

	private FuzzySystem() {
		this(Collections.emptyList(), Collections.emptyList(), Collections.emptyList());
	}

	private FuzzySystem(List<FuzzyInput> inputs, List<FuzzyOutput> outputs, List<FuzzyRule> rules) {
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

	public FuzzySystem addInput(FuzzyInput input) {
		List<FuzzyInput> newInputs = new ArrayList<>();
		newInputs.addAll(inputs);
		newInputs.add(input);
		return new FuzzySystem(newInputs, outputs, rules);
	}

	public FuzzySystem addOutput(FuzzyOutput output) {
		List<FuzzyOutput> newOutputs = new ArrayList<>();
		newOutputs.addAll(outputs);
		newOutputs.add(output);
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

	public static FuzzySystem empty() {
		return new FuzzySystem();
	}

	public double[] evaluate(Map<String, Double> inputs) {
		double[] outputs = new double[outputSize()];
//		FuzzyValue valueA = fuzzyRuleA.apply(value);
//		FuzzyValue valueB = fuzzyRuleB.apply(value);
//
//		double result = FuzzyPredicate.or(fuzzySetOutputA.limit(valueA), fuzzySetOutputB.limit(valueB)).centroid(-1.5 + 0.3, 1.5 + 0.3, 25);
//		rules.stream().map(rule -> rule.evaluate(0)).map(sets -> sets.)
		return outputs;
	}
}
