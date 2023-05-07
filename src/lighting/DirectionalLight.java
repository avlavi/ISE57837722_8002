package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class DirectionalLight extends Light implements LightSource {
    Vector direction;

    /**
     * Creates a new DirectionalLight object with the specified intensity and direction.
     *
     * @param intensity The intensity of the point light.
     * @param direction The direction of the spot light.
     */
    public DirectionalLight(Color intensity, Vector direction) {
        super(intensity);
        this.direction = direction;
    }

    @Override
    public Color getIntensity(Point p) {
        return this.getIntensity();
    }

    @Override
    public Vector getL(Point p) {
        return this.direction.normalize();
    }
}
