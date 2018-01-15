package com.voronoiStrategy.playertwo;

import com.model.Point;
import com.util.MergeSortPointSet;

import java.util.*;

public class GridLikeResponseStrategy extends PlayerTwoStrategy {

    public Set<Point> apply(int n, Set<Point> inputPoints, float xmax, float ymax) {
        Set<Point> result = new HashSet<Point>();
        List<Point> newconvexHullVertices = new ArrayList<Point>();
        List<Point> points = new ArrayList<Point>(inputPoints);

        // Adding the four bouding points of new convex hull
        float changedx = points.get(0).getX() - EPSILON;
        float changedy = points.get(0).getY() + EPSILON;
        Point point1 = new Point(OWNER, changedx, changedy);
        result.add(point1);
        newconvexHullVertices.add(point1);

        changedx = points.get(1).getX() + EPSILON;
        changedy = points.get(1).getY() - EPSILON;
        Point point2 = new Point(OWNER, changedx, changedy);
        result.add(point2);
        newconvexHullVertices.add(point2);

        changedx = points.get(2).getX() - EPSILON;
        changedy = points.get(2).getY() + EPSILON;
        Point point3 = new Point(OWNER, changedx, changedy);
        result.add(point3);
        newconvexHullVertices.add(point3);

        changedx = points.get(3).getX() + EPSILON;
        changedy = points.get(3).getY() + EPSILON;
        Point point4 = new Point(OWNER, changedx, changedy);
        result.add(point4);
        newconvexHullVertices.add(point4);

        //Iterate the points over the edges of convex hull

                //1. Create a list of all the edges in the convex hull. 

        List<GridEdge> newedgeList = new ArrayList<GridEdge>();
        
                //2. Add the four bounding edges of convex hull to the list. 
                    //Initialize the flagX attribute using setFlag() to get the orientation of the edge. 
        
        GridEdge e1 = new GridEdge(newconvexHullVertices.get(0), newconvexHullVertices.get(1));
        e1.setFlag(false);
        newedgeList.add(e1);
        GridEdge e2 = new GridEdge(newconvexHullVertices.get(0), newconvexHullVertices.get(2));
        e2.setFlag(true);
        newedgeList.add(e2);
        GridEdge e3 = new GridEdge(newconvexHullVertices.get(2), newconvexHullVertices.get(3));
        e3.setFlag(false);
        newedgeList.add(e3);
        GridEdge e4 = new GridEdge(newconvexHullVertices.get(1), newconvexHullVertices.get(3));
        e4.setFlag(true);
        newedgeList.add(e4);
        
                //3. Sort the list based on the length in descending order. 

        Collections.sort(newedgeList);
    
        int k = n-4;  // counter for remaining number of points.
        while (k > 0){
            
            GridEdge edge = newedgeList.get(0);
            float mid_point = edge.getMidpoint();
            Point p1 = edge.getP1();
            Point p2 = edge.getP2();
            float p1x = p1.getX();
            float p1y = p1.getY();
            if (edge.getFlag() == false) {
                p1x += mid_point;
                Point newpoint = new Point(OWNER, p1x, p1y);
                GridEdge newedge1, newedge2;
                result.add(newpoint);
                newedgeList.remove(0);
                newedge1 = new GridEdge (p1, newpoint);
                newedge2 = new GridEdge (newpoint, p2);
                newedgeList.add(newedge1);
                newedgeList.add(newedge2);
            }
            else{
                p1y += mid_point;
                Point newpoint = new Point(OWNER, p1x, p1y);
                GridEdge newedge1, newedge2;
                result.add(newpoint);
                newedgeList.remove(0);
                newedge1 = new GridEdge (p1, newpoint);
                newedge2 = new GridEdge (newpoint, p2);
                newedgeList.add(newedge1);
                newedgeList.add(newedge2);
            }
            
            Collections.sort(newedgeList);
            k--;
        }
    
        return result;

    }
}
