package model;

import model.TriangulationDAG.DAGLocation;
import model.TriangulationDAG.TriangulationDAG;

import java.util.*;

public class DelaunayTriangulation {

    private class LegalizeCallPair {
        private Triangle triangle;
        private Edge edge;

        public LegalizeCallPair(Triangle triangle, Edge edge) {
            this.triangle = triangle;
            this.edge = edge;
        }
    }

    public List<Triangle> calculate(Set<Point> points) {
        Triangle outerTriangle = constructOuterTriangle(points);

        TriangulationDAG triangulationDAG = outerTriangle.getTriangulationDAG();

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
                Triangle adjacent = triangle.getAdjacentTriangle(boundary);

                List<Triangle> newTriangles = new ArrayList<>();
                for (Edge edge : triangle.getEdges()) {
                    if (!edge.equals(boundary)) {
                        newTriangles.add(new Triangle(edge.getP1(), edge.getP2(), p));
                    }
                }
                for (Edge edge : adjacent.getEdges()) {
                    if (!edge.equals(boundary)) {
                        newTriangles.add(new Triangle(edge.getP1(), edge.getP2(), p));
                    }
                }
                if (newTriangles.size() != 4) throw new IllegalStateException("Shit!");
                for (Triangle t : newTriangles) {
                    for (Triangle s : newTriangles) {
                        if (!t.equals(s)) {
                            t.link(s);
                        }
                    }
                    for (Triangle s : triangle.getAdjacency().values()) {
                        if (!s.equals(adjacent)) t.link(s);
                    }
                    for (Triangle s : adjacent.getAdjacency().values()) {
                        if (!s.equals(triangle)) t.link(s);
                    }

                    TriangulationDAG td = t.getTriangulationDAG();
                    dag.addChild(td);
                    adjacent.getTriangulationDAG().addChild(td);
                }
                for (Triangle t : newTriangles) {
                    List<LegalizeCallPair> calls = findLegalizeCallPairs(p, t);
                    for (LegalizeCallPair call : calls) {
                        legalizeEdge(triangulationDAG, call.triangle, call.edge);
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
                for (Map.Entry<Edge, Triangle> entry : triangle.getAdjacency().entrySet()) {
                    t1.link(entry.getValue());
                    t2.link(entry.getValue());
                    t3.link(entry.getValue());
                }

                TriangulationDAG td1 = t1.getTriangulationDAG();
                TriangulationDAG td2 = t2.getTriangulationDAG();
                TriangulationDAG td3 = t3.getTriangulationDAG();

                dag.addChild(td1);
                dag.addChild(td2);
                dag.addChild(td3);

                List<LegalizeCallPair> calls = findLegalizeCallPairs(p, t1, t2, t3);
                if (calls.size() != 3) throw new IllegalArgumentException("Damn!");
                for (LegalizeCallPair call : calls) {
                    legalizeEdge(triangulationDAG, call.triangle, call.edge);
                }
            }
        }

        List<Triangle> aux = triangulationDAG.getTriangulation();
        List<Triangle> result = new ArrayList<>();

        for (Triangle t : aux) {
            boolean p1 = t.containsPoint(outerTriangle.getP1());
            boolean p2 = t.containsPoint(outerTriangle.getP2());
            boolean p3 = t.containsPoint(outerTriangle.getP3());

            boolean containsNoPoints = !(p1 || p2 || p3);
            if (containsNoPoints && containsNoEquivalentTriangle(result, t)) {
                result.add(t);
            }
        }
        return result;
    }

    private boolean containsNoEquivalentTriangle(List<Triangle> result, Triangle t) {
        for (Triangle s : result) {
            if (s.equals(t)) return false;
        }
        return true;
    }

    private List<LegalizeCallPair> findLegalizeCallPairs(Point p, Triangle... triangles) {
        List<LegalizeCallPair> result = new ArrayList<>();
        for (Triangle t : triangles) {
            for (Edge edge : t.getEdges()) {
                if (!edge.getP1().equals(p) && !edge.getP2().equals(p)) {
                    result.add(new LegalizeCallPair(t, edge));
                    break;
                }
            }
        }
        return result;
    }

