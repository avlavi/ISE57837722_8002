package lighting;

import geometries.Geometries;
import primitives.Color;
import primitives.Double3;

/**
 * A class representing the ambient light.
 */
public class AmbientLight extends Light {

    /**
     * The ambient color of the light.
     */
    private Color IA;

    /**
     * The ambient coefficient of the light.
     */
    private Double3 kA;

    /**
     * A constant ambient light with zero intensity.
     */
    public static final AmbientLight NONE = new AmbientLight(Color.BLACK, Double3.ZERO);

    /**
     * Creates a new AmbientLight object with the specified ambient color and coefficient.
     * @param IA The ambient color of the light.
     * @param kA The ambient coefficient of the light.
     */
    public AmbientLight(Color IA, Double3 kA) {
        super(IA.scale(kA));
        this.IA = IA;
        this.kA = kA;
    }

    /**
     * Creates a new AmbientLight object with the specified ambient color and coefficient.
     * @param IA The ambient color of the light.
     * @param kA The scalar ambient coefficient of the light.
     */
    public AmbientLight(Color IA, double kA) {
        super(IA.scale(kA));
        this.IA = IA;
        this.kA = new Double3(kA);
    }
}
