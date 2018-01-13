package model;

import java.util.ArrayList;
import java.util.List;

public class Edge {
    private Point p1, p2;
    private List<Triangle> triangles;

    public Edge(Point p1, Point p2) {
        this.p1 = p1;
        this.p2 = p2;
        this.triangles = new ArrayList<>();
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
}
