//package models;

import java.util.ArrayList;
import java.util.List;

public class GruppenListe {
    private List<Gruppe> groupList = new ArrayList<>();

    public void addGroup(Gruppe group) {
        groupList.add(group);
    }

    public void removeGroup(Gruppe group) {
        groupList.remove(group);
    }

    public List<Gruppe> getGroupList() {
        return groupList;
    }
}
