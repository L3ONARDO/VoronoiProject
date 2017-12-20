package com.model;

/**
 * Created by littl on 20/12/2017.
 */
public class DCEL {
    private HalfEdge head, tail;

    public DCEL() {}

    public void addFront(HalfEdge halfEdge) {
        if (head == null) {
            head = halfEdge;
            tail = halfEdge;
            head.setNext(tail);
            tail.setPrev(head);
            return;
        }
        head.setPrev(halfEdge);
        head.getTwin().setNext(halfEdge.getTwin());
        halfEdge.getTwin().setPrev(head.getTwin());
        halfEdge.setNext(head);
        head = halfEdge;
    }

    public void addBack(HalfEdge halfEdge) {
        if (head == null) {
            head = halfEdge;
            tail = halfEdge;
            head.setNext(tail);
            tail.setPrev(head);
            return;
        }
        tail.setNext(halfEdge);
        tail.getTwin().setPrev(halfEdge.getTwin());
        halfEdge.getTwin().setNext(tail.getTwin());
        halfEdge.setPrev(tail);
        tail = halfEdge;
    }
}
