package models;

import java.util.ArrayList;
import java.util.List;

import controller.CRITERIA;
import controller.Calculation;

public class Pair implements Calculation{
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
    public double calculateFoodMatchScore() {
        Participant p1 = this.participant1;
        Participant p2 = this.participant2;

    switch (p1.getFoodPreference()) {
        case meat:
            switch (p2.getFoodPreference()) {
                case meat:
                    return 3;
                case none:
                    return 2;
                case vegan:
                    return 0;
                case veggie:
                    return 1;
            }
        case none:
            switch (p2.getFoodPreference()) {
                case meat:
                    return 1;
                case none:
                    return 3;
                case vegan:
                    return 2;
                case veggie:
                    return 2;
            }
        case vegan:
            switch (p2.getFoodPreference()) {
                case meat:
                    return 0;
                case none:
                    return 2;
                case vegan:
                    return 3;
                case veggie:
                    return 1;
            }
        case veggie:
            switch (p2.getFoodPreference()) {
                case meat:
                    return 1;
                case none:
                    return 2;
                case vegan:
                    return 1;
                case veggie:
                    return 3;
            }
    }

    return 0; // Default case, should not be reached
}

    @Override
    public void show(){
        System.out.println("This is a pair: ");
        System.out.println("Is PreMade: " + isPreMade);
        System.out.println();
        System.out.print("      ");
        System.out.println("First Participant ");
        this.participant1.show();
        System.out.println();
        System.out.print("      ");
        System.out.println("Second Participant ");
        this.participant2.show();
    }
  


    @Override
    public double calculateSexDiversity() {
        double femCount = 0;
        double maleCount = 0;
        double otherCount = 0;
        
        if(participant1.getSex() == SEX.female){
            femCount++;
        } else if(participant1.getSex() == SEX.male){
            maleCount++;
        } else {
        otherCount++;
        }

        if(participant2.getSex() == SEX.female){
            femCount++;
        } else if(participant2.getSex() == SEX.male){
            maleCount++;
        } else {
        otherCount++;
        }
        double totalCount = femCount + maleCount + otherCount;
        double idealRatio = 0.5;
        double femRatio = femCount / totalCount;
        double maleRatio = maleCount / totalCount;
        double otherRatio = otherCount / totalCount;
        
        return Math.abs(femRatio - idealRatio) + Math.abs(maleRatio - idealRatio) + Math.abs(otherRatio - idealRatio);
    }
        
        


    @Override
    public double calculateDistanceBetweenKitchens() {
        Kitchen kitchen1 = participant1.getKitchen();
        Kitchen kitchen2 = participant2.getKitchen();
    
        if(kitchen1 == null || kitchen2 == null) {
            return 0 ;
        }

        double xDiff = kitchen1.getKitchenLatitude() - kitchen2.getKitchenLatitude();
        double yDiff = kitchen1.getKitchenLongitude() - kitchen2.getKitchenLongitude();
        
        return Math.sqrt(Math.pow(xDiff, 2) + Math.pow(yDiff, 2));
    }




    @Override
    public int calculatePairAgeDifference() {
        return Math.abs(participant1.getAge() - participant2.getAge());
    }

    @Override
    public int calculatePreferenceDev() {
        return 0;
    }


    public double calculatePairWeightedScore(){
        double foodMatchScore = calculateFoodMatchScore() * CRITERIA.FOOD_PREFERENCES.getWeight();
        double ageDifferenceScore = calculatePairAgeDifference() * CRITERIA.AGE_DIFFERENCE.getWeight();
        double genderDiversityScore = calculateSexDiversity() * CRITERIA.GENDER_DIVERSITY.getWeight();
        double travelDistanceScore = calculateDistanceBetweenKitchens() * CRITERIA.PATH_LENGTH.getWeight();

    
        return foodMatchScore  + ageDifferenceScore + genderDiversityScore + travelDistanceScore;
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



}
