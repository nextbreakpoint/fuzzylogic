package com.nextbreakpoint.fuzzylogic;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class FuzzySystem {
	private final List<FuzzyRule> rules;

	private FuzzySystem() {
		this(Collections.emptyList());
	}

	private FuzzySystem(List<FuzzyRule> rules) {
		Objects.requireNonNull(rules);
		this.rules = rules;
	}
	
	public int numberOfRules() {
		return rules.size();
	}

	public FuzzySystem addRule(FuzzyRule rule) {
		List<FuzzyRule> newRules = new ArrayList<>();
		newRules.addAll(rules);
		newRules.add(rule);
		return new FuzzySystem(newRules);
	}

	public static FuzzySystem empty() {
		return new FuzzySystem();
	}

	public Map<String, Double> evaluate(Map<String, Double> inputs, double start, double end, int steps) {
		return rules.stream().map(rule -> rule.evaluate(inputs)).collect(Collector.of(supplier(), accumulator(), combiner(), finisher(start, end, steps)));
	}

	private Supplier<Map<String, List<FuzzyMembership>>> supplier() {
		return () -> new HashMap<String, List<FuzzyMembership>>();
	}

	private BinaryOperator<Map<String, List<FuzzyMembership>>> combiner() {
		return (map1, map2) -> { map1.putAll(map2); return map1; };
	}

	private BiConsumer<Map<String, List<FuzzyMembership>>, FuzzyVariable[]> accumulator() {
		return (map, vars) -> combine(map, vars);
	}

	private Function<Map<String, List<FuzzyMembership>>, Map<String, Double>> finisher(double start, double end, int steps) {
		return map -> map.entrySet().stream().collect(supplier2(), accumulator2(start, end, steps), combiner2());
	}

	private void combine(Map<String, List<FuzzyMembership>> map, FuzzyVariable[] vars) {
		Arrays.stream(vars).forEach(var -> combine(map, var));
	}

	private void combine(Map<String, List<FuzzyMembership>> map, FuzzyVariable var) {
		List<FuzzyMembership> sets = map.get(var.name());
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

	private BiConsumer<Map<String, Double>, Map.Entry<String, List<FuzzyMembership>>> accumulator2(double start, double end, int steps) {
		return (map, entry) -> combine(map, entry, start, end, steps);
	}

	private void combine(Map<String, Double> map, Map.Entry<String, List<FuzzyMembership>> entry, double start, double end, int steps) {
		map.put(entry.getKey(), FuzzyMembership.of(entry.getValue()).centroid(start, end, steps));
	}
}
