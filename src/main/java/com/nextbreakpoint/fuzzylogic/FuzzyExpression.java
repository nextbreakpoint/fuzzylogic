package com.nextbreakpoint.fuzzylogic;

import java.util.Map;

/**
 * Created by medeghinia on 17/06/2016.
 */
public interface FuzzyExpression {
    public FuzzyValue evaluate(Map<String, Double> inputs);
}
