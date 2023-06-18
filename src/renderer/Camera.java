package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import static java.awt.Color.RED;
import static java.awt.Color.YELLOW;
import static primitives.Util.alignZero;
import static primitives.Util.isZero;

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
    private int numberOfRays = 1;
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
        if (numberOfRays < 1)
            throw new IllegalArgumentException("The number of rays must be >= 1");
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
     * Calculates the center coordinates of a pixel on the view plane, given its position in the grid and the indices of the pixel.
     *
     * @param nX The number of pixels in the x-axis of the view plane grid.
     * @param nY The number of pixels in the y-axis of the view plane grid.
     * @param j  The index of the pixel in the x-axis of the grid.
     * @param i  The index of the pixel in the y-axis of the grid.
     * @return The center coordinates of the specified pixel as a Point.
     */
    private Point getCenterOfPixel(int nX, int nY, double j, double i) {
        // calculate the ratio of the pixel by the height and by the width of the view plane
        // the ratio Ry = h/Ny, the height of the pixel
        double rY = alignZero(height / nY);
        // the ratio Rx = w/Nx, the width of the pixel
        double rX = alignZero(width / nX);

        // Xj = (j - (Nx -1)/2) * Rx
        double xJ = alignZero((j - ((nX - 1d) / 2d)) * rX);
        // Yi = -(i - (Ny - 1)/2) * Ry
        double yI = alignZero(-(i - ((nY - 1d) / 2d)) * rY);

        Point pIJ = this.location.add(vTo.scale(this.distance));

        if (!isZero(xJ)) {
            pIJ = pIJ.add(vRight.scale(xJ));
        }
        if (!isZero(yI)) {
            pIJ = pIJ.add(vUp.scale(yI));
        }
        return pIJ;
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
        Point Pij = getCenterOfPixel(nX, nY, j, i);
        return new Ray(this.location, Pij.subtract(this.location));
    }


    /**
     * Constructs a list of rays originating from the camera's location and passing through the specified pixel.
     * The pixel is divided into grid.
     *
     * @param nX The number of pixels in the x-axis of the view plane grid.
     * @param nY The number of pixels in the y-axis of the view plane grid.
     * @param j  The index of the pixel in the x-axis of the grid.
     * @param i  The index of the pixel in the y-axis of the grid.
     * @return A list of rays passing through the specified pixel, divided into segments.
     */
    public List<Ray> constructRays(int nX, int nY, int j, int i) {
        Point pIJ = getCenterOfPixel(nX, nY, j, i);

        // divide the pixel into segments and find the middle for every segment
        // ratio segment width and length
        List<Ray> rays = new LinkedList<>();
        double rY = height / nY;
        double rX = width / nX;
        double sRY = rY / numberOfRays;
        double sRX = rX / numberOfRays;

        // segment[i,j] center
        double sYI, sXJ;
        Point sPIJ;

        for (int si = 0; si < numberOfRays; si++) {
            for (int sj = 0; sj < numberOfRays; sj++) {
                sPIJ = pIJ;
                sYI = -(si - (numberOfRays - 1) / 2d) * sRY;
                sXJ = (sj - (numberOfRays - 1) / 2d) * sRX;

                if (!isZero(sXJ))
                    sPIJ = sPIJ.add(vRight.scale(sXJ));
                if (!isZero(sYI))
                    sPIJ = sPIJ.add(vUp.scale(sYI)); // sPIJ is the segment center

                rays.add(new Ray(location, sPIJ.subtract(location)));
            }
        }

        return rays;
    }


    /**
     * Casts a single ray through the specified pixel to compute the color by tracing the ray.
     *
     * @param nX The number of pixels in the x-axis of the view plane grid.
     * @param nY The number of pixels in the y-axis of the view plane grid.
     * @param j  The index of the pixel in the x-axis of the grid.
     * @param i  The index of the pixel in the y-axis of the grid.
     * @return The computed color for the pixel by tracing the ray.
     */
    private Color castRay(int nX, int nY, double j, double i) {
        return this.rayTracer.traceRay(this.constructRay(nX, nY, j, i));
    }


    /**
     * Casts multiple rays through the specified pixel to compute the color by tracing each ray and performing anti-aliasing.
     *
     * @param nX The number of pixels in the x-axis of the view plane grid.
     * @param nY The number of pixels in the y-axis of the view plane grid.
     * @param j  The index of the pixel in the x-axis of the grid.
     * @param i  The index of the pixel in the y-axis of the grid.
     * @return The computed color for the pixel after casting rays and performing anti-aliasing.
     */
    public Color castRays(int nX, int nY, int j, int i) {
        List<Ray> rays = constructRays(nX, nY, j, i);
        Color color = Color.BLACK;

        for (int k = 0; k < rays.size(); k++)
            color = color.add(rayTracer.traceRay(rays.get(k)));

        color = color.reduce(rays.size());
        return color;
    }


    /**
     * Renders the image by casting rays from the camera through each pixel of the image and writing the resulting color to the imageWriter.
     * Throws UnsupportedOperationException if any of the required resources are missing (rayTracerBase, imageWriter, width, height, distance).
     */
    public void renderImage() {
        if (this.rayTracer == null || this.imageWriter == null || this.width == 0 || this.height == 0 || this.distance == 0)
            throw new UnsupportedOperationException("MissingResourcesException");
        if (numberOfRays == 0) {
            throw new IllegalArgumentException("num Of Rays can not be 0");
        }

        int nX = imageWriter.getNx();
        int nY = imageWriter.getNy();
        if (numberOfRays == 1) {
            for (int i = 0; i < nY; i++) {
                for (int j = 0; j < nX; j++) {
                    imageWriter.writePixel(j, i, castRay(nX, nY, j, i));
                }
            }
        } else {//Anti-aliasing* improve is on
            for (int i = 0; i < nY; i++) {
                for (int j = 0; j < nX; j++) {
                    imageWriter.writePixel(j, i, castRays(nX, nY, j, i));
                }
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
