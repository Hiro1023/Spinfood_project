package models;

import java.util.ArrayList;
import java.util.List;

/**
 * The ParticipantSuccessorList class extends the SuccessorList class and represents a list of participants
 * It provides methods for adding participants to the list, retrieving the list's size, and accessing the participant successor list.
 */
public class ParticipantSuccessorList extends SuccessorList {
    private List<Participant> participantSuccessorList = new ArrayList<>();


    public ParticipantSuccessorList(){}

    /**
     * This method adds one participant into the list
     * @param participant the selected participant
     */
    public void addParticipant(Participant participant) {
        participantSuccessorList.add(participant);
    }

    /**
     * This method adds 2 participants into the list
     * @param par1 the first participant
     * @param par2 the second participant
     */
    public void addAllParticipant(Participant par1,Participant par2){
        participantSuccessorList.add(par1);
        participantSuccessorList.add(par2);
    }

    public List<Participant> getParticipantSuccessorList() {
        return participantSuccessorList;
    }

    @Override
    public int getSize() {
        return participantSuccessorList.size();
    }
}
