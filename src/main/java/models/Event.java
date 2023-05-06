package models;

import java.util.Date;

public class Event {
    private int Event_ID;
    private Date Date;
    private String partyVenue;
    private boolean isFull;
    private int maxParticipant;

    //DataList dataList = new DataList();
    private ParticipantSuccessorList participantSuccessorList = new ParticipantSuccessorList();
    private PairSuccesorList pairSuccesorList = new PairSuccesorList();

    public int getEvent_ID() {
        return Event_ID;
    }

    public java.util.Date getDate() {
        return Date;
    }

    public String getPartyVenue() {
        return partyVenue;
    }

    public boolean isFull() {
        return isFull;
    }

    /*
    public DataList getDataList() {
        return dataList;
    }

     */

    public ParticipantSuccessorList getParticipantSuccessorList() {
        return participantSuccessorList;
    }

    public PairSuccesorList getPairSuccesorList() {
        return pairSuccesorList;
    }


    public Event(int event_ID, java.util.Date date, String partyVenue, int maxParticipant) {
        Event_ID = event_ID;
        Date = date;
        this.partyVenue = partyVenue;
        this.maxParticipant = maxParticipant;
    }

    public Event(){
        Event_ID = 0;
        Date = new Date();
        this.partyVenue = "test";
        this.maxParticipant = 1000;
    }

    public int getMaxParticipant(){
        return maxParticipant;
    }
}
