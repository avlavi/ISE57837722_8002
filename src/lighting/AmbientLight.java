package lighting;

import geometries.Geometries;
import primitives.Color;
import primitives.Double3;

public class AmbientLight extends Light {
    private Color IA;
    private Double3 kA;
    public static final AmbientLight NONE =  new AmbientLight(Color.BLACK,Double3.ZERO);
    public AmbientLight(Color IA, Double3 kA) {
        super(IA.scale(kA));
        this.IA = IA;
        this.kA=kA;
    }
    public AmbientLight(Color IA, double kA) {
        super(IA.scale(kA));
        this.IA = IA;
        this.kA=new Double3(kA);
    }

}
