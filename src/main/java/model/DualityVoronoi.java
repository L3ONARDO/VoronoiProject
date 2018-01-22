package model;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by littl on 15/01/2018.
 */
public class DualityVoronoi {
    public List<VPolygon> calculate(float xmax, float xmin, float ymax, float ymin, Set<Point> points) {

        List<Edge> VoronoiEdges = new ArrayList<>();
        List<Point> pointBuffer = new ArrayList<>();
        List<Edge> edgeBuffer = new ArrayList<>();
        DelaunayTriangulation triangulation = new DelaunayTriangulation();
        List<Triangle> triangles = triangulation.calculate(points);
        List<Point> VoronoiPoints = getVoronoiPoints(triangles);
        List<VPolygon> voronoiPolygons = new ArrayList<>();
        List<Point> voronoiPolygon = new ArrayList<>();

        Point lb = new Point(true, xmin, ymin);
        Point rb = new Point(true, xmin, ymax);
        Point lt = new Point(true, xmin, ymax);
        Point rt = new Point(true, xmax, ymax);
        VoronoiPoints.add(lb);
        VoronoiPoints.add(lt);
        VoronoiPoints.add(rb);
        VoronoiPoints.add(rt);

        Line topBound = new Line(lt, rt);
        Line bottomBound = new Line(lb, rb);
        Line leftBound = new Line(lb, lt);
        Line rightBound = new Line(rb, rt);

        for(Triangle t: triangles){

            edgeBuffer = t.getEdges();
            Edge edge1 = edgeBuffer.get(0);
            Edge edge2 = edgeBuffer.get(1);
            Point mass = edge1.getLine().intersection(edge2.getLine());


            for(Edge e: t.getEdges()){
                if(t.getAdjacentTriangle(e)!=null){
                    continue;
                }
                else{
                    Point boundIntersect = getboundIntersect(xmax, xmin, ymax, ymin, mass, e);
                    VoronoiPoints.add(boundIntersect);


                    Point next = null;
                    //TODO fix the recursive
                    while(!next.equals(mass)){
                        Point current1 = mass;
//                        next = getNextVPolygonPoint();
                        voronoiPolygon.add(next);
                    }

                    voronoiPolygons.add(new VPolygon(voronoiPolygon));



//                    voronoiPolygon.add(mass);
//                    voronoiPolygon.add(boundIntersect);
//                    Point p3 = getNextVPolygonPoint(mass, boundIntersect, VoronoiPoints);
//                    voronoiPolygon.add(p3);
//                    Point p4 = getNextVPolygonPoint(boundIntersect, p3, VoronoiPoints);
//                    voronoiPolygon.add(p4);
//                    for(Point p: VoronoiPoints){
//                        Point initial1 = p3;
//                        Point initial2 = p4;
//                        if(!initial2.equals(mass)){
//                            initial1 = initial2;
//                            initial2 = getNextVPolygonPoint(initial1, initial2, VoronoiPoints)
//                        }
//
//                    }


                }
            }
        }
        return voronoiPolygons;
    }


    private List<VPolygon> getP1Polygons(List<VPolygon> voronoipolygons){
        List<VPolygon> p1 = null;
        for(VPolygon vp: voronoipolygons){
            if(vp.isOwner()){
                p1.add(vp);
            }
        }
        return p1;
    }

    private List<VPolygon> getP2Polygons(List<VPolygon> voronoipolygons){
        List<VPolygon> p2 = null;
        for(VPolygon vp: voronoipolygons){
            if(!vp.isOwner()){
                p2.add(vp);
            }
        }
        return p2;
    }

//    private List<VPolygon> assignPlayers(List<VPolygon> voronoipolygons, List<Triangle> triangles){
//
//    }


    private Point getNextVPolygonPoint(Point currentpoint1, Point currentpoint2, List<Point> voronoiPoints){
        Edge initial = new Edge(currentpoint1, currentpoint2);
        List<Edge> buffer = new ArrayList<>();
        for(Point p: voronoiPoints){
            if(!p.equals(currentpoint2)){
                buffer.add(new Edge(p, currentpoint2));
            }
        }
        float wanted = -Float.MAX_VALUE;
        Edge etemp = null;
        for(Edge edge: buffer){
            float anglediff = angleBetween2Edges(initial, edge);
            if( anglediff <=0 ){
                if(anglediff > wanted){
                    wanted = anglediff;
                    etemp = edge;
                }
            }
        }
        if(etemp.getP1().equals(currentpoint2)){
            return etemp.getP2();
        }
        else{
            return etemp.getP1();
        }

    }

    private float angleBetween2Edges(Edge e1, Edge e2)
    {
        float angle1 = (float)Math.atan2(e1.getP1().getY() - e1.getP2().getY(),
                e1.getP1().getX() - e1.getP2().getX());
        float angle2 = (float)Math.atan2(e2.getP1().getY() - e2.getP2().getY(),
                e2.getP1().getX() - e2.getP2().getX());
        return angle1-angle2;
    }

    //the returning will be a list of all mass points of triangulation, the corners and bound intersections are not
    //added yet
    private List<Point> getVoronoiPoints(List<Triangle> triangles){
        List<Point> voronoiPoints = new ArrayList<>();
        List<Edge> edgeBuffer;
        for(Triangle t: triangles){
            edgeBuffer = t.getEdges();
            Edge edge1 = edgeBuffer.get(0);
            Edge edge2 = edgeBuffer.get(1);
            Point mass = edge1.getLine().intersection(edge2.getLine());
            voronoiPoints.add(mass);
            edgeBuffer.clear();
        }
        return voronoiPoints;
    }
    //calculate a bound intersection case with given parametre
    private Point getboundIntersect(float xmax, float xmin, float ymax, float ymin, Point mass, Edge e){
        Point bisect = e.getBisectionalPoint();
        Line bisector = new Line(mass, bisect);
        if(mass.getX() < bisect.getX()) {
            if (mass.getY() > bisect.getY()){
                if(bisector.solveForX(ymin) < xmax){
                    Point boundIntersect = new Point(true, bisector.solveForX(ymin) ,ymin);
                    return boundIntersect;

                }
                else{
                    Point boundIntersect = new Point(true, xmax ,bisector.solveForY(xmax));
                    return boundIntersect;
                }
            }
            else{
                if(bisector.solveForX(ymax) < xmax){
                    Point boundIntersect = new Point(true, bisector.solveForX(ymax) ,ymax);
                    return boundIntersect;
                }
                else {
                    Point boundIntersect = new Point(true, xmax, bisector.solveForY(xmax));
                    return boundIntersect;
                }
            }
        }
        else{
            if (mass.getY() > bisect.getY()){
                if(bisector.solveForX(ymin) > xmin){
                    Point boundIntersect = new Point(true, bisector.solveForX(ymin) ,ymin);
                    return boundIntersect;
                }
                else{
                    Point boundIntersect = new Point(true, xmin ,bisector.solveForY(xmin));
                    return boundIntersect;
                }
            }
            else{
                if(bisector.solveForX(ymax) > xmin){
                    Point boundIntersect = new Point(true, bisector.solveForX(ymax) ,ymax);
                    return boundIntersect;
                }
                else {
                    Point boundIntersect = new Point(true, xmin, bisector.solveForY(xmin));
                    return boundIntersect;
                }
            }
        }
    }
}
