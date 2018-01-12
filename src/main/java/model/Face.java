package model;

import java.util.LinkedList;
import java.util.List;

public class Face {
    private List<HalfEdge> edges;

    public Face(List<HalfEdge> edges) {
        this.edges = edges;
    }

    public Face() {
        this.edges = new LinkedList<HalfEdge>();
    }

    public List<HalfEdge> getEdges() {
        return edges;
    }
}
