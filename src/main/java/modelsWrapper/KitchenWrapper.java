package modelsWrapper;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import models.Kitchen;

@JsonPropertyOrder({"kitchenStory", "kitchenLongitude", "kitchenLatitude","isEmergencyKitchen"})
public class KitchenWrapper {
    @JsonProperty("kitchenStory")
    private double kitchenStory;

    @JsonProperty("kitchenLongitude")
    private double kitchenLongitude;

    @JsonProperty("kitchenLatitude")
    private double kitchenLatitude;
    @JsonProperty("isEmergencyKitchen")
    private boolean isEmergencyKitchen;

    public KitchenWrapper(Kitchen kitchen) {
        this.kitchenStory = kitchen.getKitchenStory();
        this.kitchenLongitude = kitchen.getKitchenLongitude();
        this.kitchenLatitude = kitchen.getKitchenLatitude();
        this.isEmergencyKitchen = kitchen.isEmergencyKitchen();
    }
}
