package ru.dubov.voronoidiagram;

import org.junit.Test;
import ru.dubov.primitives.Point;
import ru.dubov.primitives.Polygon;
import strategy.playerone.GridLikeUniformStrategy;
import util.PolygonIntersection;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by Thomas on 21-2-2018.
 */
public class VoronoiDiagramTest {
    private final PolygonIntersection polygonIntersection = new PolygonIntersection();
    @Test
    public void gridTest() {
        GridLikeUniformStrategy gridLikeUniformStrategy = new GridLikeUniformStrategy();
        Set<model.Point> aux = gridLikeUniformStrategy.apply(9, null, 1f, 1f);
        List<Point> input = new ArrayList<>();
        for (model.Point p : aux) {
            input.add(new Point(p.getX(), p.getY()));
        }
        VoronoiDiagram instance = new VoronoiDiagram();
        List<Polygon> result = instance.viaHalfplanesIntersection(input);
        List<model.Polygon> modelPolygons = new ArrayList<>();
        List<model.Polygon> resultPolygons = new ArrayList<>();
        for (Polygon p : result) {
            model.Polygon q = new model.Polygon();
            for (int i = 0; i < p.size(); i++) {
                q.addPoint(new model.Point(true, (float) p.get(i).getX(), (float) p.get(i).getY()));
            }
            modelPolygons.add(q);
        }
        model.Polygon boundary = new model.Polygon();
        boundary.addPoint(new model.Point(true, 0f, 0f));
        boundary.addPoint(new model.Point(true, 1f, 0f));
        boundary.addPoint(new model.Point(true, 1f, 1f));
        boundary.addPoint(new model.Point(true, 0f, 1f));
        for (model.Polygon polygon : modelPolygons) {
            resultPolygons.add(polygonIntersection.calculate(boundary, polygon));
        }
        System.out.println();
    }

}