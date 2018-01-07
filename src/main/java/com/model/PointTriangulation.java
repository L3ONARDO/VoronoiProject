package com.model;

import java.util.*;
import java.util.ArrayList;
/**
 * Created by li on 07/01/2018.
 */
public class PointTriangulation {
    /**
     * Computes raw points triangulation to be used
     * for edge flipping
     *
     * @return Triangulation
     */
    public static ArrayList<Triangle> triangulation(List<Point> points) {

        Point p0 = fetchlowest(points);
        points.remove(p0);

        Collections.sort(points, new PointComparator(p0));

        ArrayList<Triangle> triangulation = new ArrayList<Triangle>();

        Stack<Point> S = new Stack<Point>();
        S.push(p0);
        S.push(points.get(0));

        Point top, nextToTop;
        Triangle lastAdded = null, temp = null,
                lastAddedInStack = null, tempInStack = null;

        for (int i = 0; i < points.size(); i++) {

            if (i < points.size() - 1) {

                // Adding new "narrow" triangle with p0 as one of its vertices
                temp = new Triangle(p0, points.get(i), points.get(i+1));

                // Linking the triangles
                temp.link(lastAdded);

                triangulation.add(temp);
                lastAdded = temp;
            }

            if (i > 0) {

                // using the idea of Graham's scan
                // to make the triangulation "convex" (!)

                top = S.peek();
                nextToTop = S.elementAt(S.size() - 2);

                while (isRightTurn(nextToTop, top, points.get(i))) {

                    tempInStack = new Triangle(nextToTop, top, points.get(i));

                    // Linking the triangles (!)
                    tempInStack.link(lastAddedInStack);
                    int k = 0;
                    for (int j = triangulation.size()-1; j >= 0; j--) {
                        if (tempInStack.link(triangulation.get(j))) {
                            k++;
                        }
                        if (k == 2) {
                            break;
                        }
                    }

                    triangulation.add(tempInStack);
                    lastAddedInStack = tempInStack;

                    S.pop();

                    top = nextToTop;
                    nextToTop = S.elementAt(S.size() - 2);
                }

                S.push(points.get(i));
            }
        }

        return triangulation;
    }

    /**
     * Compares two points by their polar angles
     * by using the cross product.
     */
    public static class PointComparator implements Comparator<Point> {

        private Point p0;

        public PointComparator(Point p0) {
            this.p0 = p0;
        }

        // Compare two points by their polar angle with respect to p0;
        // in case the angle is the same, the distance to p0 is used

        /**
         * Compare two points by their polar angle with respect to p0
         * @param p1 point1
         * @param p2 point2
         * @return 0 if colinear, 1 if the dist is larger, -1 if the dist is smaller.
         */
        public int compare(Point p1, Point p2) {

            double crossProduct = crossProduct(p0, p1, p2);

            if (crossProduct > 0) return -1;
            if (crossProduct < 0) return 1;

            float d1 = p0.distanceTo(p1);
            float d2 = p0.distanceTo(p2);

            if (d1 < d2) return -1;
            if (d1 > d2) return 1;

            return 0;
        }
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
    private static double crossProduct(Point p0, Point p1, Point p2) {
        return (p1.getX() - p0.getX()) * (p2.getY() - p0.getY()) -
                (p2.getX() - p0.getX()) * (p1.getY() - p0.getY());
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
    private static boolean isRightTurn(Point p0, Point p1, Point p2) {
        return (crossProduct(p0, p1, p2) < 0);
    }


    /**
     * Get the reletively lowest point from the given point set
     * @param points point set
     * @return lowest point
     */
    private static Point fetchlowest(List<Point> points) {

        Point lowest = points.get(0);

        Point candidates;
        for (int i = 1; i < points.size(); i++) {
            candidates = points.get(i);
            if (candidates.getY() < lowest.getY() ||
                    candidates.getY() == lowest.getY() && candidates.getX() < lowest.getX()) {
                lowest = candidates;
            }
        }
        return lowest;
    }
}
