package controller;

public interface Calculation {


//food Score
public double calculateFoodPreference();

//sex diversity
public double calculateSexDiversity();

// path between participants
public double calculateDistanceBetweenKitchens();


public double calculateDistanceBetweenKitchenAndParty(double plon, double pla);

    // age diff
public int calculatePairAgeDifference();


public void show();

}
