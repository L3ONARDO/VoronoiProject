package com.model;

import com.model.TriangulationDAG.DAGLocation;
import com.model.TriangulationDAG.TriangulationDAG;

import java.util.*;

public class DelaunayTriangulation {

    public List<Edge> calculate(Set<Point> points) {
        Triangle outerTriangle = constructOuterTriangle(points);

        TriangulationDAG triangulationDAG = new TriangulationDAG(outerTriangle);

        List<Point> permutation = randomShuffle(points);

        while (!permutation.isEmpty()) {
            Point p = permutation.get(0);
            permutation.remove(0);

            DAGLocation location = triangulationDAG.locatePoint(p);
            Triangle triangle = location.getTriangle();
            TriangulationDAG dag = location.getTriangulationDAG();

            if (pointOnBoundary(p, triangle)) {
                // Split into four triangles
                Edge boundary = triangle.getBoundary(p);
                Triangle adjacent = triangle.getAdjacency().get(boundary);
                Edge adjacentBoundary = adjacent.getBoundary(p);

                List<Triangle> newTriangles = new ArrayList<>();
                for (Edge edge : triangle.getEdges()) {
                    if (!edge.equals(boundary)) {
                        newTriangles.add(new Triangle(edge.getP1(), edge.getP2(), p));
                    }
                }
                for (Edge edge : adjacent.getEdges()) {
                    if (!edge.equals(adjacentBoundary)) {
                        newTriangles.add(new Triangle(edge.getP1(), edge.getP2(), p));
                    }
                }
                if (newTriangles.size() != 4) throw new IllegalStateException("Shit!");
                List<Edge> newEdgesLegalized = new ArrayList<>();
                for (Triangle t : newTriangles) {
                    for (Triangle s : newTriangles) {
                        if (!t.equals(s)) {
                            t.link(s);
                        }
                    }
                    t.link(triangle);

                    TriangulationDAG td = new TriangulationDAG(t);
                    dag.addChild(td);

                    for (Edge edge : t.getEdges()) {
                        if ((edge.getP1().equals(p) || edge.getP2().equals(p)) && !newEdgesLegalized.contains(edge)) {
                            newEdgesLegalized.add(edge);
                            legalizeEdge(p, t, edge);
                        }
                    }
                }
            } else {
                // Split into three triangles
                Triangle t1 = new Triangle(triangle.getP1(), triangle.getP2(), p);
                Triangle t2 = new Triangle(triangle.getP2(), triangle.getP3(), p);
                Triangle t3 = new Triangle(triangle.getP3(), triangle.getP1(), p);

                t1.link(t2);
                t1.link(t3);
                t2.link(t3);
                t1.link(triangle);
                t2.link(triangle);
                t3.link(triangle);

                TriangulationDAG td1 = new TriangulationDAG(t1);
                TriangulationDAG td2 = new TriangulationDAG(t2);
                TriangulationDAG td3 = new TriangulationDAG(t3);

                dag.addChild(td1);
                dag.addChild(td2);
                dag.addChild(td3);

                List<Edge> newEdges = findNewEdges(t1, t2, t3, p);
                legalizeEdge(p, t1, newEdges.get(0));
                legalizeEdge(p, t2, newEdges.get(1));
                legalizeEdge(p, t3, newEdges.get(2));
            }
        }
    }

    private void legalizeEdge(Point p, Triangle t1, Edge edge) {

    }

    private List<Edge> findNewEdges(Triangle t1, Triangle t2, Triangle t3, Point p) {
        List<Edge> result = new ArrayList<>();
        for (Edge edge : t1.getEdges()) {
            if (edge.getP1().equals(p) || edge.getP2().equals(p)) result.add(edge); break;
        }
        for (Edge edge : t2.getEdges()) {
            if ((edge.getP1().equals(p) || edge.getP2().equals(p)) && !result.contains(edge)) result.add(edge); break;
        }
        for (Edge edge : t3.getEdges()) {
            if ((edge.getP1().equals(p) || edge.getP2().equals(p)) && !result.contains(edge)) result.add(edge); break;
        }
        return result;
    }

    private Triangle constructOuterTriangle(Set<Point> points) {
        Point top = getHighestPoint(points);
        Point bottom = getLowestPoint(points);

        Line l1 = new Line(top, new Point(top.getOwner(), top.getX(), top.getY() - 10));
        Line l2 = new Line(top, new Point(top.getOwner(), top.getX(), top.getY() - 10));
        Line l3;

        Point p0 = new Point(top.getOwner(), top.getX(), top.getY() + 10);
        Point p1, p2;

        for (Point p : points) {
            if (!p0.equals(p)) {
                l3 = new Line(p0, p);

                if (l3.getAngle() >= l1.getAngle()) {
                    l1 = new Line(p0, new Point(p.getOwner(),p.getX() + 10, p.getY()));
                }
                if (l3.getAngle() <= l2.getAngle()) {
                    l2 = new Line(p0, new Point(p.getOwner(), p.getX() - 10, p.getY()));
                }
            }
        }

        if (l1.isHorizontal()) p1 = new Point(p0.getOwner(), p0.getX() + 10, bottom.getY() - 10);
        else p1 = new Point(bottom.getOwner(), l1.solveForX(bottom.getY() - 10), bottom.getY() - 10);
        if (l2.isHorizontal()) p2 = new Point(p0.getOwner(), p0.getX() - 10, bottom.getY() - 10);
        else p2 = new Point(bottom.getOwner(), l2.solveForX(bottom.getY() - 10), bottom.getY() - 10);

        return new Triangle(p1, p0, p2);
    }

    private Point getHighestPoint(Set<Point> points) {
        if (points.isEmpty()) throw new IllegalArgumentException("Cannot get highest point from empty set.");
        Point result = points.iterator().next();
        for (Point point : points) {
            if (point.getY() > result.getY() ||
                    (point.getY() == result.getY() && point.getX() > result.getX())) {
                result = point;
            }
        }
        return result;
    }

    private Point getLowestPoint(Set<Point> points) {
        if (points.isEmpty()) throw new IllegalArgumentException("Cannot get highest point from empty set.");
        Point result = points.iterator().next();
        for (Point point : points) {
            if (point.getY() < result.getY() ||
                    (point.getY() == result.getY() && point.getX() > result.getX())) {
                result = point;
            }
        }
        return result;
    }

    /**
     * Fisher-Yates random shuffling algorithm
     * (Also known as Knuth shuffling,
     * see algorithm 3.4.2P from [TAOCP II]).
     *
     * @param <T> ArrayList generic type
     * @param set Set that needs to be shuffled
     * @return The shuffled list
     */
    private static <T> List<T> randomShuffle(Set<T> set) {
        List<T> list = new ArrayList<T>(set);

        // Clone the list
        List<T> res = new ArrayList<>();
        for (T t : list) {
            res.add(t);
        }

        // Algorithm 3.4.2P from [TAOCP II]
        int j = res.size();
        Random r = new Random();
        int k;
        T temp;

        do {
            k = (int)(j*r.nextDouble());

            temp = res.get(k);
            res.set(k, res.get(j-1));
            res.set(j-1, temp);

            j--;

        } while(j > 1);

        return res;
    }

    private boolean pointOnBoundary(Point p, Triangle t) {
        for (Edge edge : t.getEdges()) {
            if (p.onEdge(edge)) return true;
        }
        return false;
    }
}
