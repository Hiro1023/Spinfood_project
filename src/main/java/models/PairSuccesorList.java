package models;

import java.util.ArrayList;
import java.util.List;

public class PairSuccesorList extends SuccesorList{
    public List<Pair> getPairSuccessorList() {
        return pairSuccessorList;
    }

    private List<Pair> pairSuccessorList = new ArrayList<>();

    public PairSuccesorList() {};

    public void addPair(Pair pair) {
        pairSuccessorList.add(pair);
    }
    public boolean isEmpty(){
        return pairSuccessorList.isEmpty();
    }

    public Pair getFirst(){
        return pairSuccessorList.get(0);
    }

    public void removeFirst(){
        pairSuccessorList.remove(0);
    }



}
