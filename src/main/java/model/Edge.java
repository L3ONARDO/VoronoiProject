package model;

import java.util.ArrayList;
import java.util.List;

public class Edge {
    private Point p1, p2;
    private List<Triangle> triangles;
    private int polygonCount;

    public Edge(Point p1, Point p2) {
        this.p1 = p1;
        this.p2 = p2;
        this.triangles = new ArrayList<>();
        this.polygonCount = 0;
    }

    public void incrementPolygonCount() {
        polygonCount++;
    }

    public int getPolygonCount() {
        return polygonCount;
    }

    public void setPolygonCount(int polygonCount) {
        this.polygonCount = polygonCount;
    }

    public Point getLeft(){
        if(p1.getX() > p2.getX()) {
            Point temp = p2;
            p2 = p1;
            p1 = temp;
        }
        return p1;
    }

    public Point getRight(){
        if(p1.getX() > p2.getX()) {
            return p1;
        }
        return p2;
    }

    public Point getP1() {
        return p1;
    }

    public Point getP2() {
        return p2;
    }

    public List<Triangle> getTriangles() {
        return triangles;
    }

    public LineInterface getBisectorLine() {
        float px = p1.getX() + ((p2.getX() - p1.getX())  / 2f);
        float py = p1.getY() + ((p2.getY() - p1.getY()) / 2f);
        float a = -1f / ((p2.getY() - p1.getY())/(p2.getX() - p1.getX()));
        if (a == Float.POSITIVE_INFINITY || a == Float.NEGATIVE_INFINITY) return new VerticalLine(px);
        float b = py - a * px;
        return new Line(-a, 1, -b);
    }

    public Point getBisectionalPoint() {
        return new Point(p1.getOwner(),(p1.getX() + p2.getX()) / 2,
                (p1.getY() + p2.getY()) / 2);
    }

    public Line getLine() {
        return new Line(p1, p2);
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Edge)) return false;
        Edge edge = (Edge) object;
        return p1.equals(edge.getP1()) && p2.equals(edge.getP2()) ||
                p1.equals(edge.getP2()) && p2.equals(edge.getP1());
    }

    @Override
    public String toString() {
        return "(" + p1.toString() + ", " + p2.toString() + ")";
    }

    public boolean isIllegal(Triangle t1, Triangle t2) {
        Circle c = new Circle(t1.getP1(), t1.getP2(), t1.getP3());
        if (c.getCenter() == null) return false;
        return c.isInside(t2.getThirdPoint(p1, p2));
    }

    // Clock-wise angle from this to e
    public float getAngleWith(Edge e) {
        float x1 = p2.getX() - p1.getX();
        float x2 = e.p2.getX() - e.p1.getX();
        float y1 = p2.getY() - p1.getY();
        float y2 = e.p2.getY() - e.p1.getY();
        float result = (float) (Math.PI - Math.atan2(x1 * y2 - y1 * x2, x1* x2 + y1 * y2));
        return result;
    }

    public float getLength() {
        return (float) Math.sqrt((p2.getX() - p1.getX()) * (p2.getX() - p1.getX()) + (p2.getY() - p1.getY()) * (p2.getY() - p1.getY()));
    }
}
