package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * An interface representing a light source (not including ambient light because
 * it doesn't care about distance).
 */
public interface LightSource {

    /**
     * Returns the intensity of the light at the specified point.
     *
     * @param p The point at which to evaluate the intensity of the light.
     * @return The intensity of the light at the specified point.
     */
    public Color getIntensity(Point p);

    /**
     * Returns the direction of the light from the specified point.
     *
     * @param p The point from which to evaluate the direction of the light.
     * @return The direction of the light from the specified point.
     */
    public Vector getL(Point p);
    public double getDistance(Point point);
}

