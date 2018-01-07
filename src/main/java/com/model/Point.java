package com.model;

import java.util.Set;

public class Point {
    private boolean owner;
    private float x, y;
    private Segment segment;

    public Point(boolean owner, float x, float y) {
        this.owner = owner;
        this.x = x;
        this.y = y;
    }

    public boolean getOwner() {
        return owner;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    /**
     * Return the euclidean distance from {@code this} {@link Point} to {@link Point} {@code point}.
     * @param point the {@link Point} to find the distance to.
     * @return {@link float}.
     */
    public float distanceTo(Point point) {
        return (float) Math.sqrt(((x - point.getX()) * (x - point.getX())) + ((y - point.getY()) * (y - point.getY())));
    }

    /**
     * Find the extreme (i.e. closest or furthest) {@link Point} {@code point} from {@code this}.
     * @param points the point set to find the extreme point in.
     * @param closest {@code true} iff the extreme point is the closest point.
     *                            The extreme point is the furthest point otherwise.
     * @return {Link Point}.
     */
//    public Point findExtremePoint(Set<Point> points, boolean closest) {
//        if (points.isEmpty()) throw new IllegalArgumentException("Must have points to compare to.");
//        Point result = points.iterator().next();
//        float distance = result.distanceTo(this);
//        for (Point p : points) {
//            float newDistance = p.distanceTo(this);
//            if (closest) {
//                if (newDistance < distance) result = p;
//            } else {
//                if (newDistance > distance) result = p;
//            }
//        }
//        return result;
//    }

    /**
     * Attaches a segment to the point.
     *
     * @param segment The attached segment
     */
    public void setSegment(Segment segment) {
        this.segment = segment;
    }

    /**
     * Returns the edge attached to the point.
     * @return The attached segment
     */
    public Segment getSegment() {
        return segment;
    }

     /**
      * Calculates the cross product
      * of two vectors (p0, p1) and (p0, p2)
      * defined by three points p0, p1 and p2.
      *
      * @param p0 point1
      * @param p1 point2
      * @param p2 point3
      * @return The cross product
      */
     private static float crossProduct(Point p0, Point p1, Point p2) {
        return (p1.getX() - p0.getX()) * (p2.getY() - p0.getY()) -
                (p2.getX() - p0.getX()) * (p1.getY() - p0.getY());
     }

    /**
      * Check if two vectors (p0, p1) and (p1, p2)
      * defined by three points p0, p1 and p2 make a left turn.
      *
      * @param p0 point1
      * @param p1 point2
      * @param p2 point3
      * @return true, if the turn is left
      */
     public static boolean isLeftTurn(Point p0, Point p1, Point p2) {
        return (crossProduct(p0, p1, p2) > 0);
     }

     /**
      * Check if two vectors (p0, p1) and (p1, p2)
      * defined by three points p0, p1 and p2 make a right turn.
      *
      * @param p0 point1
      * @param p1 point2
      * @param p2 point3
      * @return true, if the turn is right
      */
     public static boolean isRightTurn(Point p0, Point p1, Point p2) {
        return (crossProduct(p0, p1, p2) < 0);
     }

      /**
       * Determines if the point is the left vertex of the attached segment.
       *
       * @return true, if the point is the left vertex
       */
      public boolean isLeft() {
         return (segment != null && this.equals(segment.getLeft()));
      }

     /**
      * Determines if the point is the right vertex of the attached segment.
      *
      * @return true, if the point is the right vertex
      */
      public boolean isRight() {
         return (segment != null && this.equals(segment.getRight()));
      }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Point point = (Point) o;

        if (owner != point.owner) return false;
        if (Float.compare(point.x, x) != 0) return false;
        return Float.compare(point.y, y) == 0;

    }
}
