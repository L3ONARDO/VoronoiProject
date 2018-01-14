package model;

import org.junit.Test;

import static org.junit.Assert.*;

public class PointTest {

    @Test
    public void onEdge() {
        Edge edge = new Edge(new Point(true, 2f, 6f), new Point(true, 2f, 0f));
        Point point = new Point(true, 2f, 2f);
        boolean on = point.onEdge(edge);
        assertEquals(true, on);
        edge = new Edge(new Point(true, 2f, 6f), new Point(true, 44f, -2f));
        point = new Point(true, 4f, 1f);
        on = point.onEdge(edge);
        assertEquals(false, on);
        edge = new Edge(new Point(true, 44f, -2f), new Point(true, 2f, 0f));
        point = new Point(true, 4f, 1f);
        on = point.onEdge(edge);
        assertEquals(false, on);
        edge = new Edge(new Point(true, -1f, 1f), new Point(true, 1f, -1f));
        point = new Point(true, 0f, 0f);
        on = point.onEdge(edge);
        assertEquals(true, on);
        edge = new Edge(new Point(true, -1f, -1f), new Point(true, 1f, 1f));
        point = new Point(true, 0f, 0f);
        on = point.onEdge(edge);
        assertEquals(true, on);
    }
}