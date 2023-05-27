package controller;

import models.Event;
import models.Group;
import models.Pair;
import models.Participant;

import java.util.ArrayList;
import java.util.List;

public class DataList {

    //List(Participant + pair)
    List<Participant> participantList;
    List<Pair> pairList;

    List<Group> groupListGang01;
    List<Group> groupListGang02;
    List<Group> groupListGang03;

    List<Participant> unmatchedParticipants;

    public Event getEvent() {
        return event;
    }

    Event event ;

    //Constructor
    public DataList (Event event){
        participantList = new ArrayList<>();
        pairList = new ArrayList<>();
        groupListGang01 = new ArrayList<>();
        groupListGang02 = new ArrayList<>();
        groupListGang03 = new ArrayList<>();
        this.event = event;
        this.unmatchedParticipants = new ArrayList<>();
    }

    public void addParticipantToList(Participant participant){
        if(participantList.size() < event.getMaxParticipant()){
            participantList.add(participant);
        } else {
            event.getParticipantSuccessorList().addParticipant(participant);
        }
    }

    public void addUnmatchedParticipantToList(Participant participant){
        unmatchedParticipants.add(participant);
    }

    public void addPairToList(Pair pair) {
        if(participantList.size() + 2 <= event.getMaxParticipant()){
            pairList.add(pair);
        } else {
            event.getPairSuccesorList().addPair(pair);
        }
    }

    public void removePairFromList(Pair pair) {
        pairList.remove(pair);
        if (participantList.size() + 2 <= event.getMaxParticipant()) {
            if (!event.getPairSuccesorList().isEmpty()) {
                Pair par = event.getPairSuccesorList().getFirst();
                pairList.add(par);
                event.getPairSuccesorList().removeFirst();
            }
        }
    }

    /**
     *
     * @return groupListGang01 as List of Group for Gang 1
     */
    public List<Group> getGroupListGang01() {
        return groupListGang01;
    }
    public List<Group> getGroupListGang02() {
        return groupListGang02;
    }
    public List<Group> getGroupListGang03() {
        return groupListGang03;
    }
    /**
     *
     * @return
     */
    public List<Participant> getParticipantList() {
        return participantList;
    }

    /** @Description:
     *
     * @return unmatchedParticipants as List of Participant
     */
    public List<Participant> getUnmatchedParticipants() {
        return unmatchedParticipants;
    }

    /**
     *
     * @return
     */
    public List<Pair> getPairList() {
        return pairList;
    }
}
