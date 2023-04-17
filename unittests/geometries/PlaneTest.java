package geometries;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

import primitives.Point;
import primitives.Vector;

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
    void testFindIntsersections() {
        // ============ Equivalence Partitions Tests ==============


        // =============== Boundary Values Tests ==================

    }
}