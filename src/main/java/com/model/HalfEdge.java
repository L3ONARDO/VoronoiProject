package com.model;

/**
 * Created by littl on 20/12/2017.
 */
public class HalfEdge extends Edge {
    private HalfEdge twin;
    private HalfEdge next;
    private HalfEdge prev;

    public HalfEdge(Point p1, Point p2) {
        super(p1, p2);
    }

    public HalfEdge(Point p1, Point p2, HalfEdge twin) {
        super(p1, p2);
        this.twin = twin;
    }

    public HalfEdge getTwin() {
        return twin;
    }

    public void setTwin(HalfEdge twin) {
        this.twin = twin;
    }

    public HalfEdge getNext() {
        return next;
    }

    public void setNext(HalfEdge next) {
        this.next = next;
    }

    public HalfEdge getPrev() {
        return prev;
    }

    public void setPrev(HalfEdge prev) {
        this.prev = prev;
    }
}
