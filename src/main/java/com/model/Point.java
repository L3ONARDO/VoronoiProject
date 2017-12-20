package com.model;

import java.util.Set;

public class Point {
    private boolean owner;
    private float x, y;

    public Point(boolean owner, float x, float y) {
        this.owner = owner;
        this.x = x;
        this.y = y;
    }

    public boolean getOwner() {
        return owner;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    /**
     * Return the euclidean distance from {@code this} {@link Point} to {@link Point} {@code point}.
     * @param point the {@link Point} to find the distance to.
     * @return {@link float}.
     */
    public float distanceTo(Point point) {
        return (float) Math.sqrt(((x - point.getX()) * (x - point.getX())) + ((y - point.getY()) * (y - point.getY())));
    }

    /**
     * Find the extreme (i.e. closest or furthest) {@link Point} {@code point} from {@code this}.
     * @param points the point set to find the extreme point in.
     * @param closest {@code true} iff the extreme point is the closest point.
     *                            The extreme point is the furthest point otherwise.
     * @return {Link Point}.
     */
    public Point findExtremePoint(Set<Point> points, boolean closest) {
        if (points.isEmpty()) throw new IllegalArgumentException("Must have points to compare to.");
        Point result = points.iterator().next();
        float distance = result.distanceTo(this);
        for (Point p : points) {
            float newDistance = p.distanceTo(this);
            if (closest) {
                if (newDistance < distance) result = p;
            } else {
                if (newDistance > distance) result = p;
            }
        }
        return result;
    }
}
