package model;

import util.MathUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class VoronoiDiagram {

    public List<Polygon> calculate(Set<Point> inputPoints, float xmin, float xmax, float ymin, float ymax) {
        List<ru.dubov.primitives.Point> dubovPoints = convertPoints(inputPoints);
        List<ru.dubov.primitives.Polygon> dubovPolygons = ru.dubov.voronoidiagram.VoronoiDiagram.viaHalfplanesIntersection(dubovPoints);
        List<Polygon> polygons = convertPolygons(dubovPolygons);
        // Todo: Calculate intersection with boundary
        for (Polygon polygon : polygons) {
            for (Point point : inputPoints) {
                if (point.inPolygon(polygon)) {
                    polygon.setOwner(point.getOwner());
                    break;
                }
            }
        }
        return polygons;
    }

    private List<Polygon> convertPolygons(List<ru.dubov.primitives.Polygon> dubovPolygons) {
        List<Polygon> result = new ArrayList<>();
        for (ru.dubov.primitives.Polygon dubovPolygon : dubovPolygons) {
             Polygon p = new Polygon();
             for (int i = 0; i < dubovPolygon.size(); i++) {
                 p.addPoint(new Point(true, (float) dubovPolygon.get(i).getX(), (float) dubovPolygon.get(i).getY()));
             }
             result.add(p);
        }
        return result;
    }

    private List<ru.dubov.primitives.Point> convertPoints(Set<Point> inputPoints) {
        List<ru.dubov.primitives.Point> result = new ArrayList<>();
        for (Point p : inputPoints) {
            result.add(new ru.dubov.primitives.Point(p.getX(), p.getY()));
        }
        return result;
    }
//            // Make Delaunay triangulation
//            DelaunayTriangulation delaunayTriangulation = new DelaunayTriangulation();
//            List<Triangle> triangulation = delaunayTriangulation.calculate(inputPoints);
//
//            // Construct boundary lines
//            List<LineInterface> lines = new ArrayList<>();
//            Point bottomLeft = new Point(true, xmin, ymin);
//            Point bottomRight = new Point(true, xmax, ymin);
//            Point topRight = new Point(true, xmax, ymax);
//            Point topLeft = new Point(true, xmin, ymax);
//            LineInterface bottomBoundary = new Line(bottomLeft, bottomRight);
//            LineInterface rightBoundary = new VerticalLine(bottomRight.getX());
//            LineInterface topBoundary = new Line(topRight, topLeft);
//            LineInterface leftBoundary = new VerticalLine(topLeft.getX());
//            lines.add(bottomBoundary);
//            lines.add(rightBoundary);
//            lines.add(topBoundary);
//            lines.add(leftBoundary);
//
//            for (Point inputPoint : inputPoints) {
//                List<Edge> incidentEdges = getIncidentEdges(inputPoint, delaunayTriangulation);
//                incidentEdges = clockwiseOrder(incidentEdges, new Edge(inputPoint, new Point(inputPoint.getOwner(), inputPoint.getX() + 1f, inputPoint.getY())));
//                Polygon polygon = new Polygon();
//                for (int i = 0; i < incidentEdges.size(); i++) {
//                    Edge edge = incidentEdges.get(i);
//                    Edge aux = incidentEdges.get((i + 1) % incidentEdges.size());
//                    LineInterface bisector1 = edge.getBisectorLine();
//                    LineInterface bisector2 = aux.getBisectorLine();
//                    Point massPoint = bisector1.intersection(bisector2);
//                    Triangle adjacentTriangle = getAdjacentTriangle(edge, delaunayTriangulation);
//                    if (adjacentTriangle == null) {
//                        List<Point> boundaryIntersections = getBoundaryIntersections(bisector1, lines);
//                        Edge a = new Edge(massPoint, boundaryIntersections.get(0));
//                        Edge b = new Edge(massPoint, boundaryIntersections.get(1));
//                        float bx = a.getP2().getX();
//                        float by = a.getP2().getY();
//                        float cx = massPoint.getX();
//                        float cy = massPoint.getY();
//                        float ax = a.getP1().getX();
//                        float ay = a.getP2().getY();
//                        Edge c = counterClockwiseFirst(a, b, new Point(true, bx + (cx - ax), by + (cy - ay)));
//                        polygon.addPoint(c.getP2());
//                    } else {
//                        // TODO: Add voronoi edge to other mass point
//                    }
//                }
//            }
//
//            return null;
//        }
//
//        private boolean isBoundaryEdge(Edge potentialEdge, float xmin, float xmax, float ymin, float ymax) {
//            return (potentialEdge.getP1().getX() == xmin && potentialEdge.getP2().getX() == xmin)
//                    || (potentialEdge.getP1().getX() == xmax && potentialEdge.getP2().getX() == xmax)
//                    || (potentialEdge.getP1().getY() == ymin && potentialEdge.getP2().getY() == ymin)
//                    || (potentialEdge.getP1().getY() == ymax && potentialEdge.getP2().getY() == ymax);
//        }
//
//        private boolean isInBounds(Point intersection, float xmin, float xmax, float ymin, float ymax) {
//            return xmin <= intersection.getX() && intersection.getX() <= xmax
//                    && ymin <= intersection.getY() && intersection.getY() <= ymax;
//        }
//
//        private void polygonWalk(List<Edge> edges, Edge edge, Polygon polygon, float xmin, float xmax, float ymin, float ymax) {
//            edge.incrementPolygonCount();
//            if ((isBoundaryEdge(edge, xmin, xmax, ymin, ymax) && edge.getPolygonCount() > 0) || edge.getPolygonCount() > 1) {
//                edges.remove(edge);
//            }
//            polygon.addPoint(edge.getP1());
//            if (polygon.deepContainsPoint(edge.getP2())) return;
//            Edge nextEdge = null;
//            Edge oldEdge = null;
//            float angle = Float.MAX_VALUE;
//            for (Edge e : edges) {
//                if (e.getP1().equals(edge.getP2())) { // Potential next edge
//                    if (nextEdge == null) nextEdge = e;
//                    float newAngle = edge.getAngleWith(e);
//                    if (newAngle < angle) {
//                        nextEdge = e;
//                        angle = newAngle;
//                    }
//                }
//                if (e.getP2().equals(edge.getP2()) && !(e.getP1().equals(edge.getP1()))) { // Potential next edge, needs switching
//                    Edge f = new Edge(e.getP2(), e.getP1());
//                    f.setPolygonCount(e.getPolygonCount());
//                    if (nextEdge == null) nextEdge = f;
//                    float newAngle = edge.getAngleWith(f);
//                    if (newAngle < angle) {
//                        nextEdge = f;
//                        oldEdge = e;
//                        angle = newAngle;
//                    }
//                }
//            }
//            if (oldEdge != null) { // Switched an edge, remove the old one, add the new one
//                edges.remove(oldEdge);
//                edges.add(nextEdge);
//            }
//            polygonWalk(edges, nextEdge, polygon, xmin, xmax, ymin, ymax);
//        }
//
//        private EdgeIntersection findEdgeIntersection(Edge potentialEdge, Edge edge) {
//            LineInterface l = buildLine(potentialEdge);
//            LineInterface m = buildLine(edge);
//            Point intersection = l.intersection(m);
//            if (intersection != null && intersection.onEdge(potentialEdge) && intersection.onEdge(edge)) {
//                return new EdgeIntersection(edge, intersection);
//            }
//            return null;
//        }
//
//        private LineInterface buildLine(Edge edge) {
//            if (edge.getP1().getX() == edge.getP2().getX()) return new VerticalLine(edge.getP1().getX());
//            return new Line(edge.getP1(), edge.getP2());
//        }
//
//        private class EdgeIntersection {
//            private Edge edge;
//            private Point point;
//
//            public EdgeIntersection(Edge edge, Point point) {
//                this.edge = edge;
//                this.point = point;
//            }
//        }
}