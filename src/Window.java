/**
 * Represents a series of t,x,y,z values and a class as determined by some windowing function
 * @author Andrew H. Johnston <a href="mailto:ajohnston9@fordham.edu">ajohnston9@fordham.edu</a>
 * @version 0.01ALPHA
 */
public class Window {

    private long timestamp;
    private float x;
    private float y;
    private float z;
    private int dmClass;

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public int getDmClass() {
        return dmClass;
    }

    public void setDmClass(int dmClass) {
        this.dmClass = dmClass;
    }
}
