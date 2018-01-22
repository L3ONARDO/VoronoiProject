package strategy.playertwo;

import model.Point;
import util.MergeSortPointSet;

import java.util.*;

public class ColinearUniformResponseStrategy extends PlayerTwoStrategy {

    public Set<Point> apply(int n, Set<Point> inputPoints, float xmax, float ymax) {
        Set<Point> result = new HashSet<>();
        List<Point> points = new MergeSortPointSet().sort(inputPoints, false);
        List<Point> abovePoints = new ArrayList<>();
        List<Point> belowPoints = new ArrayList<>();
        int m = inputPoints.size();
        float aboveY = points.get(0).getY() + EPSILON;
        float belowY = points.get(0).getY() - EPSILON;
        Point p1 = new Point(OWNER, points.get(0).getX() + ((points.get(1).getX() - points.get(0).getX()) / 2f), aboveY);
        result.add(p1);
        abovePoints.add(p1);
        if (n == 1) return result;
        Point p2 = new Point(OWNER, points.get(0).getX() + ((points.get(1).getX() - points.get(0).getX()) / 2f), belowY);
        result.add(p2);
        belowPoints.add(p2);
        if (n == 2) return result;
        Point p3 = new Point(OWNER, points.get(m - 2).getX() + ((points.get(m - 1).getX() - points.get(m - 2).getX()) / 2f), aboveY);
        result.add(p3);
        abovePoints.add(p3);
        if (n == 3) return result;
        Point p4 = new Point(OWNER, points.get(m - 2).getX() + ((points.get(m - 1).getX() - points.get(m - 2).getX()) / 2f), belowY);
        result.add(p4);
        belowPoints.add(p4);
        n -= 4;
        boolean addAbove = true;
        while (n > 0) {
            if (addAbove) {
                addAbove = false;
                Point[] largestGap = findLargestGap(abovePoints);
                Point middlePoint = new Point(OWNER, largestGap[0].getX() + ((largestGap[1].getX() - largestGap[0].getX()) / 2f), aboveY);
                Point leftOf = findLeftOfPoint(middlePoint, inputPoints);
                Point rightOf = findRightOfPoint(middlePoint, inputPoints);
                Point newPoint = new Point(OWNER, leftOf.getX() + ((rightOf.getX() - leftOf.getX()) / 2f), aboveY);
                abovePoints.add(newPoint);
                abovePoints = new MergeSortPointSet().sort(abovePoints, false);
                result.add(newPoint);
            } else {
                addAbove = true;
                Point[] largestGap = findLargestGap(belowPoints);
                Point middlePoint = new Point(OWNER, largestGap[0].getX() + ((largestGap[1].getX() - largestGap[0].getX()) / 2f), belowY);
                Point leftOf = findLeftOfPoint(middlePoint, inputPoints);
                Point rightOf = findRightOfPoint(middlePoint, inputPoints);
                Point newPoint = new Point(OWNER, leftOf.getX() + ((rightOf.getX() - leftOf.getX()) / 2f), belowY);
                belowPoints.add(newPoint);
                belowPoints = new MergeSortPointSet().sort(belowPoints, false);
                result.add(newPoint);
            }
            n--;
        }
        return result;
    }

    private Point findRightOfPoint(Point middlePoint, Set<Point> inputPoints) {
        Iterator<Point> i = inputPoints.iterator();
        Point result = i.next();
        while (i.hasNext() && result.getX() < middlePoint.getX()) {
            result = i.next();
        }
        for (Point p : inputPoints) {
            if (p.getX() > middlePoint.getX() && p.getX() < result.getX()) result = p;
        }
        return result;
    }

    private Point findLeftOfPoint(Point middlePoint, Set<Point> inputPoints) {
        Iterator<Point> i = inputPoints.iterator();
        Point result = i.next();
        while (i.hasNext() && result.getX() > middlePoint.getX()) {
            result = i.next();
        }
        for (Point p : inputPoints) {
            if (p.getX() < middlePoint.getX() && p.getX() > result.getX()) result = p;
        }
        return result;
    }
}
