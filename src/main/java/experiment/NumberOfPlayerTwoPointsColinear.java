package experiment;

public class NumberOfPlayerTwoPointsColinear implements NumberOfPlayerTwoPoints {

    @Override
    public double[] calculate(double m) {
        double[] result = {m - 1, Math.ceil(3 * m / 4), Math.ceil(m / 2), Math.ceil(m / 4)};
        return result;
    }
}
