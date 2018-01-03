package com.voronoiStrategy.playerone;

import com.model.Point;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.lang.Math.*;

public class ImprovedGridLikeStrategy extends PlayerOneStrategy {

    public Set<Point> apply(int n, Set<Point> inputPoints, float xmax, float ymax) {
        Random random = new Random();
        Set<Point> result = new HashSet<Point>();

        //Plot the first point at the center of the playing area

        float xdist = xmax/2;
        float ydist = ymax/2;
        Point midpoint = new Point(OWNER, xdist, ydist);
        result.add(midpoint);
        float x = xmax / 4;
        float y = ymax / 4;

        //Plot 4 points along (3/4)th of each diagonal from the center to corners.
        for (int i=0; i < 2; i++){
            for (int j=0; j < 2; j++ ){
                Point point = new Point(OWNER, x, y);
                result.add(point);
                x += xdist;    
            }
            y += ydist;
            x = (xdist / 2);
        }

        //TODO: Plot the remaining points iteratively on the convex hull (square). 

        if (ymax > xmax) { // If the bounding box is taller than its wide, transform the points.
            return transformY(result, xmax, ymax);
        }
        return result;

    }
}
