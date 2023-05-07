package geometries;

import primitives.Double3;
import primitives.Ray;
import primitives.Point;

import java.util.List;

import static primitives.Util.isZero;

/**
 * An abstract class that contains all the methods and fields common to all geometries
 */
public abstract class Intersectable {
    public List<Point> findIntersections(Ray ray) {
        var geoList = findGeoIntersections(ray);
        return geoList == null ? null : geoList.stream().map(gp -> gp.point).toList();
    }

    /**
     * A class representing a point on a geometry.
     */
    public static class GeoPoint {

        /**
         * The geometry that contains the point.
         */
        public Geometry geometry;

        /**
         * The point represented by this object.
         */
        public Point point;

        /**
         * Creates a new GeoPoint object with the specified geometry and point.
         *
         * @param geometry The geometry containing the point.
         * @param point    The point represented by this object.
         */
        public GeoPoint(Geometry geometry, Point point) {
            this.geometry = geometry;
            this.point = point;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj instanceof GeoPoint) {
                return this.geometry.equals(((GeoPoint) obj).geometry) && this.point.equals(((GeoPoint) obj).point);
            }
            return false;
        }

        @Override
        public String toString() {
            return "Geometry:" + this.geometry.toString() + "point{" + this.point.toString() + "}";
        }
    }

    public List<GeoPoint> findGeoIntersections(Ray ray) {
        return findGeoIntersectionsHelper(ray);

    }

    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray);

}
