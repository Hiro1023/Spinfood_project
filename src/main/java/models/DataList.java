package models;

import java.util.ArrayList;
import java.util.List;

public class DataList {

    //List(Participant + pair)
    List<Participant> participantList;
    List<Pair> pairList = new ArrayList<>();
    Event event ;

    //Constructor
    public DataList (Event event){
        participantList = new ArrayList<>();
        pairList = new ArrayList<>();
        this.event = event;
    }

    //SuccessorList (Participant + Pair)
    public void addParticipantToList(Participant participant){
        if(participantList.size() < event.getMaxParticipant()){
            participantList.add(participant);
        } else {
            event.getParticipantSuccessorList().addParticipant(participant);
        }
    }

    public void addPairToList(Pair pair) {
        if(participantList.size() + 2 <= event.getMaxParticipant()){
            pairList.add(pair);
        } else {
            event.getPairSuccesorList().addPair(pair);
        }
    }

    public void removePairFromList(Pair pair,int index) {
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

    /**
     *
     * @return
     */
    public List<Pair> getPairList() {
        return pairList;
    }
}
