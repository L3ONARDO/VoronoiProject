package util;

import model.Point;
import org.junit.Test;
import util.MathUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class MathUtilsTest {
    private MathUtils instance = new MathUtils();

    @Test
    public void excludeInnerPointTest() {
        Point p1 = new Point(true, 2f, 1f);
        Point p2 = new Point(true, 1f, 1f);
        Point p3 = new Point(true, 0f, 1f);
        Point p4 = new Point(true, 1f, 0f);
        Point p5 = new Point(true, 1f, 2f);
        Set<Point> input = new HashSet<Point>();
        input.add(p1);
        input.add(p2);
        input.add(p3);
        input.add(p4);
        input.add(p5);
        List<Point> result = instance.orderByGiftWrapping(input);
        assertEquals(4, result.size());
        assertTrue(result.get(0).equals(p4));
        assertTrue(result.get(1).equals(p1));
        assertTrue(result.get(2).equals(p5));
        assertTrue(result.get(3).equals(p3));
    }
}