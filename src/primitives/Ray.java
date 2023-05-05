/**
 * Represents a ray in a 3D space, defined by a starting point and a direction vector.
 */
package primitives;

import geometries.Intersectable.GeoPoint;

import java.util.List;
import java.util.Objects;

public class Ray {
    final Point p0;//The starting point of the ray
    final Vector dir;//The direction vector of the ray

    public Ray(Point p0, Vector dir) {//con
        if (dir == null)
            throw new NullPointerException("The given vector is null");
        this.p0 = p0;
        this.dir = dir.normalize();
    }

    public Point getP0() {//return p0
        return p0;
    }

    public Vector getDir() {//return dir
        return dir;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ray ray = (Ray) o;

        if (!p0.equals(ray.p0)) return false;
        return dir.equals(ray.dir);
    }

    @Override
    public int hashCode() {
        int result = p0 != null ? p0.hashCode() : 0;
        result = 31 * result + (dir != null ? dir.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Ray{" +
                "p0=" + p0.toString() +
                ", dir=" + dir.toString() +
                '}';
    }

    public Point getPoint(double t) {
        return p0.add(dir.scale(t));
    }

    /**
     * Finds the point in the given list that is closest to the starting point of the ray.
     *
     * @param points the list of points to search for the closest point
     * @return the point in the list that is closest to the reference point, or null if the input list is empty
     * @throws NullPointerException if the input list is null
     */
    public Point findClosestPoint(List<Point> points) {
        return points == null || points.isEmpty() ? null
                : findClosestGeoPoint(points.stream().map(p -> new GeoPoint(null, p)).toList()).point;
    }

    public GeoPoint findClosestGeoPoint(List<GeoPoint> points) {
        if (points == null || points.size() == 0) return null;
        GeoPoint closestPoint = points.get(0);
        double distance = closestPoint.point
                .distance(p0);
        for (GeoPoint element : points) {
            if (element.point.distance(p0) < distance) {
                closestPoint = element;
                distance = element.point.distance(p0);
            }
        }
        return closestPoint;
    }
}
