package modelsWrapper;

import com.fasterxml.jackson.annotation.JsonProperty;
import models.Pair;
import models.PairSuccessorList;
import models.Participant;
import models.ParticipantSuccessorList;
import utility.MapUtility;

import java.util.List;

public class PairSuccessorListWrapper implements MapUtility {

    @JsonProperty("participantSuccessorList")
    private List<PairWrapper> pairSuccessorList;

    public PairSuccessorListWrapper(PairSuccessorList pl) {
        this.pairSuccessorList = mapPairList(pl.getPairSuccessorList());
    }

    @Override
    public FOOD_PREFERENCEWrapper mapFoodPreference(Object o) {
        return null;
    }

    @Override
    public List<ParticipantWrapper> mapParticipantList(Object o) {
        return null;
    }

    @Override
    public List<PairWrapper> mapPairList(Object o) {
        List<Pair> pl = (List<Pair>) o;
        java.util.List<PairWrapper> wrapperList = new java.util.ArrayList<>();
        for (Pair p : pl) {
            wrapperList.add(new PairWrapper(p));
        }
        return wrapperList;
    }

    @Override
    public List<GroupWrapper> mapGroupList(Object o) {
        return null;
    }
}
