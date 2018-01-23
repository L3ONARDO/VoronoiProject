package model;

public class Line implements LineInterface {

    private float a, b, c;

    public Line(float a, float b, float c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public Line(Point p1, Point p2) {
        this.a = (p2.getY() - p1.getY()) / (p2.getX() - p1.getX());
        this.b = -1f;
        float px = p1.getX() + ((p2.getX() - p1.getX()) / 2f);
        float py = p1.getY() + ((p2.getY() - p1.getY()) / 2f);
        this.c = py - this.a * px;
    }

    public float getAngle() {
        if (isVertical()) return (float) Math.PI / 2.0f;

        float arctan = (float) Math.atan(-a / b);
        if (arctan < 0f) arctan += (float) Math.PI;

        return arctan;
    }

//    public float angleBetween2Lines(Line line1, Line line2)
//    {
//        float angle1 = Math.atan2(line1.getY1() - line1.getY2(),
//                line1.getX1() - line1.getX2());
//        double angle2 = Math.atan2(line2.getY1() - line2.getY2(),
//                line2.getX1() - line2.getX2());
//        return angle1-angle2;
//    }

    public boolean isVertical() {
        if (a == Float.NEGATIVE_INFINITY || a == Float.POSITIVE_INFINITY) return true;
        return (b == 0 && a != 0);
    }

    public boolean isHorizontal() {
        return (a == 0 && b != 0);
    }

    public float solveForX(float y) {
        return (-c - b * y) / a;
    }

    public float solveForY(float x) { return  (-c - a*x)/b; }

    public Point intersection(LineInterface line) {
        if (line instanceof VerticalLine) {
            if (isVertical()) return null;
            VerticalLine verticalLine = (VerticalLine) line;
            return new Point(true, verticalLine.getX(), (-a / b) * verticalLine.getX() - (c / b));
        }
        Line l2 = (Line) line;
        if (b != 0) {
            float f = l2.a - l2.b*a/b;
            if (f == 0) {
                return null;
            } else {
                float x = (-l2.c + l2.b*c/b) / f;
                float y = (-c - a*x) / b;

                return new Point(true, x, y);
            }
        } else {
            if (a == 0) {
                return null;
            } else {
                float f = l2.b - l2.a*b/a;
                if (f == 0) {
                    return null;
                } else {
                    float y = (-l2.c + l2.a*c/a) / f;
                    float x = (-c - b*y) / a;

                    return new Point(true, x, y);
                }
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Line)) return false;
        Line line = (Line) o;
        return equalUpToMinusZero(a, line.a) && equalUpToMinusZero(b, line.b) && equalUpToMinusZero(c, line.c);
    }

    private boolean equalUpToMinusZero(float a, float b) {
        if (a == b) return true;
        if (a == 0.0f && b == -0.0f || a == -0.0f && b == 0.0f) return true;
        return false;
    }
}
