package models;

import java.util.ArrayList;
import java.util.List;

public class Pair {
    private String Pair_ID;
    private boolean isPremade;

    private FOOD_PREFERENCE foodPreference;
    private List<Pair> visitedPairs = new ArrayList<>();
    private Participant participant1;
    private Participant participant2;



    public Pair(Participant participant1, Participant participant2) {
        this.participant1 = participant1;
        this.participant2 = participant2;
    }

    
    public void meetPair(Pair pair) {
        visitedPairs.add(pair);
    }

    public String getPairId() {
        return Pair_ID;
    }

    public boolean isPremade() {
        return isPremade;
    }

    public List<Pair> getVisitedPairs() {
        return visitedPairs;
    }

    public Participant getTeilnehmer1() {
        return participant1;
    }

    public Participant getTeilnehmer2() {
        return participant2;
    }


}
