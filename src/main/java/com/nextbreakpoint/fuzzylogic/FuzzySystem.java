package com.nextbreakpoint.fuzzylogic;

import java.util.*;
import java.util.function.Function;
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
		return rules.stream().map(rule -> rule.evaluate(inputs))
			.collect(collector(start, end, steps));
	}

	private Collector<FuzzyVariable[], Map<String, List<FuzzyMembership>>, Map<String, Double>> collector(double start, double end, int steps) {
		return Collector.of(() -> new HashMap<String, List<FuzzyMembership>>(),
        	(map, vars) -> Arrays.stream(vars).forEach(var -> accumulate(map, var)),
        	(map1, map2) -> { map1.putAll(map2); return map1; },
        	finisher(start, end, steps));
	}

	private Function<Map<String, List<FuzzyMembership>>, Map<String, Double>> finisher(double start, double end, int steps) {
		return map -> map.entrySet().stream().collect(() -> new HashMap<String, Double>(),
			(mapSets, entry) -> mapSets.put(entry.getKey(), centroid(entry.getValue(), end, steps, start)),
			(map1, map2) -> map1.putAll(map2));
	}

	private double centroid(List<FuzzyMembership> set, double end, int steps, double start) {
		return FuzzyMembership.of(set).centroid(start, end, steps);
	}

	private void accumulate(Map<String, List<FuzzyMembership>> map, FuzzyVariable var) {
		List<FuzzyMembership> sets = map.get(var.name());
		if (sets == null) {
			sets = new LinkedList<>();
			map.put(var.name(), sets);
		}
		sets.add(var.set());
	}
}
