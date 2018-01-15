package strategy.playerone;

import model.Point;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class ColinearNonUniformStrategy extends PlayerOneStrategy {

    public Set<Point> apply(int n, Set<Point> inputPoints, float xmax, float ymax) {
        Random random = new Random();
        Set<Point> result = new HashSet<>();
        // Calculate statistics for normal distribution, as well as base x and constant y.
        float mu = xmax / (n + 1);
        float sigma = mu / ((n + 1) * (n + 1));
        float x = 0f;
        float y = ymax / 2.0f;
        for (int i = 0; i < n; i++) { // Add new points based on normal distribution samples.
            x += Math.abs(random.nextGaussian() * sigma + mu);
            x = Math.min(x, 1.0f);
            Point point = new Point(OWNER, x, y);
            result.add(point);
        }
        if (ymax > xmax) { // If the bounding box is taller than it is wide, transform the points.
            return transformY(result, xmax, ymax);
        }
        return result;
    }
}
