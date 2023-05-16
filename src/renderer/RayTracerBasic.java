package renderer;

import lighting.LightSource;
import primitives.*;
import scene.Scene;
import geometries.Intersectable.GeoPoint;

import java.util.List;

import static primitives.Util.alignZero;

/**
 * A basic implementation of the {@code RayTracerBase} class that computes the color of the closest intersection point with the scene.
 */
public class RayTracerBasic extends RayTracerBase {
    private static final double DELTA = 0.1;//Constant for rayhead offset size for shading rays

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
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
        if (intersections == null) return scene.background;
        GeoPoint closestPoint = ray.findClosestGeoPoint(intersections);
        return calcColor(closestPoint, ray);
    }

    /**
     * Computes the color of the intersection point using the Phong reflection model.
     *
     * @param point the intersection point
     * @return the color of the intersection point.
     */
    private Color calcColor(GeoPoint point, Ray ray) {
        return this.scene.ambientLight.getIntensity().add(calcLocalEffects(point, ray));
    }

    /**
     * Calculates the local effects of the given geometry point and ray, taking into account
     * the emission of the geometry, the direction of the ray, the normal of the geometry, and
     * the scene's lights. Returns the resulting color.
     *
     * @param gp  The geometry point to calculate the local effects for.
     * @param ray The ray to calculate the local effects for.
     * @return The resulting color after calculating the local effects.
     */
    private Color calcLocalEffects(GeoPoint gp, Ray ray) {
        Color color = gp.geometry.getEmission();
        Vector v = ray.getDir();
        Vector n = gp.geometry.getNormal(gp.point);
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0) return color;
        Material mat = gp.geometry.getMaterial();
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(gp.point);
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0) { // sign(nl) == sing(nv)
                if (unshaded(gp, lightSource, l, n, nl)) {
                    Color iL = lightSource.getIntensity(gp.point);
                    color = color.add(iL.scale(calcDiffusive(mat, nl)),
                            iL.scale(calcSpecular(mat, n, l, nl, v)));
                }
            }
        }
        return color;
    }

    /**
     * Calculates the diffuse reflection of the given material and normal-lights angle.
     *
     * @param material The material to calculate the diffuse reflection for.
     * @param nl       The normal-lights angle to calculate the diffuse reflection for.
     * @return The calculated diffuse reflection.
     */
    private Double3 calcDiffusive(Material material, double nl) {
        return material.kD.scale(Math.abs(nl));
    }

    /**
     * Calculates the specular reflection of the given material, normal, light, normal-lights angle,
     * and viewer direction.
     *
     * @param material The material to calculate the specular reflection for.
     * @param n        The normal of the geometry point.
     * @param l        The direction of the light.
     * @param nl       The normal-lights angle.
     * @param v        The viewer direction.
     * @return The calculated specular reflection.
     */
    private Double3 calcSpecular(Material material, Vector n, Vector l, double nl, Vector v) {
        Vector r = l.subtract(n.scale(l.dotProduct(n)).scale(2)).normalize();
        return material.kS.scale(Math.pow(Math.max(0, v.dotProduct(r) * (-1)), material.nShininess));
    }

    //From the non-shading test method between a point and the light source
    private boolean unshaded(GeoPoint gp, LightSource light, Vector l, Vector n, double nl) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Vector delta = n.scale(nl < 0 ? DELTA : -DELTA);
        Point point = gp.point.add(delta);
        Ray lightRay = new Ray(point, lightDirection);
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);
        if (intersections == null) return true;
        double pointDistance = light.getDistance(point);
        for (GeoPoint element : intersections) {
            if (light.getDistance(element.point) < pointDistance) return false;
        }
        return true;
    }
}

