import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.*;

public class Paerchen {
    private String pairID;
    private boolean isTogether;
    private int ageDiff ;
    private double pathLength;

    private List<Paerchen> meetPairList = new ArrayList<>();

    public Paerchen(Teilnehmer teilnehmer_1,Teilnehmer teilnehmer_2){

        this.pairID = teilnehmer_1.getID()+"-"+teilnehmer_2.getID();
        this.isTogether = (teilnehmer_1.getID_2().equals(teilnehmer_2.getID()));
        this.ageDiff = calculateAgeDiff(teilnehmer_1,teilnehmer_2);
        this.pathLength = (isTogether())? 0: calculatePathLength(teilnehmer_1,teilnehmer_2);
    }

    /**
     * save the Paar, who this Paar has meeted
     * @param paerchen
     */
    public void meetPair(Paerchen paerchen){
        meetPairList.add(paerchen);
    }

    /**
     * calculate AgeDifference of Paerchen
     * @param teilnehmer_1
     * @param teilnehmer_2
     * @return Age Differece by int
     */
    public int calculateAgeDiff(Teilnehmer teilnehmer_1,Teilnehmer teilnehmer_2){
        return abs(teilnehmer_1.getAge() - teilnehmer_2.getAge_2());
    }

    /**
     * calculate distance of Paerchen
     * @param teilnehmer_1
     * @param teilnehmer_2
     * @return distance of Paerchen with datatyp "double"
     */
    public double calculatePathLength(Teilnehmer teilnehmer_1,Teilnehmer teilnehmer_2){
        double x1 = teilnehmer_1.getKitchen().getKichen_Latitude();
        double y1 = teilnehmer_1.getKitchen().getKitchen_Longitude();
        double x2 = teilnehmer_2.getKitchen().getKichen_Latitude();
        double y2 = teilnehmer_2.getKitchen().getKitchen_Longitude();
        return sqrt(pow(x2-x1,2) + pow((y2-y1),2));
    }

    public String getPairID() {
        return pairID;
    }

    public boolean isTogether() {
        return isTogether;
    }

    public int getAgeDiff() {
        return ageDiff;
    }

    public double getPathLength() {
        return pathLength;
    }
}
