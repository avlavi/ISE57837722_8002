package lighting;

import geometries.Geometries;
import primitives.Color;
import primitives.Double3;

public class AmbientLight {
    private Color IA;
    private Color intensity;
    private Double3 kA;
    public static final AmbientLight NONE =  new AmbientLight(Color.BLACK,Double3.ZERO);
    public AmbientLight(Color IA, Double3 kA) {
        this.IA = IA;
        this.kA=kA;
        this.intensity=IA.scale(kA);
    }
    public AmbientLight(Color IA, double kA) {
        this.IA = IA;
        this.kA=new Double3(kA);
        this.intensity=IA.scale(kA);
    }

    public Color getIntensity() {
        return intensity;
    }
}
