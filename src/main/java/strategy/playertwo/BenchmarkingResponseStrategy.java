package strategy.playertwo;

import model.Point;
import util.Line;
import util.MathUtils;

import java.util.*;

public class BenchmarkingResponseStrategy extends PlayerTwoStrategy {
    private MathUtils mathUtils;

    public BenchmarkingResponseStrategy() {
        this.mathUtils = new MathUtils();
    }

    public Set<Point> apply(int n, Set<Point> inputPoints, float xmax, float ymax) {
        Set<Point> result = new HashSet<>();
        Set<Point> aux = new HashSet<>();
        result.addAll(inputPoints);
        aux.addAll(inputPoints);
        Point center = new Point(OWNER, xmax / 2.0f, ymax / 2.0f);
        for (int i = 0; i < n; i++) {
            Point distanceMaximalPoint = findDistanceMaximizingPoint(aux, result, center);
//            Line distanceMaximalToCenter = new Line(distanceMaximalPoint, center);
//            List<Point> convexHull = mathUtils.orderByGiftWrapping(inputPoints);
//            List<Point> intersectionsWithHull = mathUtils.findIntersectionsBetweenLineAndShape(convexHull, distanceMaximalToCenter);
//            //if (intersectionsWithHull.isEmpty()) throw new IllegalStateException("distanceMaximalToCenter should have an intersection with convex hull.");
//            System.out.println(intersectionsWithHull.size());
//            Point intersectionWithHull = intersectionsWithHull.get(0);
//            if (distanceMaximalPoint.distanceTo(intersectionsWithHull.get(1)) < distanceMaximalPoint.distanceTo(intersectionWithHull)) intersectionWithHull  = intersectionsWithHull.get(1);
            float signX = 1.0f;
            float signY = 1.0f;
            Point intersectionWithHull = distanceMaximalPoint;
            if (intersectionWithHull.getX() < center.getX()) {
                signX = -1.0f;
            }
            if (intersectionWithHull.getY() < center.getY()) {
                signY = -1.0f;
            }
            result.add(new Point(PlayerTwoStrategy.OWNER, intersectionWithHull.getX() + signX * MathUtils.EPSILON, intersectionWithHull.getY() + signY * MathUtils.EPSILON));
            aux.remove(distanceMaximalPoint);
        }
        return result;
    }

    private Point findDistanceMaximizingPoint(Set<Point> playerOnePoints, Set<Point> playerTwoPoints, Point center) {
        if (playerTwoPoints.isEmpty()) {
            return center.findExtremePoint(playerOnePoints, false);
        }
        Map<Point, Float> distanceMap = new HashMap<>();
        for (Point playerOnePoint : playerOnePoints) {
            Point closestPoint = playerOnePoint.findExtremePoint(playerTwoPoints, true);
            distanceMap.put(playerOnePoint, playerOnePoint.distanceTo(closestPoint));
        }
        Map.Entry<Point, Float> furthestPoint = distanceMap.entrySet().iterator().next();
        for (Map.Entry<Point, Float> entry : distanceMap.entrySet()) {
            if (entry.getValue() > furthestPoint.getValue()) {
                furthestPoint = entry;
            }
        }
        return furthestPoint.getKey();
    }
}

