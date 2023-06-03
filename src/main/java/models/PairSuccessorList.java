package models;

import java.util.ArrayList;
import java.util.List;

/**
 * The PairSuccessorList class contains all information about a List of Pair Successor
 * It contains the modification methods too
 */
public class PairSuccessorList extends SuccessorList {
    private List<Pair> pairSuccessorList = new ArrayList<>();

    public PairSuccessorList() {}

    /**
     * This method add a chosen Pair into the List
     * @param pair the chosen Pair
     */
    public void addPair(Pair pair) {
        pairSuccessorList.add(pair);
    }

    /**
     * This method checks if the list is empty
     * @return true if the list is empty
     */
    public boolean isEmpty(){
        return pairSuccessorList.isEmpty();
    }

    /**
     * This method finds the first pair in the list
     * @return the first pair
     */
    public Pair getFirst(){
        return pairSuccessorList.get(0);
    }

    /**
     * This method remove the first pair from the list
     */
    public void removeFirst(){
        pairSuccessorList.remove(0);
    }

    public List<Pair> getPairSuccessorList() {
        return pairSuccessorList;
    }
    @Override
    public int getSize() {
        return pairSuccessorList.size();
    }
}
