package com.model.TriangulationDAG;

import com.model.Triangle;

public class DAGLocation {
    private TriangulationDAG triangulationDAG;
    private Triangle triangle;

    public DAGLocation(TriangulationDAG triangulationDAG, Triangle triangle) {
        this.triangulationDAG = triangulationDAG;
        this.triangle = triangle;
    }

    public TriangulationDAG getTriangulationDAG() {
        return triangulationDAG;
    }

    public Triangle getTriangle() {
        return triangle;
    }
}
