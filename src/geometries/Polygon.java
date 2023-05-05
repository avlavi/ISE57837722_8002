package geometries;

import static primitives.Util.isZero;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * Polygon class represents two-dimensional polygon in 3D Cartesian coordinate
 * system
 *
 * @author Dan
 */
public class Polygon extends Geometry {
    /**
     * List of polygon's vertices
     */
    protected final List<Point> vertices;
    /**
     * Associated plane in which the polygon lays
     */
    protected final Plane plane;
    private final int size;

    /**
     * Polygon constructor based on vertices list. The list must be ordered by edge
     * path. The polygon must be convex.
     *
     * @param vertices list of vertices according to their order by
     *                 edge path
     * @throws IllegalArgumentException in any case of illegal combination of
     *                                  vertices:
     *                                  <ul>
     *                                  <li>Less than 3 vertices</li>
     *                                  <li>Consequent vertices are in the same
     *                                  point
     *                                  <li>The vertices are not in the same
     *                                  plane</li>
     *                                  <li>The order of vertices is not according
     *                                  to edge path</li>
     *                                  <li>Three consequent vertices lay in the
     *                                  same line (180&#176; angle between two
     *                                  consequent edges)
     *                                  <li>The polygon is concave (not convex)</li>
     *                                  </ul>
     */
    public Polygon(Point... vertices) {
        if (vertices.length < 3)
            throw new IllegalArgumentException("A polygon can't have less than 3 vertices");
        this.vertices = List.of(vertices);
        size = vertices.length;

        // Generate the plane according to the first three vertices and associate the
        // polygon with this plane.
        // The plane holds the invariant normal (orthogonal unit) vector to the polygon
        plane = new Plane(vertices[0], vertices[1], vertices[2]);
        if (size == 3) return; // no need for more tests for a Triangle

        Vector n = plane.getNormal();
        // Subtracting any subsequent points will throw an IllegalArgumentException
        // because of Zero Vector if they are in the same point
        Vector edge1 = vertices[vertices.length - 1].subtract(vertices[vertices.length - 2]);
        Vector edge2 = vertices[0].subtract(vertices[vertices.length - 1]);

        // Cross Product of any subsequent edges will throw an IllegalArgumentException
        // because of Zero Vector if they connect three vertices that lay in the same
        // line.
        // Generate the direction of the polygon according to the angle between last and
        // first edge being less than 180 deg. It is hold by the sign of its dot product
        // with
        // the normal. If all the rest consequent edges will generate the same sign -
        // the
        // polygon is convex ("kamur" in Hebrew).
        boolean positive = edge1.crossProduct(edge2).dotProduct(n) > 0;
        for (var i = 1; i < vertices.length; ++i) {
            // Test that the point is in the same plane as calculated originally
            if (!isZero(vertices[i].subtract(vertices[0]).dotProduct(n)))
                throw new IllegalArgumentException("All vertices of a polygon must lay in the same plane");
            // Test the consequent edges have
            edge1 = edge2;
            edge2 = vertices[i].subtract(vertices[i - 1]);
            if (positive != (edge1.crossProduct(edge2).dotProduct(n) > 0))
                throw new IllegalArgumentException("All vertices must be ordered and the polygon must be convex");
        }
    }

    @Override
    public Vector getNormal(Point point) {
        return plane.getNormal(point);
    }

    /**
     Computes the intersection point(s) between the current polygon and a given ray.
     @param ray the ray to intersect with the polygon
     @return a List<Point> containing the intersection point(s) if exists, otherwise null.
     @throws IllegalArgumentException if the ray is null
     */
    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {

        if (plane.findIntersections(ray) == null) {//at first find if thar is intersection with the plane of the triangle
            return null;
        }
        //calculate according to the calculation in the course's book
        Point p = plane.findIntersections(ray).get(0);//intersection point
        ArrayList<Vector> vectors = new ArrayList<>();
        ArrayList<Vector> normals = new ArrayList<>();
        Point p0 = ray.getP0();
        Vector dir = ray.getDir();

        for (Point ver : vertices) {//calculate vectors from p0 to all the vertices of the polygon
            vectors.add(ver.subtract(p0));
        }

        for (int j = 0; j < vectors.size() - 1; j++) {//calculate normals
                normals.add(vectors.get(j).crossProduct(vectors.get(j+1)).normalize());
        }

        normals.add(vectors.get(vectors.size()-1).crossProduct(vectors.get(0)).normalize());
        double[] numbers = new double[normals.size()];
        double a = dir.dotProduct(normals.get(0));
        double b = 0;
        for (int j = 1; j < normals.size(); j++) {//checks if all the normals have the same sign
            b = dir.dotProduct(normals.get(j));
            if (a * b <= 0)
                return null;
        }
        return List.of(new GeoPoint(this,p));
    }
}
