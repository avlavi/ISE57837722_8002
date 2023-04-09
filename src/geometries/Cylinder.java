package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

public class Cylinder extends Tube {
    double height;//The height of the cylinder

    public Cylinder(double radius, Ray axisRay, double height) {//con
        super(radius, axisRay);
        this.height = height;
    }

    @Override
    public Vector getNormal(Point point) {
        Vector v = axisRay.getDir();//get the direction vector
        Point p0 = axisRay.getP0();//get the head point of the cylinder's ray
        Point upperPoint = p0.add(v.scale(height));//get the center of the top base of the cylinder
        //If the given point is the same as one of the centers of the bases
        // or the point is on one of the bases
        // the normal vector is the same as the direction vector of the cylinder's ray
        if (point.equals(p0) || point.equals(upperPoint) || v.dotProduct(point.subtract(p0)) == 0 || v.dotProduct(point.subtract(upperPoint)) == 0)
            return axisRay.getDir();
        return super.getNormal(point);//else, the point is on the body of the cylinder so use the father(Tube) to calculate the normal
    }
}
