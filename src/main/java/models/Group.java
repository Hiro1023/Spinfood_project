package models;
import java.util.ArrayList;
import java.util.List;

import logic.CRITERIA;
import logic.Calculation;
import utility.Utility;

/**
 * The Group class represents a group of pairs in an event
 * It includes methods for calculating weighted scores and displaying group information
 */
public class Group implements Calculation, Utility {
    private List<Pair> Pairs = new ArrayList<>();
    private Pair cookingPair;
    private COURSE course;
    private FOOD_PREFERENCE foodPreference;

    public Group(Pair pair1, Pair pair2, Pair pair3, int course) {
        Pairs.add(pair1);
        Pairs.add(pair2);
        Pairs.add(pair3);
        this.foodPreference = FOOD_PREFERENCE.fromValue(Math.max(pair1.getFoodPreference().getValue(),Math.max(pair2.getFoodPreference().getValue(),pair3.getFoodPreference().getValue())));
        this.course = COURSE.fromValue(course);
    }

    /**
     * This method removes pair from group
     * @param pair the to be removed pair
     */
    public void removePairFromGroup(Pair pair){
        Pairs.remove(pair);
    }

    /**
     * This method calculates food preference score for each pair in the group
     * @return food score for the group
     */
    @Override
    public double calculateFoodPreference() {
        double groupScore = 0;
        for (Pair pair : getPairs()) {
            groupScore += pair.calculateFoodPreference();
        }
        return groupScore/3;
    }


    /**
     * This method calculates sex diversity score for each pair in the group
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
     * This method calculates path length score for each pair in the group
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
     * This method calculates age difference score for each pair in the group
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
     * This methode calculate all the points for each criterion and multiply it with their corresponding weight
     * The higher the weight is, the higher the priority. The final score wil be the sum of all four points
     * @return the total weight score for the whole group
     */
    @Override
    public double calculateWeightedScore(){
        double foodMatchScore = calculateFoodPreference() * CRITERIA.FOOD_PREFERENCES.getWeight();
        double ageDifferenceScore = calculatePathLength() * CRITERIA.PATH_LENGTH.getWeight();
        double genderDiversityScore = calculateSexDiversity() * CRITERIA.GENDER_DIVERSITY.getWeight();
        double travelDistanceScore = (double) calculateAgeDifference() * CRITERIA.AGE_DIFFERENCE.getWeight();
        return foodMatchScore  + ageDifferenceScore + genderDiversityScore + travelDistanceScore;
    }

    /**
     * this method shows all members of the group each pair together
     */
    @Override
    public void show() {
        System.out.println("Group : ");
        System.out.print(this.getPairs().get(0).getParticipant1().getName()+" "+this.getPairs().get(0).getParticipant2().getName());
        System.out.println(this.getPairs().get(0).getHasCooked());
        System.out.print(this.getPairs().get(1).getParticipant1().getName()+" "+this.getPairs().get(1).getParticipant2().getName());
        System.out.println(this.getPairs().get(1).getHasCooked());
        System.out.print(this.getPairs().get(2).getParticipant1().getName()+" "+this.getPairs().get(2).getParticipant2().getName());
        System.out.println(this.getPairs().get(2).getHasCooked());
        for (Pair p : this.getPairs()) {
            System.out.println(p.getParticipant1().getFoodPreference());
            System.out.println(p.getParticipant2().getFoodPreference());
        }
        System.out.println("Main Food Pref : " + this.getFoodPreference());
    }

    /**
     * This method checks if 2 Groups are equal
     * @param o represent the Group that is chosen to compare with
     * @return true if both Groups are equal
     */
    @Override
    public boolean equal(Object o) {
        int count = 0;
        Group g = (Group) o;
        for (Pair p : getPairs()) {
            for (Pair p2 : g.getPairs()) {
                if (p.equal(p2)) {
                    count++;
                }
            }
        }
        return count == 3;
    }

    public List<Pair> getPairs() {
        return Pairs;
    }
    public void setPairs(List<Pair> pairs) {
        Pairs = pairs;
    }

    public Pair getCookingPair() {
        return cookingPair;
    }

    public void setCookingPair(Pair cookingPair) {
        this.cookingPair = cookingPair;
    }

    public COURSE getCourse() {
        return course;
    }

    public void setCourse(COURSE course) {
        this.course = course;
    }

    public FOOD_PREFERENCE getFoodPreference() {
        return foodPreference;
    }

    public void setFoodPreference(FOOD_PREFERENCE foodPreference) {
        this.foodPreference = foodPreference;
    }

 
}