package util;

import model.Point;
import model.Polygon;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Thomas on 21-2-2018.
 */
public class PolygonIntersectionTest {
    private final PolygonIntersection instance = new PolygonIntersection();

    @Test
    public void simpleTest() {
        Polygon a = new Polygon();
        Polygon b = new Polygon();
        Polygon expectedPolygon = new Polygon();
        a.addPoint(new Point(true, 0f, 0.1f));
        a.addPoint(new Point(true, 1f, 0.1f));
        a.addPoint(new Point(true, 1f, 0.9f));
        a.addPoint(new Point(true, 0f, 0.9f));
        b.addPoint(new Point(true, 0.5f, 0f));
        b.addPoint(new Point(true, 1.5f, 0f));
        b.addPoint(new Point(true, 1.5f, 1f));
        b.addPoint(new Point(true, 0.5f, 1f));
        expectedPolygon.addPoint(new Point(true, 0.5f, 0.1f));
        expectedPolygon.addPoint(new Point(true, 1f, 0.1f));
        expectedPolygon.addPoint(new Point(true, 1f, 0.9f));
        expectedPolygon.addPoint(new Point(true, 0.5f, 0.9f));
        Polygon result = instance.calculate(a, b);
        assertEquals(4, result.getPoints().size());
        for (int i = 0; i < 4; i++) {
            assertEquals(expectedPolygon.getPoints().get(i), result.getPoints().get(i));
        }
    }

    @Test
    public void simpleOutOfOrderTest() {
        Polygon a = new Polygon();
        Polygon b = new Polygon();
        Polygon expectedPolygon = new Polygon();
        a.addPoint(new Point(true, 0f, 0.1f));
        a.addPoint(new Point(true, 1f, 0.9f));
        a.addPoint(new Point(true, 0f, 0.9f));
        a.addPoint(new Point(true, 1f, 0.1f));
        b.addPoint(new Point(true, 1.5f, 0f));
        b.addPoint(new Point(true, 0.5f, 0f));
        b.addPoint(new Point(true, 1.5f, 1f));
        b.addPoint(new Point(true, 0.5f, 1f));
        expectedPolygon.addPoint(new Point(true, 0.5f, 0.1f));
        expectedPolygon.addPoint(new Point(true, 1f, 0.1f));
        expectedPolygon.addPoint(new Point(true, 1f, 0.9f));
        expectedPolygon.addPoint(new Point(true, 0.5f, 0.9f));
        Polygon result = instance.calculate(a, b);
        assertEquals(4, result.getPoints().size());
        for (int i = 0; i < 4; i++) {
            assertEquals(expectedPolygon.getPoints().get(i), result.getPoints().get(i));
        }
    }

    @Test
    public void onBoundaryTest() {
        Polygon a = new Polygon();
        Polygon b = new Polygon();
        Polygon expectedPolygon = new Polygon();
        a.addPoint(new Point(true, 0f, 0f));
        a.addPoint(new Point(true, 1f, 0f));
        a.addPoint(new Point(true, 1f, 1f));
        a.addPoint(new Point(true, 0f, 1f));
        b.addPoint(new Point(true, 0.5f, 0f));
        b.addPoint(new Point(true, 1.5f, 0f));
        b.addPoint(new Point(true, 1.5f, 1f));
        b.addPoint(new Point(true, 0.5f, 1f));
        expectedPolygon.addPoint(new Point(true, 0.5f, 0f));
        expectedPolygon.addPoint(new Point(true, 1f, 0f));
        expectedPolygon.addPoint(new Point(true, 1f, 1f));
        expectedPolygon.addPoint(new Point(true, 0.5f, 1f));
        Polygon result = instance.calculate(a, b);
        assertEquals(4, result.getPoints().size());
        for (int i = 0; i < 4; i++) {
            assertEquals(expectedPolygon.getPoints().get(i), result.getPoints().get(i));
        }
    }

//    @Test
//    public void containmentTest() {
//        Polygon a = new Polygon();
//        Polygon b = new Polygon();
//        Polygon expectedPolygon = new Polygon();
//        a.addPoint(new Point(true, 0.25f, 0.25f));
//        a.addPoint(new Point(true, 0.75f, 0.25f));
//        a.addPoint(new Point(true, 0.75f, 0.75f));
//        a.addPoint(new Point(true, 0.25f, 0.75f));
//        b.addPoint(new Point(true, 0f, 0f));
//        b.addPoint(new Point(true, 1f, 0f));
//        b.addPoint(new Point(true, 1f, 1f));
//        b.addPoint(new Point(true, 0f, 1f));
//        expectedPolygon.addPoint(new Point(true, 0.75f, 0.25f));
//        expectedPolygon.addPoint(new Point(true, 0.75f, 0.75f));
//        expectedPolygon.addPoint(new Point(true, 0.25f, 0.75f));
//        expectedPolygon.addPoint(new Point(true, 0.25f, 0.25f));
//        Polygon result = instance.calculate(a, b);
//        assertEquals(4, result.getPoints().size());
//        for (int i = 0; i < 4; i++) {
//            assertEquals(expectedPolygon.getPoints().get(i), result.getPoints().get(i));
//        }
//    }
}