package primitives;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing primitives.Ray
 *
 * @author Shilo and Aviad
 */
class RayTest {

    /**
     * Test method for {@link .primitives.Ray#FindClosestPoint(.primitives.Ray)}.
     */
    @Test
    void testFindClosestPoint() {
        Point p1 = new Point(0, 0, 1);
        Point p2 = new Point(0, 0, 2);
        Point p3 = new Point(0, 0, 3);
        Ray ray = new Ray(new Point(0, 0, 0.5), new Vector(0, 0, 1));

        // ============ Equivalence Partitions Tests ==============
        //TC01: point is in the middle of the list
        List<Point> points1 = new ArrayList<Point>();
        points1.add(p2);
        points1.add(p1);
        points1.add(p3);
        assertEquals(p1, ray.findClosestPoint(points1), "wrong point");

        // =============== Boundary Values Tests ==================
        //TC02: list is empty
        List<Point> points2 = new ArrayList<Point>();
        assertNull(ray.findClosestPoint(points2), "wrong point");
        //TC03: point is in the beginning of the list
        List<Point> points3 = new ArrayList<Point>();
        points3.add(p1);
        points3.add(p2);
        points3.add(p3);
        assertEquals(p1, ray.findClosestPoint(points3), "wrong point");
        //TC04: point is in the end of the list
        List<Point> points4 = new ArrayList<Point>();
        points4.add(p2);
        points4.add(p3);
        points4.add(p1);
        assertEquals(p1, ray.findClosestPoint(points4), "wrong point");
    }
}