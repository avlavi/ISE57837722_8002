package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import scene.Scene;
import java.util.List;

/**
 * A basic implementation of the {@code RayTracerBase} class that computes the color of the closest intersection point with the scene.
 */
public class RayTracerBasic extends RayTracerBase {

    /**
     * Creates a new instance of the {@code RayTracerBasic} class with the specified scene.
     *
     * @param scene the scene to be traced
     */
    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    @Override
    public Color traceRay(Ray ray) {
        Point point = ray.findClosestPoint(scene.geometries.findIntersections(ray));
        if (point == null)
            return scene.background;
        return this.calcColor(ray.findClosestPoint(scene.geometries.findIntersections(ray)));
    }

    /**
     * Computes the color of the intersection point using the Phong reflection model.
     *
     * @param point the intersection point
     * @return the color of the intersection point
     */
    private Color calcColor(Point point) {
        return this.scene.ambientLight.getIntensity();
    }
}

