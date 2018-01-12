package model;

public class HalfEdgeBuilder {

    public HalfEdgeBuilder() {}

    public HalfEdge buildPair(boolean owner, float x1, float x2, float y1, float y2) {
        Point p1 = new Point(owner, x1, y1);
        Point p2 = new Point(owner, x2, y2);
        HalfEdge e1 = new HalfEdge(p1, p2);
        HalfEdge e2 = new HalfEdge(p2, p1, e1);
        e1.setTwin(e2);
        return e1;
    }
}
