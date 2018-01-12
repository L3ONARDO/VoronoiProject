package com.model;

public class Line {

    private float a, b, c;

    public Line(float a, float b, float c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public Line(Point p1, Point p2) {
        this.a = p1.getY() - p2.getY();
        this.b = p2.getX() - p1.getY();
        this.c = p1.getX() * p2.getY() - p2.getX() * p1.getY();
    }


    public float getAngle() {
        if (isVertical()) return (float) Math.PI / 2.0f;

        float arctan = (float) Math.atan(-a / b);
        if (arctan < 0f) arctan += (float) Math.PI;

        return arctan;
    }

    private boolean isVertical() {
        return (b == 0 && a != 0);
    }

    public boolean isHorizontal() {
        return (a == 0 && b != 0);
    }

    public float solveForX(float y) {
        return (-c - b * y) / a;
    }

    public static Point intersection(Line l1, Line l2) {
        if (l1.b != 0) {
            float f = l2.a - l2.b*l1.a/l1.b;
            if (f == 0) {
                return null;
            } else {
                float x = (-l2.c + l2.b*l1.c/l1.b) / f;
                float y = (-l1.c - l1.a*x) / l1.b;

                return new Point(true, x, y);
            }
        } else {
            if (l1.a == 0) {
                return null;
            } else {
                float f = l2.b - l2.a*l1.b/l1.a;
                if (f == 0) {
                    return null;
                } else {
                    float y = (-l2.c + l2.a*l1.c/l1.a) / f;
                    float x = (-l1.c - l1.b*y) / l1.a;

                    return new Point(true, x, y);
                }
            }
        }
    }
}
