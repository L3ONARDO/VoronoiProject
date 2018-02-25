package model;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class DelaunayTriangulationTest {
    private DelaunayTriangulation instance = new DelaunayTriangulation();

    @Test
    public void trivialTest() {
        for (int i = 0; i < 10000; i++) {
            Point p1 = new Point(true, 0f, 0f);
            Point p2 = new Point(true, 0.5f, 1.0f);
            Point p3 = new Point(true, 1f, 0f);
            List<Triangle> result = instance.calculate(buildInput(p1, p2, p3));
            assertEquals(1, result.size());
            assertEquals(true, result.get(0).getP1().equals(p1) || result.get(0).getP2().equals(p1) || result.get(0).getP3().equals(p1));
            assertEquals(true, result.get(0).getP1().equals(p2) || result.get(0).getP2().equals(p2) || result.get(0).getP3().equals(p2));
            assertEquals(true, result.get(0).getP1().equals(p3) || result.get(0).getP2().equals(p3) || result.get(0).getP3().equals(p3));
        }
    }

    @Test
    public void simpleTest() {
        Point p1 = new Point(true, 0f, 1f);
        Point p2 = new Point(true, 2f, 2f);
        Point p3 = new Point(true, 2f, 0f);
        Point p4 = new Point(true, 4f, 1f);
        Triangle t1 = new Triangle(p1, p2, p3);
        Triangle t2 = new Triangle(p2, p3, p4);
        for (int i = 0; i < 1000; i++) { // Repeats to make it very likely that every permutation is tested
            List<Triangle> result = instance.calculate(buildInput(p1, p2, p3, p4));
            assertEquals(2, result.size());
            boolean option1 = result.get(0).equals(t1) && result.get(1).equals(t2);
            boolean option2 = result.get(0).equals(t2) && result.get(1).equals(t1);
            boolean res = option1 || option2;
            assertEquals(true, res);
        }
    }

    @Test
    public void advancedTest() {
        Point p1 = new Point(true, -6f, 0f);
        Point p2 = new Point(true, -4f, 1f);
        Point p3 = new Point(true, -4f, -1f);
        Point p4 = new Point(true, -2f, 2f);
        Point p5 = new Point(true, -2f, 0f);
        Point p6 = new Point(true, -2f, -2f);
        Point p7 = new Point(true, 0f, 3f);
        Point p8 = new Point(true, 0f, 1f);
        Point p9 = new Point(true, 0f, -1f);
        Point p10 = new Point(true, 0f, -3f);
        Point p11 = new Point(true, 2f, 2f);
        Point p12 = new Point(true, 2f, 0f);
        Point p13 = new Point(true, 2f, -2f);
        Point p14 = new Point(true, 4f, 1f);
        Point p15 = new Point(true, 4f, -1f);
        Point p16 = new Point(true, 6f, 0f);
        Triangle t1 = new Triangle(p1, p2, p3);
        Triangle t2 = new Triangle(p2, p3, p5);
        Triangle t3 = new Triangle(p2, p4, p5);
        Triangle t4 = new Triangle(p3, p5, p6);
        Triangle t5 = new Triangle(p4, p7, p8);
        Triangle t6 = new Triangle(p4, p5, p8);
        Triangle t7 = new Triangle(p5, p8, p9);
        Triangle t8 = new Triangle(p5, p6, p9);
        Triangle t9 = new Triangle(p6, p9, p10);
        Triangle t10 = new Triangle(p7, p8, p11);
        Triangle t11 = new Triangle(p8, p11, p12);
        Triangle t12 = new Triangle(p8, p9, p12);
        Triangle t13 = new Triangle(p9, p12, p13);
        Triangle t14 = new Triangle(p9, p10, p13);
        Triangle t15 = new Triangle(p11, p12, p14);
        Triangle t16 = new Triangle(p12, p14, p15);
        Triangle t17 = new Triangle(p12, p13, p15);
        Triangle t18 = new Triangle(p14, p15, p16);
        List<Triangle> expectedTriangles = listBuilder(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18);
        Set<Point> input = buildInput(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16);
        for (int i = 0; i < 10000; i++) { // Repeats to test more permutations, P(perm. not chosen in 10000 runs) = approx 1 still...
            List<Triangle> result = instance.calculate(input);
            assertEquals(18, result.size());
            List<Triangle> unexpectedTriangles = new ArrayList<>();
            for (Triangle t : result) {
                if (!expectedTriangles.contains(t)) unexpectedTriangles.add(t);
            }
            assertEquals(0, unexpectedTriangles.size());
        }
    }

    @Test
    public void randomTest() {
        float scale = 500f;
        Random random = new Random();
        Set<Point> input = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            float x = random.nextFloat();
            float y = random.nextFloat();
            Point point = new Point(true, x, y);
            input.add(point);
        }
        List<Triangle> triangulation = instance.calculate(input);
        for (Triangle t : triangulation) {
            System.out.print("[" + scale * t.getP1().getX() + ", " + scale * t.getP1().getY() + ", " + scale * t.getP2().getX() + ", " + scale * t.getP2().getY() + ", " + + scale * t.getP3().getX() + ", " + scale * t.getP3().getY() + "], ");
        }
    }

    private Set<Point> buildInput(Point... points) {
        Set<Point> result = new HashSet<>();
        for (Point p : points) {
            result.add(p);
        }
        return result;
    }

    private List<Triangle> listBuilder(Triangle... triangles) {
        return Arrays.asList(triangles);
    }
}