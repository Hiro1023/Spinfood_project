package models;

import java.util.ArrayList;
import java.util.List;

public class DataList {

    //List(Participant + pair)
    List<Participant> participantList = new ArrayList<>();
    List<Pair> pairlist = new ArrayList<>();
    public int numberOfParticipant;
    private int maxParticipant;

    //SuccesorList (Participant + Pair)
    private ParticipantSuccesorList participantSuccesorList = new ParticipantSuccesorList();
    private PairSuccesorList pairSuccesorList = new PairSuccesorList();
    public void addParticipantToList(Participant participant){
        if(numberOfParticipant < maxParticipant){
            participantList.add(participant);
        } else {
            participantSuccesorList.addParticipant(participant);
        }
    }

    public void addPairToList(Pair pair) {
        if(participantList.size() + 2 <= maxParticipant){
            pairlist.add(pair);
        } else {
            pairSuccesorList.addPair(pair);
        }
    }

    public void removePairFromList(Pair pair,int index) {
        pairlist.remove(pair);

        if (participantList.size() + 2 <= maxParticipant) {
            if (!pairSuccesorList.isEmpty()) {
                Pair par = pairSuccesorList.getFirst();
                pairlist.add(par);
                pairSuccesorList.removeFirst();
            }
        }
    }


}
