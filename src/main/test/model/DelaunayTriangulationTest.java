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
        List<Triangle> result = instance.calculate(buildInput(p1, p2, p3));
        assertEquals(1, result.size());
        assertEquals(true, result.get(0).getP1().equals(p1) || result.get(0).getP2().equals(p1) || result.get(0).getP3().equals(p1));
        assertEquals(true, result.get(0).getP1().equals(p2) || result.get(0).getP2().equals(p2) || result.get(0).getP3().equals(p2));
        assertEquals(true, result.get(0).getP1().equals(p3) || result.get(0).getP2().equals(p3) || result.get(0).getP3().equals(p3));
    }

    @Test
    public void simpleTest() {
        Point p1 = new Point(true, 0f, 1f);
        Point p2 = new Point(true, 2f, 2f);
        Point p3 = new Point(true, 2f, 0f);
        Point p4 = new Point(true, 4f, 1f);
        List<Triangle> result = instance.calculate(buildInput(p1, p2, p3, p4));
        assertEquals(2, result.size());
    }

    private Set<Point> buildInput(Point... points) {
        Set<Point> result = new HashSet<>();
        for (Point p : points) {
            result.add(p);
        }
        return result;
    }
}