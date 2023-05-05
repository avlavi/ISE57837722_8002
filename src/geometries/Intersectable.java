package geometries;

import primitives.Double3;
import primitives.Ray;
import primitives.Point;

import java.util.List;

import static primitives.Util.isZero;

public abstract class Intersectable {
    public List<Point> findIntersections(Ray ray) {
        var geoList = findGeoIntersections(ray);
        return geoList == null ? null : geoList.stream().map(gp -> gp.point).toList();
    }
    public static class GeoPoint {
        public Geometry geometry;
        public Point point;

        public GeoPoint(Geometry geometry, Point point) {
            this.geometry = geometry;
            this.point = point;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj instanceof GeoPoint && this.geometry.equals(obj)) {
                return this.point.equals(((GeoPoint) obj).point);
            }
            return false;
        }

        @Override
        public String toString() {
            return "Geometry:"+this.geometry.toString()+"point{"+this.point.toString()+"}";
        }
    }

    public List<GeoPoint> findGeoIntersections(Ray ray) {
        return  findGeoIntersectionsHelper(ray);

    }

    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray);

}
