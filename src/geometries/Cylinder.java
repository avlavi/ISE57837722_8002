package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

public class Cylinder extends Tube{
    double height;//The height of the cylinder

    public Cylinder(double radius, Ray axisRay, double height) {//con
        super(radius, axisRay);
        this.height = height;
    }

    @Override
    public Vector getNormal(Point point) {
        return null;
    }
}
