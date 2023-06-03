package logic;

/**
 * This is an interface for all calculation
 */
public interface Calculation {

    /**
     * This method calculate a Group or Pair's Deviation of the member's food preferences
     * @return the calculated deviation
     */
    double calculateFoodPreference();

    /**
     * This method calculate the Deviation of the gender diversity of a Group or Pair from 0.5 (Ideal Ratio)
     * @return the calculated deviation
     */
    double calculateSexDiversity();

    /**
     * This method calculate the total distance between participants in a Group or Pair
     * @return the calculated total distance
     */
    double calculatePathLength();

    /**
     * This method calculate the distance between a participant and the party venue
     * @param plon represent the coordinate of the chosen participant
     * @param pla represent the coordinate of the party venue
     * @return the calculated distance
     */
    double calculateDistanceBetweenKitchenAndParty(double plon, double pla);

    /**
     * This method calculate the total age difference between participants in a Group or Pair
     * @return the total calculated age difference
     */
    int calculateAgeDifference();

    /**
     * This method calculate the weighted score in a group or pair
     * @return the calculated score
     */
    double calculateWeightedScore();
}
