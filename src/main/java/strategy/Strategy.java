package com.strategy;

import com.model.Point;

import java.util.Set;

public abstract class Strategy {

    public Set<Point> run(int m, int n, float xmax, float ymax) {
        if (m <= n) {
            throw new IllegalArgumentException("Cannot have m smaller than- or equal to n.");
        }
        if (xmax < 1.0f || ymax <= 1.0f) {
            throw new IllegalArgumentException("Must have at least unit size playing field.");
        }
        return apply(m , n, xmax, ymax);
    }

    protected abstract Set<Point> apply(int m, int n, float xmax, float ymax);
}
