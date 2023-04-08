package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

/**
 * Testing geometries.Sphere
 *
 * @author Shilo and Aviad
 */
class SphereTest {

    /**
     * Test method for {@link .geometries.Sphere#GetNormal(.geometries.Sphere)}.
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        Sphere sph = new Sphere(1, new Point(0, 0, 0));
        // ensure there are no exceptions
        assertDoesNotThrow(() -> sph.getNormal(new Point(0, 0, 1)), "");
        // generate the test result
        Vector result = sph.getNormal(new Point(0, 0, 1));
        // ensure |result| = 1
        assertEquals(1, result.length(), 0.00000001, "Sphere's normal is not a unit vector");
        // ensure the result is orthogonal to the sphere
        assertEquals(new Vector(0, 0, 1), result, "Sphere's normal is not normal to the sphere");

        // =============== Boundary Values Tests ==================

    }
}