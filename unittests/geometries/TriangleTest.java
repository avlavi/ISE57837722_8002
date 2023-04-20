package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

/**
 * Testing geometries.Triangle
 *
 * @author Shilo and Aviad
 */
class TriangleTest {
    /**
     * Test method for {@link .geometries.Triangle#getNormal(.geometries.Trinagle)}.
     */
    @Test
    void testTestGetNormal() {
        // TC01: Test getNormal() on an ordinary triangle
        Triangle tr = new Triangle(new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 0, 1));
        // ============ Equivalence Partitions Tests ==============
        // ensure there are no exceptions
        assertDoesNotThrow(() -> tr.getNormal(new Point(1, 0, 0)), "");
        // generate the test result
        Vector result = tr.getNormal(new Point(1, 0, 0));
        // ensure |result| = 1
        assertEquals(1, result.length(), 0.00000001, "Triangle's normal is not a unit vector");
        // ensure the result is orthogonal to all the edges
        assertTrue(isZero(result.dotProduct(new Point(0, 1, 0).subtract(new Point(1, 0, 0)))),
                "Triangle's normal is not orthogonal to one of the Triangle's edges");
        assertTrue(isZero(result.dotProduct(new Point(0, 0, 1).subtract(new Point(1, 0, 0)))),
                "Triangle's normal is not orthogonal to one of the Triangle's edges");
        assertTrue(isZero(result.dotProduct(new Point(0, 0, 1).subtract(new Point(0, 1, 0)))),
                "Triangle's normal is not orthogonal to one of the Triangle's edges");
        // =============== Boundary Values Tests ==================
    }

    @Test
    void findIntsersections() {

    }
}