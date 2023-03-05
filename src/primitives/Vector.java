package primitives;

public class
Vector {
    Double3 coordinate;

    public Vector(Double3 coordinate) {
        this.coordinate = new Double3(coordinate.d1, coordinate.d2, coordinate.d3);
    }


    public Vector(double i, double i1, double i2) {
        this.coordinate = new Double3(i, i1, i2);
    }

    public double lengthSquared() {
        return this.coordinate.d1 * this.coordinate.d1 +
                this.coordinate.d2 * this.coordinate.d2 +
                this.coordinate.d3 * this.coordinate.d3;
    }

    public double length() {
        return Math.sqrt(this.lengthSquared());
    }

    public Object add(Vector vector) {
        Vector v1 = new Vector(
                this.coordinate.d1 + vector.coordinate.d1,
                this.coordinate.d2 + vector.coordinate.d2,
                this.coordinate.d3 + vector.coordinate.d3
        );
        return v1;
    }

    public Object subtract(Vector v2) {
        Vector v1 = new Vector(
                this.coordinate.d1 - v2.coordinate.d1,
                this.coordinate.d2 - v2.coordinate.d2,
                this.coordinate.d3 - v2.coordinate.d3
        );
        return v1;
    }

    public double dotProduct(Vector v3) {
        double scalar = this.coordinate.d1 * v3.coordinate.d1 + this.coordinate.d2 * v3.coordinate.d2 + this.coordinate.d3 * v3.coordinate.d3;
        return scalar;
    }

    public Vector crossProduct(Vector v2) {
        Vector result = new Vector(
                this.coordinate.d2 * v2.coordinate.d3 - this.coordinate.d3 * v2.coordinate.d2,
                this.coordinate.d3 * v2.coordinate.d1 - this.coordinate.d1 * v2.coordinate.d3,
                this.coordinate.d1 * v2.coordinate.d2 - this.coordinate.d2 * v2.coordinate.d1
        );
        return result;
    }

    public Vector normalize() {
        double length = this.length();
        Vector normalizedVector = new Vector(
                this.coordinate.d1 / length,
                this.coordinate.d2 / length,
                this.coordinate.d3 / length
        );
        return normalizedVector;
    }

    public Vector scale(double scalar) {
        Vector result = new Vector(
                this.coordinate.d1 * scalar,
                this.coordinate.d2 * scalar,
                this.coordinate.d3 * scalar
        );
        return result;
    }
}
