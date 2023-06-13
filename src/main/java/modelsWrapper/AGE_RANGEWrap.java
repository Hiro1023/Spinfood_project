package modelsWrapper;
import com.fasterxml.jackson.annotation.JsonProperty;

public enum AGE_RANGEWrap {
    @JsonProperty("LessThan18")
    LessThan18,
    @JsonProperty("LessThan24")
    LessThan24,
    @JsonProperty("LessThan28")
    LessThan28,
    @JsonProperty("LessThan31")
    LessThan31,
    @JsonProperty("LessThan36")
    LessThan36,
    @JsonProperty("LessThan42")
    LessThan42,
    @JsonProperty("LessThan47")
    LessThan47,
    @JsonProperty("LessThan57")
    LessThan57,
    @JsonProperty("MoreThan56")
    MoreThan56;
}
