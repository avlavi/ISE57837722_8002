package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing geometries.Tube
 *
 * @author Shilo and Aviad
 */
class TubeTest {

    /**
     * Test method for {@link .geometries.Tube#GetNormal(.geometries.Tube)}.
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        Tube sph = new Tube(1, new Ray(new Point(0, 0, 0), new Vector(0, 0, 1)));
        // ensure there are no exceptions
        assertDoesNotThrow(() -> sph.getNormal(new Point(0,1, 2)), "");
        // generate the test result
        Vector result = sph.getNormal(new Point(0, 1, 2));
        // ensure the result is orthogonal to the sphere
        assertEquals(new Vector(0, 1, 0), result, "Tube's normal is not normal to the Tube");

        // =============== Boundary Values Tests ==================
        // ensure there are no exceptions
        assertDoesNotThrow(() -> sph.getNormal(new Point(0,1, 0)), "");
        // generate the test result
        result = sph.getNormal(new Point(0, 1, 0));
        // ensure the result is orthogonal to the sphere
        assertEquals(new Vector(0, 1, 0), result, "Tube's normal is not normal to the Tube when the point is in front of the hed of the ray");
    }
}