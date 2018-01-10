package com.model;

import java.util.Objects;

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
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return owner == point.owner &&
                Float.compare(point.x, x) == 0 &&
                Float.compare(point.y, y) == 0;
    }

    public boolean onEdge(Edge edge) {
        Line line = new Line(edge.getP1(), edge.getP2());
        return x == line.solveForX(y);
    }
}
