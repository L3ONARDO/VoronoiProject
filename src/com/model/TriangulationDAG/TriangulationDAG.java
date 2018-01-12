package com.model.TriangulationDAG;

import com.model.Point;
import com.model.Triangle;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

    public TriangulationDAG locateTriangle(Triangle triangle) {
        if (root == triangle) return this;
        for (TriangulationDAG child : children) {
            if (child.root.containsPoint(triangle.getP1()) &&
                    child.root.containsPoint(triangle.getP2()) &&
                    child.root.containsPoint(triangle.getP3())) {
                return child.locateTriangle(triangle);
            }
        }
        return null;
    }

    public List<Triangle> getTriangulation() {
        List<Triangle> result = new ArrayList<>();
        recursivelyFillTriangulation(result);
        return result;
    }

    private void recursivelyFillTriangulation(List<Triangle> result) {
        if (children.isEmpty()) {
            result.add(root);
        } else {
            for (TriangulationDAG child : children) {
                child.recursivelyFillTriangulation(result);
            }
        }
    }
}
