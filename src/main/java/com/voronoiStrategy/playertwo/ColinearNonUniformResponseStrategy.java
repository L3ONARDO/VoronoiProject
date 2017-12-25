package com.voronoiStrategy.playertwo;

import com.model.Point;
import com.util.MathUtils;
import com.util.MergeSortPointSet;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ColinearNonUniformResponseStrategy extends PlayerTwoStrategy {

    public Set<Point> apply(int n, Set<Point> inputPoints, float xmax, float ymax) {
        Set<Point> result = new HashSet<Point>();
        List<Point> aux = new MergeSortPointSet().sort(inputPoints);
        result.addAll(inputPoints);
        for (int i = 0; i < (n - (n % 2)); i = i + 2) {
            Point[] largestGap = findLargestGap(aux);
            result.add(new Point(PlayerTwoStrategy.OWNER, largestGap[0].getX() + MathUtils.EPSILON, largestGap[0].getY()));
            result.add(new Point(PlayerTwoStrategy.OWNER, largestGap[1].getX() - MathUtils.EPSILON, largestGap[1].getY()));
            aux.remove(largestGap[0]);
            aux.remove(largestGap[1]);
        }
        if (n % 2 == 1) {
            Point[] largestGap = findLargestGap(aux);
            result.add(new Point(PlayerTwoStrategy.OWNER, largestGap[0].getX() + MathUtils.EPSILON, largestGap[0].getY()));
        }
        return result;
    }

    private Point[] findLargestGap(List<Point> points) {
        if (points.size() < 2) throw new IllegalArgumentException("Should have at least two points to find a gap.");
        Point[] result = new Point[2];
        result[0] = points.get(0);
        result[1] = points.get(1);
        for (int i = 1; i < points.size() - 1; i++) {
            Point p1 = points.get(i);
            Point p2 = points.get(i + 1);
            if (p1.distanceTo(p2) > result[0].distanceTo(result[1])) {
                result[0] = p1;
                result[1] = p2;
            }
        }
        return result;
    }
}
