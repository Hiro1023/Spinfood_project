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

import java.sql.Wrapper;
import java.util.ArrayList;
import java.util.HashMap;
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

    public List<HashMap<?,Double>> makeTable() {
        HashMap<PairWrapper,Double> ht = new HashMap<>();
        for (PairWrapper p : this.getPairList()) {
            ht.put(p,p.getWeightedScore());
        }
        List<HashMap<?,Double>> res = new ArrayList<>();
        res.add(ht);
        HashMap<GroupWrapper,Double> ht2 = new HashMap<>();
        for (GroupWrapper g1 : this.getGroupListCourse01()) {
            ht2.put(g1,g1.getWeightedScore());
        }
        res.add(ht2);
        HashMap<GroupWrapper,Double> ht3 = new HashMap<>();
        for (GroupWrapper g2 : this.getGroupListCourse02()) {
            ht3.put(g2,g2.getWeightedScore());
        }
        res.add(ht3);
        HashMap<GroupWrapper,Double> ht4 = new HashMap<>();
        for (GroupWrapper g3 : this.getGroupListCourse03()) {
            ht4.put(g3,g3.getWeightedScore());
        }
        res.add(ht4);
        return res;
    }

    public List<ParticipantWrapper> getParticipantList() {
        return participantList;
    }

    public void setParticipantList(List<ParticipantWrapper> participantList) {
        this.participantList = participantList;
    }

    public List<PairWrapper> getPairList() {
        return pairList;
    }

    public void setPairList(List<PairWrapper> pairList) {
        this.pairList = pairList;
    }

    public List<GroupWrapper> getGroupListCourse01() {
        return groupListCourse01;
    }

    public void setGroupListCourse01(List<GroupWrapper> groupListCourse01) {
        this.groupListCourse01 = groupListCourse01;
    }

    public List<GroupWrapper> getGroupListCourse02() {
        return groupListCourse02;
    }

    public void setGroupListCourse02(List<GroupWrapper> groupListCourse02) {
        this.groupListCourse02 = groupListCourse02;
    }

    public List<GroupWrapper> getGroupListCourse03() {
        return groupListCourse03;
    }

    public void setGroupListCourse03(List<GroupWrapper> groupListCourse03) {
        this.groupListCourse03 = groupListCourse03;
    }

    public ParticipantSuccessorListWrapper getParticipantSuccessorList() {
        return participantSuccessorList;
    }

    public void setParticipantSuccessorList(ParticipantSuccessorListWrapper participantSuccessorList) {
        this.participantSuccessorList = participantSuccessorList;
    }

    public PairSuccessorListWrapper getPairSuccessorList() {
        return pairSuccessorList;
    }

    public void setPairSuccessorList(PairSuccessorListWrapper pairSuccessorList) {
        this.pairSuccessorList = pairSuccessorList;
    }
}
