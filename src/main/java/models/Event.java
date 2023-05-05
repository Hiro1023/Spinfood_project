package models;

import java.util.Date;

public class Event {
    private int Event_ID;
    private Date Date;
    private String partyVenue;
    private boolean isFull;
    private int maxParticipant;

    private PairSuccesorList pairSuccesorList = new PairSuccesorList();
    private ParticipantSuccesorList participantSuccesorList = new ParticipantSuccesorList();


    public Event(int event_ID, java.util.Date date, String partyVenue, int maxParticipant) {
        Event_ID = event_ID;
        Date = date;
        this.partyVenue = partyVenue;
        this.maxParticipant = maxParticipant;
    }
}
