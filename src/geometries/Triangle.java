package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;

public class Triangle extends Polygon{
    public Triangle(Point p1, Point p2,Point p3) {//con
        super(p1,p2,p3);
    }
    @Override
    public List<Point> findIntersections(Ray ray) {
        return null;
    }
}
