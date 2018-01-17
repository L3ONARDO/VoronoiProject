package model;

import java.util.List;

/**
 * Created by littl on 16/01/2018.
 */
public class VPolygon {
    private List<Point> points;
    private List<Edge> edges;
    private Point voronoiPoint;
    private boolean owner;

    public VPolygon(List<Point> points) {
        this.points = points;
        this.owner = owner;
    }

    public boolean isOwner() {
        return owner;
    }

    public void setOwner(boolean owner) {
        this.owner = owner;
    }

    public List<Point> getPoints() {
        return points;
    }

    //invert if result is neg
    public float getArea(VPolygon vPolygon){
        float area = 0;
        List<Point> vertices = vPolygon.getPoints();
        int j = vertices.size()-1;
        for (int i = 0; i < vertices.size(); i++ ){
            area = area + (vertices.get(j).getX() + vertices.get(i).getX())*
                    (vertices.get(j).getY() - vertices.get(i).getY());
            j = i;
        }
        return area/2.0f;
    }
}
