package com.voronoiStrategy;

import com.model.Point;

import java.util.Set;

/**
 * Defines a strategy pair.
 * Use this class to run a configuration of two strategies, rather than running them separately.
 */
public class StrategyPair {
    private Strategy strategyOne, strategyTwo;

    public StrategyPair(Strategy strategyOne, Strategy strategyTwo) {
        this.strategyOne = strategyOne;
        this.strategyTwo = strategyTwo;
    }

    /**
     * Applies the first strategy
     * @param m the number of points for strategyOne.
     * @param n the number of points for strategyTwo.
     * @param xmax the maximal x-coordinate of the bounding box. The minimal x-coordinate is assumed to be 0.
     * @param ymax the maximal y-coordinate of the bounding box. The minimal y-coordinate is assumed to be 0.
     * @return {@link Set<Point>}.
     */
    public Set<Point> apply(int m, int n, float xmax, float ymax) {
        if (m <= n) {
            throw new IllegalArgumentException("Cannot have m smaller than- or equal to n.");
        }
        if (xmax < 1.0f || ymax < 1.0f) {
            throw new IllegalArgumentException("Must have at least unit size playing field.");
        }
        Set<Point> result = strategyOne.apply(m, null, xmax, ymax);
        result.addAll(strategyTwo.apply(n, result, xmax, ymax));
        return result;
    }
}
