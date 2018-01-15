package strategy.playertwo;

import com.model.Point;
import com.util.MathUtils;
import strategy.Strategy;

import java.util.List;

public abstract class PlayerTwoStrategy implements Strategy {
    protected static final boolean OWNER = false;
    protected static final float EPSILON = 1.0e-2f;
    protected MathUtils mathUtils = new MathUtils();

    protected Point[] findLargestGap(List<Point> points) {
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
