package com.strategy.playertwo;

import com.model.Point;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class BenchmarkingStrategy extends PlayerTwoStrategy {

    @Override
    public Set<Point> apply(int n, Set<Point> inputPoints, float xmax, float ymax) {
        Set<Point> result = new HashSet<>();
        Set<Point> aux = new HashSet<>();
        aux.addAll(inputPoints);
        Point center = new Point(OWNER, xmax / 2.0f, ymax / 2.0f);
        for (int i = 0; i < n; i++) {
            Point distanceMaximalPoint = findDistanceMaximizingPoint(aux, result, center);
            // TODO: Continue here by finding line from distanceMaximalPoint to center, finding the lines closest intersection with convex hull and return infinitesimally close point to that intersection.
            // TODO: This involves first setting up robust representations of- and calculations for lines, convex-hulls and intersections between them.
            result.add(distanceMaximalPoint);
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
