package edu.fordham.cis.wisdm.biometrics.knn;

import java.util.*;

/**
 * An implementation of the k-Nearest Neighbors machine learning algorithm that uses FastDTW as a distance
 * metric.
 * @author Andrew H. Johnston <a href="mailto:ajohnston9@fordham.edu">ajohnston9@fordham.edu</a>
 * @version 0.01ALPHA
 */
public class kNN {

    private ArrayList<Window> trainedExamples = new ArrayList<>();
    private int k = 1;

    public kNN(ArrayList<Window> examples) {
        trainedExamples.addAll(examples);
    }

    public kNN(ArrayList<Window> examples, int numClosest) {
        trainedExamples.addAll(examples);
        k = numClosest;
    }

    public void addExample(Window example) {
        trainedExamples.add(example);
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

    private int calculateMajority(HashMap<Window, Double> distances) {
        return calculateMajority(getClosest(distances));
    }

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



    public int classify(Window toClassify) {
        HashMap<Window, Double> distances = new HashMap<>();
        for(Window other : trainedExamples) {
            double distance = FastDTWHelper.getDistance(toClassify, other); //TODO: Calculate distance
            distances.put(other, distance);
        }
        return calculateMajority(distances);
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
