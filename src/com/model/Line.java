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
}
