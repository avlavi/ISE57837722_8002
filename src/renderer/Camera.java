package renderer;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;


public class Camera {
    private Point location;
    private Vector vTo;
    private Vector vUp;
    private Vector vRight;
double width;
double height;
double distance;
    public Camera(Point location, Vector vTo, Vector vUp) {
        if(vTo.dotProduct(vUp)!=0)
            throw new IllegalArgumentException("The vectors are not orthogonal\n");
        this.location = location;
        this.vTo = vTo.normalize();
        this.vUp = vUp.normalize();
        vRight = vTo.crossProduct(vUp).normalize();
    }

    public Camera setVPSize(double width, double height){
        this.width = width;
        this.height = height;
        return this;
    }

    public Camera setVPDistance(double distance) {
        this.distance = distance;
        return this;
    }

    public Ray constructRay(int nX, int nY, int j, int i){return null;}
}
