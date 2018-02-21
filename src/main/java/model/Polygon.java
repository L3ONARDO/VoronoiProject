package model;

import util.MathUtils;

import java.util.ArrayList;
import java.util.List;

public class Polygon {
    private List<Point> points;
    private boolean owner;

    public boolean getOwner() {
        return owner;
    }

    public void setOwner(boolean owner) {
        this.owner = owner;
    }

    public Polygon() {
        this.points = new ArrayList<>();
    }

    public void addPoint(Point point) {
        points.add(point);
    }

    public List<Point> getPoints() {
        return points;
    }

    public boolean deepContainsPoint(Point point) {
        for (Point p  : points) {
            float xdiff = Math.abs(p.getX() - point.getX());
            float ydiff = Math.abs(p.getY() - point.getY());
            if (xdiff < MathUtils.EPSILON && ydiff < MathUtils.EPSILON) return true;
        }
        return false;
    }

    public List<LineInterface> getLines() {
        List<LineInterface> result = new ArrayList<>();
        for (int i = 0; i < points.size(); i++) {
            Point p = points.get(i);
            Point q = points.get((i + 1) % points.size());
            if (p.getX() == q.getX()) {
                result.add(new VerticalLine(p.getX()));
            } else {
                result.add(new Line(p, q));
            }
        }
        return result;
    }

    /**
     * Calculates the area of a (assumed convex) polygon given a list of ordered vertices.
     * @return {@code double}
     */
    public float getArea() {
        float sum = 0f;
        for (int i = 0; i < points.size(); i++) {
            Point p1 = points.get(i);
            Point p2 = points.get((i + 1) % points.size());
            sum += (p1.getX() * p2.getY() - p2.getX() * p1.getY());
        }
        return (float) (0.5 * Math.abs(sum));
    }
}
