package model;

import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class VoronoiDiagramTest {
    private VoronoiDiagram instance = new VoronoiDiagram();

    @Test
    public void simpleTest() {
        Set<Point> input = new HashSet<>();
        float xmin, xmax, ymin, ymax;
        xmin = ymin = -4f;
        xmax = ymax = 4f;
        input.add(new Point(true, -0.5f * (float) Math.sqrt(2), -0.5f * (float) Math.sqrt(2)));
        input.add(new Point(true, 0.5f * (float) Math.sqrt(2), -0.5f * (float) Math.sqrt(2)));
        input.add(new Point(true, 0f, 1f));
        List<Polygon> result = instance.calculate(input, xmin, xmax, ymin, ymax);
        assertEquals(3, result.size()); // Stepped through on debug to make sure the polygons themselves are also correct.
    }

    @Test
    public void twoTriangleTest() {
        Set<Point> input = new HashSet<>();
        float xmin, xmax, ymin, ymax;
        xmin = ymin = -6f;
        xmax = ymax = 6;
        input.add(new Point(true, 0f, 1f));
        input.add(new Point(true, 2f, 0f));
        input.add(new Point(true, 4f, 1f));
        input.add(new Point(true, 2f, 2f));
        List<Polygon> result = instance.calculate(input, xmin, xmax, ymin, ymax);
        assertEquals(4, result.size());
    }

}