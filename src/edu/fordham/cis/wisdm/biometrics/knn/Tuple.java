package edu.fordham.cis.wisdm.biometrics.knn;

/**
 * Represents a sensor reading in three axes at one point in time. Assumes timestamps are UNIX-style.
 * @author Andrew H. Johnston <a href="mailto:ajohnston9@fordham.edu">ajohnston9@fordham.edu</a>
 * @version 0.01ALPHA
 */
public class Tuple {

    private long timestamp;
    private float x;
    private float y;
    private float z;

    public Tuple(long timestamp, float x, float y, float z) {
        this.timestamp = timestamp;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * To make using the TimeSeriesPoint constructor easy, this returns
     * @return
     */
    public double[] toDoubleArray() {
        double[] arr = {x, y, z};
        return arr;
    }


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

}
