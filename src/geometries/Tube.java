package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

public class Tube extends RadialGeometry {
    Ray axisRay;//The direction vector of the tube

    public Tube(double radius, Ray axisRay) {//con
        super(radius);
        this.axisRay = axisRay;
    }

    @Override
    public Vector getNormal(Point point) {
        Vector v = axisRay.getDir();
        Point p0 = axisRay.getP0();
        double projection = v.dotProduct(point.subtract(p0));
        if(projection == 0) {
            Vector normal = point.subtract(p0);
            return normal.normalize();
        }
        Point O = p0.add(v.scale(projection));
        Vector normal = point.subtract(O);
        return normal.normalize();
    }

    public Ray getAxisRay() {//return axisRay
        return axisRay;
    }

}
