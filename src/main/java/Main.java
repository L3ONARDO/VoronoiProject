import experiment.Experiment;
import experiment.NumberOfPlayerTwoPointsColinear;
import strategy.StrategyPair;
import strategy.playerone.ColinearNonUniformStrategy;
import strategy.playerone.ColinearUniformStrategy;
import strategy.playerone.GridLikeImprovedStrategy;
import strategy.playerone.GridLikeUniformStrategy;
import strategy.playertwo.BenchmarkingResponseStrategy;
import strategy.playertwo.ColinearNonUniformResponseStrategy;
import strategy.playertwo.ColinearUniformResponseStrategy;
import strategy.playertwo.GridLikeResponseStrategy;

public class Main {

    public static void main(String[] args) {
        float xmin = 0f;
        float xmax = 1f;
        float ymin = 0f;
        float ymax = 1f;
        StrategyPair colinearNonUniformVsBenchmarking = new StrategyPair(new ColinearNonUniformStrategy(), new BenchmarkingResponseStrategy());
        StrategyPair colinearNonUniformVsResponse = new StrategyPair(new ColinearNonUniformStrategy(), new ColinearNonUniformResponseStrategy());
        StrategyPair colinearUniformVsBenchmarking = new StrategyPair(new ColinearUniformStrategy(), new BenchmarkingResponseStrategy());
        StrategyPair colinearUniformVsResponse = new StrategyPair(new ColinearUniformStrategy(), new ColinearUniformResponseStrategy());
        StrategyPair gridLikeUniformVsBenchmarking = new StrategyPair(new GridLikeUniformStrategy(), new BenchmarkingResponseStrategy());
        StrategyPair gridLikeUniformVsResponse = new StrategyPair(new GridLikeUniformStrategy(), new GridLikeResponseStrategy());
        StrategyPair gridLikeImprovedVsBenchmarking = new StrategyPair(new GridLikeImprovedStrategy(), new BenchmarkingResponseStrategy());
        StrategyPair gridLikeImprovedVsResponse = new StrategyPair(new GridLikeImprovedStrategy(), new GridLikeResponseStrategy());

        Experiment experiment = new Experiment();
        double[] pop = {2, 10, 100, 1000};
//        experiment.perform(0, colinearNonUniformVsBenchmarking, xmin, xmax, ymin, ymax, pop,
//                new NumberOfPlayerTwoPointsColinear(), "colinearNonUniform",
//                "benchmarking", false);
//        experiment.perform(1, colinearNonUniformVsResponse, xmin, xmax, ymin, ymax, pop,
//                new NumberOfPlayerTwoPointsColinear(), "colinearNonUniform",
//                "colinearNonUniformResponse", false);
//        experiment.perform(2, colinearUniformVsBenchmarking, xmin, xmax, ymin, ymax, pop,
//                new NumberOfPlayerTwoPointsColinear(), "colinearUniform",
//                "benchmarking", false);
        // This one does not seem to work.
//        experiment.perform(3, colinearUniformVsResponse, xmin, xmax, ymin, ymax, pop,
//                new NumberOfPlayerTwoPointsColinear(), "colinearUniform",
//                "colinearUniformResponse", false);
        double[] pop2 = {4, 36, 256, 1024};
//        experiment.perform(4, gridLikeUniformVsBenchmarking, xmin, xmax, ymin, ymax, pop2,
//                new NumberOfPlayerTwoPointsColinear(), "gridLikeUniform",
//                "benchmarking", false);
        experiment.perform(5, gridLikeUniformVsResponse, xmin, xmax, ymin, ymax, pop2,
                new NumberOfPlayerTwoPointsColinear(), "gridLikeUniform",
                "gridLikeUniformResponse", false);
//        experiment.perform(6, gridLikeImprovedVsBenchmarking, xmin, xmax, ymin, ymax, pop2,
//                new NumberOfPlayerTwoPointsColinear(), "gridLikeImproved",
//                "benchmarking", false);
//        experiment.perform(7, gridLikeImprovedVsResponse, xmin, xmax, ymin, ymax, pop2,
//                new NumberOfPlayerTwoPointsColinear(), "gridLikeImrpoved",
//                "gridLikeImprovedResponse", false);
    }
}
