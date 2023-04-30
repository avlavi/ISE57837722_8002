package lighting;

import geometries.Geometries;
import primitives.Color;
import primitives.Double3;

public class AmbientLight {
    private Color lA;
    private Color intensity;
    private Double3 kA;
    AmbientLight ambientLight; //= AmbientLight.NONE;
    Geometries geometries = new Geometries();
}
