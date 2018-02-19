import computation.VoronoiDiagramAnalyzer;
import model.*;
import strategy.StrategyPair;
import strategy.playerone.ColinearUniformStrategy;
import strategy.playertwo.ColinearUniformResponseStrategy;

import java.util.List;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        float xmin = -10000f;
        float xmax = 10000f;
        float ymin = -10000f;
        float ymax = 10000f;
        StrategyPair strategyPair = new StrategyPair(new ColinearUniformStrategy(), new ColinearUniformResponseStrategy());
        Set<Point> input = strategyPair.apply(100, 99, xmax, ymax);
        VoronoiDiagram voronoiDiagram = new VoronoiDiagram();
        List<Polygon> result = voronoiDiagram.calculate(input, xmin, xmax, ymin, ymax);
        VoronoiDiagramAnalyzer voronoiDiagramAnalyzer = new VoronoiDiagramAnalyzer();
        float[] proportions = voronoiDiagramAnalyzer.analyze(result, xmin, xmax, ymin, ymax);
        System.out.println(result.size());
    }
}
