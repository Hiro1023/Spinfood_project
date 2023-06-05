package models;
import java.util.ArrayList;
import java.util.List;

import controller.CRITERIA;
import controller.Calculation;
import controller.Utility;

public class Group implements Calculation, Utility {
    private List<Pair> Pairs = new ArrayList<>();
    private Pair cookingPair;
    private COURSE course;

    public FOOD_PREFERENCE getFoodPreference() {
        return foodPreference;
    }
    public void setFoodPreference(FOOD_PREFERENCE foodPreference) {
        this.foodPreference = foodPreference;
    }

    private FOOD_PREFERENCE foodPreference;

    public Group(Pair pair1, Pair pair2, Pair pair3) {
        Pairs.add(pair1);
        Pairs.add(pair2);
        Pairs.add(pair3);
        //this.foodPreference = findFoodPreferenceGroup(pair1,pair2,pair3);
    }
    public FOOD_PREFERENCE findFoodPreferenceGroup(Pair p1, Pair p2, Pair p3){
        if(p1.getParticipant1().getFoodPreference().equals(FOOD_PREFERENCE.meat)||p2.getFoodPreference().equals(FOOD_PREFERENCE.meat)||p3.getFoodPreference().equals(FOOD_PREFERENCE.meat))
            return  FOOD_PREFERENCE.meat;
        else if (p1.getFoodPreference().equals(FOOD_PREFERENCE.vegan)
                && p2.getFoodPreference().equals(FOOD_PREFERENCE.vegan)
                && p3.getFoodPreference().equals(FOOD_PREFERENCE.vegan))
            return FOOD_PREFERENCE.vegan;
        else if(p1.getFoodPreference().equals(FOOD_PREFERENCE.veggie)
                ||p2.getFoodPreference().equals(FOOD_PREFERENCE.veggie)
                ||p3.getFoodPreference().equals(FOOD_PREFERENCE.veggie))
            return FOOD_PREFERENCE.veggie;
        else
            return FOOD_PREFERENCE.none;

    }

    /**
     * @description : removes pair from group
     * @param pair
     * @return void
     */
    public void removePairFromGroup(Pair pair){
        Pairs.remove(pair);
    }

    /**
     * @description: calculates food prefrence score for each pair in the group
     * @return food score for the group
     */
    @Override
    public double calculateFoodPreference() {
        double groupScore = 0;
        for (Pair pair : this.getPairs()) {
            groupScore += pair.calculateFoodPreference();
        }
        return groupScore/3;
    }


    /**
     * @description: calculates sex diversity score for each pair in the group
     * @return sex diversity score for the group
     */
    @Override
    public double calculateSexDiversity() {
        double totalDiversity = 0;
        for (Pair pair : getPairs()) {
            totalDiversity += pair.calculateSexDiversity();
        }
        return totalDiversity;
    }


    /**
     * @description: calculates path length score for each pair in the group
     * @return path score for the group
     */
    @Override
    public double calculatePathLength() {
        double totalDistance = 0;
        for(Pair pair : getPairs()){
            totalDistance += pair.calculatePathLength();
        }
        return totalDistance;
    }


    @Override
    public double calculateDistanceBetweenKitchenAndParty(double p1,double p2) {
        return 0;
    }

    /**
     * @description: calculates age difference score for each pair in the group
     * @return age diff score for the group
     */
    @Override
    public int calculateAgeDifference() {
        int totalAgeDiff = 0;
        for(Pair pair : getPairs()){
            totalAgeDiff += pair.calculateAgeDifference();
        }
        return totalAgeDiff/3;
    }

    /**
     * @description: this shows all members of the group each pair togather
     */
    @Override
    public void show() {
        System.out.println("Group : ");
        //this.getPairs().forEach(x -> x.show());
        System.out.print(this.getPairs().get(0).getParticipant1().getName()+" "+this.getPairs().get(0).getParticipant2().getName());
        System.out.println(this.getPairs().get(0).getHasCooked());
        System.out.print(this.getPairs().get(1).getParticipant1().getName()+" "+this.getPairs().get(1).getParticipant2().getName());
        System.out.println(this.getPairs().get(1).getHasCooked());
        System.out.print(this.getPairs().get(2).getParticipant1().getName()+" "+this.getPairs().get(2).getParticipant2().getName());
        System.out.println(this.getPairs().get(2).getHasCooked());
        //System.out.println(this.calculateGroupWeightedScore());

    }

    @Override
    public boolean equal(Object o) {
        return false;
    }

    /**
     * @description: This methode calculate all the points for each criteria and multiply it with their corresponding weight
     *               The higher the weight is, the higher the priority. The final score wil be the sum of all four points
     * @return the total weight score for the whole group
     */
    public double calculateGroupWeightedScore(){
        double foodMatchScore = calculateFoodPreference() * CRITERIA.FOOD_PREFERENCES.getWeight();
        double ageDifferenceScore = calculatePathLength() * CRITERIA.PATH_LENGTH.getWeight();
        double genderDiversityScore = calculateSexDiversity() * CRITERIA.GENDER_DIVERSITY.getWeight();
        double travelDistanceScore = (double) calculateAgeDifference() * CRITERIA.AGE_DIFFERENCE.getWeight();
        double score =  foodMatchScore  + ageDifferenceScore + genderDiversityScore + travelDistanceScore;

        return score;
    }

    public List<Pair> getPairs() {
        return Pairs;
    }

    public static void main(String[] args) {

    }

 
}