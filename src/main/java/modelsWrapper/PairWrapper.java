package modelsWrapper;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import models.*;
import utility.MapUtility;

import java.util.List;

@JsonPropertyOrder({
        "pairId",
        "isPreMade",
        "foodPreference",
        "visitedPairs",
        "participant1",
        "participant2",
        "hasCooked",
        "WeightedScore"
})
public class PairWrapper implements MapUtility {
    @JsonProperty("pairId")
    private String pairId;

    @JsonProperty("isPreMade")
    private boolean isPreMade;

    @JsonProperty("foodPreference")
    private FOOD_PREFERENCEWrapper foodPreference;

    @JsonProperty("visitedPairs")
    private java.util.List<PairWrapper> visitedPairs;

    @JsonProperty("participant1")
    private ParticipantWrapper participant1;

    @JsonProperty("participant2")
    private ParticipantWrapper participant2;

    @JsonProperty("hasCooked")
    private java.util.Map<Boolean, Integer> hasCooked;

    @JsonProperty("WeightedScore")
    private double WeightedScore;

    public PairWrapper(Pair pair) {
        this.pairId = pair.getPairId();
        this.isPreMade = pair.getIsPreMade();
        this.foodPreference = mapFoodPreference((Object) pair.getFoodPreference());
        this.visitedPairs = mapPairList(pair.getVisitedPairs());
        this.participant1 = new ParticipantWrapper(pair.getParticipant1());
        this.participant2 = new ParticipantWrapper(pair.getParticipant2());
        this.hasCooked = pair.getHasCooked();
        this.WeightedScore = pair.calculateWeightedScore();
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
        List<Pair> pl = (List<Pair>) o;
        java.util.List<PairWrapper> wrapperList = new java.util.ArrayList<>();
        for (Pair pair : pl) {
            wrapperList.add(new PairWrapper(pair));
        }
        return wrapperList;
    }

    @Override
    public List<GroupWrapper> mapGroupList(Object o) {
        return null;
    }
}