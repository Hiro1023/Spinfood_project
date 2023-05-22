package models;

import java.util.ArrayList;
import java.util.List;

import controller.CRITERIA;
import controller.Calculation;
import controller.Utility;

public class  Pair implements Calculation, Utility {
    private String Pair_ID;

    public void setPreMade(boolean preMade) {
        isPreMade = preMade;
    }

    private boolean isPreMade;
    private FOOD_PREFERENCE foodPreference;
    private List<Pair> visitedPairs = new ArrayList<>();
    private Participant participant1;
    private Participant participant2;



    public Pair(Participant participant1, Participant participant2) {
        this.participant1 = participant1;
        this.participant2 = participant2;
        this.Pair_ID = participant1.getID()+"-"+participant2.getID();
    }

    
    public void meetPair(Pair pair) {
        visitedPairs.add(pair);
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

        if (kitchen1 == null || kitchen2 == null) {
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
    




      
    public String getPairId() {
        return Pair_ID;
    }

    public boolean isPreMade() {
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
        System.out.println(getParticipant1().getName() + " " + getParticipant2().getName());
    }

    @Override
    public boolean equal(Object o) {
        return false;
    }
}
