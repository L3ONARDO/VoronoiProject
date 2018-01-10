package com.model.TriangulationDAG;

import com.model.Point;
import com.model.Triangle;

import java.util.HashSet;
import java.util.Set;

public class TriangulationDAG {
    private Triangle root;
    private Set<TriangulationDAG> children;

    public TriangulationDAG(Triangle rootTriangle) {
        this.root = rootTriangle;
        this.children = new HashSet<>();
    }

    public Triangle getRoot() {
        return root;
    }

    public Set<TriangulationDAG> getChildren() {
        return children;
    }

    public void addChild(TriangulationDAG dag) {
        children.add(dag);
    }

    public DAGLocation locatePoint(Point point) {
        if (children.isEmpty()) return new DAGLocation(this, root);
        for (TriangulationDAG child : children) {
            if (child.root.containsPoint(point)) return child.locatePoint(point);
        }
        return null;
    }
}
