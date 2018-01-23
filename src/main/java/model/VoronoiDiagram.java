package model;

import util.MergeSortPointSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class VoronoiDiagram {

    public List<Polygon> calculate(Set<Point> inputPoints, float xmin, float xmax, float ymin, float ymax) {
        // Make Delaunay triangulation
        DelaunayTriangulation delaunayTriangulation = new DelaunayTriangulation();
        List<Triangle> triangulation = delaunayTriangulation.calculate(inputPoints);

        // Construct boundary lines
        List<LineInterface> lines = new ArrayList<>();
        Point bottomLeft = new Point(true, xmin, ymin);
        Point bottomRight = new Point(true, xmax, ymin);
        Point topRight = new Point(true, xmax, ymax);
        Point topLeft = new Point(true, xmin, ymax);
        LineInterface bottomBoundary = new Line(bottomLeft, bottomRight);
        LineInterface rightBoundary = new VerticalLine(bottomRight.getX());
        LineInterface topBoundary = new Line(topRight, topLeft);
        LineInterface leftBoundary = new VerticalLine(topLeft.getX());
        lines.add(bottomBoundary);
        lines.add(rightBoundary);
        lines.add(topBoundary);
        lines.add(leftBoundary);

        // Calculate bisector lines, store Delaunay edges for later use
        List<Edge> delaunayEdges = new ArrayList<>();
        for (Triangle triangle : triangulation) {
            for (Edge edge : triangle.getEdges()) {
                lines.add(edge.getBisectorLine());
                delaunayEdges.add(edge);
            }
        }
        lines = removeDuplicateLines(lines);

        // Calculate intersections between bisectors and boundaries and construct edges for them
        List<Edge> potentialVoronoiEdges = new ArrayList<>();
        for (LineInterface l : lines) {
            List<Point> intersections = new ArrayList<>();
            for (LineInterface m : lines) {
                Point intersection = l.intersection(m);
                if (intersection != null && isInBounds(intersection, xmin, xmax, ymin, ymax)) intersections.add(intersection);
            }
            MergeSortPointSet mergeSortPointSet = new MergeSortPointSet();
            intersections = mergeSortPointSet.sort(intersections, l.isVertical()); // Sort on Y if l is vertical, otherwise sort on X
            for (int i = 0; i < intersections.size() - 1; i++) {
                Point p = intersections.get(i);
                Point q = intersections.get(i + 1);
                Edge newEdge = new Edge(p, q);
                if(newEdge.getLength() > 1E-4) {
                    potentialVoronoiEdges.add(new Edge(p, q));
                }
            }
        }

        // Calculate valid Voronoi edges
        List<Edge> voronoiEdges = new ArrayList<>();
        for (Edge potentialEdge : potentialVoronoiEdges) {
            EdgeIntersection edgeIntersection = null;
            for (Edge delaunayEdge : delaunayEdges) {
                EdgeIntersection potentialIntersection = findEdgeIntersection(potentialEdge, delaunayEdge);
                if (potentialIntersection != null) {
                    edgeIntersection = potentialIntersection;
                    break;
                }
            }
            if (edgeIntersection == null) { // Potential boundary edge
                if (isBoundaryEdge(potentialEdge, xmin, xmax, ymin, ymax)) {
                    voronoiEdges.add(potentialEdge);
                }
            } else {
                Point p1 = edgeIntersection.edge.getP1();
                Point intersection = edgeIntersection.point;
                Point p2 = edgeIntersection.edge.getP2();
                float da = p1.distanceTo(intersection);
                float db = p2.distanceTo(intersection);
                float diff = Math.abs(da - db);
                if (diff < 0.001) {
                    voronoiEdges.add(potentialEdge);
                }
            }
        }

        // Perform left-hand walk along the Voronoi edges
        List<Polygon> result = new ArrayList<>();
        while (!voronoiEdges.isEmpty()) {
            Edge edge = voronoiEdges.get(0);
            Polygon polygon = new Polygon();
            polygonWalk(voronoiEdges, edge, polygon, xmin, xmax, ymin, ymax);
            result.add(polygon);
        }
        return result;
    }

    private List<LineInterface> removeDuplicateLines(List<LineInterface> lines) {
        List<LineInterface> result = new ArrayList<>();
        for (LineInterface l : lines) {
            boolean add = true;
            for (LineInterface m : result) {
                if (l.equals(m)) add = false;
            }
            if (add) result.add(l);
        }
        return result;
    }

    private boolean isBoundaryEdge(Edge potentialEdge, float xmin, float xmax, float ymin, float ymax) {
        return (potentialEdge.getP1().getX() == xmin && potentialEdge.getP2().getX() == xmin)
                || (potentialEdge.getP1().getX() == xmax && potentialEdge.getP2().getX() == xmax)
                || (potentialEdge.getP1().getY() == ymin && potentialEdge.getP2().getY() == ymin)
                || (potentialEdge.getP1().getY() == ymax && potentialEdge.getP2().getY() == ymax);
    }

    private boolean isInBounds(Point intersection, float xmin, float xmax, float ymin, float ymax) {
        return xmin <= intersection.getX() && intersection.getX() <= xmax
                && ymin <= intersection.getY() && intersection.getY() <= ymax;
    }

    private void polygonWalk(List<Edge> edges, Edge edge, Polygon polygon, float xmin, float xmax, float ymin, float ymax) {
        edge.incrementPolygonCount();
        if ((isBoundaryEdge(edge, xmin, xmax, ymin, ymax) && edge.getPolygonCount() > 0) || edge.getPolygonCount() > 1) {
            edges.remove(edge);
        }
        polygon.addPoint(edge.getP1());
        if (polygon.deepContainsPoint(edge.getP2())) return;
        Edge nextEdge = null;
        Edge oldEdge = null;
        float angle = Float.MAX_VALUE;
        for (Edge e : edges) {
            if (e.getP1().equals(edge.getP2())) { // Potential next edge
                if (nextEdge == null) nextEdge = e;
                float newAngle = edge.getAngleWith(e);
                if (newAngle < angle) {
                    nextEdge = e;
                    angle = newAngle;
                }
            }
            if (e.getP2().equals(edge.getP2()) && !(e.getP1().equals(edge.getP1()))) { // Potential next edge, needs switching
                Edge f = new Edge(e.getP2(), e.getP1());
                f.setPolygonCount(e.getPolygonCount());
                if (nextEdge == null) nextEdge = f;
                float newAngle = edge.getAngleWith(f);
                if (newAngle < angle) {
                    nextEdge = f;
                    oldEdge = e;
                    angle = newAngle;
                }
            }
        }
        if (oldEdge != null) { // Switched an edge, remove the old one, add the new one
            edges.remove(oldEdge);
            edges.add(nextEdge);
        }
        polygonWalk(edges, nextEdge, polygon, xmin, xmax, ymin, ymax);
    }

    private EdgeIntersection findEdgeIntersection(Edge potentialEdge, Edge edge) {
        LineInterface l = buildLine(potentialEdge);
        LineInterface m = buildLine(edge);
        Point intersection = l.intersection(m);
        if (intersection != null && intersection.onEdge(potentialEdge) && intersection.onEdge(edge)) {
            return new EdgeIntersection(edge, intersection);
        }
        return null;
    }

    private LineInterface buildLine(Edge edge) {
        if (edge.getP1().getX() == edge.getP2().getX()) return new VerticalLine(edge.getP1().getX());
        return new Line(edge.getP1(), edge.getP2());
    }

    private class EdgeIntersection {
        private Edge edge;
        private Point point;

        public EdgeIntersection(Edge edge, Point point) {
            this.edge = edge;
            this.point = point;
        }
    }
}