package geometries;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * Testing geometries.Plan
 *
 * @author Shilo and Aviad
 */
class PlaneTest {

    /**
     * Test method for {@link .geometries.Plane#Constructor(.geometries.Plane)}.
     */
    @Test
    void testConstructor() {
        // ============ Equivalence Partitions Tests ==============

        // =============== Boundary Values Tests ==================
        // TC01: Constructed a plane whit to similar points
assertThrows(IllegalArgumentException.class, () -> new Plane(new Point(1, 0, 0), new Point(1, 0, 0), new Point(0, 0, 1)),
        "Constructed a plane whit to similar points");

        // TC02: Constructed a plane when the 3 points are on the same line
        assertThrows(IllegalArgumentException.class, () -> new Plane(new Point(0, 0, 0), new Point(1, 1, 0), new Point(2, 2, 0)),
                "Constructed a plane when the 3 points are on the same line");
    }

    /**
     * Test method for {@link .geometries.Plane#getNormal(.geometries.Plane)}.
     */
    @Test
    void testTestGetNormal() {
        // TC01: Test getNormal() on an ordinary plane
        Plane pl = new Plane(new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 0, 1));
        // ============ Equivalence Partitions Tests ==============
        // ensure there are no exceptions
        assertDoesNotThrow(() -> pl.getNormal(new Point(1, 0, 0)), "");
        // generate the test result
        Vector result = pl.getNormal(new Point(1, 0, 0));
        // ensure |result| = 1
        assertEquals(1, result.length(), 0.00000001, "plane's normal is not a unit vector");
        // ensure the result is orthogonal to the plane's vectors
            assertTrue(isZero(result.dotProduct(new Point(0, 1, 0).subtract(new Point(1, 0, 0)))),
                    "plane's normal is not orthogonal to one of the plane's vectors");
        assertTrue(isZero(result.dotProduct(new Point(0, 0, 1).subtract(new Point(1, 0, 0)))),
                "plane's normal is not orthogonal to one of the plane's vectors");
        // =============== Boundary Values Tests ==================
    }

    /**
     * Test method for {@link .geometries.Plane#findIntsersections(.geometries.Plane)}.
     */
    @Test
    void testFindIntersections() {
        Plane plane = new Plane(new Point(0,0,1),new Point(1,0,1),new Point(0,1,1));
        // ============ Equivalence Partitions Tests ==============
        //The Ray must be neither orthogonal nor parallel to the plane
        //TC01: Ray intersects the plane (1 point)
        Point p1 = new Point(1, 0, 1);
        List<Point> result = plane.findIntersections(new Ray(new Point(2, 0, 0), new Vector(-2, 1, 1)));
        assertEquals(1, result.size(), "Wrong number of points");
        //TC02: Ray does not intersect the plane (0 point)
        assertNull(plane.findIntersections(new Ray(new Point(1, 0, 0), new Vector(-1, 0, -1))),
                "Ray's line out of sphere");
        // =============== Boundary Values Tests ==================
        // **** Group: Ray is parallel to the plane
        // TC03: Ray included in the plane (0 point)
        assertNull(plane.findIntersections(new Ray(new Point(0, 2, 1), new Vector(0, -1, 0))),
                "Ray's line out of sphere");
        // TC04: Ray not included in the plane (0 point)
        assertNull(plane.findIntersections(new Ray(new Point(0, 1, 0.5), new Vector(0, -1, 0))),
                "Ray's line out of sphere");
        // **** Group: Ray is orthogonal to the plane
        // TC05: Ray start before plane (1 point)
        p1 = new Point(0, 1, 1);
        result = plane.findIntersections(new Ray(new Point(0, 1, 0.5), new Vector(0, 0, 0.5)));
        assertEquals(1, result.size(), "Wrong number of points");
        // TC06: Ray start in  plane (0 point)
        assertNull(plane.findIntersections(new Ray(new Point(0, 1, 1), new Vector(0, 0, 0.5))),
                "Ray's line out of sphere");
        // TC06: Ray start after  plane (0 point)
        assertNull(plane.findIntersections(new Ray(new Point(0, 1, 2), new Vector(0, 0, 0.5))),
                "Ray's line out of sphere");
        // **** Group: Ray is neither orthogonal nor parallel to and begins at the plane
        // TC07: not the same point which appears as reference point in the plane (0 point)
        assertNull(plane.findIntersections(new Ray(new Point(2, 0, 1), new Vector(-2, 0, 2))),
                "Ray's line out of sphere");
        // TC08: the same point which appears as reference point in the plane (0 point)
        assertNull(plane.findIntersections(new Ray(new Point(1, 0, 1), new Vector(-2, 0, 2))),
                "Ray's line out of sphere");
    }


}