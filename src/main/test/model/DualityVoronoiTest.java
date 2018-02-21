package model;

import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DualityVoronoiTest {

    @Test
    public void test() {
        DualityVoronoi instance = new DualityVoronoi();
        Set<Point> input = new HashSet<>();
        input.add(new Point(true, -2f, 0f));
        input.add(new Point(true, 0f, 1f));
        input.add(new Point(true, 2f, 0f));
        input.add(new Point(true, 0f, -1f));
        List<VPolygon> polygons = instance.calculate(4f, -4f, 2, -2f, input);
        System.out.println(polygons.size());
    }
}