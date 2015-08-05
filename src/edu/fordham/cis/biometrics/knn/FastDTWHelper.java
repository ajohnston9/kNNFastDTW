package edu.fordham.cis.biometrics.knn;

/**
 * Used to convert the internal representation of data as Window objects into TimeSeriesPoint and TimeSeries
 * objects used by the FastDTW library.
 * @author Andrew H. Johnston <a href="mailto:ajohnston9@fordham.edu">ajohnston9@fordham.edu</a>
 * @version 0.01ALPHA
 */
public class FastDTWHelper {

    /**
     * Converts data format and then calculates the distance between the two windows using FastDTW. The order in which
     * you apply arguments to this function should not matter.
     * @param a The first window
     * @param b The second window
     * @return The distance between the two windows
     */
    public static double getDistance(Window a, Window b) {
        //TODO: Convert distances into TimeSeries
        //TODO: Use FastDTW library to find the distance between the two
        throw new UnsupportedOperationException("Not implemented!");
    }
}
