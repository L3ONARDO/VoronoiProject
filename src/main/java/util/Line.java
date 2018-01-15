package util;

import model.Point;

public class Line  {
    private Point p1;
    private Point p2;

    public Line(Point p1, Point p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    public Point getIntersectionWithLine(Line line) {
        Point p1, p2, p3, p4;
        p1 = this.p1;
        p2 = this.p2;
        p3 = line.getP1();
        p4 = line.getP2();
        double d = (p1.getX() - p2.getX()) * (p3.getY() - p4.getY()) - (p1.getY() - p2.getY()) * (p3.getX() - p4.getX());
        if (d == 0) {
            return null;
        }
        double pre = (p1.getX() * p2.getY() - p1.getY() * p2.getX()), post = (p3.getX() * p4.getY() - p3.getY() * p4.getX());
        double x = (pre * (p3.getX() - p4.getX()) - (p1.getX() - p2.getX()) * post) / d;
        double y = (pre * (p3.getY() - p4.getY()) - (p1.getY() - p2.getY()) * post) / d;
        if (x < Math.min(p1.getX(), p2.getX()) || x > Math.max(p1.getX(), p2.getX())
                || x < Math.min(p3.getX(), p4.getX()) || x > Math.max(p3.getX(), p4.getX())) {
            return null;
        }
        if (y < Math.min(p1.getY(), p2.getY()) || y > Math.max(p1.getY(), p2.getY())
                || y < Math.min(p3.getY(), p4.getY()) || y > Math.max(p3.getY(), p4.getY())) {
            return null;
        }
        return new Point(true, (float) x, (float) y);
    }


    public Point getP1() {
        return p1;
    }

    public void setP1(Point p1) {
        this.p1 = p1;
    }

    public Point getP2() {
        return p2;
    }

    public void setP2(Point p2) {
        this.p2 = p2;
    }

    public double getX1() {
        return this.p1.getX();
    }

    public double getX2() {
        return this.p2.getX();
    }

    public double getY1() {
        return this.p1.getY();
    }

    public double getY2() {
        return this.p2.getY();
    }
}

