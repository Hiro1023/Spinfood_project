package modelsWrapper;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import models.*;

@JsonPropertyOrder({"meat","vegan","veggie","none"})
public enum FOOD_PREFERENCEWrapper {
    @JsonProperty("meat")
    meat,
    @JsonProperty("vegan")
    vegan,
    @JsonProperty("veggie")
    veggie,
    @JsonProperty("none")
    none;
}
