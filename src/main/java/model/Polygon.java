package model;

import java.util.ArrayList;
import java.util.List;

public class Polygon {
    private List<Point> points;

    public Polygon() {
        this.points = new ArrayList<>();
    }

    public void addPoint(Point point) {
        points.add(point);
    }

    public List<Point> getPoints() {
        return points;
    }

    public boolean deepContainsPoint(Point point) {
        for (Point p  : points) {
            if (p == point || (p.getX() == point.getX() && p.getY() == point.getY())) return true;
        }
        return false;
    }
}
