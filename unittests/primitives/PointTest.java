package primitives;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing primitives.Point
 *
 * @author Shilo and Aviad
 */
class PointTest {

    /**
     * Test method for {@link .primitives.Point.Add(.primitives.Point)}.
     */
    @Test
    void testAdd() {
        // ============ Equivalence Partitions Tests ==============
        Point  p1 = new Point(0,1,2);
        assertEquals(new Vector(0, 1, 3), p1.add(new Vector(0, 0, 1)), "The addition is not working");
        assertEquals(new Vector(-1, -1, -1), p1.add(new Vector(-1, -2, -3)), "Addition of negative vector is not working");
        // =============== Boundary Values Tests ==================
    }

    /**
     * Test method for {@link .primitives.Point.Subtract(.primitives.Point)}.
     */
    @Test
    void testSubtract() {
        // ============ Equivalence Partitions Tests ==============
        // =============== Boundary Values Tests ==================
    }

    /**
     * Test method for {@link .primitives.Point.DistanceSquared(.primitives.Point)}.
     */
    @Test
    void testDistanceSquared() {
        // ============ Equivalence Partitions Tests ==============
        // =============== Boundary Values Tests ==================
    }

    /**
     * Test method for {@link .primitives.Point.Distance(.primitives.Point)}.
     */
    @Test
    void testDistance() {
        // ============ Equivalence Partitions Tests ==============
        // =============== Boundary Values Tests ==================
    }
}