package models;
import java.util.ArrayList;
import java.util.List;

public class Group {
    private List<Pair> Pairs = new ArrayList<>();
    private Pair cookingPair;
    private COURSE course;
    private FOOD_PREFERENCE foodPreference;

    public Group(Pair pair1, Pair pair2, Pair pair3) {
        Pairs.add(pair1);
        Pairs.add(pair2);
        Pairs.add(pair3);
    }

    public void removePairFromGroup(Pair pair){
        Pairs.remove(pair);
    }
}