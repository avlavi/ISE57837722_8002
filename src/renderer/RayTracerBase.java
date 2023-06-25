package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import scene.Scene;

import java.util.List;

/**
 * An abstract class that serves as a father to the RayTracerBasic class
 * whose implements the operation of coloring the rays sent from the view plane
 */
public abstract class RayTracerBase {
    protected Scene scene;

    /**
     * Creates a new instance of the {@code RayTracerBase} class with the specified scene.
     *
     * @param scene the scene to be traced
     */
    public RayTracerBase(Scene scene) {
        this.scene = scene;
    }

    /**
     * Computes the color of the intersection point of the specified ray with the scene.
     *
     * @param ray the ray to trace
     * @return the color of the intersection point, or {@code null} if the ray does not intersect any object in the scene
     */
    public abstract Color traceRay(Ray ray);

    /**
     * Checks the color of the pixel with the help of individual rays and averages between
     * them and only if necessary continues to send beams of rays in recursion
     * @param centerP center pixl
     * @param Width Length
     * @param Height width
     * @param minWidth min Width
     * @param minHeight min Height
     * @param cameraLoc Camera location
     * @param Vright Vector right
     * @param Vup vector up
     * @param prePoints pre Points
     * @return Pixel color
     */
    public abstract Color AdaptiveSuperSamplingRec(Point centerP, double Width, double Height, double minWidth, double minHeight, Point cameraLoc, Vector Vright, Vector Vup, List<Point> prePoints);
}
