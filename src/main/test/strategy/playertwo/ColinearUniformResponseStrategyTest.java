package strategy.playertwo;

import com.model.Point;
import org.junit.Test;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import static org.junit.Assert.*;

public class ColinearUniformResponseStrategyTest {
    private ColinearUniformResponseStrategy instance = new ColinearUniformResponseStrategy();

    @Test
    public void onePointTest() {
        Set<Point> input = new HashSet<>();
        input.add(new Point(true, 0f, 0f));
        input.add(new Point(true, 1f, 0f));
        Set<Point> result = instance.apply(1, input, 1f, 1f);
        assertEquals(1, result.size());
        assertEquals(new Point(false, 0.5f, PlayerTwoStrategy.EPSILON), result.iterator().next());
        input.add(new Point(true, 0.5f, 0f));
        result = instance.apply(1, input,1f, 1f);
        assertEquals(1, result.size());
        assertEquals(new Point(false, 0.25f, PlayerTwoStrategy.EPSILON), result.iterator().next());
    }

    @Test
    public void twoPointTest() {
        Set<Point> input = new HashSet<>();
        input.add(new Point(true, 0f, 0f));
        input.add(new Point(true, 1f, 0f));
        Set<Point> result = instance.apply(2, input, 1f, 1f);
        assertEquals(2, result.size());
        Point p1 = new Point(false, 0.5f, PlayerTwoStrategy.EPSILON);
        Point p2 = new Point(false, 0.5f, -PlayerTwoStrategy.EPSILON);
        Iterator<Point> i = result.iterator();
        Point r1 = i.next();
        Point r2 = i.next();
        assertEquals(true, (r1.equals(p1) && r2.equals(p2)) || (r1.equals(p2) && r2.equals(p1)));
    }

    @Test
    public void threePointTest() {
        Set<Point> input = new HashSet<>();
        input.add(new Point(true, 0f, 0f));
        input.add(new Point(true, 0.5f, 0f));
        input.add(new Point(true, 1f, 0f));
        Set<Point> result = instance.apply(3, input, 1f, 1f);
        assertEquals(3, result.size());
        Point p1 = new Point(false, 0.25f, PlayerTwoStrategy.EPSILON);
        Point p2 = new Point(false, 0.25f, -PlayerTwoStrategy.EPSILON);
        Point p3 = new Point(false, 0.75f, PlayerTwoStrategy.EPSILON);
        Set<Point> expectedPoints = new HashSet<>();
        expectedPoints.add(p1);
        expectedPoints.add(p2);
        expectedPoints.add(p3);
        Set<Point> unexpectedPoints = new HashSet<>();
        for (Point p : result) {
            boolean expected = false;
            for (Point q : expectedPoints) {
                if (p.equals(q)) expected = true;
            }
            if (!expected) {
                unexpectedPoints.add(p);
            }
        }
        assertEquals(0, unexpectedPoints.size());
    }

    @Test
    public void fourPointTest() {
        Set<Point> input = new HashSet<>();
        input.add(new Point(true, 0f, 0f));
        input.add(new Point(true, 0.5f, 0f));
        input.add(new Point(true, 1f, 0f));
        Set<Point> result = instance.apply(4, input, 1f, 1f);
        assertEquals(4, result.size());
        Point p1 = new Point(false, 0.25f, PlayerTwoStrategy.EPSILON);
        Point p2 = new Point(false, 0.25f, -PlayerTwoStrategy.EPSILON);
        Point p3 = new Point(false, 0.75f, PlayerTwoStrategy.EPSILON);
        Point p4 = new Point(false, 0.75f, -PlayerTwoStrategy.EPSILON);
        Set<Point> expectedPoints = new HashSet<>();
        expectedPoints.add(p1);
        expectedPoints.add(p2);
        expectedPoints.add(p3);
        expectedPoints.add(p4);
        Set<Point> unexpectedPoints = new HashSet<>();
        for (Point p : result) {
            boolean expected = false;
            for (Point q : expectedPoints) {
                if (p.equals(q)) expected = true;
            }
            if (!expected) {
                unexpectedPoints.add(p);
            }
        }
        assertEquals(0, unexpectedPoints.size());
    }

    @Test
    public void fivePointTest() {
        Set<Point> input = new HashSet<>();
        input.add(new Point(true, 0f, 0f));
        input.add(new Point(true, 0.25f, 0f));
        input.add(new Point(true, 0.5f, 0f));
        input.add(new Point(true, 0.75f, 0f));
        Set<Point> result = instance.apply(5, input, 1f, 1f);
        assertEquals(5, result.size());
        Point p1 = new Point(false, 0.125f, PlayerTwoStrategy.EPSILON);
        Point p2 = new Point(false, 0.125f, -PlayerTwoStrategy.EPSILON);
        Point p3 = new Point(false, 0.375f, PlayerTwoStrategy.EPSILON);
        Point p4 = new Point(false, 0.625f, -PlayerTwoStrategy.EPSILON);
        Point p5 = new Point(false, 0.625f, PlayerTwoStrategy.EPSILON);
        Set<Point> expectedPoints = new HashSet<>();
        expectedPoints.add(p1);
        expectedPoints.add(p2);
        expectedPoints.add(p3);
        expectedPoints.add(p4);
        expectedPoints.add(p5);
        Set<Point> unexpectedPoints = new HashSet<>();
        for (Point p : result) {
            boolean expected = false;
            for (Point q : expectedPoints) {
                if (p.equals(q)) expected = true;
            }
            if (!expected) {
                unexpectedPoints.add(p);
            }
        }
        assertEquals(0, unexpectedPoints.size());
    }
}