package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Vector;
import primitives.Point;

/**
 * A class represent a geometry in tje 3 dimension
 */
public abstract class Geometry extends Intersectable {
    protected Color emission = Color.BLACK;
    private Material material = new Material();

    /**
     * Returns the emission color of the geometry.
     *
     * @return The emission color of the geometry.
     */
    public Color getEmission() {
        return emission;
    }

    /**
     * Sets the emission color of the geometry to the specified color.
     *
     * @param emission The new emission color of the geometry.
     * @return A reference to the modified geometry object.
     */
    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }


    public abstract Vector getNormal(Point point);//return the normal vector

    /**
     * Returns the material of this object.
     *
     * @return The material of this object.
     */
    public Material getMaterial() {
        return material;
    }


    public Geometry setMaterial(Material material) {
        this.material = material;
        return this;
    }
}
