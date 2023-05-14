package controller;

import java.util.ArrayList;
import java.util.List;

import models.*;

public class ListManagement{
public DataList dataList;

    public ListManagement(DataList dataList){
        this.dataList = dataList;
    }

    public void editCriteria(CRITERIA criteria, int newWeight) {
        criteria.setWeight(newWeight);
    }

    public void makeBestPairList(List<Participant> participants) {
        while (dataList.unmatchedParticipants.size() > 1) {
            Participant participant1 = dataList.unmatchedParticipants.get(0);
            Pair bestPair = makeBestPair(participant1);
            dataList.pairList.add(bestPair);
            dataList.unmatchedParticipants.remove(bestPair.getParticipant1());
            dataList.unmatchedParticipants.remove(bestPair.getParticipant2());
        }
        if (dataList.unmatchedParticipants.size() == 1) {
            dataList.event.getParticipantSuccessorList().addParticipant(dataList.unmatchedParticipants.get(0));
        }
    }

    public Pair makeBestPair(Participant participant) {
        Pair bestPair = null;
        double bestScore = -1;
            for (Participant participant2 : dataList.getParticipantList()) {
                boolean bothNoKitchen = participant.getKitchen() == null && participant2.getKitchen() == null;
                boolean bothHaveKitchen = participant.getKitchen() != null && participant2.getKitchen() != null;
                if (participant == participant2 || bothNoKitchen || bothHaveKitchen) {
                    continue;
                }
                Pair tempPair = new Pair(participant, participant2);
                double tempScore = tempPair.calculatePairWeightedScore(tempPair);
                if (tempScore > bestScore) {
                    bestScore = tempScore;
                    bestPair = tempPair;
                }
            }
        return bestPair;
    }


    //creating the best possible Group
    public List<Group> makeBestGroupList(List<Pair> pairs, ParticipantSuccessorList successorsList) {
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
