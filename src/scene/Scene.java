package scene;

import geometries.Geometries;
import lighting.AmbientLight;
import lighting.LightSource;
import primitives.Color;
import java.util.LinkedList;
import java.util.List;

/**
 * A class of scene that represent a scene(an image) with all the properties
 * like name, background, geometries ect
 */
public class Scene {
    public String name;
    public Color background;
    public AmbientLight ambientLight;
    public Geometries geometries;
    public List<LightSource> lights = new LinkedList<>();

    public Scene(String name) {
        this.name = name;
        this.background = Color.BLACK;
        this.ambientLight = AmbientLight.NONE;
        this.geometries = new Geometries();
    }

    public Scene setBackground(Color background) {
        this.background = background;
        return this;
    }

    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }

    /**
     * Sets the list of light sources for this scene.
     *
     * @param lights The list of light sources to set.
     * @return This Scene object with the updated list of light sources.
     */
    public Scene setLights(List<LightSource> lights) {
        this.lights = lights;
        return this;
    }

}
