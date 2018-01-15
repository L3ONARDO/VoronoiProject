package com.voronoiStrategy.playertwo;


import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import com.model.Point;

import java.lang.Math.*;

public class GridEdge implements Comparable<GridEdge> {
    public Point p1, p2;
    public float distance, midpoint;
    private boolean flagX;
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
    
    public void setFlag(boolean value) {
    	this.flagX = value;
    }
    
    public boolean getFlag() {
    	return flagX;
    }

    public float getDistance(){
    	float x1 = p1.getX();
    	float y1 = p1.getY();
    	float x2 = p2.getX();
    	float y2 = p2.getY();

    	double delta = Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2);
        double sqrRoot = Math.pow(delta, 0.5);

        this.distance = (float) sqrRoot;

        return distance;

    }

    public float getMidpoint(){
    	this.midpoint = (this.distance) / 2;
    	return midpoint;	
    }
    
	public int compareTo(GridEdge maxEdge) {
		float comparedist= (float)((GridEdge)maxEdge).getDistance();

        return (int) (comparedist-this.distance);
		//return 0;
	}
}