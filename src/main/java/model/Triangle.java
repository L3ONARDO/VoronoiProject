package model;

import model.TriangulationDAG.TriangulationDAG;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Triangle {
    private Point p1, p2, p3;
    private Edge side1, side2, side3;
    private Map<Edge, Triangle> adjacency;
    private TriangulationDAG triangulationDAG;

    public Triangle(Point p1, Point p2, Point p3) {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        this.side1 = new Edge(p1, p2);
        this.side2 = new Edge(p2, p3);
        this.side3 = new Edge(p3, p1);
        this.adjacency = new HashMap<>();
        this.triangulationDAG = new TriangulationDAG(this);
    }

    /**
     * From https://stackoverflow.com/questions/2049582/how-to-determine-if-a-point-is-in-a-2d-triangle
     */
    public boolean containsPoint(Point p) {
        /* Calculate area of triangle ABC */
        float A = area(p1.getX(), p1.getY(), p2.getX(), p2.getY(), p3.getX(), p3.getY());

        /* Calculate area of triangle PBC */
        float A1 = area(p.getX(), p.getY(), p2.getX(), p2.getY(), p3.getX(), p3.getY());

        /* Calculate area of triangle PAC */
        float A2 = area(p1.getX(), p1.getY(), p.getX(), p.getY(), p3.getX(), p3.getY());

        /* Calculate area of triangle PAB */
        float A3 = area(p1.getX(), p1.getY(), p2.getX(), p2.getY(), p.getX(), p.getY());

        /* Check if sum of A1, A2 and A3 is same as A */
        float diff = Math.abs(A - (A1 + A2 + A3));
        return diff < 0.001;
    }

    /* A utility function to calculate area of triangle
      formed by (x1, y1) (x2, y2) and (x3, y3) */
    private float area(float x1, float y1, float x2, float y2,
                      float x3, float y3)
    {
        return (float) Math.abs((x1*(y2-y3) + x2*(y3-y1)+
                x3*(y1-y2))/2.0);
    }

    public void link(Triangle triangle) {
        for (Edge e1 : this.getEdges()) {
            for (Edge e2 : triangle.getEdges()) {
                if (e1.equals(e2)) {
                    addAdjacency(e1, triangle);
                    triangle.addAdjacency(e2, this);
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

    public void addAdjacency(Edge edge, Triangle triangle) {
        if (edge == null || triangle == null) return;
        Map<Edge, Triangle> newAdjacency = new HashMap<>();
        for (Map.Entry<Edge, Triangle> entry : adjacency.entrySet()) {
            if (!entry.getKey().equals(edge)) {
                newAdjacency.put(entry.getKey(), entry.getValue());
            }
        }
        newAdjacency.put(edge, triangle);
        adjacency = newAdjacency;
    }

    public TriangulationDAG getTriangulationDAG() {
        return triangulationDAG;
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

    @Override
    public String toString() {
        return "(" + p1.toString() + ", " + p2.toString() + ", " + p3.toString() + ")";
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Triangle)) return false;
        Triangle triangle = (Triangle) object;
        return (p1.equals(triangle.p1) || p1.equals(triangle.p2) || p1.equals(triangle.p3)) &&
                (p2.equals(triangle.p1) || p2.equals(triangle.p2) || p2.equals(triangle.p3)) &&
                (p3.equals(triangle.p1) || p3.equals(triangle.p2) || p3.equals(triangle.p3));
    }

    public Triangle getAdjacentTriangle(Edge boundary) {
        for (Map.Entry<Edge, Triangle> entry : adjacency.entrySet()) {
            if (entry.getKey().equals(boundary)) return entry.getValue();
        }
        return null;
    }
}
