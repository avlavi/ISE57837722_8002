package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

public class Triangle extends Polygon{
    public Triangle(Point p1, Point p2,Point p3) {//con
        super(p1,p2,p3);
    }
    @Override
    public List<Point> findIntersections(Ray ray) {

        if (super.plane.findIntersections(ray) == null) {
            return null;
        }
        Point p = super.plane.findIntersections(ray).get(0);
        Vector v1 = this.vertices.get(0).subtract(ray.getP0());
        Vector v2 = this.vertices.get(1).subtract(ray.getP0());
        Vector v3 = this.vertices.get(2).subtract(ray.getP0());
        Vector n1 = v1.crossProduct(v2).normalize();
        Vector n2 = v2.crossProduct(v3).normalize();
        Vector n3 = v3.crossProduct(v1).normalize();
        double a = ray.getDir().dotProduct(n1);
        double b = ray.getDir().dotProduct(n2);
        double c = ray.getDir().dotProduct(n3);
        if (a == 0 || b == 0 || c == 0) {
            return null;
        }
        if ((a * b > 0 && b * c > 0) || (a * b < 0 && b * c < 0)) {
            return List.of(p);
        }
        return null;
    }
}
