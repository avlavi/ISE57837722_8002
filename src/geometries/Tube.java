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

        return null;
    }

    public Ray getAxisRay() {//return axisRay
        return axisRay;
    }

}
