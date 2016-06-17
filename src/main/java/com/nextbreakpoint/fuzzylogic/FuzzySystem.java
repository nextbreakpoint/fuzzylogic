package com.nextbreakpoint.fuzzylogic;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

	public Map<String, Double> evaluate(Map<String, Double> inputs, double start, double end, int steps) {
//		Set<String> validInputs = this.inputs.stream().map(input -> input.name()).collect(Collectors.toSet());
//		Set<String> validOutputs = this.outputs.stream().map(output -> output.name()).collect(Collectors.toSet());
		return rules.stream().map(rule -> rule.evaluate(inputs)).collect(Collector.of(supplier(), accumulator(), combiner(), finisher(start, end, steps)));
	}

	private Supplier<Map<String, List<FuzzySet>>> supplier() {
		return () -> new HashMap<String, List<FuzzySet>>();
	}

	private BinaryOperator<Map<String, List<FuzzySet>>> combiner() {
		return (map1, map2) -> { map1.putAll(map2); return map1; };
	}

	private BiConsumer<Map<String, List<FuzzySet>>, FuzzyVariable[]> accumulator() {
		return (map, vars) -> combine(map, vars);
	}

	private Function<Map<String, List<FuzzySet>>, Map<String, Double>> finisher(double start, double end, int steps) {
		return map -> map.entrySet().stream().collect(supplier2(), accumulator2(start, end, steps), combiner2());
	}

	private void combine(Map<String, List<FuzzySet>> map, FuzzyVariable[] vars) {
		Arrays.stream(vars).forEach(var -> combine(map, var));
	}

	private void combine(Map<String, List<FuzzySet>> map, FuzzyVariable var) {
		List<FuzzySet> sets = map.get(var.name());
		if (sets == null) {
			sets = new LinkedList<>();
			map.put(var.name(), sets);
		}
		sets.add(var.set());
	}

	private Supplier<Map<String, Double>> supplier2() {
		return () -> new HashMap<String, Double>();
	}

	private BiConsumer<Map<String, Double>, Map<String, Double>> combiner2() {
		return (map1, map2) -> map1.putAll(map2);
	}

	private BiConsumer<Map<String, Double>, Map.Entry<String, List<FuzzySet>>> accumulator2(double start, double end, int steps) {
		return (map, entry) -> combine(map, entry, start, end, steps);
	}

	private void combine(Map<String, Double> map, Map.Entry<String, List<FuzzySet>> entry, double start, double end, int steps) {
		map.put(entry.getKey(), FuzzySet.of(entry.getValue()).centroid(start, end, steps));
	}
}
