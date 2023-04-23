package geometries;

import primitives.Point;
import primitives.Ray;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Geometries implements Intersectable {

    private List<Intersectable> geometries;

    public Geometries() {
        this.geometries = new LinkedList<Intersectable>();
    }

    public Geometries(Intersectable... geometries) {
        if (geometries != null) {
            this.geometries = (List.of(geometries));
        }

    }

    public void add(Intersectable... geometries) {
        if (geometries != null) {
            this.geometries.addAll(List.of(geometries));
        }
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        boolean intersectExist = false;
        for (Intersectable element : geometries) {
            if (element.findIntersections(ray) != null) {
                intersectExist = true;
                break;
            }
        }
        if (intersectExist == false) {
            return null;
        }

        ArrayList<Point> IntersectionsPoints = new ArrayList<Point>();
        for (Intersectable element : geometries) {
            if (element.findIntersections(ray) != null) {
                IntersectionsPoints.addAll(element.findIntersections(ray));
            }
        }
        return List.of(IntersectionsPoints.toArray(new Point[0]));
    }
}