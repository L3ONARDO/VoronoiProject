package com.voronoiStrategy.playerone;

import com.model.Point;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class GridLikeUniformStrategy extends PlayerOneStrategy {

    public Set<Point> apply(int n, Set<Point> inputPoints, float xmax, float ymax) {
        Random random = new Random();
        Set<Point> result = new HashSet<Point>();

        // Calculate the sides of each grid to be formed

        float xdist = xmax / n;
        float ydist = ymax / n;
        float x = (xdist / 2);
        float y = (ydist / 2);
        
        //Adding the center points of all the grids to the Set 'result'

        for (int i=0; i < n; i++){
            for (int j=0; j < n; j++ ){
                Point point = new Point(OWNER, x, y);
                result.add(point);
                x += xdist;
            }
            y += ydist;
            x = (xdist / 2);
        }
        if (ymax > xmax) { // If the bounding box is taller than it is wide, transform the points.
            return transformY(result, xmax, ymax);
        }
        return result;
    }
}