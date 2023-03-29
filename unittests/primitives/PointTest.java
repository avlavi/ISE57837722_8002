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
     * Test method for {@link .primitives.Point#Add(.primitives.Point)}.
     */
    @Test
    void testAdd() {
        // ============ Equivalence Partitions Tests ==============
        Point p1 = new Point(1, 2, 3);
        assertEquals(new Point(0, 0, 0), p1.add(new Vector(-1, -2, -3)), "add() dose not work");

        // =============== Boundary Values Tests ==================
    }

    /**
     * Test method for {@link .primitives.Point#Subtract(.primitives.Point)}.
     */
    @Test
    void testSubtract() {
        // ============ Equivalence Partitions Tests ==============
        Point p1 = new Point(1, 2, 3);
        assertEquals(new Vector(1, 1, 1), new Point(2, 3, 4).subtract(p1), "subtract() is not working");

        // =============== Boundary Values Tests ==================
        assertThrows(IllegalArgumentException.class, () -> p1.subtract(new Vector(1, 2, 3)),
                "subtract() of vector similar to the point resulted by zero vector does not throw an exception");
    }

    /**
     * Test method for {@link .primitives.Point#DistanceSquared(.primitives.Point)}.
     */
    @Test
    void testDistanceSquared() {
        // ============ Equivalence Partitions Tests ==============
        Point p1 = new Point(3, 4, 2);
        assertEquals(9, p1.distanceSquared(new Point(5, 6, 3)), "The subtractionSq is not working when the given point is after the point");
        assertEquals(26, p1.distanceSquared(new Point(2, 1, -2)), "The subtractionSq is not working when the given point is before the point");

        // =============== Boundary Values Tests ==================
        assertEquals(0, p1.distanceSquared(new Point(3, 4, 2)), "The subtractionSq is not working when the given point is the same point");
    }

    /**
     * Test method for {@link .primitives.Point#Distance(.primitives.Point)}.
     */
    @Test
    void testDistance() {
        Point p1 = new Point(3, 4, 2);
        assertEquals(3, p1.distance(new Point(5, 6, 3)), "The subtraction is not working when the given point is after the point");
        assertEquals(5, p1.distance(new Point(0, 0, 2)), "The subtraction is not working when the given point is before the point");

        // =============== Boundary Values Tests ==================
        assertEquals(0, p1.distance(new Point(3, 4, 2)), "The subtraction is not working when the given point is the same point");
    }
}