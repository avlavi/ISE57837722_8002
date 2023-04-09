package geometries;

import primitives.Vector;
import primitives.Point;

import static primitives.Util.isZero;

public class Plane implements Geometry {
    Point q0;//Reference point of the plane
    Vector normal;//The normal to the plane

//constructor that create a plane from point and vector
    public Plane(Point q0, Vector normal) {
        this.q0 = q0;
        this.normal = normal.normalize();
    }
//constructor that create a plane from 3 points
    public Plane(Point p1, Point p2, Point p3) {
        this.q0 = p1;
        //create the vectors of the plane
        Vector v1 = p2.subtract(p1);
        Vector v2 = p3.subtract(p1);
        this.normal = v1.crossProduct(v2).normalize();//the normal to the plane is the normal to it's vectors
    }

    public Vector getNormal() {   return normal;}
    @Override
    public Vector getNormal(Point point) {  return normal;}
}
