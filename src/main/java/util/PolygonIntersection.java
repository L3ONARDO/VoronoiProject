package util;

import model.Edge;
import model.LineInterface;
import model.Point;
import model.Polygon;

/**
 * Created by Thomas on 21-2-2018.
 */
public class PolygonIntersection {
    private final MathUtils mathUtils = new MathUtils();
    private final LineBuilder lineBuilder = new LineBuilder();

    public Polygon calculate(Polygon p, Polygon q) {
        p = mathUtils.orderByGiftWrapping(p);
        q = mathUtils.orderByGiftWrapping(q);
        Polygon result = new Polygon();
        for (Point a : p.getPoints()) {
            if (a.inPolygon(q)) result.addPoint(a);
        }
        for (Point a : q.getPoints()) {
            if (a.inPolygon(p)) result.addPoint(a);
        }
        for (int i = 0; i < p.getPoints().size(); i++) {
            Point p1 = p.getPoints().get(i);
            Point p2 = p.getPoints().get((i + 1) % p.getPoints().size());
            LineInterface a = lineBuilder.build(p1, p2);
            Edge e = new Edge(p1, p2);
            for (int j = 0; j < q.getPoints().size(); j++) {
                Point q1 = q.getPoints().get(j);
                Point q2 = q.getPoints().get((j + 1) % q.getPoints().size());
                LineInterface b = lineBuilder.build(q1, q2);
                Edge f = new Edge(q1, q2);
                Point intersection = a.intersection(b);
                if (intersection != null && intersection.onEdge(e) && intersection.onEdge(f) && !result.deepContainsPoint(intersection)) {
                    result.addPoint(intersection);
                }
            }
        }
        return mathUtils.orderByGiftWrapping(result);
    }
}
