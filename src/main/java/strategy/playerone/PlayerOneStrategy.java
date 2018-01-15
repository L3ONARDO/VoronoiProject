package com.voronoiStrategy.playerone;

import com.model.Point;
import com.voronoiStrategy.Strategy;

import java.util.HashSet;
import java.util.Set;

public abstract class PlayerOneStrategy implements Strategy {
    protected static final boolean OWNER = true;

    /**
     * Transforms a given x-oriented point set into a y-oriented point set.
     * @param toTranform the set to transform.
     * @param xmax the maximal x-value of the bounding box.
     * @param ymax the maximal y-value of the bounding box.
     * @return {@link Set<Point>}.
     */
    protected Set<Point> transformY(Set<Point> toTranform, float xmax, float ymax) {
        Set<Point> result = new HashSet<Point>();
        float x = xmax / 2.0f; // The transformed x-coordinate.
        for (Point point : toTranform) {
            float y = (point.getX() / xmax) * ymax; // The transformed y-coordinate.
            Point transformedPoint = new Point(OWNER, x, y);
            result.add(transformedPoint);
        }
        return result;
    }
}
