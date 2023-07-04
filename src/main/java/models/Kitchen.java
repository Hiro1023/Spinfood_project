package models;
import jdk.jshell.execution.Util;
import org.json.JSONObject;
import utility.Utility;

/**
 * The Kitchen class contains all the information about the participant's kitchen
 */
public class Kitchen implements Utility {

    private boolean isEmergencyKitchen;
    private double kitchenStory;
    private double kitchenLongitude;
    private double kitchenLatitude;

    public Kitchen(double kitchenStory, double kitchenLongitude, double kitchenLatitude) {
        this.kitchenStory = kitchenStory;
        this.kitchenLongitude = kitchenLongitude;
        this.kitchenLatitude = kitchenLatitude;
    }

    public Kitchen(){}

    @Override
    public void show() {
        System.out.println("Kitchen Floor: "+this.kitchenStory+", "+"Kitchen kitchenLongitude: "+ this.kitchenLongitude+", "+"Kitchen kitchenLatitude: "+this.kitchenLatitude);

    }

    @Override
    public boolean equal(Object o) {
        return false;
    }

   @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("emergencyKitchen", this.isEmergencyKitchen());
        jsonObject.put("story", this.getKitchenStory());
        jsonObject.put("longitude", this.getKitchenLongitude());
        jsonObject.put("latitude", this.getKitchenLatitude());
        return jsonObject;
    }

    public void setEmergencyKitchen(boolean emergencyKitchen) {
        isEmergencyKitchen = emergencyKitchen;
    }
    public boolean isEmergencyKitchen() {
        return isEmergencyKitchen;
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
