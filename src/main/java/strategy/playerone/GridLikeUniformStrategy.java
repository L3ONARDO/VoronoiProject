package strategy.playerone;

import model.Point;
import java.util.HashSet;
import java.util.Set;

public class GridLikeUniformStrategy extends PlayerOneStrategy {

    public Set<Point> apply(int n, Set<Point> inputPoints, float xmax, float ymax) {
        Set<Point> result = new HashSet<>();
        
        // Check if number of points is 2
        if (n == 2){
            float y = ymax/2; //Mid point along y-axis
            float x1 = xmax/4; //x mid-point of first grid
            float x2 = 3*x1; //x mid-point of second grid
            Point point1 = new Point(OWNER, x1, y);
            Point point2 = new Point(OWNER, x2, y);
            result.add(point1);
            result.add(point2);
        }
        
        else{

        // Calculate the root of n. The sqrt_n should be an integer to form grids. 
        double sqrt_nvalue = Math.sqrt((double) n);
        float sqrt_n = (float) sqrt_nvalue;
        

        // Calculate the size of each grid 
        float xdist = xmax / sqrt_n;
        float ydist = ymax / sqrt_n;
        float x = (xdist / 2);
        float y = (ydist / 2);
        
        //Adding the center points of all the grids to the Set 'result'
        for (int i=0; i < sqrt_n; i++){
            for (int j=0; j < sqrt_n; j++ ){
                Point point = new Point(OWNER, x, y);
                result.add(point);
                x += xdist;
            }
            y += ydist;
            x = (xdist / 2);
        }
        }
        if (ymax > xmax) { // If the bounding box is taller than it is wide, transform the points.
            return transformY(result, xmax, ymax);
        }
        return result;
    }
}
