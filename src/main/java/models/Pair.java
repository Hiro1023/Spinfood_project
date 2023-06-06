package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import logic.CRITERIA;
import logic.Calculation;
import utility.Utility;

/**
 * The Pair class represents a pair of Participants in an event
 * It includes methods for calculating weighted scores and displaying pair information
 */
public class  Pair implements Calculation, Utility {
    private final String Pair_ID;
    private boolean isPreMade;
    private final FOOD_PREFERENCE foodPreference;
    private List<Pair> visitedPairs = new ArrayList<>();
    private final Participant participant1;
    private final Participant participant2;
    private Map<Boolean, Integer> hasCooked; //ex <true,1>

    public Pair(Participant participant1, Participant participant2) {
        this.participant1 = participant1;
        this.participant2 = participant2;
        this.Pair_ID = participant1.getID() + "-" + participant2.getID();
        hasCooked = new HashMap<>();
        this.foodPreference = FOOD_PREFERENCE.fromValue(Math.max(participant1.getFoodPreference().getValue(),participant2.getFoodPreference().getValue()));
    }

    /**
     * This method adds a pair to the visited list
     * @param pair1 first met pair
     * @param pair2 second met pair
     */
    public void meetPair(Pair pair1, Pair pair2) {
        visitedPairs.add(pair1);
        visitedPairs.add(pair2);
    }

    /**
     * This method absolute value of the difference between the food preference between 2 participants
     * @return food preference score
     */
    @Override
    public double calculateFoodPreference() {
        return Math.abs(this.participant1.getFoodPreference().getValue() - this.getParticipant2().getFoodPreference().getValue());
    }

    /**
     * This method calculates the sex diversity between 2 participants having 0.5 as ideal
     * @return the calculated deviation
     */
    @Override
    public double calculateSexDiversity() {
        return Math.abs(0.5 - ((double) this.participant1.getSex().getValue() + this.participant2.getSex().getValue()) / 2);
    }

    /**
     * This method calculates Path length between 2 kitchens using Haversine Formula
     * @return distance between the 2 kitchens
     */
    @Override
    public double calculatePathLength() {
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


    /**
     * This method uses haversine formula to calculate the distance between each kitchen of the participants and the party location
     * @param partyLongitude the Longitude value of the party's coordination
     * @param partyLatitude the Latitude value of the party's coordination
     * @return distance between the kitchens to the party
     */
    public double calculateDistanceBetweenKitchenAndParty(double partyLongitude, double partyLatitude) {
        if (hasCooked.containsKey(true)) {//when the group has already cooked
            return 0.0;
        }
        Kitchen kitchen1 = participant1.getKitchen();
        Kitchen kitchen2 = participant2.getKitchen();
        int EARTH_RADIUS = 6371; // Earth's radius in kilometers
        double lon, lat;
        if (kitchen1 != null) {
            lon = kitchen1.getKitchenLongitude();
            lat = kitchen1.getKitchenLatitude();
        }else{
            lon = kitchen2.getKitchenLongitude();
            lat = kitchen2.getKitchenLatitude();
        }
        // Convert latitude and longitude to radians
        double lat1 = Math.toRadians(lat);
        double lon1 = Math.toRadians(lon);
        double lat2 = Math.toRadians(partyLatitude);
        double lon2 = Math.toRadians(partyLongitude);

        // Calculate the differences between the latitudes and longitudes
        double latDiff = Math.abs(lat2 - lat1);
        double lonDiff = Math.abs(lon2 - lon1);

        // Apply the Haversine formula
        double a = Math.sin(latDiff / 2) * Math.sin(latDiff / 2) +
                Math.cos(lat1) * Math.cos(lat2) *
                        Math.sin(lonDiff / 2) * Math.sin(lonDiff / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = EARTH_RADIUS * c;

        return Math.abs(distance);
    }

    /**
     * This method calculates the absolute value for age diff
     * @return the calculated age difference
     */
    @Override
    public int calculateAgeDifference() {
        return Math.abs(participant1.getAgerange().getValue() - participant2.getAgerange().getValue());
    }

    /**
     * This method calculate all the points for each criterion and divide it with their corresponding weight.
     * The final score wil be one divided by the sum of all four points.
     * @return the total weight score for the whole pair
     */
    @Override
    public double calculateWeightedScore() {
        double foodMatchScore = calculateFoodPreference() / CRITERIA.FOOD_PREFERENCES.getWeight();
        double ageDifferenceScore = (double) calculateAgeDifference() / CRITERIA.AGE_DIFFERENCE.getWeight();
        double genderDiversityScore = calculateSexDiversity() / CRITERIA.GENDER_DIVERSITY.getWeight();
        double travelDistanceScore = calculatePathLength() / CRITERIA.PATH_LENGTH.getWeight();

        double Score = 1 / (foodMatchScore + ageDifferenceScore + genderDiversityScore + travelDistanceScore);
        if (Score == Double.POSITIVE_INFINITY)
            Score = 1000;
        return Score;
    }

    @Override
    public void show() {
        System.out.println("This is a pair: " + this.participant1.getName() + " " + this.participant2.getName());
        System.out.println(getFoodPreference() + " p1 :" + participant1.getFoodPreference() + " p2 :" + participant2.getFoodPreference());
    }

    /**
     * This method compares 2 pairs to check if they are equal
     * @param o represent the Pair is chosen to compare with
     * @return true if both pairs are equal
     */
    @Override
    public boolean equal(Object o) {
        Pair p = (Pair) o;
        return Pair_ID.equals(p.Pair_ID);
    }

    /**
     * Setter
     */
    public void setPreMade(boolean preMade) {
        isPreMade = preMade;
    }

    public void setHasCooked(Boolean b, int i) {
        this.hasCooked.put(b, i);
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

    public FOOD_PREFERENCE getFoodPreference() {
        return foodPreference;
    }
}
