package util;

import model.LineInterface;
import model.Point;
import model.Polygon;
import model.VerticalLine;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A class for various mathematical functions.
 */
public class MathUtils {

    public static final float EPSILON = 0.0001f;

    public Polygon orderByGiftWrapping(Polygon polygon) {
        Set<Point> input = new HashSet<>();
        input.addAll(polygon.getPoints());
        List<Point> aux = orderByGiftWrapping(input);
        Polygon result = new Polygon();
        for (Point p : aux) {
            result.addPoint(p);
        }
        return result;
    }

    /**
     * Sorts a set of points such that they make up a convex polygon, based on Gift Wrapping algorithm, see:
     * <a href="https://en.wikipedia.org/wiki/Gift_wrapping_algorithm">Gift wrapping algorithm</a>.
     * @return {@link List<Point>}
     */
    public List<Point> orderByGiftWrapping(Set<Point> points) {
        List<Point> result = new ArrayList<Point>();
        Point start = findLowestPoint(points); // Find the start point by ordering smallest-y then smallest-x.
        Point p0 = start;
        Point p1 = new Point(p0.getOwner(), p0.getX() + 0.01f, p0.getY()); // Auxiliary point to draw initial line.
        result.add(p0);
        while (start != p1) { // Not yet come full-circle.
            Line currentLine = new Line(p0, p1);
            Line nextLine = null;
            double angle = Double.MAX_VALUE;
            for (Point point : points) { // Examine all points.
                if (point != p0 && point != p1) {
                    Line newLine = new Line(p1, point);
                    double newAngle = angleBetween2Lines(currentLine, newLine);
                    if (newAngle < angle) { // Find the point to which a line would have the smallest angle
                        nextLine = newLine;
                        angle = newAngle;
                    }
                }
            }
            p0 = nextLine.getP1();
            p1 = nextLine.getP2();
            if (!result.contains(p1)) {
                result.add(p1);
            }
        }
        return result;
    }

    public List<Point> findIntersectionsBetweenLineAndShape(List<Point> shape, Line line) {
        List<Point> result = new ArrayList<Point>();
        for (int i = 0; i < shape.size(); i++) {
            Point p0 = shape.get(i);
            Point p1 = shape.get((i + 1) % shape.size());
            Line segment = new Line(p0, p1);
            Point intersection = segment.getIntersectionWithLine(line);
            if (intersection != null) result.add(intersection);
            if (line.getY1() == line.getY2() && line.getY1() == segment.getY1() && segment.getY1() == segment.getY2()) {
                result.add(new Point(line.getP1().getOwner(), (float) (line.getX1() + line.getX2()) / 2.0f, (float) line.getY1()));
            }
        }
        return result;
    }

    public Point findLeftMostPoint(List<Point> points) {
        if (points.size() == 0) return null;
        Point result = points.get(0);
        for (Point p : points) {
            if (p.getX() < result.getX()) result = p;
        }
        return result;
    }

    /**
     * Returns the clockwise angle in degrees between two lines.
     * @param line1 the first line.
     * @param line2 the second line.
     * @return {@code double} in range {@code [0, 360]}
     */
    private double angleBetween2Lines(Line line1, Line line2) {
        double x1 = (line1.getX2() - line1.getX1());
        double y1 = (line1.getY2() - line1.getY1());
        double x2 = (line2.getX2() - line2.getX1());
        double y2 = (line2.getY2() - line2.getY1());
        double dot = (x1 * x2) + (y1 * y2); // dot-product
        double det = (x1 * y2) - (y1 * x2); // determinant
        return Math.toDegrees(Math.atan2(det, dot));
    }

    /**
     * Finds the point with the smallest y-value, breaking ties on x-value.
     * @param points the list of points to choose from.
     * @return {@code Point}
     */
    private Point findLowestPoint(Set<Point> points) {
        List<Point> aux = new ArrayList();
        if (points.size() == 0) return null;
        Point result = points.iterator().next();
        double smallestY = Double.MAX_VALUE;
        for (Point point : points) { // Find smallest y-value.
            if (result != point && point.getY() < result.getY()) {
                smallestY = point.getY();
                result = point;
            }
        }
        for (Point point : points) { // Find the points with smallest y-value.
            if (point.getY() == smallestY) {
                aux.add(point);
            }
        }
        for (Point point : aux) { // Break ties on x-value.
            if (result != point && point.getX() < result.getX()) {
                result = point;
            }
        }
        return result;
    }
}