    private boolean containsEquivalentEdge(List<LegalizeCallPair> pairs, Edge edge) {
        for (LegalizeCallPair pair : pairs) {
            if (pair.edge.equals(edge)) return true;
        }
        return false;
    }

    private void legalizeEdge(TriangulationDAG triangulationDAG, Triangle t1, Edge edge) {

        Triangle t2 = t1.getAdjacency().get(edge);

        if (t2 != null && edge.isIllegal(t1, t2)) {
            Point p1 = t1.getThirdPoint(edge.getP1(), edge.getP2());
            Point p2 = t2.getThirdPoint(edge.getP1(), edge.getP2());

            Edge newEdge = new Edge(p1, p2);
            Triangle newT1 = new Triangle(edge.getP1(), p1, p2);
            Triangle newT2 = new Triangle(edge.getP2(), p1, p2);
            for (Triangle t : t1.getAdjacency().values()) {
                if (!t.equals(t2)) {
                    newT1.link(t);
                }
            }
            for (Triangle t : t2.getAdjacency().values()) {
                if (!t.equals(t1)) {
                    newT1.link(t);
                }
            }
            for (Triangle t : t1.getAdjacency().values()) {
                if (!t.equals(t2)) {
                    newT2.link(t);
                }
            }
            for (Triangle t : t2.getAdjacency().values()) {
                if (!t.equals(t1)) {
                    newT2.link(t);
                }
            }
            newT1.addAdjacency(newEdge, newT2);
            newT2.addAdjacency(newEdge, newT1);

            TriangulationDAG td1 = t1.getTriangulationDAG();
            TriangulationDAG td2 = t2.getTriangulationDAG();
            TriangulationDAG td3 = newT1.getTriangulationDAG();
            TriangulationDAG td4 = newT2.getTriangulationDAG();
            td1.addChild(td3);
            td1.addChild(td4);
            td2.addChild(td3);
            td2.addChild(td4);

            for (Edge e : newT1.getEdges()) {
                if (!e.equals(newEdge)) legalizeEdge(triangulationDAG, newT1, e);
            }
            for (Edge e : newT2.getEdges()) {
                if (!e.equals(newEdge)) legalizeEdge(triangulationDAG, newT2, e);
            }
        }
    }

    private Triangle constructOuterTriangle(Set<Point> points) {
        float xmin = getMinX(points);
        float xmax = getMaxX(points);
        float ymin = getMinY(points);
        float ymax = getMaxY(points);
        float dx = xmax - xmin;
        float dy = ymax - ymin;

        Point p = new Point(true, xmin - 10f * dx, ymin - dy);
        Point q = new Point(true, xmin + 0.5f * dx, ymax + 2.0f * dy);
        Point r = new Point(true, xmax + 10f * dx, ymin - dy);

        return new Triangle(p, q, r);
    }

    private float getMaxY(Set<Point> points) {
        float result = Float.MIN_VALUE;
        for (Point p : points) {
            if (p.getY() > result) result = p.getY();
        }
        return result;
    }

    private float getMinY(Set<Point> points) {
        float result = Float.MAX_VALUE;
        for (Point p : points) {
            if (p.getY() < result) result = p.getY();
        }
        return result;
    }

    private float getMaxX(Set<Point> points) {
        float result = Float.MIN_VALUE;
        for (Point p : points) {
            if (p.getX() > result) result = p.getX();
        }
        return result;
    }

    private float getMinX(Set<Point> points) {
        float result = Float.MAX_VALUE;
        for (Point p : points) {
            if (p.getX() < result) result = p.getX();
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

    public boolean pointOnBoundary(Point p, Triangle t) {
        List<Edge> edges = t.getEdges();
        for (Edge edge : edges) {
            boolean temp = p.onEdge(edge);
            if (temp) return true;
        }
        return false;
    }
}
