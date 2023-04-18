package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

/**
 * Testing primitives.Vector
 *
 * @author Shilo and Aviad
 */
class VectorTest {
    /**
     * Test method for {@link .primitives.Vector#Vector(Double3)(.primitives.Vector)}.
     */
    void testConstructorDouble3() {
        assertThrows(IllegalArgumentException.class, () -> new Vector(new Double3(0, 0, 0)),
                "Creation (by Double3) of zero vector does not throw an exception");
    }

    /**
     * Test method for {@link .primitives.Vector#Vector(xyz)(.primitives.Vector)}.
     */
    void testConstructorXYZ() {
        assertThrows(IllegalArgumentException.class, () -> new Vector(0, 0, 0),
                "Creation (by coordinates) of zero vector does not throw an exception");
    }

    /**
     * Test method for {@link .primitives.Vector#LengthSquared(.primitives.Vector)}.
     */
    @Test
    void testLengthSquared() {
        Vector v = new Vector(1, 2, 3);
        // ============ Equivalence Partitions Tests ==============
        assertEquals(14, v.lengthSquared(), "lengthSquared() gives wrong value");

        // =============== Boundary Values Tests ==================
    }

    /**
     * Test method for {@link .primitives.Vector#Length(.primitives.Vector)}.
     */
    @Test
    void testLength() {
        Vector v = new Vector(0, 3, 4);
        // ============ Equivalence Partitions Tests ==============
        assertEquals(5, v.length(), "length() gives wrong value");

        // =============== Boundary Values Tests ==================
    }

    /**
     * Test method for {@link .primitives.Vector#Add(.primitives.Vector)}.
     */
    @Test
    void testAdd() {
        Vector v1 = new Vector(1, 2, 3);
        // ============ Equivalence Partitions Tests ==============
        assertEquals(new Vector(-1, -2, -3), v1.add(new Vector(-2, -4, -6)), "add() dose not work");

        // =============== Boundary Values Tests ==================
        assertThrows(IllegalArgumentException.class, () -> v1.add(new Vector(-1, -2, -3)), "Vector + -itself does not throw an exception");
    }

    /**
     * Test method for {@link .primitives.Vector#Scale(.primitives.Vector)}.
     */
    @Test
    void testScale() {
        Vector v1 = new Vector(1, 2, 3);
        // ============ Equivalence Partitions Tests ==============
        assertEquals(new Vector(2, 4, 6), v1.scale(2), "scale() dose not work");

        // =============== Boundary Values Tests ==================
        assertThrows(IllegalArgumentException.class, () -> v1.scale(0), "Vector scale by zero does not throw an exception");

    }

    /**
     * Test method for {@link .primitives.Vector#DotProduct(.primitives.Vector)}.
     */
    @Test
    void testDotProduct() {
        Vector v1 = new Vector(1, 2, 3);

        // ============ Equivalence Partitions Tests ==============
        assertTrue(isZero(v1.dotProduct(new Vector(-2, -4, -6)) + 28), "dotProduct() wrong value");

        // =============== Boundary Values Tests ==================
        assertTrue( isZero(v1.dotProduct(new Vector(0, 3, -2))), "dotProduct() for orthogonal vectors is not zero");
    }

    /**
     * Test method for {@link .primitives.Vector#CrossProduct(.primitives.Vector)}.
     */
    @Test
    public void testCrossProduct() {
        Vector v1 = new Vector(1, 2, 3);
        // ============ Equivalence Partitions Tests ==============
        Vector v2 = new Vector(0, 3, -2);
        Vector vr = v1.crossProduct(v2);
        // TC01: Test that length of cross-product is proper (orthogonal vectors taken
        // for simplicity)
        assertEquals(v1.length() * v2.length(), vr.length(), 0.00001, "crossProduct() wrong result length");
        // TC02: Test cross-product result orthogonality to its operands
        assertTrue(isZero(vr.dotProduct(v1)), "crossProduct() result is not orthogonal to 1st operand");
        assertTrue(isZero(vr.dotProduct(v2)), "crossProduct() result is not orthogonal to 2nd operand");
        // =============== Boundary Values Tests ==================
        // TC11: test zero vector from cross-productof co-lined vectors
        Vector v3 = new Vector(-2, -4, -6);
        assertThrows(IllegalArgumentException.class, () -> v1.crossProduct(v3),
                "crossProduct() for parallel vectors does not throw an exception");
    }

    /**
     * Test method for {@link .primitives.Vector#Normalize(.primitives.Vector)}.
     */
    @Test
    void testNormalize() {
        // ============ Equivalence Partitions Tests ==============
        Vector v1 = new Vector(1, 2, 3);
        Vector u = v1.normalize();
        assertEquals(1, u.length(), 0.00000001, "The normalized vector is not a unit vector");
        assertThrows(IllegalArgumentException.class, () -> v1.crossProduct(u), "The normalized vector is not parallel to the original one");
        assertTrue(0 < v1.dotProduct(u), "the normalized vector is opposite to the original one");

        // =============== Boundary Values Tests ==================
    }
}