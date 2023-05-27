package models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import controller.CRITERIA;
import controller.Calculation;
import controller.Utility;

public class  Pair implements Calculation, Utility {
    private String Pair_ID;
    private boolean isPreMade;
    private FOOD_PREFERENCE foodPreference;
    private List<Pair> visitedPairs = new ArrayList<>();
    private Participant participant1;
    private Participant participant2;
    private Map<Boolean,Integer> hasCooked; //ex <true,1>

    public Pair(Participant participant1, Participant participant2) {
        this.participant1 = participant1;
        this.participant2 = participant2;
        this.Pair_ID = participant1.getID()+"-"+participant2.getID();
    }

    public void meetPair(Pair pair1,Pair pair2) {
        visitedPairs.add(pair1);
        visitedPairs.add(pair2);
    }

    @Override
    public double calculateFoodPreference() {
        return Math.abs(this.participant1.getFoodPreference().getValue() - this.getParticipant2().getFoodPreference().getValue());
    }

    @Override
    public double calculateSexDiversity() {
        return Math.abs(0.5 - ((double)this.participant1.getSex().getValue() + this.participant2.getSex().getValue())/2);
    }

    @Override
    public double calculateDistanceBetweenKitchens() {
        Kitchen kitchen1 = participant1.getKitchen();
        Kitchen kitchen2 = participant2.getKitchen();

        if (kitchen1 == null || kitchen2 == null) { //should not happen
            return 0.0;
        }
        double EARTH_RADIUS = 6371.0;

        double radLat1 = Math.toRadians(kitchen1.getKitchenLatitude());
        double radLon1 = Math.toRadians(kitchen1.getKitchenLongitude());
        double radLat2 = Math.toRadians(kitchen2.getKitchenLatitude());
        double radLon2 = Math.toRadians(kitchen2.getKitchenLongitude());

        double dlon = radLon2 - radLon1;
        double dlat = radLat2 - radLat1;
        double haversineFormula = Math.pow(Math.sin(dlat / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(dlon / 2), 2);
        double angularDistanceInRadians = 2 * Math.atan2(Math.sqrt(haversineFormula), Math.sqrt(1 - haversineFormula));

        return EARTH_RADIUS * angularDistanceInRadians;
    }


    @Override
    public double calculateDistanceBetweenKitchenAndParty(Double partyLongitude, Double partyLatitude) {
        Kitchen kitchen1 = participant1.getKitchen();
        Kitchen kitchen2 = participant2.getKitchen();
        int EARTH_RADIUS = 6371; // Earth's radius in kilometers
        double lon = 0, lat = 0;

        if (kitchen1 != null) {
            lon = kitchen1.getKitchenLongitude();
            lat = kitchen1.getKitchenLatitude();
        } else if (kitchen2 != null) {
            lon = kitchen2.getKitchenLongitude();
            lat = kitchen2.getKitchenLatitude();
        }

        // Calculate the differences between the latitudes and longitudes
        double latDiff = Math.abs(partyLatitude - lat);
        double lonDiff = Math.abs(partyLongitude - lon);
        // Apply the Haversine formula
        double a = Math.sin(latDiff / 2) * Math.sin(latDiff / 2) +
                Math.cos(partyLatitude) * Math.cos(lat) *
                Math.sin(lonDiff / 2) * Math.sin(lonDiff / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = EARTH_RADIUS * c;

        return distance;
    }

    @Override
    public int calculatePairAgeDifference() {
        return Math.abs(participant1.getAgerange().getValue() - participant2.getAgerange().getValue());
    }

    public double calculatePairWeightedScore(){
        double foodMatchScore = calculateFoodPreference() / CRITERIA.FOOD_PREFERENCES.getWeight();
        double ageDifferenceScore = (double) calculatePairAgeDifference() / CRITERIA.AGE_DIFFERENCE.getWeight();
        double genderDiversityScore = calculateSexDiversity() / CRITERIA.GENDER_DIVERSITY.getWeight();
        double travelDistanceScore = calculateDistanceBetweenKitchens() / CRITERIA.PATH_LENGTH.getWeight();

        double Score =  1/(foodMatchScore + ageDifferenceScore + genderDiversityScore + travelDistanceScore);
        if (Score == Double.POSITIVE_INFINITY) {
            Score = 1000;
        }
        return Score;
    }


    /**
     * Setter
     */
    public void setPreMade(boolean preMade) {
        isPreMade = preMade;
    }

    public void setHasCooked(Map<Boolean,Integer> hasCooked) {
        this.hasCooked = hasCooked;
    }
    public Map<Boolean, Integer> getHasCooked() {
        return hasCooked;
    }
    public String getPairId() {
        return Pair_ID;
    }

    public boolean getIsPreMade() {
        return isPreMade;
    }

    public List<Pair> getVisitedPairs() {
        return visitedPairs;
    }

    public Participant getParticipant1() {
        return participant1;
    }

    public Participant getParticipant2() {
        return participant2;
    }


    @Override
    public void show() {
        System.out.println("This is a pair: "+this.participant1.getName()+" and " + this.participant2.getName());
        /*
        System.out.println("This is a pair: "+this.participant1.getName()+" and " + this.participant2.getName());
        System.out.println();
        System.out.print("      ");
        System.out.println("First Participant ");
        this.participant1.show();
        System.out.println();
        System.out.print("      ");
        System.out.println("Second Participant ");
        this.participant2.show();
         */
    }



    @Override
    public boolean equal(Object o) {

        return false;
    }

    public static void main(String[] args) {

    }
}
