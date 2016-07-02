package com.nextbreakpoint.fuzzylogic;

import java.util.Map;

public interface FuzzyExpression {
    public FuzzyValue evaluate(Map<String, Double> inputs);
}
