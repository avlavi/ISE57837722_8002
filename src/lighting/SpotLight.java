package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class SpotLight extends PointLight{
    Vector direction;

    public SpotLight(Color intensity, Point position, Vector direction) {
        super(intensity, position);
        this.direction = direction;
    }
    @Override
    public Color getIntensity(Point p) {
        double nominator =Math.max(0,direction.dotProduct(getL(p))) ;
        Color IL = super.getIntensity(p).scale(nominator);
        return IL;
    }

    @Override
    public Vector getL(Point p) {
        return super.getL(p);
    }
}
