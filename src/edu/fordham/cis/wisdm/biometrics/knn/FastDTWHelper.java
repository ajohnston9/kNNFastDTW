package edu.fordham.cis.wisdm.biometrics.knn;

import com.dtw.FastDTW;
import com.dtw.TimeWarpInfo;
import com.timeseries.TimeSeries;
import com.timeseries.TimeSeriesPoint;
import com.util.DistanceFunction;
import com.util.DistanceFunctionFactory;

/**
 * Used to convert the internal representation of data as Window objects into TimeSeriesPoint and TimeSeries
 * objects used by the FastDTW library.
 * @author Andrew H. Johnston <a href="mailto:ajohnston9@fordham.edu">ajohnston9@fordham.edu</a>
 * @version 0.01ALPHA
 */
public class FastDTWHelper {

    private static final DistanceFunction distanceFunction = DistanceFunctionFactory.EUCLIDEAN_DIST_FN;
    /**
     * The larger the search radius for FastDTW the more accurate it is. According to paper mentioned below, a
     * radius of 10 should yield an error of 1.5%
     * @see <a href="http://cs.fit.edu/~pkc/papers/tdm04.pdf">FastDTW Paper</a>
     */
    private static final int SEARCH_RADIUS = 10;

    /**
     * Converts data format and then calculates the distance between the two windows using FastDTW. The order in which
     * you apply arguments to this function should not matter.
     * @param a The first window
     * @param b The second window
     * @return The distance between the two windows
     */
    public static double getDistance(Window a, Window b) {
        TimeSeries windowA = new TimeSeries(3);
        TimeSeries windowB = new TimeSeries(3);
        addPointsToTimeSeries(a, windowA);
        addPointsToTimeSeries(b, windowB);
        TimeWarpInfo info = FastDTW.getWarpInfoBetween(windowA, windowB, SEARCH_RADIUS, distanceFunction);
        return info.getDistance();
    }

    private static void addPointsToTimeSeries(Window window, TimeSeries timeSeries) {
        for (Tuple tuple : window.getTuples()) {
            TimeSeriesPoint point = new TimeSeriesPoint(tuple.toDoubleArray());
            timeSeries.addFirst(tuple.getTimestamp(), point);
        }
    }
}
