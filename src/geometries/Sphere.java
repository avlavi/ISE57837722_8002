package geometries;

import primitives.Point;
import primitives.Vector;

public class Sphere extends RadialGeometry{
    Point center;//center point of the sphere
    public Sphere(double radius, Point center) {//con
        super(radius);
        this.center = center;
    }

    @Override
    public Vector getNormal(Point point) {

        return null;
    }

    public Point getCenter() {//return the center point of the sphere
        return center;
    }
}
