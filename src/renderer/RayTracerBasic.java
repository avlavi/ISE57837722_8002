package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import scene.Scene;

import java.util.List;

public class RayTracerBasic extends RayTracerBase {
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

    private Color calcColor(Point point) {
        return this.scene.ambientLight.getIntensity();
    }
}
