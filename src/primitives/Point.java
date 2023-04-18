package primitives;

import java.util.Objects;

public class Point {
    final Double3 xyz;//the coordinate of the point(x,y,z)
    /**
     * Constructs a new Point object with the specified coordinates.
     * @param d1 the x-coordinate
     * @param d2 the y-coordinate
     * @param d3 the z-coordinate
     */
    public Point(double d1, double d2, double d3) {
        this.xyz = new Double3(d1, d2, d3);
    }

    /**
     * Constructs a new Point object with the specified coordinate object.
     * @param p1 the coordinate object
     * @throws NullPointerException if the coordinate object is null
     */
    Point(Double3 p1) {
        this.xyz = new Double3(p1.d1, p1.d2, p1.d3);
    }

    /**
     * Returns a new Point object that is the result of adding the specified vector to this Point.
     * @param vector the vector to add
     * @return a new Point object that is the result of adding the vector to this Point
     */
    public Point add(Vector vector) {
        return new Point(this.xyz.add(vector.xyz));
    }

    /**
     * Returns a new Vector object that is the result of subtracting the specified Point from this Point.
     * @param p1 the Point to subtract
     * @return a new Vector object that is the result of subtracting the specified Point from this Point
     */
    public Vector subtract(Point p1) {
        return new Vector(this.xyz.subtract(p1.xyz));
    }

    /**
     * Returns the squared distance between this Point and the specified Point.
     * @param p1 the Point to calculate the distance to
     * @return the squared distance between this Point and the specified Point
     */
    public double distanceSquared(Point p1) {
        return (this.xyz.d1 - p1.xyz.d1) * (this.xyz.d1 - p1.xyz.d1) +
                (this.xyz.d2 - p1.xyz.d2) * (this.xyz.d2 - p1.xyz.d2) +
                (this.xyz.d3 - p1.xyz.d3) * (this.xyz.d3 - p1.xyz.d3);
    }

    /**
     * Returns the distance between this Point and the specified Point.
     * @param p1 the Point to calculate the distance to
     * @return the distance between this Point and the specified Point
     */
    public double distance(Point p1) {
        return Math.sqrt(this.distanceSquared(p1));
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Point point = (Point) o;

        return xyz.equals(point.xyz);
    }

    @Override
    public int hashCode() {
        return xyz != null ? xyz.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Point{" + xyz.toString() + '}';
    }

}
