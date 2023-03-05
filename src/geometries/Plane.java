package geometries;

import primitives.Vector;
import primitives.Point;

public class Plane implements Geometry {
    Point q0;
    Vector normal;

    public Plane(Point q0, Vector normal) {
        this.q0 = q0;
        this.normal = normal;
    }

    public Plane(Point p1, Point p2, Point p3) {
        this.q0 = p1;
        this.normal = null;
    }

    public Vector getNormal() {
        return normal;
    }
    @Override
    public Vector getNormal(Point point) {
        return null;
    }

}
