package models;

import java.util.ArrayList;
import java.util.List;

public class ParticipantSuccessorList extends SuccesorList{
    private List<Participant> participantSuccessorList = new ArrayList<>();

    public ParticipantSuccessorList() {};

    public void addParticipant(Participant participant) {
        participantSuccessorList.add(participant);
    }

    public List<Participant> getParticipantSuccessorList() {
        return participantSuccessorList;
    }

    public int getNumberOfParticipantSuccessorList() {
        return participantSuccessorList.size();
    }

}
