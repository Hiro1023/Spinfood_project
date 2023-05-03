//package models;

//import models.Paerchen;
//import models.PaerchenListe;

import java.util.ArrayList;
import java.util.List;

public class Gruppe {
    private String groupId;
    
    private int alterdifferenz;
    private double sexDiversity;
    private double pathLength;
    private List<Paerchen> groupPairs = new ArrayList<>();
    private List<Gruppe> getroffeneGroups = new ArrayList<>();
  

    public Gruppe(String groupId, Paerchen paerchen1, Paerchen paerchen2, Paerchen paerchen3) {
        this.groupId = groupId;
        groupPairs.add(paerchen1);
        groupPairs.add(paerchen2);
        groupPairs.add(paerchen3);
        this.alterdifferenz = calculateGroupAgeDiff();
        this.pathLength =  calculateGroupPathLength();
        this.sexDiversity = calculateSexDiversity(paerchen1,paerchen2,paerchen3);
    }

    
    public double calculateSexDiversity(Paerchen pair1, Paerchen pair2, Paerchen pair3) {
        PaerchenList pairList = new PaerchenList();
        pairList.addPairToList(pair1);
        pairList.addPairToList(pair2);
        pairList.addPairToList(pair3);
    
        return pairList.calculateSexDiversity();
    }

    public int calculateGroupAgeDiff() {
        int ageDiff = 0;
        for (Paerchen pair : groupPairs) {
            ageDiff += pair.getAlterdifferenz();
        }
        return ageDiff;
    }

    public double calculateGroupPathLength() {
        double totalPathLength = 0;
        for (Paerchen pair : groupPairs) {
            totalPathLength += pair.getPathLength();
        }
        return totalPathLength;
    }

    public void removePairFromGroup(Paerchen pair){
        groupPairs.remove(pair);
    }

    public boolean checkVisited(Paerchen pair1, Paerchen pair2){
        // Check if the pairs have met before by checking their getroffeneGroups
        for (Paerchen metPair1 : pair1.getGetroffenePairs()) {
            for (Paerchen metPair2 : pair2.getGetroffenePairs()) {
                if (metPair1.getPairId().equals(metPair2.getPairId())) {
                    return true;
                }
            }
        }
        return false;
    }

    public void meetGroup(Gruppe group){
        getroffeneGroups.add(group);
    }


    //getters

    public String getGroupId() {
        return groupId;
    }

    public int alterdifferenz() {
        return alterdifferenz;
    }

    public double getPathLength() {
        return pathLength;
    }

    public List<Gruppe> getGetroffeneGroups() {
        return getroffeneGroups;
    }


    public double getSexDiversity() {
        return sexDiversity;
    }

}