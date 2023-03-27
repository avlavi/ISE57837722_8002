/**

 Represents a ray in a 3D space, defined by a starting point and a direction vector.
 */
package primitives;
import java.util.Objects;

public class Ray {
    final Point p0;//The starting point of the ray
    final Vector dir;//The direction vector of the ray

    public Ray(Point p0, Vector dir) {//con
        if(dir == null)
            throw new NullPointerException("The given vector is null");
        this.p0 = p0;
        this.dir = dir.normalize();
    }

    public Point getP0() {//return p0
        return p0;
    }

    public Vector getDir() {//return dir
        return dir;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ray ray = (Ray) o;

        if (!p0.equals(ray.p0)) return false;
        return dir.equals(ray.dir);
    }
    @Override
    public int hashCode() {
        int result = p0 != null ? p0.hashCode() : 0;
        result = 31 * result + (dir != null ? dir.hashCode() : 0);
        return result;
    }
    @Override
    public String toString() {
        return "Ray{" +
                "p0=" + p0.toString() +
                ", dir=" + dir.toString() +
                '}';
    }

}
