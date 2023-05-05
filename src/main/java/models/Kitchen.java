package models;//package models;

public class Kitchen {
    private double kitchenStory;
    private double kitchenLongitude;
    private double kitchenLatitude;

    public Kitchen(double kitchenStory, double kitchenLongitude, double kitchenLatitude) {
        this.kitchenStory = kitchenStory;
        this.kitchenLatitude = kitchenLatitude;
        this.kitchenLongitude = kitchenLongitude;
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
}
