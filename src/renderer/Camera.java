package renderer;

import primitives.Point;
import primitives.Vector;

public class Camera {
    private Point location;
    private Vector vTo;
    private Vector vUp;
    private Vector vRight;

    public Camera(Point location, Vector vTo, Vector vUp) {
        this.location = location;
        this.vTo = vTo;
        this.vUp = vUp;

    }
}
