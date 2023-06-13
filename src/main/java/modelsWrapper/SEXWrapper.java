package modelsWrapper;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"male","female","other"})
public enum SEXWrapper {
    @JsonProperty("male")
    male,
    @JsonProperty("female")
    female,
    @JsonProperty("other")
    other
}
