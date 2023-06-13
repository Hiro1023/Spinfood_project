package modelsWrapper;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import models.*;
import utility.MapUtility;

import java.util.List;

@JsonPropertyOrder({"ID", "name", "foodPreference", "age", "sex", "kitchen","ageRange","kitchenCount"})
public class ParticipantWrapper implements MapUtility {

    @JsonProperty("ID")
    private String ID;
    @JsonProperty("name")
    private String name;
    @JsonProperty("foodPreference")
    private FOOD_PREFERENCEWrapper foodPreference;
    @JsonProperty("age")
    private int age;
    @JsonProperty("sex")
    private SEXWrapper sex;
    @JsonProperty("kitchen")
    private KitchenWrapper kitchen;
    @JsonProperty("ageRange")
    private AGE_RANGEWrap ageRange;
    @JsonProperty("KitchenCount")
    private int KitchenCount;

    public ParticipantWrapper(Participant participant) {
        this.ID = participant.getID();
        this.name = participant.getName();
        this.foodPreference = mapFoodPreference(participant.getFoodPreference());
        this.age = participant.getAge();
        this.sex = mapSex(participant.getSex());
        this.kitchen = (participant.getKitchen() == null)? null:new KitchenWrapper(participant.getKitchen());
        this.ageRange = mapAgeRange(participant.getAgeRange());
        this.KitchenCount = participant.getKitchenCount();
    }

    private AGE_RANGEWrap mapAgeRange(AGE_RANGE ageRange) {
        AGE_RANGEWrap ageRangeW = null;
        switch (ageRange) {
            case LessThan18:
                ageRangeW = AGE_RANGEWrap.LessThan18;
                break;
            case LessThan24:
                ageRangeW = AGE_RANGEWrap.LessThan24;
                break;
            case LessThan28:
                ageRangeW = AGE_RANGEWrap.LessThan28;
                break;
            case LessThan31:
                ageRangeW = AGE_RANGEWrap.LessThan31;
                break;
            case LessThan36:
                ageRangeW = AGE_RANGEWrap.LessThan36;
                break;
            case LessThan42:
                ageRangeW = AGE_RANGEWrap.LessThan42;
                break;
            case LessThan47:
                ageRangeW = AGE_RANGEWrap.LessThan47;
                break;
            case LessThan57:
                ageRangeW = AGE_RANGEWrap.LessThan57;
                break;
            case MoreThan56:
                ageRangeW = AGE_RANGEWrap.MoreThan56;
                break;
        }
        return ageRangeW;
    }

    private SEXWrapper mapSex(SEX sex) {
        SEXWrapper sexW = null;
        switch (sex) {
            case male:
                sexW = SEXWrapper.male;
                break;
            case female:
                sexW = SEXWrapper.female;
                break;
            case other:
                sexW = SEXWrapper.other;
                break;
        }
        return sexW;
    }

    @Override
    public FOOD_PREFERENCEWrapper mapFoodPreference(Object o) {
        FOOD_PREFERENCE foodPreference = (FOOD_PREFERENCE) o;
        FOOD_PREFERENCEWrapper foodPref = null;
        switch (foodPreference) {
            case meat:
                foodPref = FOOD_PREFERENCEWrapper.meat;
                break;
            case vegan:
                foodPref = FOOD_PREFERENCEWrapper.vegan;
                break;
            case veggie:
                foodPref = FOOD_PREFERENCEWrapper.veggie;
                break;
            case none:
                foodPref = FOOD_PREFERENCEWrapper.none;
                break;
        }
        return foodPref;
    }

    @Override
    public List<ParticipantWrapper> mapParticipantList(Object o) {
        return null;
    }

    @Override
    public List<PairWrapper> mapPairList(Object o) {
        return null;
    }

    @Override
    public List<GroupWrapper> mapGroupList(Object o) {
        return null;
    }
}