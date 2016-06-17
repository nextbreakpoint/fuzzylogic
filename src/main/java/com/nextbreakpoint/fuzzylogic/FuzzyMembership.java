package com.nextbreakpoint.fuzzylogic;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

@FunctionalInterface
public interface FuzzyMembership {
	public FuzzyValue apply(double value);
	
	public default FuzzyMembership limit(FuzzyValue limit) {
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

	public static FuzzyMembership triangle(double begin, double end) {
		return value -> FuzzyValue.of(value < begin ? 0 : value < begin + ((end - begin) / 2) ? (value - begin) / ((end - begin) / 2) : value < end ? (end - value) / ((end - begin) / 2) : 0);
	}

	public static FuzzyMembership trapezoid(double begin, double end, double delta) {
		return value -> FuzzyValue.of(value < begin ? 0 : value < begin + delta ? (value - begin) / delta : value < end - delta ? 1 : value < end ? (end - value) / delta : 0);
	}

	public static FuzzyMembership constant(double v) {
		return value -> FuzzyValue.of(v);
	}

	public static FuzzyMembership of(FuzzyMembership membership, FuzzyMembership... otherMemberships) {
		FuzzyMembership[] memberships = new FuzzyMembership[otherMemberships.length + 1];
		memberships[0] = membership;
		System.arraycopy(otherMemberships, 0, memberships, 1, otherMemberships.length);
		return new MergedMembership(memberships);
	}

	public static FuzzyMembership of(Collection<FuzzyMembership> memberships) {
		return new MergedMembership(memberships.toArray(new FuzzyMembership[0]));
	}

	public class MergedMembership implements FuzzyMembership {
		private final FuzzyMembership[] memberships;

		private MergedMembership(FuzzyMembership[] memberships) {
			Objects.nonNull(memberships);
			if (Arrays.stream(memberships).anyMatch(membership -> membership == null)) {
				throw new NullPointerException("Membership can't be null");
			}
			this.memberships = memberships;
		}

		@Override
		public FuzzyValue apply(double value) {
			if (memberships.length == 0) {
				return FuzzyValue.of(1);
			} else {
				double result = 1;
				for (int i = 0; i < memberships.length; i++) {
					result *= 1 - memberships[i].apply(value).get();
				}
				return FuzzyValue.of(1 - result);
			}
		}
	}
}
