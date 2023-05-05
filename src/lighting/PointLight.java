package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class PointLight extends Light implements LightSource {
    private Point position;
    private double kC = 0, kL = 0, kQ = 0;

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

    public PointLight(Color intensity, Point position) {
        super(intensity);
        this.position = position;
    }

    @Override
    public Color getIntensity(Point p) {
        double distance = position.distance(p);
        double denominator = 1.0 / (this.kC +
                this.kL * distance +
                this.kQ * distance * distance);
        Color IL = this.getIntensity().scale(denominator);
        return IL;
    }

    @Override
    public Vector getL(Point p) {
        return p.subtract(position).normalize();
    }
}
