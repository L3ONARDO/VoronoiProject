package com.model;

public class Edge {
    private Point p1, p2;

    public Edge(Point p1, Point p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    public Point getP1() {
        return p1;
    }

    public Point getP2() {
        return p2;
    }

    public boolean isIllegal(Triangle t1, Triangle t2) {
        Circle c = new Circle(t1.getP1(), t1.getP2(), t1.getP3());
        return c.isInside(t2.getThirdPoint(p1, p2));
    }
}
