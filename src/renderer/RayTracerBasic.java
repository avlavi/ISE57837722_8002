package renderer;

import geometries.Intersectable;
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
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;
    private static final Double3 INITIAL_K = Double3.ONE;
    private static final double EPS = 0.1;


    /**
     * Creates a new instance of the {@code RayTracerBasic} class with the specified scene.
     *
     * @param scene the scene to be traced
     */
    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    private GeoPoint findClosestIntersection(Ray ray) {
        return ray.findClosestGeoPoint(scene.geometries.findGeoIntersections(ray));
    }

    @Override
    public Color traceRay(Ray ray) {
        GeoPoint closestPoint = findClosestIntersection(ray);
        if (closestPoint == null) return scene.background;
        return calcColor(closestPoint, ray);
    }
    /**
     * Computes the color of the intersection point using the Phong reflection model.
     *
     * @param point the intersection point
     * @return the color of the intersection point.
     */
    private Color calcColor(GeoPoint point, Ray ray) {
        return calcColor(point, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K)
                .add(scene.ambientLight.getIntensity());

    }

    /**
     * Calculates the color at a given geometric point based on the reflection and refraction effects.
     *
     * @param gp    The geometric point to calculate the color for.
     * @param ray   The incident ray at the geometric point.
     * @param level The recursion level.
     * @param k     The attenuation factor for the color calculation.
     * @return The calculated color at the given geometric point.
     */
    private Color calcColor(GeoPoint gp, Ray ray, int level, Double3 k) {
        Color color = calcLocalEffects(gp, ray, k);
        return 1 == level ? color : color.add(calcGlobalEffects(gp, ray, level, k));
    }

    /**
     * Calculates the global reflection and refraction effects for a given geometric point.
     *
     * @param gp    The geometric point to calculate the effects for.
     * @param ray   The incident ray at the geometric point.
     * @param level The recursion level.
     * @param k     The attenuation factor for the effects calculation.
     * @return The accumulated color based on the global effects.
     */
    private Color calcGlobalEffects(GeoPoint gp, Ray ray, int level, Double3 k) {
        Color color = Color.BLACK;
        Material mat = gp.geometry.getMaterial();
        Double3 kr = mat.kR, kkr = k.product(kr);
        Vector n = gp.geometry.getNormal(gp.point);
        Ray reflectedRay = constructReflectedRay(n, gp.point, ray);
        GeoPoint reflectedPoint = findClosestIntersection(reflectedRay);

        // Calculate reflection effect if the attenuation factor is above the minimum threshold
        if (!(kkr.lowerThan(MIN_CALC_COLOR_K)) && reflectedPoint != null) {
            color = color.add(calcColor(reflectedPoint, reflectedRay, level - 1, kkr).scale(kr));
        }

        Double3 kt = mat.kT, kkt = k.product(kt);
        Ray refractedRay = constructRefractedRay(n, gp.point, ray);
        GeoPoint refractedPoint = findClosestIntersection(refractedRay);

        // Calculate refraction effect if the attenuation factor is above the minimum threshold
        if (!(kkt.lowerThan(MIN_CALC_COLOR_K)) && refractedPoint != null) {
            color = color.add(calcColor(refractedPoint, refractedRay, level - 1, kkt).scale(kt));
        }

        return color;
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
    private Color calcLocalEffects(GeoPoint gp, Ray ray, Double3 k) {
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
                Double3 ktr = transparency(gp, lightSource, l, n);
                if (!(ktr.product(k).lowerThan(MIN_CALC_COLOR_K))) {
                    Color iL = lightSource.getIntensity(gp.point).scale(ktr);
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
        Vector r = l.subtract(n.scale(nl).scale(2)).normalize();
        return material.kS.scale(Math.pow(Math.max(0, v.dotProduct(r) * (-1)), material.nShininess));
    }

    /**
     * Calculates the transparency factor between a geometric point and a light source.
     *
     * @param gp     The geometric point to calculate the transparency for.
     * @param light  The light source.
     * @param l      The direction vector from the point to the light source.
     * @param n      The normal vector at the point.
     * @return The transparency factor between the geometric point and the light source.
     */
    private Double3 transparency(GeoPoint gp, LightSource light, Vector l, Vector n) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Ray lightRay = new Ray(gp.point, lightDirection, n);
        Point point = lightRay.getP0();
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);
        if (intersections == null)
            return Double3.ONE;
        Double3 ktr = Double3.ONE;
        for (GeoPoint element : intersections) {
            if (light.getDistance(point) > point.distance(element.point))
                ktr = ktr.product(element.geometry.getMaterial().kT);
        }
        return ktr;
    }

    /**
     * Constructs a reflected ray based on the given normal vector, point, and incident ray.
     *
     * @param n        The normal vector at the reflection point.
     * @param point    The reflection point.
     * @param inRay    The incident ray.
     * @return The constructed reflected ray.
     */
    private Ray constructReflectedRay(Vector n, Point point, Ray inRay) {
        Vector sub = n.scale(inRay.getDir().dotProduct(n)).scale(2);
        Vector dir = inRay.getDir().subtract(sub);
        return new Ray(point, dir, n);
    }

    /**
     * Constructs a refracted ray based on the given normal vector, point, and incident ray.
     *
     * @param n        The normal vector at the refraction point.
     * @param point    The refraction point.
     * @param inRay    The incident ray.
     * @return The constructed refracted ray.
     */
    private Ray constructRefractedRay(Vector n, Point point, Ray inRay) {
        return new Ray(point, inRay.getDir(), n);
    }
}

