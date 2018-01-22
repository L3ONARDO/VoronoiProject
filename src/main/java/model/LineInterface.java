package model;

public interface LineInterface {

    boolean isVertical();

    boolean isHorizontal();

    Point intersection(LineInterface line);
}
