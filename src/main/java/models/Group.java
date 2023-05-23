package models;
import java.util.ArrayList;
import java.util.List;

import controller.CRITERIA;
import controller.Calculation;

public class Group implements Calculation{
    private List<Pair> Pairs = new ArrayList<>();
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
    public double calculateFoodPreference() {
        int groupScore = 0;
        for (Pair pair : getPairs()) {
            groupScore += pair.calculateFoodPreference();
        }
        return groupScore/3;
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
        double totalDistance = 0;
        for(Pair pair : getPairs()){
            totalDistance += pair.calculateDistanceBetweenKitchens();
        }
        return totalDistance;
    }

    @Override
    public int calculatePairAgeDifference() {
        int totalAgeDiff = 0;
        for(Pair pair : getPairs()){
            totalAgeDiff += pair.calculatePairAgeDifference();
        }
        return totalAgeDiff/3;
    }

    @Override
    public void show() {
        System.out.println("Group Pairs : ");
        this.getPairs().forEach(x -> x.show());
        System.out.println(this.calculateGroupWeightedScore());
        System.out.println("----------------------------------------");
    }


    public double calculateGroupWeightedScore(){
        double foodMatchScore = calculateFoodPreference() / CRITERIA.FOOD_PREFERENCES.getWeight();
        double ageDifferenceScore = calculateDistanceBetweenKitchens() / CRITERIA.PATH_LENGTH.getWeight();
        double genderDiversityScore = calculateSexDiversity() / CRITERIA.GENDER_DIVERSITY.getWeight();
        double travelDistanceScore = (double) calculatePairAgeDifference() / CRITERIA.AGE_DIFFERENCE.getWeight();

    
        return 1/(foodMatchScore  + ageDifferenceScore + genderDiversityScore + travelDistanceScore);
    }
    

    public List<Pair> getPairs() {
        return Pairs;
    }

 
}