package com.voronoiStrategy.playerone;

import com.model.Point;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class ColinearUniformStrategy extends PlayerOneStrategy {

    public Set<Point> apply(int n, Set<Point> inputPoints, float xmax, float ymax) {
        Set<Point> result = new HashSet<Point>();
        // Calculate the distance between each point on the mid-line. 
        float mu = xmax / (n + 1);
        float x = 0f;
        float y = ymax / 2.0f;
        for (int i = 0; i < n; i++) { // Add new points at uniform distance along the horizontal line.
            x += mu;
            Point point = new Point(OWNER, x, y);
            result.add(point);
        }
        if (ymax > xmax) { // If the bounding box is taller than it is wide, transform the points.
            return transformY(result, xmax, ymax);
        }
        return result;
    }
}
