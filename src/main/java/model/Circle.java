package model;

public class Circle {
    private Point center;
    private float radius;

    /**
     * Initializes a circle by three different
     * points lying on its boundary.
     *
     * @param a A point that lies on the circle boundary
     * @param b A point that lies on the circle boundary
     * @param c A point that lies on the circle boundary
     */
    public Circle(Point a, Point b, Point c) {
        Line l1 = new Line(2*a.getX() - 2*b.getX(),
                2*a.getY() - 2*b.getY(),
                b.getX()*b.getX() - a.getX()*a.getX() +
                        b.getY()*b.getY() - a.getY()*a.getY());
        Line l2 = new Line(2*a.getX() - 2*c.getX(),
                2*a.getY() - 2*c.getY(),
                c.getX()*c.getX() - a.getX()*a.getX() +
                        c.getY()*c.getY() - a.getY()*a.getY());

        center = Line.intersection(l1, l2);
        radius = center.dist(a);
    }

    /**
     * Determines whether the given point lies inside the circle.
     *
     * @param p The point
     * @return true, if the point is inside, false otherwise
     */
    public boolean isInside(Point p) {
        return center.dist(p) < radius;
    }
}
