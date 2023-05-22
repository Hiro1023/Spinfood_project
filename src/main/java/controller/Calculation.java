package controller;

public interface Calculation {


//food Score
public double calculateFoodPreference();

//sex diversity
public double calculateSexDiversity();

// path between participants
public double calculateDistanceBetweenKitchens();

// age diff
public int calculatePairAgeDifference();


public void show();

}
