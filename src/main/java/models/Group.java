package models;
import java.util.ArrayList;
import java.util.List;

import controller.CRITERIA;
import controller.Calculation;

public class Group implements Calculation{
    private List<Pair> Pairs = new ArrayList<>();
    private List<Pair> MetPairs = new ArrayList<>();
    private Pair cookingPair;
    private COURSE course;
    private FOOD_PREFERENCE foodPreference;

    public Group(Pair pair1, Pair pair2, Pair pair3) {
        Pairs.add(pair1);
        Pairs.add(pair2);
        Pairs.add(pair3);
    }

    public void removePairFromGroup(Pair pair){
        Pairs.remove(pair);
    }

    @Override
    public double calculateFoodMatchScore() {
        int groupScore = 0;
        for (Pair pair : getPairs()) {
            groupScore += pair.calculateFoodMatchScore();
        }
        return groupScore;
    }
    

    @Override
    public double calculateSexDiversity() {
        double totalDiversity = 0;
        for (Pair pair : getPairs()) {
            totalDiversity += pair.calculateSexDiversity();
        }
        return totalDiversity;
    }
    

    @Override
    public double calculateDistanceBetweenKitchens() {
        double totalAgeDiff = 0;
        for(Pair pair : getPairs()){
            totalAgeDiff += pair.calculateDistanceBetweenKitchens();
        }
        return totalAgeDiff;
    }

    @Override
    public int calculatePairAgeDifference() {
        int totalDistance = 0;
        for(Pair pair : getPairs()){
            totalDistance += pair.calculatePairAgeDifference();
        }
        return totalDistance;
    }

    @Override
    public int calculatePreferenceDev() {
        return 0;
    }


    public double calculateGroupWeightedScore(){
        double foodMatchScore = calculateFoodMatchScore() * CRITERIA.FOOD_PREFERENCES.getWeight();
        double ageDifferenceScore = calculateDistanceBetweenKitchens() * CRITERIA.AGE_DIFFERENCE.getWeight();
        double genderDiversityScore = calculateSexDiversity() * CRITERIA.GENDER_DIVERSITY.getWeight();
        double travelDistanceScore = calculatePairAgeDifference() * CRITERIA.PATH_LENGTH.getWeight();

    
        return foodMatchScore  +
               ageDifferenceScore + genderDiversityScore + travelDistanceScore;
    }
    

    public List<Pair> getPairs() {
        return Pairs;
    }

 
}