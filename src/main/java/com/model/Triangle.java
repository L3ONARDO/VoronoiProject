package com.model;

/**
 * Created by littl on 07/01/2018.
 */
public class Triangle {

    private Point a, b, c;

    /*adjacent triangles*/
    private Triangle Tab, Tbc, Tac;

    /**
     * Initializes a triangle. make it counter clockwise
     *
     * @param A A vertex
     * @param B B vertex
     * @param C C vertex
     */
    public Triangle (Point A, Point B, Point C) {
        a = A;
        b = B;
        c = C;

        Tab = null;
        Tbc = null;
        Tac = null;

        /*ccw*/
        float sum = (b.getX() - a.getX())*
                (b.getY() + a.getY()) +
                (c.getX() - b.getX())*
                        (c.getY() + b.getY()) +
                (a.getX() - c.getX())*
                        (a.getY() + c.getY());

        if (sum > 0) {
            Point temp = a;
            a = b;
            b = temp;
        }
    }

    /**
     * Returns the A vertex of ABC.
     *
     * @return The vertex
     */
    public Point getA() {
        return a;
    }

    /**
     * Returns B vertex of ABC.
     *
     * @return The vertex
     */
    public Point getB() {
        return b;
    }

    /**
     * Returns 'C' triangle ABC.
     *
     * @return The vertex
     */
    public Point getC() {
        return c;
    }


    /**
     * Returns the ith vertex of the triangle ABC,
     * 'A' is the vertex0, 'B' is the vertex1 and 'C' is the vertex2.
     *
     * @param i The index
     * @return The vertex
     */
    public Point getIth(int i) {
        switch (i) {
            case 0: return a;
            case 1: return b;
            case 2: return c;
            default: return null;
        }
    }

    private void setIth(int i, Point p) {
        switch (i) {
            case 0: a = p;
                break;
            case 1: b = p;
                break;
            case 2: c = p;
                break;
        }
    }

    /**
     * The enum for the triangle sides.
     */
    public enum Side {
        AB(0), BC(1), AC(2);
        int i;

        Side(int i) {
            this.i = i;
        }

        /**
         * Returns the triangle side for its index,
         * AB is the side0,
         * BC is the side1,
         * AC is the side2,
         *
         * @param i The index
         * @return side
         */
        public static Side valueOf(int i) {
            switch(i) {
                case 0:
                    return AB;
                case 1:
                    return BC;
                case 2:
                    return AC;
                default:
                    return null;
            }
        }
    }

    /**
     * Returns adjacent triangle.
     *
     * @param s The side where the adjacent triangle lies
     * @return The adjacent triangle
     */
    public Triangle getAdjacent(Side s) {
        switch (s) {
            case AB: 
                return Tab;
            case BC: 
                return Tbc;
            case AC: 
                return Tac;
            default: 
                return null;
        }
    }

    /**
     * Return adjecent triangle of given side
     *
     * @param s The side of the current triangle
     * @return adjacent triangle of s
     */
    public Side getAdjacentSide(Side s) {
        Triangle t = this.getAdjacent(s);
        Triangle.Side res = null;
        if (t != null) {
            for (Triangle.Side sd : Triangle.Side.values()) {
                if (t.isAdjacent(this, sd)) {
                    res = sd;
                    break;
                }
            }
        }
        return res;
    }

    private void setAdjacent(Side thisSide, Triangle t) {
        switch (thisSide) {
            case AB:
                this.Tab = t;
                break;
            case BC:
                this.Tbc = t;
                break;
            case AC:
                this.Tac = t;
                break;
        }
    }

    /**
     * check if a given triangle
     * is adjacent for the given side.
     *
     * @param t The triangle
     * @param s The side
     * @return true if the triangle is adjacent
     */
    public boolean isAdjacent(Triangle t, Side s) {
        return this.getAdjacent(s) == t;
    }

    /**
     * Updates the links to adjacent triangles given another triangle.
     * @param t Another triangle
     * @return true, if the triangle is adjacent, false otherwise
     */
    public boolean link(Triangle t) {

        if (t == null) {
            return false;
        }

        Segment s1, s2;
        for (int i = 0; i <= 2; i++) {
            for (int j = 0; j <= 2; j++) {
                s1 = new Segment(this.getIth(i), this.getIth((i+1) % 3));
                s2 = new Segment(t.getIth(j), t.getIth((j+1) % 3));

                if (s1.equals(s2)) {

                    Side sThis = Side.valueOf(i);
                    Side sT = Side.valueOf(j);

                    this.setAdjacent(sThis, t);
                    t.setAdjacent(sT, this);

                    return true;
                }
            }
        }

        return false;
    }


    @Override
    public boolean equals(Object o2) {

        if (! (o2 instanceof Triangle)) {
            return false;
        }

        Triangle t2 = (Triangle) o2;

        return this.getA().equals(t2.getA()) &&
                this.getB().equals(t2.getB()) &&
                this.getC().equals(t2.getC()) ||
                this.getA().equals(t2.getB()) &&
                        this.getB().equals(t2.getC()) &&
                        this.getC().equals(t2.getA()) ||
                this.getA().equals(t2.getC()) &&
                        this.getB().equals(t2.getA()) &&
                        this.getC().equals(t2.getB()) ||
                this.getA().equals(t2.getC()) &&
                        this.getB().equals(t2.getB()) &&
                        this.getC().equals(t2.getA()) ||
                this.getA().equals(t2.getB()) &&
                        this.getB().equals(t2.getA()) &&
                        this.getC().equals(t2.getC()) ||
                this.getA().equals(t2.getA()) &&
                        this.getB().equals(t2.getC()) &&
                        this.getC().equals(t2.getB());
    }

}
