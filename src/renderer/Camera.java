package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.Random;

import static java.awt.Color.RED;
import static java.awt.Color.YELLOW;


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
    private ImageWriter imageWriter;
    private RayTracerBase rayTracer;
    private int numberOfRays = 0;
    double width = 0;
    double height = 0;
    double distance = 0;

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
     */
    public Camera setVPSize(double width, double height) {
        this.width = width;
        this.height = height;
        return this;
    }

    /**
     * Sets the parameter that represent weather to improve the picture whit anti analyzing or not
     * default is false
     */
    public Camera setNumberOfRays(int numberOfRays) {
        this.numberOfRays = numberOfRays;
        return this;
    }

    /**
     * Sets the distance between the camera and the view plane.
     */
    public Camera setVPDistance(double distance) {
        this.distance = distance;
        return this;
    }

    /**
     * Sets the imageWriter.
     */
    public Camera setImageWriter(ImageWriter imageWriter) {
        this.imageWriter = imageWriter;
        return this;
    }

    /**
     * Sets the rayTracer.
     */
    public Camera setRayTracerBase(RayTracerBase rayTracerBase) {
        this.rayTracer = rayTracerBase;
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
    public Ray constructRay(int nX, int nY, double j, double i) {
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

    /**
     * cast ray
     */
    private Color castRay(int nX, int nY, double j, double i) {
        return this.rayTracer.traceRay(this.constructRay(nX, nY, j, i));
    }

    /**
     * Renders the image by casting rays from the camera through each pixel of the image and writing the resulting color to the imageWriter.
     * Throws UnsupportedOperationException if any of the required resources are missing (rayTracerBase, imageWriter, width, height, distance).
     */
    public void renderImage() {
        if (this.rayTracer == null || this.imageWriter == null || this.width == 0 || this.height == 0 || this.distance == 0)
            throw new UnsupportedOperationException("MissingResourcesException");
        if(numberOfRays != 0){
            this.renderImageRandomeAnalizyng();
            return;
        }
        int nX = imageWriter.getNx();
        int nY = imageWriter.getNy();

        for (int i = 0; i < nY; i++) {
            for (int j = 0; j < nX; j++) {
                imageWriter.writePixel(j, i, castRay(nX, nY, j, i));
            }
        }
    }

    /**
     * Renders the image by casting rays from the camera through each pixel of the image and writing the resulting color to the imageWriter.
     * Throws UnsupportedOperationException if any of the required resources are missing (rayTracerBase, imageWriter, width, height, distance).
     */
    public void renderImageRandomeAnalizyng() {
        int nX = imageWriter.getNx();
        int nY = imageWriter.getNy();
        double halfPixelHeight = (height / nY) / 2.0;
        double halfPixelWidth = (width / nX) / 2.0;
        Color color = Color.BLACK;
        Color colorHelp;
        Random random = new Random();
        double rand1 = 0;
        double rand2 = 0;
        int sizeBeam = 1000;
        for (int i = 0; i < nY; i++) {
            for (int j = 0; j < nX; j++) {
                for (int k = 0; k < sizeBeam; k++) {
                    rand1 = halfPixelHeight * (random.nextDouble() * 2.0 - 1.0);
                    rand2 = halfPixelWidth * (random.nextDouble() * 2.0 - 1.0);
                    colorHelp = this.castRay(nX, nY, j + rand2, i + rand1);
                    color = color.add(colorHelp);
                }
                imageWriter.writePixel(j, i, color.reduce(sizeBeam));
                color = Color.BLACK;
            }
        }
    }
    /**
     * Draws a grid on the image by writing a specified color to the pixels that fall on the grid lines.
     * Throws UnsupportedOperationException if imageWriter object is null.
     *
     * @param interval The spacing between grid lines.
     * @param color    The color to use for the grid lines.
     */
    public void printGrid(int interval, Color color) {
        if (imageWriter == null) throw new UnsupportedOperationException("MissingResourcesException");
        int nx = imageWriter.getNx();
        int ny = imageWriter.getNy();

        for (int i = 0; i < ny; i += interval)
            for (int j = 0; j < nx; j++) {
                imageWriter.writePixel(j, i, color);
            }
        for (int j = 0; j < nx; j += interval)
            for (int i = 0; i < ny; i++) {
                imageWriter.writePixel(j, i, color);
            }
    }

    /**
     * Writes the rendered image to the output file using the imageWriter object.
     * Throws UnsupportedOperationException if imageWriter object is null.
     */
    public void writeToImage() {
        if (imageWriter == null) throw new UnsupportedOperationException("MissingResourcesException");
        this.imageWriter.writeToImage();
    }
}
