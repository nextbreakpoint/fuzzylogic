package com.nextbreakpoint.fuzzylogic;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

@FunctionalInterface
public interface FuzzySet {
	public FuzzyValue apply(double value);
	
	public default FuzzySet limit(FuzzyValue limit) {
		return value -> FuzzyValue.of(Math.min(apply(value).get(), limit.get()));
	}

	public default double centroid(double begin, double end, int steps) {
		if (steps < 2) {
			return 0;
		}
		double y = 0;
		double yNum = 0;
		double yDen = 0;
		double x = begin;
		double dx = (end - begin) / (steps - 1);
		for (int i = 0; i < steps; i++) {
			y = apply(x).get();
			yNum += y * x;
			yDen += y;
			x += dx;
		}
		return yNum / yDen;
	}

	public static FuzzySet triangle(double begin, double end) {
		return value -> FuzzyValue.of(value < begin ? 0 : value < begin + ((end - begin) / 2) ? (value - begin) / ((end - begin) / 2) : value < end ? (end - value) / ((end - begin) / 2) : 0);
	}

	public static FuzzySet trapezoid(double begin, double end, double delta) {
		return value -> FuzzyValue.of(value < begin ? 0 : value < begin + delta ? (value - begin) / delta : value < end - delta ? 1 : value < end ? (end - value) / delta : 0);
	}

	public static FuzzySet constant(double v) {
		return value -> FuzzyValue.of(v);
	}

	public static FuzzySet of(FuzzySet set, FuzzySet... otherSets) {
		FuzzySet[] sets = new FuzzySet[otherSets.length + 1];
		sets[0] = set;
		System.arraycopy(otherSets, 0, sets, 1, otherSets.length);
		return new MergedSet(sets);
	}

	public static FuzzySet of(Collection<FuzzySet> collection) {
		return new MergedSet(collection.toArray(new FuzzySet[0]));
	}

	public class MergedSet implements FuzzySet {
		private final FuzzySet[] sets;

		private MergedSet(FuzzySet[] sets) {
			Objects.nonNull(sets);
			if (Arrays.stream(sets).anyMatch(set -> set == null)) {
				throw new NullPointerException("Set can't be null");
			}
			this.sets = sets;
		}

		@Override
		public FuzzyValue apply(double value) {
			if (sets.length == 0) {
				return FuzzyValue.of(1);
			} else {
				double result = 1;
				for (int i = 0; i < sets.length; i++) {
					result *= 1 - sets[i].apply(value).get();
				}
				return FuzzyValue.of(1 - result);
			}
		}
	}
}
