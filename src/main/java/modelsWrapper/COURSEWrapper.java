package modelsWrapper;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"Starter","mainCourse","Dessert"})
public enum COURSEWrapper {
    @JsonProperty("Starter")
    Starter,
    @JsonProperty("mainCourse")
    mainCourse,
    @JsonProperty("Dessert")
    Dessert
}
