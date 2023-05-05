package models;

import java.util.ArrayList;
import java.util.List;

public class ParticipantSuccesorList extends SuccesorList{
    private List<Participant> participantSuccesorList = new ArrayList<>();

    public ParticipantSuccesorList() {};

    public void addParticipant(Participant participant) {
        participantSuccesorList.add(participant);
    }

    public List<Participant> getParticipantSuccesorList() {
        return participantSuccesorList;
    }

    public int getNumberOfParticipantSuccesorList() {
        return participantSuccesorList.size();
    }

}
