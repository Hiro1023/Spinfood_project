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

    public List<Group> getGroupList() {
        return groupList;
    }

    public List<Group> deleteGroupList(){
        groupList = new ArrayList<>();
        return groupList;
    }

    List<Group> groupList;

    List<Participant> unmatchedParticipants;

    public Event getEvent() {
        return event;
    }

    Event event ;

    //Constructor
    public DataList (Event event){
        participantList = new ArrayList<>();
        pairList = new ArrayList<>();
        groupList = new ArrayList<>();
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
