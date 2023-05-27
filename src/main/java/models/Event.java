package models;


import controller.DataList;

import java.util.Date;

public class Event {
    private int Event_ID;
    private Date Date;
    private double partyLongitude;
    private double partyLatitude;
    private boolean isFull;
    private int maxParticipant;

    private DataList dataList;
    private ParticipantSuccessorList participantSuccessorList = new ParticipantSuccessorList();
    private PairSuccesorList pairSuccesorList = new PairSuccesorList();

    public int getEvent_ID() {
        return Event_ID;
    }

    public java.util.Date getDate() {
        return Date;
    }

    public boolean isFull() {
        return isFull;
    }

    public DataList getDataList() {
        return dataList;
    }

    public double getPartyLatitude() {
        return partyLatitude;
    }

    public double getPartyLongitude() {
        return partyLongitude;
    }
    public void setPartyLongitude(double partyLongitude) {
        this.partyLongitude = partyLongitude;
    }

    public void setPartyLatitude(double partyLatitude) {
        this.partyLatitude = partyLatitude;
    }
    public ParticipantSuccessorList getParticipantSuccessorList() {
        return participantSuccessorList;
    }

    public PairSuccesorList getPairSuccesorList() {
        return pairSuccesorList;
    }


    public Event(int event_ID, java.util.Date date, double partyLongitude,double partyLatitude, int maxParticipant) {
        Event_ID = event_ID;
        Date = date;
        this.partyLatitude = partyLatitude;
        this.partyLongitude = partyLongitude;
        this.maxParticipant = maxParticipant;
    }

    public Event(){
        Event_ID = 0;
        Date = new Date();
        this.maxParticipant = 1000;
        this.dataList = new DataList(this);
    }

    public int getMaxParticipant(){
        return maxParticipant;
    }
}
