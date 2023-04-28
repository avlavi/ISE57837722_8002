package renderer;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;




/**
 * Camera class represents a camera in a 3D scene. It defines the camera's location,
 * direction, and orientation. It also provides methods for setting the viewport size
 * and distance, and for constructing rays from the camera to the scene.
 */
public class Camera {

    /**
     * The location of the camera in 3D space.
     */
    private Point location;

    private Vector vTo;
    private Vector vUp;
    private Vector vRight;

    double width;
    double height;
    double distance;

    /**
     * Constructs a new camera with the specified location, direction, and up vector.
     *
     * @param location the location of the camera
     * @param vTo      the direction the camera is pointing in
     * @param vUp      the up vector of the camera
     * @throws IllegalArgumentException if the direction and up vector are not orthogonal
     */
    public Camera(Point location, Vector vTo, Vector vUp) throws IllegalArgumentException {
        if (vTo.dotProduct(vUp) != 0) {
            throw new IllegalArgumentException("The vectors are not orthogonal");
        }
        this.location = location;
        this.vTo = vTo.normalize();
        this.vUp = vUp.normalize();
        vRight = vTo.crossProduct(vUp).normalize();
    }

    /**
     * Sets the width and height of the view plane.
     *
     * @param width  the width of the viewport
     * @param height the height of the viewport
     * @return this Camera object
     */
    public Camera setVPSize(double width, double height) {
        this.width = width;
        this.height = height;
        return this;
    }

    /**
     * Sets the distance between the camera and the view plane.
     *
     * @param distance the distance between the camera and the viewport
     * @return this Camera object
     */
    public Camera setVPDistance(double distance) {
        this.distance = distance;
        return this;
    }

    /**
     * Constructs a ray from the camera to a specific point in the view plane.
     *
     * @param nX the number of pixels along the x-axis of the view plane
     * @param nY the number of pixels along the y-axis of the view plane
     * @param j  the x-coordinate of the pixel in the view plane
     * @param i  the y-coordinate of the pixel in the view plane
     * @return the constructed Ray object
     */
    public Ray constructRay(int nX, int nY, int j, int i) {
        Point Pc = this.location.add(vTo.scale(this.distance));
        double rY = this.height / nY;
        double rX = this.width / nX;
        double yI = -(i - (double) (nY - 1) / 2) * rY;
        double xJ = (j - (double) (nX - 1) / 2) * rX;
        Point Pij = Pc;
        if (xJ != 0) Pij = Pij.add(vRight.scale(xJ));
        if (yI != 0) Pij = Pij.add(vUp.scale(yI));
        Vector Vij = Pij.subtract(this.location);
        return new Ray(this.location, Vij);
    }
}