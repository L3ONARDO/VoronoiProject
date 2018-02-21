package experiment;

import computation.VoronoiDiagramAnalyzer;
import kn.uni.voronoitreemap.datastructure.OpenList;
import kn.uni.voronoitreemap.diagram.PowerDiagram;
import kn.uni.voronoitreemap.j2d.PolygonSimple;
import kn.uni.voronoitreemap.j2d.Site;
import model.Point;
import model.Polygon;
import strategy.StrategyPair;
import strategy.playerone.ColinearNonUniformStrategy;
import strategy.playerone.ColinearUniformStrategy;
import strategy.playerone.GridLikeImprovedStrategy;
import strategy.playerone.GridLikeUniformStrategy;
import strategy.playertwo.BenchmarkingResponseStrategy;
import strategy.playertwo.ColinearNonUniformResponseStrategy;
import strategy.playertwo.ColinearUniformResponseStrategy;
import strategy.playertwo.GridLikeResponseStrategy;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Experiment {
    float scale = 10000f;

    public void perform(int testId, StrategyPair strategyPair, float xmin, float xmax, float ymin, float ymax, double[] pop, NumberOfPlayerTwoPoints numberOfPlayerTwoPoints, String playerOneStrategyName, String playerTwoStrategyName, boolean outputPoints) {
        VoronoiDiagramAnalyzer analyzer = new VoronoiDiagramAnalyzer();

        for (double m : pop) {
            double[] ptp = numberOfPlayerTwoPoints.calculate(m);
            for (double n : ptp) {
                n = Math.min(m - 1, n);
                Set<Point> input = strategyPair.apply((int) m, (int) n, xmax, ymax);
                input = scaleInput(input);
                if (outputPoints) {
                    for (Point p : input) {
                        System.out.println(p.getX() + "," + p.getY());
                    }
                    System.out.println("\n\n");
                }
                List<Polygon> result = computeVoronoi(input, (int) xmin, (int) xmax, (int) ymin, (int) ymax);
                float[] stats = analyzer.analyze(result, xmin, xmax, ymin, ymax);
                int winner = 0;
                if (stats[0] < stats[1]) winner = 1;
                if (stats[0] == stats[1]) winner = 2;
                String[] winnerStrings = {"player1", "player2", "tie"};
                System.out.println(testId + "," + m + "," + n + "," + stats[0] + "," + stats[1] + "," + playerOneStrategyName + "," + playerTwoStrategyName + "," + winnerStrings[winner]);
            }
        }
    }

    private Set<Point> scaleInput(Set<Point> input) {
        Set<Point> result = new HashSet<>();
        for (Point p : input) {
            result.add(new Point(p.getOwner(), scale * p.getX(), scale * p.getY()));
        }
        return result;
    }

    private List<Polygon> computeVoronoi(Set<Point> input, int xmin, int xmax, int ymin, int ymax) {
        PowerDiagram diagram = new PowerDiagram();

        OpenList sites = new OpenList();
        PolygonSimple rootPolygon = new PolygonSimple();
        rootPolygon.add(scale * xmin, scale * ymin);
        rootPolygon.add(scale * xmax, scale * ymin);
        rootPolygon.add(scale * xmax, scale * ymax);
        rootPolygon.add(scale * xmin, scale * ymax);

        for (Point point : input) {
            Site site = new Site(point.getX(), point.getY());
            site.setWeight(0);
            site.setOwner(point.getOwner());
            sites.add(site);
        }

        diagram.setSites(sites);
        diagram.setClipPoly(rootPolygon);

        diagram.computeDiagram();

        return diagram.getPolygons(1f / scale);
    }
}
