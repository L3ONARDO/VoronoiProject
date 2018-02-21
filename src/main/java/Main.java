import computation.VoronoiDiagramAnalyzer;
import kn.uni.voronoitreemap.datastructure.OpenList;
import kn.uni.voronoitreemap.diagram.PowerDiagram;
import kn.uni.voronoitreemap.j2d.PolygonSimple;
import kn.uni.voronoitreemap.j2d.Site;
import model.*;
import strategy.StrategyPair;
import strategy.playerone.ColinearUniformStrategy;
import strategy.playerone.GridLikeUniformStrategy;
import strategy.playertwo.ColinearUniformResponseStrategy;

import java.util.List;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        GridLikeUniformStrategy gridLikeUniformStrategy = new GridLikeUniformStrategy();
        Set<Point> input = gridLikeUniformStrategy.apply(196, null, 1f, 1f);

        PowerDiagram diagram = new PowerDiagram();

        OpenList sites = new OpenList();

        PolygonSimple rootPolygon = new PolygonSimple();
        rootPolygon.add(0, 0);
        rootPolygon.add(1, 0);
        rootPolygon.add(1, 1);
        rootPolygon.add(0, 1);

        for (Point point : input) {
            Site site = new Site(point.getX(), point.getY());
            site.setWeight(0);
            sites.add(site);
        }

        diagram.setSites(sites);
        diagram.setClipPoly(rootPolygon);

        diagram.computeDiagram();
        List<Polygon> result = diagram.getPolygons();
        System.out.println();
    }
}
