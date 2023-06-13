package models;

/**
 * The Kitchen class contains all the information about the participant's kitchen
 */
public class Kitchen {
    private boolean IsEmergencyKitchen;
    private double kitchenStory;
    private double kitchenLongitude;
    private double kitchenLatitude;

    public Kitchen(double kitchenStory, double kitchenLongitude, double kitchenLatitude,boolean emergency) {
        this.kitchenStory = kitchenStory;
        this.kitchenLongitude = kitchenLongitude;
        this.kitchenLatitude = kitchenLatitude;
        this.IsEmergencyKitchen = emergency;
    }

    public Kitchen(){}

    public String showKitchen(){
        return "Kitchen Floor: "+this.kitchenStory+", "+"Kitchen kitchenLongitude: "+ this.kitchenLongitude+", "+"Kitchen kitchenLatitude: "+this.kitchenLatitude;
    }

    public double getKitchenStory() {
        return kitchenStory;
    }

    public void setKitchenStory(double kitchenStory) {
        this.kitchenStory = kitchenStory;
    }

    public double getKitchenLongitude() {
        return kitchenLongitude;
    }

    public void setKitchenLongitude(double kitchenLongitude) {
        this.kitchenLongitude = kitchenLongitude;
    }

    public double getKitchenLatitude() {
        return kitchenLatitude;
    }

    public void setKitchenLatitude(double kitchenLatitude) {
        this.kitchenLatitude = kitchenLatitude;
    }

    public boolean isEmergencyKitchen() {
        return IsEmergencyKitchen;
    }

    public void setEmergencyKitchen(boolean emergencyKitchen) {
        IsEmergencyKitchen = emergencyKitchen;
    }
}
