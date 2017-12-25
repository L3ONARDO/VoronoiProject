package com.util;

import com.model.Point;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class MergeSortPointSetTest {
    private MergeSortPointSet instance = new MergeSortPointSet();

    @Test
    public void simpleTest() {
        Set<Point> points = new HashSet<Point>();
        Point p1 = new Point(true, 0f, 0f);
        Point p2 = new Point(true, 1f, 0f);
        Point p3 = new Point(true, 0.5f, 0f);
        points.add(p1);
        points.add(p2);
        points.add(p3);
        List<Point> result = instance.sort(points);
        assertEquals(3, result.size());
        assertTrue(result.get(0).equals(p1));
        assertTrue(result.get(1).equals(p3));
        assertTrue(result.get(2).equals(p2));
    }
}