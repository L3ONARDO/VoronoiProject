package model;

public class VerticalLine implements LineInterface {
    private float x;

    public VerticalLine(float x) {
        this.x = x;
    }

    @Override
    public boolean isVertical() {
        return true;
    }

    @Override
    public boolean isHorizontal() {
        return false;
    }

    @Override
    public Point intersection(LineInterface line) {
        if (line instanceof VerticalLine) return null;
        Line l = (Line) line;
        return l.intersection(this);
    }

    public float getX() {
        return x;
    }
}
