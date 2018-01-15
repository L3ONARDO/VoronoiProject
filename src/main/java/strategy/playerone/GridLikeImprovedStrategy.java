package strategy.playerone;

import model.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GridLikeImprovedStrategy extends PlayerOneStrategy {

    @SuppressWarnings("unchecked")
	public Set<Point> apply(int n, Set<Point> inputPoints, float xmax, float ymax) {
        Set<Point> result = new HashSet<>();

        List<Point> convexHullVertices = new ArrayList<>(); //@param convexHullVertices a list for the four vertices of convex hull

        //Plot the first point at the center of the playing area

        float xdist = xmax/2;
        float ydist = ymax/2;
        Point midpoint = new Point(OWNER, xdist, ydist);
        result.add(midpoint);
        
        //Plot 4 points along (3/4)th of each diagonal from the center to corners.
        float d = (float) Math.sqrt(xdist * xdist + ydist * ydist);
        float dy = (float) (0.25f * Math.sin(Math.PI / 4.0) * d);
        float dx = (float) (0.25f * Math.cos(Math.PI / 4.0) * d);
        Point a = new Point(OWNER, dx, dy);
        Point b = new Point(OWNER, xmax - dx, dy);
        Point c = new Point(OWNER, dx,ymax - dy);
        Point e = new Point(OWNER, xmax - dx, ymax -dy);
        result.add(a);
        result.add(b);
        result.add(c);
        result.add(e);
        convexHullVertices.add(a);
        convexHullVertices.add(b);
        convexHullVertices.add(c);
        convexHullVertices.add(e);
//        float x = xmax / 4;
//        float y = ymax / 4;
//        for (int i=0; i < 2; i++){
//            for (int j=0; j < 2; j++ ){
//                Point point = new Point(OWNER, x, y);
//                result.add(point);
//                convexHullVertices.add(point);
//                x += xdist;
//            }
//            y += ydist;
//            x = (xdist / 2);
//        }

        //Plot the remaining points iteratively on the convex hull (square). 

        		//1. Create a list of all the edges in the convex hull. 

        List<GridEdge> edgeList = new ArrayList<>();
        
        		//2. Add the four bounding edges of convex hull to the list. 
        			//Initialize the flagX attribute using setFlag() to get the orientation of the edge. 
        
        GridEdge e1 = new GridEdge(convexHullVertices.get(0), convexHullVertices.get(1));
        e1.setFlag(false);
        edgeList.add(e1);
        GridEdge e2 = new GridEdge(convexHullVertices.get(0), convexHullVertices.get(2));
        e2.setFlag(true);
        edgeList.add(e2);
        GridEdge e3 = new GridEdge(convexHullVertices.get(2), convexHullVertices.get(3));
        e3.setFlag(false);
        edgeList.add(e3);
        GridEdge e4 = new GridEdge(convexHullVertices.get(1), convexHullVertices.get(3));
        e4.setFlag(true);
        edgeList.add(e4);
        
        		//3. Sort the list based on the length in descending order. 

        Collections.sort(edgeList);
    
        int k = n-5;  // counter for remaining number of points.
        while (k > 0){
        	
        	GridEdge edge = edgeList.get(0);
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
        		edgeList.remove(0);
        		newedge1 = new GridEdge (p1, newpoint);
        		newedge2 = new GridEdge (newpoint, p2);
        		edgeList.add(newedge1);
        		edgeList.add(newedge2);
        	}
        	else{
        		p1y += mid_point;
        		Point newpoint = new Point(OWNER, p1x, p1y);
        		GridEdge newedge1, newedge2;
        		result.add(newpoint);
        		edgeList.remove(0);
        		newedge1 = new GridEdge (p1, newpoint);
        		newedge2 = new GridEdge (newpoint, p2);
        		edgeList.add(newedge1);
        		edgeList.add(newedge2);
        	}
        	
        	Collections.sort(edgeList);
            k--;
        }

        

        if (ymax > xmax) { // If the bounding box is taller than its wide, transform the points.
            return transformY(result, xmax, ymax);
        }
    
        return result;
    }
}
