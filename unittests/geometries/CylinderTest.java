package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing geometries.Cylinder
 *
 * @author Shilo and Aviad
 */
class CylinderTest {
    /**
     * Test method for {@link .geometries.Cylinder#GetNormal(.geometries.Cylinder)}.
     */
    @Test
    void testGetNormal() {
        Cylinder cyl = new Cylinder(1, new Ray(new Point(0, 0, 0), new Vector(0, 0, 1)), 2);
        // ============ Equivalence Partitions Tests ==============
 // TC01: point is on the body of the cylinder
        // ensure there are no exceptions
        assertDoesNotThrow(() -> cyl.getNormal(new Point(0,1, 1)), "");
        // generate the test result
        Vector result = cyl.getNormal(new Point(0, 1, 1));
        // ensure the result is orthogonal to the cylinder
        assertEquals(new Vector(0, 1, 0), result,
                "getnormal() dose not work when the point is on the body of the cylinder");

 // TC02: point is on the lower base, but not in the middle
        // ensure there are no exceptions
        assertDoesNotThrow(() -> cyl.getNormal(new Point(0,0.5, 0)), "");
        // generate the test result
        result = cyl.getNormal(new Point(0, 0.5, 0));
        // ensure the result is orthogonal to the cylinder
        assertEquals(new Vector(0, 0, 1), result,
                "getnormal() dose not work when the point is on the lower base of the cylinder, but not in the middle");

 // TC03: point is on the upper base, but not in the middle
        // ensure there are no exceptions
        assertDoesNotThrow(() -> cyl.getNormal(new Point(0,0.5, 2)), "");
        // generate the test result
        result = cyl.getNormal(new Point(0, 0.5, 2));
        // ensure the result is orthogonal to the cylinder
        assertEquals(new Vector(0, 0, 1), result,
                "getnormal() dose not work when the point is on the upper base of the cylinder, but not in the middle");

        // =============== Boundary Values Tests ==================
 // TC04: point is in the middle of the lower base
        // ensure there are no exceptions
        assertDoesNotThrow(() -> cyl.getNormal(new Point(0,0, 0)), "");
        // generate the test result
        result = cyl.getNormal(new Point(0, 0, 0));
        // ensure the result is orthogonal to the cylinder
        assertEquals(new Vector(0, 0, 1), result,
                "getnormal() dose not work when the point is in the middle of the lower base");

 // TC05: point is in the middle of the upper base
        // ensure there are no exceptions
        assertDoesNotThrow(() -> cyl.getNormal(new Point(0,0, 2)), "");
        // generate the test result
        result = cyl.getNormal(new Point(0, 0, 2));
        // ensure the result is orthogonal to the cylinder
        assertEquals(new Vector(0, 0, 1), result,
                "getnormal() dose not work when the point is in the middle of the upper base");
    }
}