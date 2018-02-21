package util;

import model.*;
import model.Line;

/**
 * Created by Thomas on 21-2-2018.
 */
public class LineBuilder {

    public LineInterface build(Edge edge) {
        return build(edge.getP1(), edge.getP2());
    }

    public LineInterface build(Point a, Point b) {
        if (a.getX() == b.getX()) return new VerticalLine(a.getX());
        return new Line(a, b);
    }
}
