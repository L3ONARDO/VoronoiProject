package model;

import java.util.ArrayList;
import java.util.List;

public class Polygon {
    private List<Point> points;
    private boolean owner;

    public Polygon() {
        this.points = new ArrayList<>();
    }

    public boolean getOwner() {
        return owner;
    }

    public void setOwner(boolean owner) {
        this.owner = owner;
    }

    public void addPoint(Point point) {
        points.add(point);
    }

    public List<Point> getPoints() {
        return points;
    }

    public boolean deepContainsPoint(Point point) {
        for (Point p  : points) {
            if (p == point || (p.getX() == point.getX() && p.getY() == point.getY())) return true;
        }
        return false;
    }

//    /**
//     * Return true if the given point is contained inside the boundary.
//     * See: http://www.ecse.rpi.edu/Homepages/wrf/Research/Short_Notes/pnpoly.html
//     * @param point The point to check
//     * @return true if the point is inside the boundary, false otherwise
//     */
//    public boolean hasInteriorPoint(Point point) {
//        int i;
//        int j;
//        boolean result = false;
//        for (i = 0, j = points.size() - 1; i < points.size(); j = i++) {
//            if ((points.get(i).getY() > point.getY()) != (points.get(j).getY() > point.getY()) &&
//                    (point.getX() < (points.get(j).getX() - points.get(i).getX()) * (point.getY() - points.get(i).getY()) / (points.get(j).getY() - points.get(i).getY() + points.get(i).getX()))) {
//                result = !result;
//            }
//        }
//        return result;
//    }
    
    public boolean hasInteriorPoint(Point point) {
        int i, j = points.size() - 1;
        boolean oddNodes = false;
        for (i = 0; i < points.size(); i++) {
            Point vi = points.get(i);
            Point vj = points.get(j);
            if (vi.getY() < point.getY() && vj.getY() >= point.getY() || vj.getY() < point.getY() && vi.getY() >= point.getY()) {
                if (vi.getX() + (point.getY() - vi.getY()) / (vj.getY() - vi.getY()) * (vj.getX() - vi.getX()) < point.getX()) {
                    oddNodes = !oddNodes;
                }
            }
            j = i;
        }
        return oddNodes;
    }

    /**
     * Calculates the area of a (assumed convex) polygon given a list of ordered vertices.
     * @return {@code double}
     */
    public float getArea() {
        float sum = 0f;
        for (int i = 0; i < points.size(); i++) {
            Point p1 = points.get(i);
            Point p2 = points.get((i + 1) % points.size());
            sum += (p1.getX() * p2.getY() - p2.getX() * p1.getY());
        }
        return (float) (0.5 * Math.abs(sum));
    }
}
