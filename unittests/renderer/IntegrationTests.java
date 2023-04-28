package renderer;

import geometries.Intersectable;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Testing the integration between the constructRay function and the findIntersections function
 * of each of the figures, a plane , a Sphere and a triangle
 * by using a 3x3 view plane and constructing rays through each of the pixels
 *
 * @author Shilo and Aviad
 */

public class IntegrationTests {
    /**
     * Calculates the number of intersections between the given geometric object and rays constructed by the camera for each pixel in its view.
     *
     * @param geometric an object implementing the Intersectable interface to test for intersections with the rays
     * @param cam       the Camera object used to construct the rays
     * @return the total number of intersections found
     */
    public int launcher(Intersectable geometric, Camera cam) {
        int sum = 0;
        for (int i = 0; i < cam.width; i++)//
            for (int j = 0; j < cam.height; j++) {//לבדוק במקרה של NULL אם הוא מ
                if (geometric.findIntersections(cam.constructRay(3, 3, i, j)) != null)
                    sum += geometric.findIntersections(cam.constructRay(3, 3, i, j)).size();
            }
        return sum;
    }

    /**
     * Test Integration between geometries.Sphere#findIntersections and renderer.Camera#constructRay.
     */
    @Test
    void testIntegrationSphere() {
        String wrongNumber = "wrong number of points";
        Vector vTo = new Vector(0, 0, -1);
        Vector vUp = new Vector(0, 1, 0);

        // TC01: Sphere r = 1
        Camera cam1 = new Camera(new Point(0, 0, 0), vTo, vUp);
        cam1.setVPSize(3, 3);
        cam1.setVPDistance(1);
        assertEquals(2,
                launcher(new Sphere(1, new Point(0, 0, -3)), cam1),
                wrongNumber);

        // TC02: Sphere r = 2.5
        Camera cam2 = new Camera(new Point(0, 0, 0.5), vTo, vUp);
        cam2.setVPSize(3, 3);
        cam2.setVPDistance(1);
        assertEquals(18,
                launcher(new Sphere(2.5, new Point(0, 0, -2.5)), cam2),
                wrongNumber);

        // TC03: Sphere r = 2
        Camera cam3 = new Camera(new Point(0, 0, 0.5), vTo, vUp);
        cam3.setVPSize(3, 3);
        cam3.setVPDistance(1);
        assertEquals(10,
                launcher(new Sphere(2, new Point(0, 0, -2)), cam3),
                wrongNumber);

        // TC04: Sphere r = 4
        Camera cam4 = new Camera(new Point(0, 0, 0.5), vTo, vUp);
        cam4.setVPSize(3, 3);
        cam4.setVPDistance(1);
        assertEquals(9,
                launcher(new Sphere(4, new Point(0, 0, -0.5)), cam4),
                wrongNumber);

        // TC05: Sphere r = 0.5
        Camera cam5 = new Camera(new Point(0, 0, 0.5), vTo, vUp);
        cam5.setVPSize(3, 3);
        cam5.setVPDistance(1);
        assertEquals(0,
                launcher(new Sphere(0.5, new Point(0, 0, 1)), cam5),
                wrongNumber);

    }

    /**
     * Test Integration between geometries.Plane#findIntersections and renderer.Camera#constructRay.
     */
    @Test
    void testIntegrationPlane() {
        String wrongNumber = "wrong number of points";
        Vector vTo = new Vector(0, 0, -1);
        Vector vUp = new Vector(0, 1, 0);

        // TC01: Plane is parallel to the view plane
        Camera cam1 = new Camera(new Point(0, 0, 0.5), vTo, vUp);
        cam1.setVPSize(3, 3);
        cam1.setVPDistance(1);
        assertEquals(9,
                launcher(new Plane(new Point(1, 1, -5), new Vector(0, 0, 1)), cam1),
                wrongNumber);

        // TC02: Plane is not parallel to the view plane (intersected in 9 points)
        Camera cam2 = new Camera(new Point(0, 0, 0.5), vTo, vUp);
        cam2.setVPSize(3, 3);
        cam2.setVPDistance(1);
        assertEquals(9,
                launcher(new Plane(new Point(1, 1, -5), new Vector(0, 1, -5)), cam2),
                wrongNumber);

        // TC03: Plane is not parallel to the view plane (intersected in 6 points)
        Camera cam3 = new Camera(new Point(0, 0, 0.5), vTo, vUp);
        cam3.setVPSize(3, 3);
        cam3.setVPDistance(1);
        assertEquals(6,
                launcher(new Plane(new Point(0, 0, -5), new Vector(0, 6, -5)), cam3),
                wrongNumber);
    }

    /**
     * Test Integration between geometries.Triangle#findIntersections and renderer.Camera#constructRay.
     */
    @Test
    void testIntegrationTriangle() {
        String wrongNumber = "wrong number of points";
        Vector vTo = new Vector(0, 0, -1);
        Vector vUp = new Vector(0, 1, 0);

        // TC01: Triangle is parallel to the view plane and the size  of one pixel
        Camera cam1 = new Camera(new Point(0, 0, 0.5), vTo, vUp);
        cam1.setVPSize(3, 3);
        cam1.setVPDistance(1);
        assertEquals(1,
                launcher(new Triangle(new Point(0, 1, -2), new Point(1, -1, -2), new Point(-1 , -1, -2)), cam1),
                wrongNumber);

        // TC02: Plane is parallel to the view plane (intersected in 2 points)
        Camera cam2 = new Camera(new Point(0, 0, 0), vTo, vUp);
        cam2.setVPSize(3, 3);
        cam2.setVPDistance(1);
        assertEquals(2,
                launcher(new Triangle(new Point(0, 20, -2), new Point(1, -1, -2), new Point(-1, -1, -2)), cam2),
                wrongNumber);
    }
}
