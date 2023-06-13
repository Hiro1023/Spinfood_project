package modelsWrapper;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import models.COURSE;
import models.FOOD_PREFERENCE;
import models.Group;
import models.Pair;
import utility.MapUtility;

import java.util.ArrayList;
import java.util.List;

@JsonPropertyOrder({ "pairs", "cookingPair", "course", "foodPreference" })
public class GroupWrapper implements MapUtility {
    @JsonProperty("pairs")
    private List<PairWrapper> Pairs;

    @JsonProperty("cookingPair")
    private PairWrapper cookingPair;

    @JsonProperty("course")
    private COURSEWrapper course;

    @JsonProperty("foodPreference")
    private FOOD_PREFERENCEWrapper foodPreference;

    public GroupWrapper(Group group) {
        this.Pairs = mapPairList(group.getPairs());
        this.cookingPair = (group.getCookingPair() == null)? null:new PairWrapper(group.getCookingPair());
        this.course = (group.getCourse() == null)? null:mapCourse(group.getCourse());
        this.foodPreference = mapFoodPreference(group.getFoodPreference());
    }

    private COURSEWrapper mapCourse(COURSE course) {
        COURSEWrapper cw = null;
        switch (course) {
            case Starter:
                cw = COURSEWrapper.Starter;
                break;
            case mainCourse:
                cw = COURSEWrapper.mainCourse;
                break;
            case Dessert:
                cw = COURSEWrapper.Dessert;
                break;
        }
        return cw;
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
        List<Pair> Pairs = (List<Pair>) o;
        java.util.List<PairWrapper> wrapperList = new java.util.ArrayList<>();
        for (Pair pair : Pairs) {
            wrapperList.add(new PairWrapper(pair));
        }
        return wrapperList;
    }

    @Override
    public List<GroupWrapper> mapGroupList(Object o) {
        return null;
    }
}