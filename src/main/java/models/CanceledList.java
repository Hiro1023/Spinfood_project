package models;

import java.util.ArrayList;
import java.util.List;

/**
 * The ListManagement class contains the Lists of Participants and Pairs that are canceled from the event.
 */
public class CanceledList {
    private List<Participant> canceledParticipants;
    private List<Pair> canceledPair;
    public CanceledList() {
        this.canceledPair = new ArrayList<>();
        this.canceledParticipants = new ArrayList<>();
    }

    public List<Participant> getCanceledParticipants() {
        return canceledParticipants;
    }

    public void setCanceledParticipants(List<Participant> canceledParticipants) {
        this.canceledParticipants = canceledParticipants;
    }

    public List<Pair> getCanceledPair() {
        return canceledPair;
    }

    public void setCanceledPair(List<Pair> canceledPair) {
        this.canceledPair = canceledPair;
    }
}
