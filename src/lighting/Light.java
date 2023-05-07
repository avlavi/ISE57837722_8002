package lighting;

import primitives.Color;

/**
 * An abstract class representing a light source in a scene.
 */
public abstract class Light {

    /**
     * The intensity of the light.
     */
    private Color intensity;

    /**
     * Constructs a new Light object with the specified intensity.
     *
     * @param intensity The intensity of the light.
     */
    protected Light(Color intensity) {
        this.intensity = intensity;
    }

    /**
     * Returns the intensity of the light.
     *
     * @return The intensity of the light.
     */
    public Color getIntensity() {
        return intensity;
    }
}
