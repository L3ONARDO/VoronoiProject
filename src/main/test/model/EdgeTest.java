package model;

import org.junit.Test;

import static org.junit.Assert.*;

public class EdgeTest {

    @Test
    public void getBisectorLine() {
        Edge edge = new Edge(new Point(true, 0f, -1f), new Point(true, 0f, 1f));
        LineInterface bisector = edge.getBisectorLine();
        assertEquals(true, bisector.isHorizontal());
        edge = new Edge(new Point(true, -1f, 0f), new Point(true, 1f, 0f));
        bisector = edge.getBisectorLine();
        assertEquals(true, bisector.isVertical());
        edge = new Edge(new Point(true, -1f, 0f), new Point(true, 1f, 2f));
        bisector = edge.getBisectorLine();
        assertEquals(false, bisector.isHorizontal());
        assertEquals(false, bisector.isVertical());
        Line aux = new Line(new Point(true, 0f, 0f), new Point(true, 1f, 0f));
        Point intersection = bisector.intersection(aux);
        //assertEquals(0.5f, intersection.getX(), 0.01f);
        assertEquals(0f, intersection.getY(), 0.01f);
        edge = new Edge(new Point(true, -1f, -2f), new Point(true, 1f, 2f));
        bisector = edge.getBisectorLine();
        assertEquals(true, true);
    }

    @Test
    public void angleTest() {
        Point o = new Point(true, 0f, 0f);
        Point p1 = new Point(true, -1f, 0f);
        Point p2 = new Point(true, -1f, 1f);
        Point p3 = new Point(true, 0f, 1f);
        Point p4 = new Point(true, 1f, 1f);
        Point p5 = new Point(true, 1f, 0f);
        Point p6 = new Point(true, 1f, -1f);
        Point p7 = new Point(true, 0f, -1f);
        Point p8 = new Point(true, -1f, -1f);
        Edge a = new Edge(p1, o);
        Edge b = new Edge(o, p2);
        Edge c = new Edge(o, p3);
        Edge d = new Edge(o, p4);
        Edge e = new Edge(o, p5);
        Edge ep = new Edge(p5, o);
        Edge f = new Edge(o, p6);
        Edge g = new Edge(o, p7);
        Edge h = new Edge(o, p8);
        Edge i = new Edge(o, p1);
        assertEquals(Math.PI / 4f, a.getAngleWith(b), 1E-6);
        assertEquals(Math.PI / 2f, a.getAngleWith(c), 1E-6);
        assertEquals(3f * Math.PI / 4f, a.getAngleWith(d), 1E-6);
        assertEquals(Math.PI, a.getAngleWith(e), 1E-6);
        assertEquals(5 * Math.PI / 4f, a.getAngleWith(f), 1E-6);
        assertEquals(3 * Math.PI / 2f, a.getAngleWith(g), 1E-6);
        assertEquals(7 * Math.PI / 4f, a.getAngleWith(h), 1E-6);
        assertEquals(0, a.getAngleWith(i), 1E-6);
        assertEquals(Math.PI / 4f, ep.getAngleWith(f), 1E-6);
        assertEquals(Math.PI / 2f, ep.getAngleWith(g), 1E-6);
        assertEquals(3f * Math.PI / 4f, ep.getAngleWith(h), 1E-6);
        assertEquals(Math.PI, ep.getAngleWith(i), 1E-6);
        assertEquals(5f * Math.PI / 4f, ep.getAngleWith(b), 1E-6);
        assertEquals(3f * Math.PI / 2f, ep.getAngleWith(c), 1E-6);
        assertEquals(7 * Math.PI / 4f, ep.getAngleWith(d), 1E-6);
        Edge s = new Edge(new Point(true, 0f, 0f), new Point(true, -1f, 1f));
        Edge t = new Edge(new Point(true, -1f, 1f), new Point(true,-1f, 0f));
        Edge u = new Edge(new Point(true, -1f, 1f), new Point(true, -1, 2f));
        assertEquals(Math.PI / 4f, s.getAngleWith(t), 1E-6);
        assertEquals(5 * Math.PI / 4f, s.getAngleWith(u), 1E-6);
    }
}