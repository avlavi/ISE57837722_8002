package primitives;

public class Point {
    Double3 point;

    public Point(double d1,double d2,double d3) {
        this.point=new Double3 (d1, d2, d3);
    }
    public Point(Double3 p1) {
        this.point = p1;
    }
    public Object add(Vector vector) {
        return this.point.add(vector.coordinate);
    }

    public Vector subtract(Point p1) {
        Vector result= new Vector(this.point.subtract(p1.point)
        );
        return result;
    }
    public double distanceSquared(Point p1){
        return (this.point.d1- p1.point.d1)*(this.point.d1- p1.point.d1)+
                (this.point.d2- p1.point.d2)*(this.point.d2- p1.point.d2)+
                (this.point.d3- p1.point.d3)*(this.point.d3- p1.point.d3);
    }
    public double distance(Point p1){
        return Math.sqrt(this.distanceSquared(p1));
    }
}

