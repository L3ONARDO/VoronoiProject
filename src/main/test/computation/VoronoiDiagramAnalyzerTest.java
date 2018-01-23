package computation;

import model.Point;
import model.Polygon;
import model.VoronoiDiagram;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class VoronoiDiagramAnalyzerTest {
    private VoronoiDiagramAnalyzer instance = new VoronoiDiagramAnalyzer();

    @Test
    public void analyze() {
        Set<Point> input = new HashSet<>();
        float xmin, xmax, ymin, ymax;
        xmin = ymin = -6f;
        xmax = ymax = 6;
        input.add(new Point(false, 0f, 1f));
        input.add(new Point(true, 2f, 0f));
        input.add(new Point(true, 4f, 1f));
        input.add(new Point(true, 2f, 2f));
        float[] result = instance.analyze(new VoronoiDiagram().calculate(input, xmin, xmax, ymin, ymax), input, xmin, xmax, ymin, ymax);
        assertEquals(true, result[0] > result[1]);
    }
}