package controller;

import models.Participant;

public interface Utility {

    public void show();

    public int hashCode();

    public boolean equal(Object o);

    boolean equal(Participant participant_2);
}
