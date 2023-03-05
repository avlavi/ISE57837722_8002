package geometries;

import primitives.Ray;

public class Cylinder extends Tube{
    double height;

    public Cylinder(double radius, Ray axisRay, double height) {
        super(radius, axisRay);
        this.height = height;
    }
}
