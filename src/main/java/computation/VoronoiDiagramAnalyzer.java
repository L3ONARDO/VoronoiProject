package computation;

import model.Point;
import model.Polygon;

import java.util.List;
import java.util.Set;

public class VoronoiDiagramAnalyzer {

    public VoronoiDiagramAnalyzer() {}

    public float[] analyze(List<Polygon> voronoiDiagram, Set<Point> inputPoints, float xmin, float xmax, float ymin, float ymax) {
        // Set the ownership of each polygon.
        calculateOwnership(voronoiDiagram, inputPoints);

        // Calculate total area
        float totalArea = (xmax - xmin) * (ymax - ymin);

        // Calculate cumulative area per owner
        float[] result = new float[2];
        for (Polygon p : voronoiDiagram) {
            int i = 0;
            if (!p.getOwner()) i = 1;
            result[i] = result[i] + p.getArea();
        }
        // Calculate area proportion
        result[0] = result[0] / totalArea;
        result[1] = result[1] / totalArea;
        return result;
    }

    private void calculateOwnership(List<Polygon> polygons, Set<Point> points) {
        for (Polygon p : polygons) {
            for (Point q : points) {
                if (p.hasInteriorPoint(q)) {
                    p.setOwner(q.getOwner());
                    break;
                }
            }
        }
    }
}
