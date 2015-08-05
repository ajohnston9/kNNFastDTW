package edu.fordham.cis.wisdm.biometrics.knn;

import java.util.ArrayList;

/**
 * Represents a series of t,x,y,z values and a class as determined by some windowing function
 * @author Andrew H. Johnston <a href="mailto:ajohnston9@fordham.edu">ajohnston9@fordham.edu</a>
 * @version 0.01ALPHA
 */
public class Window {

    private ArrayList<Tuple> tuples = new ArrayList<>();
    private int dmClass;

    public void addPoint(long timestamp, float x, float y, float z) {
        tuples.add(new Tuple(timestamp, x, y, z));
    }

    public Tuple getPoint(int index) {
        return tuples.get(index);
    }


    public int getDmClass() {
        return dmClass;
    }

    public void setDmClass(int dmClass) {
        this.dmClass = dmClass;
    }
}
