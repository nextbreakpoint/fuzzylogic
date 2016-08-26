package com.nextbreakpoint.fuzzylogic;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.function.Function;

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
		if (yNum == 0) {
			return 0;
		}
		if (yDen == 0) {
			// it should never happen
			throw  new IllegalStateException();
		}
		return yNum / yDen;
	}

	public default FuzzyMembership translate(double v) {
		return value -> apply(value - v);
	}

	public default FuzzyMembership scale(double v) {
		return value -> apply(value / v);
	}

	public static FuzzyMembership triangle() {
		return value -> FuzzyValue.of(FuzzyMath.triangle(-0.5, 0.5).apply(value));
	}

//	public static FuzzyMembership triangle(double begin, double end) {
//		return value -> FuzzyValue.of(FuzzyMath.triangle(begin, end).apply(value));
//	}

//	public static FuzzyMembership trapezoid(double begin, double end, double delta) {
//		return value -> FuzzyValue.of(FuzzyMath.trapezoid(begin, end, delta).apply(value));
//	}
//
//	public static FuzzyMembership line(double begin, double end) {
//		return value -> FuzzyValue.of(FuzzyMath.line(begin, end).apply(value));
//	}

	public static FuzzyMembership inverse(FuzzyMembership membership) {
		return value -> FuzzyValue.of(1 - membership.apply(value).get());
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
