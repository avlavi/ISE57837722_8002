package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class SpotLight extends PointLight {
    Vector direction;

    /**
     * Creates a new SpotLight object with the specified intensity, position, and direction.
     *
     * @param intensity The intensity of the spot light.
     * @param position  The position of the spot light.
     * @param direction The direction of the spot light.
     */
    public SpotLight(Color intensity, Point position, Vector direction) {
        super(intensity, position);
        this.direction = direction.normalize();
    }

    @Override
    public Color getIntensity(Point p) {
        double nominator = Math.max(0, direction.dotProduct(getL(p)));
        return super.getIntensity(p).scale(nominator);
    }

    @Override
    public Vector getL(Point p) {
        return super.getL(p);
    }
}
