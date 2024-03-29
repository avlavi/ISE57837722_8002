package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class PointLight extends Light implements LightSource {
    private Point position;
    private double kC = 1, kL = 0, kQ = 0;

    /**
     * Creates a new PointLight object with the specified intensity and position.
     *
     * @param intensity The intensity of the point light.
     * @param position  The position of the point light.
     */
    public PointLight(Color intensity, Point position) {
        super(intensity);
        this.position = position;
    }

    public PointLight setkC(double kC) {
        this.kC = kC;
        return this;
    }

    public PointLight setkL(double kL) {
        this.kL = kL;
        return this;

    }

    public PointLight setkQ(double kQ) {
        this.kQ = kQ;
        return this;
    }


    @Override
    public Color getIntensity(Point p) {
        double distance = position.distance(p);
        double denominator = (1.0 / (this.kC +
                this.kL * distance +
                this.kQ * distance * distance));
        return this.getIntensity().scale(denominator);
    }

    @Override
    public Vector getL(Point p) {
        return p.subtract(position).normalize();
    }

    @Override
    public double getDistance(Point point) {
        return position.distance(point);
    }
}
