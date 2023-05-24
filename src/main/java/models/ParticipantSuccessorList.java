package models;

import java.util.ArrayList;
import java.util.List;

public class ParticipantSuccessorList extends SuccesorList{
    private List<Participant> participantSuccessorList = new ArrayList<>();

    public ParticipantSuccessorList() {};

    public void addParticipant(Participant participant) {
        participantSuccessorList.add(participant);
    }

    public void addAllParticipant(Participant par1,Participant par2){
        participantSuccessorList.add(par1);
        participantSuccessorList.add(par2);
    }

    public List<Participant> getParticipantSuccessorList() {
        return participantSuccessorList;
    }

    public int getNumberOfParticipantSuccessorList() {
        return participantSuccessorList.size();
    }

}
