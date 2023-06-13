package logicWrapper;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import logic.DataList;
import models.Event;
import models.Group;
import models.Pair;
import models.Participant;
import modelsWrapper.*;
import utility.MapUtility;
import java.util.List;

@JsonPropertyOrder({ "participantList", "pairList", "groupListCourse01",
        "groupListCourse02", "groupListCourse03", "participantSuccessorList", "pairSuccessorList"})
public class DataListWrapper implements MapUtility {
    @JsonProperty("participantList")
    private List<ParticipantWrapper> participantList;
    @JsonProperty("pairList")
    private List<PairWrapper> pairList;
    @JsonProperty("groupListCourse01")
    private List<GroupWrapper> groupListCourse01;
    @JsonProperty("groupListCourse02")
    private List<GroupWrapper> groupListCourse02;
    @JsonProperty("groupListCourse03")
    private List<GroupWrapper> groupListCourse03;
    @JsonProperty("participantSuccessorList")
    private ParticipantSuccessorListWrapper participantSuccessorList;
    @JsonProperty("pairSuccessorList")
    private PairSuccessorListWrapper pairSuccessorList;

    public DataListWrapper(DataList dataList) {
        this.participantList = mapParticipantList(dataList.getParticipantList());
        this.pairList = mapPairList(dataList.getPairList());
        this.groupListCourse01 = mapGroupList(dataList.getGroupListCourse01());
        this.groupListCourse02 = mapGroupList(dataList.getGroupListCourse02());
        this.groupListCourse03 = mapGroupList(dataList.getGroupListCourse03());
        this.participantSuccessorList = new ParticipantSuccessorListWrapper(dataList.getParticipantSuccessorList());
        this.pairSuccessorList = new PairSuccessorListWrapper(dataList.getPairSuccessorList());
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
        List<Pair> Pairs = (List<Pair>) o;
        java.util.List<PairWrapper> wrapperList = new java.util.ArrayList<>();
        for (Pair pair : Pairs) {
            wrapperList.add(new PairWrapper(pair));
        }
        return wrapperList;
    }

    @Override
    public List<GroupWrapper> mapGroupList(Object o) {
        List<Group> gl = (List<Group>) o;
        java.util.List<GroupWrapper> wrapperList = new java.util.ArrayList<>();
        for (Group g : gl) {
            wrapperList.add(new GroupWrapper(g));
        }
        return wrapperList;
    }
}
