package renderer;

import geometries.Intersectable;
import lighting.LightSource;
import primitives.*;
import scene.Scene;
import geometries.Intersectable.GeoPoint;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * A basic implementation of the {@code RayTracerBase} class that computes the color of the closest intersection point with the scene.
 */
public class RayTracerBasic extends RayTracerBase {
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;
    private static final Double3 INITIAL_K = Double3.ONE;

    private int glossinessRaysNum = 36;
    private double distanceGrid = 25;
    private double sizeGrid=4;


    /**
     * Creates a new instance of the {@code RayTracerBasic} class with the specified scene.
     *
     * @param scene the scene to be traced
     */
    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    public void setDistanceGrid(double distanceGrid) {
        this.distanceGrid = distanceGrid;
    }

    /**
     * Finds the closest intersection between a ray and the geometries in the scene.
     *
     * @param ray The ray to find the closest intersection with.
     * @return The closest intersection point as a GeoPoint object.
     */
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
        Material material = gp.geometry.getMaterial();
        Double3 kr = material.kR;
        Double3 kkr = k.product(kr); //in each recursive iteration the impact of the reflection decreases
        Vector n = gp.geometry.getNormal(gp.point);
        if (!kkr.lowerThan(MIN_CALC_COLOR_K)) {
            List<Ray> reflectedRays = constructReflectedRays(gp, ray, material.Glossy);
            Color tempColor1 = Color.BLACK;
            // each ray
            for (Ray reflectedRay : reflectedRays) {
                GeoPoint reflectedPoint = findClosestIntersection(reflectedRay);
                tempColor1 = tempColor1.add(reflectedPoint == null ?
                        Color.BLACK : calcColor(reflectedPoint, reflectedRay, level - 1, kkr).scale(kr));
            }

            color = color.add(tempColor1.reduce(reflectedRays.size()));
        }
        Double3 kt = material.kT;
        Double3 kkt = k.product(kt); //in each recursive iteration the impact of the refraction decreases
        if (!kkt.lowerThan(MIN_CALC_COLOR_K)) {
            List<Ray> refractedRays = constructRefractedRays(gp, ray, n);
            Color tempColor2 = Color.BLACK;
            //calculate for each ray
            for (Ray refractedRay : refractedRays) {
                GeoPoint refractedPoint = findClosestIntersection(refractedRay);
                tempColor2 = tempColor2.add(refractedPoint == null ?
                        Color.BLACK : calcColor(refractedPoint, refractedRay, level - 1, kkt).scale(kt));
            }

            color = color.add(tempColor2.reduce(refractedRays.size()));
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
     * @param gp    The geometric point to calculate the transparency for.
     * @param light The light source.
     * @param l     The direction vector from the point to the light source.
     * @param n     The normal vector at the point.
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

    /**  Produces a reflection bean that starts from
     * the point where the ray struck from the camera and goes diagonally to the point
     * @param geoPoint the point where the ray hit from the camera
     * @param ray the ray from the camera
     * @return a reflection ray
     */
    private List<Ray> constructReflectedRays(GeoPoint geoPoint, Ray ray, double Glossy) {
        Vector v = ray.getDir();
        Vector n = geoPoint.geometry.getNormal(geoPoint.point);
        double nv = alignZero(v.dotProduct(n));
        Vector r = v.subtract(n.scale(2d * nv)).normalize();

        return raysGrid( new Ray(geoPoint.point,r,n),1,Glossy, n);
    }

    /**
     * Produces a transparency bean of rays that starts from
     * the point where the ray hit from the camera and
     * goes in the direction like the original ray
     *
     * @param geoPoint the point where the ray hit from the camera
     * @param inRay    the ray from the camera
     * @return transparency ray
     */
    private List<Ray> constructRefractedRays(GeoPoint geoPoint, Ray inRay, Vector n) {
        return raysGrid(new Ray(geoPoint.point, inRay.getDir(), n), -1, geoPoint.geometry.getMaterial().Glossy, n);
    }

    /**
     * Building a beam of rays for transparency and reflection
     * @param ray The beam coming out of the camera
     * @param direction the vector
     * @param glossy The amount of gloss
     * @param n normal
     * @return Beam of rays
     */
    List<Ray> raysGrid(Ray ray, int direction, double glossy, Vector n){
        int numOfRowCol = isZero(glossy)? 1: (int)Math.ceil(Math.sqrt(glossinessRaysNum));
        if (numOfRowCol == 1) return List.of(ray);
        Vector Vup ;
        Vector dir = ray.getDir();
        double Ax= Math.abs(dir.getX()), Ay= Math.abs(dir.getY()), Az= Math.abs(dir.getZ());
        if (Ax < Ay)
            Vup= Ax < Az ?  new Vector(0, -dir.getZ(), dir.getY()) :
                    new Vector(-dir.getY(), dir.getX(), 0);
        else
            Vup= Ay < Az ?  new Vector(dir.getZ(), 0, -dir.getX()) :
                    new Vector(-dir.getY(), dir.getX(), 0);
        Vector Vright = Vup.crossProduct(dir).normalize();
        Point pc=ray.getPoint(distanceGrid);
        double step = glossy/sizeGrid;
        Point pij=pc.add(Vright.scale(numOfRowCol/2*-step)).add(Vup.scale(numOfRowCol/2*-step));
        Vector tempRayVector;
        Point Pij1;

        List<Ray> rays = new ArrayList<>();
        Point p0 = ray.getP0();
        rays.add(ray);
        for (int i = 1; i < numOfRowCol; i++) {
            for (int j = 1; j < numOfRowCol; j++) {
                Pij1=pij.add(Vright.scale(i*step)).add(Vup.scale(j*step));
                tempRayVector =  Pij1.subtract(p0);
                if(direction == 1 && n.dotProduct(tempRayVector) < 0) //refraction
                    rays.add(new Ray(p0, tempRayVector));
                if(direction == -1 && n.dotProduct(tempRayVector) > 0) //reflection
                    rays.add(new Ray(p0, tempRayVector));
            }
        }

        return rays;
    }

    @Override
    public Color AdaptiveSuperSamplingRec(Point centerP, double Width, double Height, double minWidth, double minHeight, Point cameraLoc, Vector Vright, Vector Vup, List<Point> prePoints) {
        if (Width < minWidth * 2 || Height < minHeight * 2) {
            return this.traceRay(new Ray(cameraLoc, centerP.subtract(cameraLoc))) ;
        }

        List<Point> nextCenterPList = new LinkedList<>();
        List<Point> cornersList = new LinkedList<>();
        List<Color> colorList = new LinkedList<>();
        Point tempCorner;
        Ray tempRay;
        for (int i = -1; i <= 1; i += 2){
            for (int j = -1; j <= 1; j += 2) {
                tempCorner = centerP.add(Vright.scale(i * Width / 2)).add(Vup.scale(j * Height / 2));
                cornersList.add(tempCorner);
                if (prePoints == null || !isInList(prePoints, tempCorner)) {
                    tempRay = new Ray(cameraLoc, tempCorner.subtract(cameraLoc));
                    nextCenterPList.add(centerP.add(Vright.scale(i * Width / 4)).add(Vup.scale(j * Height / 4)));
                    colorList.add(traceRay(tempRay));
                }
            }
        }


        if (nextCenterPList == null || nextCenterPList.size() == 0) {
            return Color.BLACK;
        }


        boolean isAllEquals = true;
        Color tempColor = colorList.get(0);
        for (Color color : colorList) {
            if (!tempColor.isAlmostEquals(color))
                isAllEquals = false;
        }
        if (isAllEquals && colorList.size() > 1)
            return tempColor;


        tempColor = Color.BLACK;
        for (Point center : nextCenterPList) {
            tempColor = tempColor.add(AdaptiveSuperSamplingRec(center, Width/2,  Height/2,  minWidth,  minHeight ,  cameraLoc, Vright, Vup, cornersList));
        }
        return tempColor.reduce(nextCenterPList.size());


    }

    /**
     * Find a point in the list
     * @param pointsList the list
     * @param point the point that we look for
     * @return
     */
    private boolean isInList(List<Point> pointsList, Point point) {
        for (Point tempPoint : pointsList) {
            if(point.equals(tempPoint))
                return true;
        }
        return false;
    }
}

