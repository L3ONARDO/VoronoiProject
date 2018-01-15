import com.model;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.lang.Math.*;

public class GridEdge implements Comparable {
    public Point p1, p2;
    public float distance, midpoint;

    public GridEdge(Point p1, Point p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    public Point getP1() {
        return p1;
    }

    public Point getP2() {
        return p2;
    }

    public getDistance(){
    	float x1 = p1.getX();
    	float y1 = p1.getY();
    	float x2 = p2.getX();
    	float y2 = p2.getY();

    	double delta = Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2);
        double sqrRoot = Math.pow(delta, 0.5);

        this.distance = (float) sqrRoot;

        return distance;

    }

    public getMidpoint(){
    	this.midpoint = (this.distance) / 2;
    	return midpoint;	
    }

    @Override
    public int compareTo(GridEdge maxEdge) {
        float comparedist=((Student)maxEdge).getDistance();

        return (int)(comparedist-this.distance);
    }
}