package model;

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
        if (edge.getP1().getX() == edge.getP2().getX()) return edge.getP1().getX() == x;
        float a1 = (y - edge.getP1().getY()) / (x - edge.getP1().getX());
        float a2 = (y - edge.getP2().getY()) / (x - edge.getP2().getX());
        return a1 == a2 && xInRange(edge.getP1().getX(), edge.getP2().getX());
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
}
