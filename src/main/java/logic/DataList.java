package logic;

import models.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * The DataList class represent a data structure that holds various lists and data related to an event
 * It contains lists of Participants, Pairs, Groups for 3 Courses and unmatched Participants
 */
public class DataList {
    private List<Participant> participantList;
    private List<Participant> unmatchedParticipants;
    private List<Pair> pairList;
    private List<Group> groupListCourse01;
    private List<Group> groupListCourse02;
    private List<Group> groupListCourse03;
    private ParticipantSuccessorList participantSuccessorList;
    private PairSuccessorList pairSuccessorList;
    private CanceledList canceledList;
    private Event event;

    //Constructor
    public DataList (Event event){
        this.participantList = new ArrayList<>();
        this.unmatchedParticipants = new ArrayList<>();
        this.pairList = new ArrayList<>();
        this.groupListCourse01 = new ArrayList<>();
        this.groupListCourse02 = new ArrayList<>();
        this.groupListCourse03 = new ArrayList<>();
        this.participantSuccessorList = new ParticipantSuccessorList();
        this.pairSuccessorList = new PairSuccessorList();
        this.canceledList = new CanceledList();
        this.event = event;

    }


    /**
     * This method add a Participant to the participantList. If it
     * @param participant the Participant that is chosen to be added
     */
    public void addParticipantToList(Participant participant){
        if(participantList.size() < event.getMaxParticipant()){
            participantList.add(participant);
        } else {
            event.setFull(true);
            participantSuccessorList.addParticipant(participant);
        }
    }

    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        JSONArray arraySuccessorParticipants = new JSONArray();
        for (Participant participant : this.getParticipantSuccessorList().getParticipantSuccessorList()) {
            arraySuccessorParticipants.put(participant.toJson());
        }
        JSONArray arraySuccessorPairs = new JSONArray();
        for (Pair pair : this.getPairSuccessorList().getPairSuccessorList()) {
            arraySuccessorPairs.put(pair.toJson());
        }
        JSONArray arrayGroups = new JSONArray();
        for (Group group : this.getAllGroups()) {
            arrayGroups.put(group.toJson());
        }
        JSONArray arrayPairs = new JSONArray();
        for (Pair pair : this.getPairList()) {
            arrayPairs.put(pair.toJson());
        }

        jsonObject.put("successorParticipants", arraySuccessorParticipants);
        jsonObject.put("successorPairs", arraySuccessorPairs);
        jsonObject.put("groups", arrayGroups);
        jsonObject.put("pairs", arrayPairs);
        return jsonObject;
    }



    private List<Group> getAllGroups() {
        List<Group> allGroups = new ArrayList<>();
        allGroups.addAll(getGroupListCourse01());
        allGroups.addAll(getGroupListCourse02());
        allGroups.addAll(getGroupListCourse03());
        return allGroups;
    }



    public void exportToJsonFile(String fileName) {
        JSONObject jsonObject = this.toJson();
        JsonExport jsonExport = new JsonExport();
        jsonExport.writeJsonToFile(jsonObject, fileName);
    }


    /**
     * This method add a Participant to the unmatchedparticipant List
     * @param participant the Participant that is chosen to be added
     */
    public void addUnmatchedParticipantToList(Participant participant){
        unmatchedParticipants.add(participant);
    }

    /**
     * This method add the chosen Pair to the pairList
     * @param pair the pair that is chosen to be added
     */
    public void addPairToList(Pair pair) {
        if(participantList.size() + 2 <= event.getMaxParticipant()){
            pairList.add(pair);
        } else {
            pairSuccessorList.addPair(pair);
        }
    }

    /**
     * This method remove the chosen pair from the pairList
     * @param pair the pair that is chosen to be removed
     */
    public void removePairFromList(Pair pair) {
        pairList.remove(pair);
        if (participantList.size() + 2 <= event.getMaxParticipant()) {
            if (!pairSuccessorList.isEmpty()) {
                Pair par = pairSuccessorList.getFirst();
                pairList.add(par);
                pairSuccessorList.removeFirst();
            }
        }
    }

    /**
     * This method return a list of Group
     * @return groupListGang01 as List of Group for Gang 1
     */
    public List<Group> getGroupListCourse01() {
        return groupListCourse01;
    }
    /**
     * This method return a list of Group
     * @return groupListGang02 as List of Group for Gang 2
     */
    public List<Group> getGroupListCourse02() {
        return groupListCourse02;
    }
    /**
     * This method return a list of Group
     * @return groupListGang03 as List of Group for Gang 3
     */
    public List<Group> getGroupListCourse03() {
        return groupListCourse03;
    }
    /**
     * This method return a list of Participants
     * @return participantList as List of Participants
     */
    public List<Participant> getParticipantList() {
        return participantList;
    }
    /**
     * This method return a list of Participants
     * @return unmatchedParticipants as List of Participants that is not in a Pair
     */
    public List<Participant> getUnmatchedParticipants() {
        return unmatchedParticipants;
    }
    /**
     * This method return a list of Pairs
     * @return pairList as List of Pairs
     */
    public List<Pair> getPairList() {
        return pairList;
    }
    /**
     * This method return dataList's event
     * @return event
     */
    public Event getEvent() {
        return event;
    }

    /**
     * Sets the list of participants
     * @param participantList the list of participants to be set
     */
    public void setParticipantList(List<Participant> participantList) {
        this.participantList = participantList;
    }

    /**
     * Sets the list of pairs
     * @param pairList list of pairs to be set
     */
    public void setPairList(List<Pair> pairList) {
        this.pairList = pairList;
    }

    /**
     * Sets the list of groups for the first course
     * @param groupListCourse01 the list of group to be set
     */
    public void setGroupListCourse01(List<Group> groupListCourse01) {
        this.groupListCourse01 = groupListCourse01;
    }

    /**
     * Sets the list of groups for the second course
     * @param groupListCourse02 the list of group to be set
     */
    public void setGroupListCourse02(List<Group> groupListCourse02) {
        this.groupListCourse02 = groupListCourse02;
    }

    /**
     * Sets the list of groups for the third course
     * @param groupListCourse03 the list of group to be set
     */
    public void setGroupListCourse03(List<Group> groupListCourse03) {
        this.groupListCourse03 = groupListCourse03;
    }

    /**
     * Sets the event
     * @param event the event to be set
     */
    public void setEvent(Event event) {
        this.event = event;
    }

    public void setUnmatchedParticipants(List<Participant> unmatchedParticipants) {
        this.unmatchedParticipants = unmatchedParticipants;
    }

    public ParticipantSuccessorList getParticipantSuccessorList() {
        return participantSuccessorList;
    }

    public void setParticipantSuccessorList(ParticipantSuccessorList participantSuccessorList) {
        this.participantSuccessorList = participantSuccessorList;
    }

    public PairSuccessorList getPairSuccessorList() {
        return pairSuccessorList;
    }

    public void setPairSuccessorList(PairSuccessorList pairSuccessorList) {
        this.pairSuccessorList = pairSuccessorList;
    }

    public CanceledList getCanceledList() {
        return canceledList;
    }

    public void setCanceledList(CanceledList canceledList) {
        this.canceledList = canceledList;
    }
}
