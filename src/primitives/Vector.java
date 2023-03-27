package primitives;

import java.util.Objects;
/**

 Represents a vector in 3D space.

 Extends the Point class, with a xyz property.
 */
public class Vector extends Point{

    /**

     Creates a Vector object from a Double3 object.
     @param xyz the xyz of the vector, as a Double3 object.
     @throws IllegalArgumentException if the given vector is zero.
     */
    Vector(Double3 xyz) {
        super(xyz);
        if (xyz.equals(xyz.ZERO))
            throw new IllegalArgumentException("The given vector is zero");
    }
    /**

     Creates a Vector object from three double values.
     @param i the first xyz of the vector.
     @param i1 the second xyz of the vector.
     @param i2 the third xyz of the vector.
     @throws IllegalArgumentException if the given vector is zero.
     */
    public Vector(double i, double i1, double i2) {
        super(i, i1, i2);
        if (xyz.equals(xyz.ZERO))
            throw new IllegalArgumentException("The given vector is zero");
    }
    /**

     Returns the squared length of the vector.
     @return the squared length of the vector.
     */
    public double lengthSquared() {
        return this.xyz.d1 * this.xyz.d1 +
                this.xyz.d2 * this.xyz.d2 +
                this.xyz.d3 * this.xyz.d3;
    }
    /**

     Returns the length of the vector.
     @return the length of the vector.
     */
    public double length() {
        return Math.sqrt(this.lengthSquared());
    }
    /**

     Adds a vector to this vector and returns the result as a new Vector object.
     @param vector the vector to add to this vector.
     @return the result of the addition as a new Vector object.
     @throws NullPointerException if the given vector is null.
     @throws ArithmeticException if the result vector of the addition is zero.
     */
    @Override
    public Vector add(Vector vector) {
        Vector v1 = new Vector(this.xyz.add(vector.xyz));
        return v1;
    }
    /**

     Scales this vector by a scalar and returns the result as a new Vector object.
     @param scalar the scalar to scale this vector by.
     @return the result of the scaling as a new Vector object.
     @throws IllegalArgumentException if the scalar is zero.
     */
    public Vector scale(double scalar) {
        Vector result = new Vector(xyz.scale(scalar));
        return result;
    }
    /**

     Returns the dot product of this vector and another vector.
     @param v3 the other vector.
     @return the dot product of this vector and another vector.
     @throws NullPointerException if the given vector is null.
     */
    public double dotProduct(Vector v3) {
        double scalar = this.xyz.d1 * v3.xyz.d1 + this.xyz.d2 * v3.xyz.d2 + this.xyz.d3 * v3.xyz.d3;
        return scalar;
    }
/**

 Returns the normal vector of this vector and another vector.
 @param v2 the other vector.
 @return the normal vector of this vector and another vector.
 @throws NullPointerException if
 */
    public Vector crossProduct(Vector v2) {
        if (v2 == null) {
            throw new NullPointerException("The given vector is null");
        }
        Vector result = new Vector(
                this.xyz.d2 * v2.xyz.d3 - this.xyz.d3 * v2.xyz.d2,
                this.xyz.d3 * v2.xyz.d1 - this.xyz.d1 * v2.xyz.d3,
                this.xyz.d1 * v2.xyz.d2 - this.xyz.d2 * v2.xyz.d1
        );
        if (result.xyz.equals(xyz.ZERO))
            throw new ArithmeticException("The normal vector is zero");
        return result;
    }


    /**
     Returns the normalized vector of this vector,
     which is a vector with the same direction but unit length.
     @return the normalized vector of this vector
     */
    public Vector normalize() {
        double length = this.length();

        Vector normalizedVector = new Vector(
                this.xyz.d1 / length,
                this.xyz.d2 / length,
                this.xyz.d3 / length
        );
        return normalizedVector;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vector vector = (Vector) o;

        return xyz.equals(vector.xyz);
    }
    @Override
    public int hashCode() {
        return xyz != null ? xyz.hashCode() : 0;
    }
    @Override
    public String toString() {
        return "Vector{" +
                xyz.toString() +
                '}';
    }
}