package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.*;

import static primitives.Util.alignZero;

public class Sphere extends RadialGeometry {
    Point center;//center point of the sphere

    public Sphere(double radius, Point center) {//con
        super(radius);
        this.center = center;
    }

    @Override
    public Vector getNormal(Point point) {
        Vector normal = point.subtract(center);//the normal is the vector from the center of the sphere to the given point
        return normal.normalize();
    }

    public Point getCenter() {//return the center point of the sphere
        return center;
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        Point p0 = ray.getP0();
        Vector dir = ray.getDir();
        if (center.equals(p0))
            return List.of(ray.getPoint(radius));
        Vector u = center.subtract(p0);
        //if (u.length() < radius)הקרן בתוך הספרה
        double tm = dir.dotProduct(u);
        if (tm == 0)//משיק לספרה
            return null;
        double d = Math.sqrt(u.lengthSquared() - (tm * tm));
        if (d >= radius) return null;
        double th = Math.sqrt(radius * radius - d * d);
        double t1 = tm + th;
        double t2 = tm - th;
        alignZero(t1); alignZero(t2);
        if (t1 < 0 && t2 < 0)
            return null;
        if (t1 < 0 && t2 > 0)
            return List.of(ray.getPoint(t2));
        if (t1 > 0 && t2 < 0)
            return List.of(ray.getPoint(t1));
        return List.of(ray.getPoint(t1), ray.getPoint(t2));
    }
}
