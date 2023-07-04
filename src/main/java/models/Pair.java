package models;

import java.util.*;

import logic.CRITERIA;
import logic.Calculation;
import org.json.JSONObject;
import utility.Utility;

/**
 * The Pair class represents a pair of Participants in an event
 * It includes methods for calculating weighted scores and displaying pair information
 */
public class  Pair implements Calculation, Utility {
    private  String Pair_ID;
    private boolean isPreMade;
    private FOOD_PREFERENCE foodPreference;
    private List<Pair> visitedPairs = new ArrayList<>();
    private  Participant participant1;
    private  Participant participant2;
    private EnumMap<COURSE , Boolean> hasCooked;
    private Kitchen pairKitchen;

    public Pair(Participant participant1, Participant participant2) {
        this.participant1 = participant1;
        this.participant2 = participant2;
        this.Pair_ID = participant1.getID() + "-" + participant2.getID();
        hasCooked = new EnumMap<>(COURSE.class);
        this.foodPreference = findFoodPreference(participant1,participant2);
        this.pairKitchen = (participant1.getKitchen()==null)? participant2.getKitchen():participant1.getKitchen();
    }

    public FOOD_PREFERENCE findFoodPreference(Participant p1, Participant p2){

        if(p1.getFoodPreference().equals(FOOD_PREFERENCE.none))
            return p2.getFoodPreference();
        if(p2.getFoodPreference().equals(FOOD_PREFERENCE.none))
            return p1.getFoodPreference();
        if(p1.getFoodPreference().equals(FOOD_PREFERENCE.meat) || p2.getFoodPreference().equals(FOOD_PREFERENCE.meat))
            return FOOD_PREFERENCE.meat;
        if(p1.getFoodPreference().equals(FOOD_PREFERENCE.vegan) || p2.getFoodPreference().equals(FOOD_PREFERENCE.vegan))
            return FOOD_PREFERENCE.vegan;
        else
            return FOOD_PREFERENCE.veggie;
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
     * This method calculates the sex diversity between 2 participants having 0.0 as ideal
     * @return the calculated deviation
     */
    @Override
    public double calculateSexDiversity() {
        List<SEX> pairSEX = new ArrayList<>();
        pairSEX.add(participant1.getSex());
        pairSEX.add(participant2.getSex());
        Set<SEX> uniqueGenders = new HashSet<>(pairSEX);
        return Math.abs(2 - uniqueGenders.size());
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

        double distLon = radLon2 - radLon1;
        double distLat = radLat2 - radLat1;
        double haversineFormula = Math.pow(Math.sin(distLat / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(distLon / 2), 2);
        double angularDistanceInRadians = 2 * Math.atan2(Math.sqrt(haversineFormula), Math.sqrt(1 - haversineFormula));

        return EARTH_RADIUS * angularDistanceInRadians;
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("premade", this.isPreMade);
        jsonObject.put("foodPreference", this.getFoodPreference().toString());
        jsonObject.put("firstParticipant", this.getParticipant1().toJson());
        jsonObject.put("secondParticipant", this.getParticipant2().toJson());
        return jsonObject;
    }

    /**
     * This method uses haversine formula to calculate the distance between each kitchen of the participants and the party location
     * @param partyLongitude the Longitude value of the party's coordination
     * @param partyLatitude the Latitude value of the party's coordination
     * @return distance between the kitchens to the party
     */
    public double calculateDistanceBetweenKitchenAndParty(double partyLongitude, double partyLatitude) {
        if (hasCooked.containsValue(true)) {//when the group has already cooked
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
     *
     * @return the calculated age difference
     */
    @Override
    public double calculateAgeDifference() {
        return Math.abs(participant1.getAge() - participant2.getAge());
    }

    /**
     * This method calculate all the points for each criterion and divide it with their corresponding weight.
     * The final score wil be one divided by the sum of all four points.
     * @return the total weight score for the whole pair
     */
    @Override
    public double calculateWeightedScore() {
        double foodMatchScore = calculateFoodPreference() * CRITERIA.FOOD_PREFERENCES.getWeight();
        double ageDifferenceScore = calculateAgeDifference() * CRITERIA.AGE_DIFFERENCE.getWeight();
        double genderDiversityScore = calculateSexDiversity() * CRITERIA.GENDER_DIVERSITY.getWeight();
        double travelDistanceScore = calculatePathLength() * CRITERIA.PATH_LENGTH.getWeight();

        if (CRITERIA.getMaxCriteria() != CRITERIA.OPTIMAL) {
            CRITERIA.OPTIMAL.setWeight(1);
        }

        return (foodMatchScore + ageDifferenceScore + genderDiversityScore + travelDistanceScore) * CRITERIA.OPTIMAL.getWeight();
    }

    @Override
    public void show() {
        System.out.println(this.participant1.getName() + " " + this.participant2.getName() + " " + this.foodPreference + this.getHasCooked().toString());
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

    public void setFoodPreference(FOOD_PREFERENCE foodPreference) {
        this.foodPreference = foodPreference;
    }

    public void setHasCooked(int course) {
        switch (course) {
            case 1:
                this.hasCooked.put(COURSE.Starter, true);
                break;
            case 2:
                this.hasCooked.put(COURSE.mainCourse, true);
                break;
            case 3:
                this.hasCooked.put(COURSE.Dessert, true);
                break;
        }
    }

    public void clearHasCooked() {
        this.hasCooked = new EnumMap<>(COURSE.class);
    }

    public EnumMap<COURSE, Boolean> getHasCooked() {
        return this.hasCooked;
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
    public Kitchen getPairKitchen() {
        return pairKitchen;
    }

    public void setPairKitchen(Kitchen kitchen) {
        this.pairKitchen = kitchen;
    }
}
