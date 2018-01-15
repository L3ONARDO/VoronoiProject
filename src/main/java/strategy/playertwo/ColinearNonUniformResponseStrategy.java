package strategy.playertwo;

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
}
