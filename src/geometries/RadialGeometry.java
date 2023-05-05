package geometries;

public abstract class RadialGeometry extends Geometry {
    protected double radius;

    public RadialGeometry(double radius) {//con
        if(radius<=0)
            throw new IllegalArgumentException("The radius must be a positive number");
        this.radius = radius;
    }
}
