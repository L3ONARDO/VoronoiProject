package kn.uni.voronoitreemap.diagram;

import kn.uni.voronoitreemap.datastructure.OpenList;
import kn.uni.voronoitreemap.j2d.PolygonSimple;
import kn.uni.voronoitreemap.j2d.Site;
import model.Point;
import model.Polygon;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by Thomas on 21-2-2018.
 */
public class PowerDiagramTest {

    @Test
    public void testGetPolygons() throws Exception {
        Set<Point> input = new HashSet<>();
        float scale = 10f;
        input.add(new Point(true, 0.25f, 0.25f));
        input.add(new Point(true,  0.75f, 0.75f));
//        input.add(new Point(true, 0.75f, 0.25f));
//        input.add(new Point(true, 0.75f, 0.75f));
//        input.add(new Point(true, 0.25f, 0.75f));
        PowerDiagram diagram = new PowerDiagram();

        OpenList sites = new OpenList();

        PolygonSimple rootPolygon = new PolygonSimple();
        rootPolygon.add(scale * 0, scale * 0);
        rootPolygon.add(scale * 1, scale * 0);
        rootPolygon.add(scale * 1, scale * 1);
        rootPolygon.add(scale * 0, scale * 1);

        for (Point point : input) {
            Site site = new Site(scale * point.getX(), scale * point.getY());
            site.setWeight(0);
            site.setOwner(point.getOwner());
            sites.add(site);
        }

        diagram.setSites(sites);
        diagram.setClipPoly(rootPolygon);

        diagram.computeDiagram();

        List<Polygon> result = diagram.getPolygons(1f / scale);

        assertEquals(4, result.size());
    }
}