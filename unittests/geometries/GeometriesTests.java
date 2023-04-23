package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing geometries.Geometries
 *
 * @author Shilo and Aviad
 */
class GeometriesTests {
    /**
     * Test method for {@link .geometries.Geometries#FindIntersections(.geometries.Geometries)}.
     */
    @Test
    void testFindIntersections() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Some of the shapes are intersected but not all of them
        Geometries geo1 = new Geometries();
        assertEquals(0, geo1.findIntersections(new Ray(new Point(0, 0, 1), new Vector(1, 0, 0))), "dose not work when the collection is empty");

        // =============== Boundary Values Tests ==================
        // TC02: Empty collection
        Geometries geo2 = new Geometries();
        assertEquals(0, geo2.findIntersections(new Ray(new Point(0, 0, 1), new Vector(0, 1, 1))), "dose not work when the collection is empty");
        // TC03: None of the shapes is intersected
        Geometries geo3 = new Geometries();
        assertEquals(0, geo3.findIntersections(new Ray(new Point(0, 0, 1), new Vector(0, 1, 1))), "dose not work when the collection is empty");
        // TC04: Just on shape is intersected
        Geometries geo4 = new Geometries();
        assertEquals(0, geo4.findIntersections(new Ray(new Point(0, 0, 1), new Vector(0, 1, 1))), "dose not work when the collection is empty");
        // TC05: All the shapes are intersected
        Geometries geo5 = new Geometries();
        assertEquals(0, geo5.findIntersections(new Ray(new Point(0, 0, 1), new Vector(0, 1, 1))), "dose not work when the collection is empty");

    }
}