package model;

import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class DelaunayTriangulationTest {
    private DelaunayTriangulation instance = new DelaunayTriangulation();

    @Test
    public void trivialTest() {
        Point p1 = new Point(true, 0f, 0f);
        Point p2 = new Point(true, 0.5f, 1.0f);
        Point p3 = new Point(true, 1f, 0f);
        Set<Point> input = new HashSet<>();
        input.add(p1);
        input.add(p2);
        input.add(p3);
        List<Triangle> result = instance.calculate(input);
        assertEquals(1, result.size());
    }

}