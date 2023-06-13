package modelsWrapper;

import com.fasterxml.jackson.annotation.JsonProperty;
import models.Participant;
import models.ParticipantSuccessorList;
import utility.MapUtility;

import java.util.List;

public class ParticipantSuccessorListWrapper implements MapUtility {

    @JsonProperty("participantSuccessorList")
    private List<ParticipantWrapper> participantSuccessorList;

    public ParticipantSuccessorListWrapper(ParticipantSuccessorList pl) {
        this.participantSuccessorList = mapParticipantList(pl.getParticipantSuccessorList());
    }

    @Override
    public FOOD_PREFERENCEWrapper mapFoodPreference(Object o) {
        return null;
    }

    @Override
    public List<ParticipantWrapper> mapParticipantList(Object o) {
        List<Participant> pl = (List<Participant>) o;
        java.util.List<ParticipantWrapper> wrapperList = new java.util.ArrayList<>();
        for (Participant p : pl) {
            wrapperList.add(new ParticipantWrapper(p));
        }
        return wrapperList;
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
