package com.model;


public class Segment {
    private Point p1, p2;

    /**
     * Initializes a segment by 2 vertices.
     *
     * @param p1 left vertex
     * @param p2 right vertex
     */
    public Segment(Point p1, Point p2) {

        if(p1.getX() > p2.getX()) {
            Point temp = p2;
            p2 = p1;
            p1 = temp;
        }

        p1.setSegment(this);
        p2.setSegment(this);

        this.p1 = p1;
        this.p2 = p2;
    }

    /**
     * Returns the left vertex of the segment.
     *
     * @return The left vertex
     */
    public Point getLeft() {
        return p1;
    }

    /**
     * Returns the right vertex of the segment.
     *
     * @return The right vertex
     */
    public Point getRight() {
        return p2;
    }

    @Override
    public boolean equals(Object o2) {

        if (! (o2 instanceof Segment)) {
            return false;
        }

        Segment s2 = (Segment) o2;

        return ((this.getLeft().equals(s2.getLeft()) &&
                this.getRight().equals(s2.getRight())) ||
                (this.getLeft().equals(s2.getRight()) &&
                        this.getRight().equals(s2.getLeft())));
    }

}
