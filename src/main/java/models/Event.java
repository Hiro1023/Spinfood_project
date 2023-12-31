package models;


import logic.DataList;

import java.util.Date;

/**
 * The Event class represents an event with various properties such as
 * event ID, date, location, participant information, and maximum participant limit.
 */
public class Event {
    private final int Event_ID;
    private  Date Date;
    private double partyLongitude;
    private double partyLatitude;
    private boolean isFull;
    private  int maxParticipant;
    private DataList dataList;

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
        this.partyLatitude = 8.6746166676233;
        this.partyLongitude = 50.5909317660173;
        this.maxParticipant = 1000;
        this.dataList = new DataList(this);
    }


    public int getMaxParticipant(){
        return maxParticipant;
    }

    public int getEvent_ID() {
        return Event_ID;
    }

    public java.util.Date getDate() {
        return Date;
    }

    public boolean isFull() {
        if (dataList.getPairList().size()>maxParticipant){
            return true;
        }
        return false;
    }



    public void setFull(boolean full){
        isFull = full;
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

    public void setDate(Date date) {
        this.Date = date;
    }

    public void setMaxParticipant(int maxParticipant) {
        this.maxParticipant = maxParticipant;
    }

}
