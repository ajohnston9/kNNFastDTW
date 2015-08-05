package edu.fordham.cis.wisdm.biometrics.knn;

import java.util.*;

/**
 * An implementation of the k-Nearest Neighbors machine learning algorithm that uses FastDTW as a distance
 * metric.
 * @author Andrew H. Johnston <a href="mailto:ajohnston9@fordham.edu">ajohnston9@fordham.edu</a>
 * @version 0.01ALPHA
 */
public class kNN {

    /**
     * The examples that are in the algorithm's "memory" and will be used for prediciting the class of new
     * users.
     */
    private ArrayList<Window> trainedExamples = new ArrayList<>();

    /**
     * The number of neighbors to check
     */
    private int k = 1;

    /**
     * Initialize the kNN scheme as Nearest-Neighbor (i.e. 1-NN) with a set of examples.
     * @param examples The examples to add to the algorithm's "memory"
     */
    public kNN(ArrayList<Window> examples) {
        trainedExamples.addAll(examples);
    }

    /**
     * Initializes the scheme with a set of examples and sets k=numClosest
     * @param examples The examples to add to the algorithm's "memory"
     * @param numClosest The number of neighbors to consider when classifying new examples
     */
    public kNN(ArrayList<Window> examples, int numClosest) {
        trainedExamples.addAll(examples);
        k = numClosest;
    }

    /**
     * Adds a Window object to the algorithm's "memory"
     * @param example
     */
    public void addExample(Window example) {
        trainedExamples.add(example);
    }

    /**
     * Classifies a new example according to the data that's already in memory.
     * @param toClassify The window to classify. It can have a non-null class parameter, as it won't be considered.
     * @return the class (an integer) as predicted by the k nearest neighbors.
     */
    public int classify(Window toClassify) {
        HashMap<Window, Double> distances = new HashMap<>();
        for(Window other : trainedExamples) {
            double distance = FastDTWHelper.getDistance(toClassify, other);
            distances.put(other, distance);
        }
        return calculateMajority(distances);
    }

    //This may not be right. Review
    //http://stackoverflow.com/questions/109383/how-to-sort-a-mapkey-value-on-the-values-in-java/3420912#3420912
    private ArrayList<Window> getClosest(HashMap<Window, Double> distances) {
        ArrayList<Window> closest = new ArrayList<>();
        WindowComparator comparator = new WindowComparator(distances);
        TreeMap<Window, Double> tree = new TreeMap<>(comparator);
        tree.putAll(distances);
        return closest;
    }

    /**
     * Caculates the majority class from the k closest neighbors. In the even of a tie, the votes will be
     * recounted from the (k-1) closest neighbors. This is the helper function that allows the
     * calculateMajority(ArrayList) function be recursive.
     * @param distances A map containing the distance from the example to each Window in memory
     * @return the class (an integer) as predicted by the majority
     */
    private int calculateMajority(HashMap<Window, Double> distances) {
        return calculateMajority(getClosest(distances));
    }

    /**
     * Calculates the majority class
     * @param closest the closest neighbors, used to determine predicted class. Will recurse (potentially down to k=1)
     *                if ties continue to happen.
     * @return the class (an integer) as predicted by the majority.
     */
    private int calculateMajority(ArrayList<Window> closest) {
        HashMap<Integer, Integer> votes = new HashMap<>();
        boolean isTie = false;
        int highestNumVotes = 0;
        int predictedClass = -1;
        for(Window vote : closest) {
            if(votes.containsKey(vote.getDmClass())) {
                Integer ballot = votes.get(vote.getDmClass());
                ballot++;
                votes.put(vote.getDmClass(), ballot);
                if (ballot > highestNumVotes) {
                    highestNumVotes = ballot;
                    predictedClass = vote.getDmClass();
                    isTie = false;
                } else if (ballot == highestNumVotes) {
                    isTie = true;
                }
            }
        }
        if (isTie) {
            closest.remove(closest.get(closest.size() - 1)); //Remove last item
            calculateMajority(closest); //Decrease by one and try again
        }
        return predictedClass;
    }

    class WindowComparator implements Comparator<Window> {

        Map<Window, Double> base;

        public WindowComparator(Map<Window, Double> base) {
            this.base = base;
        }

        // Note: this comparator imposes orderings that are inconsistent with equals.
        public int compare(Window a, Window b) {
            if (base.get(a) >= base.get(b)) {
                return -1;
            } else {
                return 1;
            } // returning 0 would merge keys
        }
    }

}
