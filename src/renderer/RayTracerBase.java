package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

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
}
