package scene;

import lighting.AmbientLight;
import primitives.Color;

import java.awt.*;

public class Scene {
    String name;
    Color background;

    public Scene(String name) {
        this.name = name;
    }

    public Scene(String name, Color background) {
        this.name = name;
        this.background = background;
    }

    public void setAmbientLight(AmbientLight ambientLight) {
    }
}
