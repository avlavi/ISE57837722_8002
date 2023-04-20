package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

public class Tube extends RadialGeometry {
    Ray axisRay;//The direction vector of the tube

    public Tube(double radius, Ray axisRay) {//con
        super(radius);
        this.axisRay = axisRay;
    }

    @Override
    public Vector getNormal(Point point) {
        Vector v = axisRay.getDir();//get the direction vector
        Point p0 = axisRay.getP0();//get the head point of the cylinder's ray
        double projection = v.dotProduct(point.subtract(p0));//calculate the projection of the point on tube's ray
        if(projection == 0) {//The point forms a 90 degree angle with the base point of the tube's ray
            Vector normal = point.subtract(p0);
            return normal.normalize();
        }
        //the point is on the body of the tube
        Point O = p0.add(v.scale(projection));//calculate the "new" center of the tube that is in front of the given point
        Vector normal = point.subtract(O);//calculate the normal
        return normal.normalize();
    }

    public Ray getAxisRay() {//return axisRay
        return axisRay;
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        return null;
    }
}
