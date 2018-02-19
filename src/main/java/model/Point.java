package model;

import java.util.Set;

public class Point {
    private boolean owner;
    private float x, y;

    public Point(boolean owner, float x, float y) {
        this.owner = owner;
        if (Math.abs(x) < 1E-4) x = 0;
        if (Math.abs(y) < 1E-4) y = 0;
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
        if (edge.getP1().getX() == edge.getP2().getX()) return edge.getP1().getX() == x && yInRange(edge.getP1().getY(), edge.getP2().getY());
        float a1 = (y - edge.getP1().getY()) / (x - edge.getP1().getX());
        float a2 = (y - edge.getP2().getY()) / (x - edge.getP2().getX());
        float diff = Math.abs(a1 - a2);
        return (diff < 1E-4) && xInRange(edge.getP1().getX(), edge.getP2().getX());
    }

    private boolean yInRange(float y1, float y2) {
        return (y1 <= y && y <= y2) || (y2 <= y && y <= y1);
    }

    private boolean xInRange(float x1, float x2) {
        return (x1 <= x && x <= x2) || (x2 <= x && x <= x1);
    }

    public float dist(Point p0) {
        return (float) Math.sqrt((this.getX() - p0.getX()) * (this.getX() - p0.getX()) +
                (this.getY() - p0.getY()) * (this.getY() - p0.getY()));
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
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

    public boolean inPolygon(Polygon polygon) {
        int i, j = polygon.getPoints().size() - 1;
        boolean oddNodes = false;
        for (i = 0; i < polygon.getPoints().size(); i++) {
            Point vi = polygon.getPoints().get(i);
            Point vj = polygon.getPoints().get(j);
            if (vi.getY() < y && vj.getY() >= y || vj.getY() < y && vi.getY() >= y) {
                if (vi.getX() + (y - vi.getY()) / (vj.getY() - vi.getY()) * (vj.getX() - vi.getX()) < x) {
                    oddNodes = !oddNodes;
                }
            }
            j = i;
        }
        return oddNodes;
    }
}
