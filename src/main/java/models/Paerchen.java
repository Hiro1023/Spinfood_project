package models;

import java.util.ArrayList;
import java.util.List;

public class Paerchen {
    private String pairId;
    private boolean isTogether;
    private int alterdifferenz;
    private double pathLength;
    private List<Paerchen> getroffenePairs = new ArrayList<>();
    private Teilnehmer teilnehmer1;
    private Teilnehmer teilnehmer2;



    public Paerchen(Teilnehmer teilnehmer1, Teilnehmer teilnehmer2, boolean isTogether, int alterdifferenz, double pathLength) {
        this.teilnehmer1 = teilnehmer1;
        this.teilnehmer2 = teilnehmer2;
        this.isTogether = teilnehmer1.getId().equals(teilnehmer2.getId());
        this.alterdifferenz = calculateAgeDiff(teilnehmer1, teilnehmer2);
        this.pathLength = (isTogether) ? 0 : calculatePathLength(teilnehmer1, teilnehmer2);
    }
  

    public int calculateAgeDiff(Teilnehmer teilnehmer1, Teilnehmer teilnehmer2) {
        return Math.abs(teilnehmer1.getAge() - teilnehmer2.getAge());
    }

    private double calculatePathLength(Teilnehmer teilnehmer1, Teilnehmer teilnehmer2) {
        double x1 = teilnehmer1.getKitchen().getKitchenLatitude();
        double y1 = teilnehmer1.getKitchen().getKitchenLongitude();
        double x2 = teilnehmer2.getKitchen().getKitchenLatitude();
        double y2 = teilnehmer2.getKitchen().getKitchenLongitude();
    
        double deltaX = x2 - x1;
        double deltaY = y2 - y1;
    
        return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }
    
    public void meetPair(Paerchen pair) {
        getroffenePairs.add(pair);
    }
    


    //getters

    public String getPairId() {
        return pairId;
    }

    public boolean isTogether() {
        return isTogether;
    }

    public int getAlterdifferenz() {
        return alterdifferenz;
    }

    public double getPathLength() {
        return pathLength;
    }

    public List<Paerchen> getGetroffenePairs() {
        return getroffenePairs;
    }

    public Teilnehmer getTeilnehmer1() {
        return teilnehmer1;
    }

    public Teilnehmer getTeilnehmer2() {
        return teilnehmer2;
    }


}
