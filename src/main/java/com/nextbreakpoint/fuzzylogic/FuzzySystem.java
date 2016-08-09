package com.nextbreakpoint.fuzzylogic;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collector;

public class FuzzySystem {
	private final List<FuzzyRule> rules;
	private final int steps;

	private FuzzySystem() {
		this(Collections.emptyList());
	}

	private FuzzySystem(List<FuzzyRule> rules) {
		this(rules, 500);
	}

	private FuzzySystem(List<FuzzyRule> rules, int steps) {
		Objects.requireNonNull(rules);
		this.rules = rules;
		this.steps = steps;
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

	public FuzzySystem withSteps(int steps) {
		return new FuzzySystem(rules, steps);
	}

	public static FuzzySystem empty() {
		return new FuzzySystem();
	}

	public Map<String, Double> evaluate(Map<String, Double> inputs) {
		return rules.stream().map(rule -> rule.evaluate(inputs)).collect(collector());
	}

	private Collector<FuzzyVariable[], Map<String, Pair<FuzzyDomain, List<FuzzyMembership>>>, Map<String, Double>> collector() {
		return Collector.of(() -> new HashMap<String, Pair<FuzzyDomain, List<FuzzyMembership>>>(),
        	(map, vars) -> Arrays.stream(vars).forEach(var -> accumulate(map, var)),
        	(map1, map2) -> { map1.putAll(map2); return map1; },
        	finisher());
	}

	private Function<Map<String, Pair<FuzzyDomain, List<FuzzyMembership>>>, Map<String, Double>> finisher() {
		return map -> map.entrySet().stream().collect(() -> new HashMap<String, Double>(),
			(mapSets, entry) -> mapSets.put(entry.getKey(), centroid(entry.getValue())),
			(map1, map2) -> map1.putAll(map2));
	}

	private double centroid(Pair<FuzzyDomain, List<FuzzyMembership>> pair) {
		return FuzzyMembership.of(pair.right).centroid(pair.left.min(), pair.left.max(), steps);
	}

	private void accumulate(Map<String, Pair<FuzzyDomain, List<FuzzyMembership>>> map, FuzzyVariable var) {
		Pair<FuzzyDomain, List<FuzzyMembership>> pair = map.get(var.name());
		if (pair == null) {
			pair = new Pair(var.domain(), new LinkedList<>());
			pair.right.add(var.membership());
			map.put(var.name(), pair);
		} else {
			pair.left = FuzzyDomain.merge(pair.left, var.domain());
			pair.right.add(var.membership());
		}
	}

	private class Pair<T,W> {
		T left;
		W right;

		public Pair(T left, W right) {
			this.left = left;
			this.right = right;
		}
	}
}
