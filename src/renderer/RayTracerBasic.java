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

    private Color calcColor(GeoPoint gp, Ray ray, int level, Double3 k) {
        Color color = calcLocalEffects(gp, ray);
        return 1 == level ? color : color.add(calcGlobalEffects(gp, ray, level, k));
    }

    private Color calcGlobalEffects(GeoPoint gp, Ray ray, int level, Double3 k) {
        Color color = Color.BLACK;
        Material mat = gp.geometry.getMaterial();
        Double3 kr = mat.kR, kkr = k.product(kr);
        Vector n = gp.geometry.getNormal(gp.point);
        Ray reflectedRay = constructReflectedRay(n, gp.point, ray);
        GeoPoint reflectedPoint = findClosestIntersection(reflectedRay);
        if (!(kkr.lowerThan(MIN_CALC_COLOR_K)) && reflectedPoint != null) {
            color = color.add(calcColor(reflectedPoint, reflectedRay, level - 1, kkr).scale(kr));
        }
        Double3 kt = mat.kT, kkt = k.product(kt);
        Ray refractedRay = constructRefractedRay(n, gp.point, ray);
        GeoPoint refractedPoint = findClosestIntersection(refractedRay);
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
        Ray lightRay = new Ray(gp.point, lightDirection, n);
        Point point = lightRay.getP0();
        List<Point> intersections = scene.geometries.findIntersections(lightRay);
        if (intersections == null) return true;
        if (gp.geometry.getMaterial().kT != Double3.ZERO) {
            for (Point element : intersections) {
                if (light.getDistance(point) > point.distance(element))
                    return false;
            }
        }
        return true;
    }

    private Ray constructReflectedRay(Vector n, Point point, Ray inRay) {
        Vector sub = n.scale(inRay.getDir().dotProduct(n)).scale(2);
        Vector dir = inRay.getDir().subtract(sub);
        return new Ray(point, dir, n);
    }

    private Ray constructRefractedRay(Vector n, Point point, Ray inRay) {
        return new Ray(point, inRay.getDir(), n);
    }
}

