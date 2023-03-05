package primitives;

public class
Vector {
    final double i;
    final double i1;
    final double i2;

    public Vector(int i, int i1, int i2) {
        this.i = i;
        this.i1 = i1;
        this.i2 = i2;
    }

    public double lengthSquared() {
        return Math.pow(this.i, 2) +
                Math.pow(this.i1, 2) +
                Math.pow(this.i2, 2);
    }

    public double length() {
        return Math.sqrt(this.length());
    }

    public Object add(Vector vector) {
        Vector v1 (0,0,0);
        v1.i = this.i + vector.i;
        v1.i1 = this.i1 + vector.i1;
        v1.i2 = this.i2 + vector.i2;
        return v1;
    }

    public Object subtract(Vector v2) {
        Vector v1;
        v1.i = this.i - v2.i;
        v1.i1 = this.i1 - v2.i1;
        v1.i2 = this.i2 - v2.i2;
        return v1;
    }

    public double dotProduct(Vector v3) {
        double scalar = this.i * v3.i + this.i1 * v3.i1 + this.i2 * v3.i2;
        return scalar;
    }

    public Vector crossProduct(Vector v2) {
        Vector result;
        result.i = this.i1 * v2.i2 - this.i2 * v2.i1;
        result.i1 = this.i2 * v2.i - this.i * v2.i2;
        result.i2 = this.i * v2.i1 - this.i1 * v2.i;
        return result;
    }

    public Vector normalize() {
        Vector normalizedVector( i =0,i1=0,i2=0;);
        double length = this.length();
        normalizedVector.i = this.i / length;
        normalizedVector.i1 = this.i1 / length;
        normalizedVector.i2 = this.i2 / length;
        return normalizedVector;
    }
}
