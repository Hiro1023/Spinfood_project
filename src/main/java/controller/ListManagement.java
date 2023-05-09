package controller;

import java.util.ArrayList;
import java.util.List;

import models.Group;
import models.Pair;
import models.Participant;
import models.ParticipantSuccessorList;

public class ListManagement{

public DataList dataList;
public ParticipantSuccessorList successorList;

public ListManagement(DataList dataList){
    this.dataList = dataList;
}
  
    

public void editCriteria(CRITERIA criteria, int newWeight) {
    criteria.setWeight(newWeight);
}


public void makeBestPairs(List<Participant> participants) {
    List<Participant> unmatchedParticipants = new ArrayList<>(participants);

    while (!unmatchedParticipants.isEmpty()) {
        Pair bestPair = makeBestPair(unmatchedParticipants);
        dataList.pairList.add(bestPair);
        unmatchedParticipants.remove(bestPair.getTeilnehmer1());
        unmatchedParticipants.remove(bestPair.getTeilnehmer2());
    }
}

public Pair makeBestPair(List<Participant> participants) {
    Pair bestPair = null;
    double bestScore = -1;
    for (Participant participant1 : participants) {
        for (Participant participant2 : participants) {
            if (participant1 == participant2 || (participant1.getKitchen() == null && participant2.getKitchen() == null) || (participant1.getKitchen() != null && participant2.getKitchen() != null)) {
                continue;
            }
            Pair tempPair = new Pair(participant1, participant2);
            double tempScore = tempPair.calculatePairWeightedScore(tempPair);
            if (tempScore > bestScore) {
                bestScore = tempScore;
                bestPair = tempPair;
            }
        }
    }
    return bestPair;
}


//creating the best possible Group
public List<Group> makeBestGroups(List<Pair> pairs, ParticipantSuccessorList successorsList) {
    List<Group> bestGroups = new ArrayList<>();
    List<Pair> unmatchedPairs = new ArrayList<>(pairs);

    while (!unmatchedPairs.isEmpty()) {
        Group bestGroup = makeBestGroup(unmatchedPairs);
        bestGroups.add(bestGroup);
        unmatchedPairs.removeAll(bestGroup.getPairs());
    }

    return bestGroups;
}

public Group makeBestGroup(List<Pair> pairs) {
    Group bestGroup = null;
    double bestScore = -1;
    int numberOfPairs = pairs.size();
    for (int i = 0; i < numberOfPairs; i++) {
        for (int j = i + 1; j < numberOfPairs; j++) {
            for (int k = j + 1; k < numberOfPairs; k++) {
                Group tempGroup = new Group(pairs.get(i), pairs.get(j), pairs.get(k));
                double tempScore = tempGroup.calculateGroupWeightedScore(tempGroup);
                if (tempScore > bestScore) {
                    bestScore = tempScore;
                    bestGroup = tempGroup;
                }
            }
        }
    }
    return bestGroup;
}
}
