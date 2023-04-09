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
        Vector normal = point.subtract(center);//the normal is the vector from the center of the sphere to the given point
        return normal.normalize();
    }

    public Point getCenter() {//return the center point of the sphere
        return center;
    }
}
