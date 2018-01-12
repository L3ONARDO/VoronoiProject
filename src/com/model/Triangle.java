package com.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Triangle {
    private Point p1, p2, p3;
    private Edge side1, side2, side3;
    private Map<Edge, Triangle> adjacency;

    public Triangle(Point p1, Point p2, Point p3) {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        this.side1 = new Edge(p1, p2);
        this.side2 = new Edge(p2, p3);
        this.side3 = new Edge(p3, p1);
        this.adjacency = new HashMap<>();
    }

    /**
     * From https://stackoverflow.com/questions/2049582/how-to-determine-if-a-point-is-in-a-2d-triangle
     */
    public boolean containsPoint(Point p) {
        boolean b1, b2, b3;

        b1 = sign(p, p1, p2) < 0.0f;
        b2 = sign(p, p2, p3) < 0.0f;
        b3 = sign(p, p3, p1) < 0.0f;

        return ((b1 == b2) && (b2 == b3));
    }

    public void link(Triangle triangle) {
        for (Edge e1 : this.getEdges()) {
            for (Edge e2 : triangle.getEdges()) {
                if (e1.equals(e2)) {
                    adjacency.put(e1, triangle);
                    triangle.getAdjacency().put(e2, this);
                }
            }
        }
    }

    public Point getP1() {
        return p1;
    }

    public Point getP2() {
        return p2;
    }

    public Point getP3() {
        return p3;
    }

    public List<Edge> getEdges() {
        List<Edge> result = new ArrayList<>();
        result.add(side1);
        result.add(side2);
        result.add(side3);
        return result;
    }

    public Map<Edge, Triangle> getAdjacency() {
        return adjacency;
    }

    private float sign (Point p1, Point p2, Point p3) {
        return (p1.getX() - p3.getX()) * (p2.getY() - p3.getY()) - (p2.getX() - p3.getX()) * (p1.getY() - p3.getY());
    }

    public Edge getBoundary(Point p) {
        for (Edge edge : getEdges()) {
            if (p.onEdge(edge)) return edge;
        }
        return null;
    }

    public Point getThirdPoint(Point p1, Point p2) {
        if (!this.p1.equals(p1) && !this.p1.equals(p2)) return this.p1;
        if (!this.p2.equals(p1) && !this.p2.equals(p2)) return this.p2;
        return this.p3;
    }
}
