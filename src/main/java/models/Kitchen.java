package models;

public class Kitchen {
    private boolean avaliablity;
    private String kitchen;
    private double kitchenStory;
    private double kitchenLongitude;
    private double kitchenLatitude;

    public Kitchen(String kitchen, double kitchenStory, double kitchenLongitude, double kitchenLatitude, boolean avalibility) {
        this.kitchen = kitchen;
        this.kitchenStory = kitchenStory;
        this.kitchenLatitude = kitchenLatitude;
        this.kitchenLongitude = kitchenLongitude;
        this.avaliablity = avalibility;
    }
    
    public String getKitchen() {
        return kitchen;
    }

    public void setKitchen(String kitchen) {
        this.kitchen = kitchen;
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

    public boolean getAvaliablity() {
        return avaliablity;
    }
}
