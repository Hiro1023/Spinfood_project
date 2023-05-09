package controller;

public interface Calculation {


//food Score
public double calculateFoodMatchScore();

//sex diversity
public double calculateSexDiversity();

// path between participants
public double calculateDistanceBetweenKitchens();

// age diff
public int calculatePairAgeDifference();

}
