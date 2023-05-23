package controller;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

import models.*;

public class ListManagement{
public DataList dataList;

    public ListManagement(DataList dataList){
        this.dataList = dataList;
    }

    public void editCriteria(CRITERIA criteria, int newWeight) {
        criteria.setWeight(newWeight);
    }

    public void makeBestPairList() {
        while (dataList.unmatchedParticipants.size() > 1) {
            boolean impossiblePair = dataList.unmatchedParticipants.size() == 2 && makeBestPair(dataList.unmatchedParticipants.get(0))==null;
            if (impossiblePair) {
                break;
            } else {
                Map<Pair, Double> tempPairs = new HashMap<>();
                List<Participant> tempParticipants = new ArrayList<>(dataList.unmatchedParticipants);
                for (Participant p : tempParticipants) {
                    Pair bestPair = makeBestPair(p);
                    if (bestPair != null) {
                        tempPairs.put(bestPair, bestPair.calculatePairWeightedScore());
                    }
                }
                List<Pair> tempPairList = new ArrayList<>();
                tempPairs.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                        .forEach(x -> tempPairList.add(x.getKey()));
                List<Pair> list = tempPairList;
                for (int i = 0; i < list.size(); i++) {
                    Pair a = list.get(0);
                    dataList.pairList.add(a);
                    dataList.unmatchedParticipants.remove(a.getParticipant1());
                    dataList.unmatchedParticipants.remove(a.getParticipant2());
                    list = list.stream().filter(x -> !containsPairedParticipant(x, a.getParticipant1(), a.getParticipant2())).collect(Collectors.toList());
                }
            }
        }
        for (Participant p : dataList.unmatchedParticipants) {
            dataList.event.getParticipantSuccessorList().addParticipant(p);
        }
    }
    public boolean containsPairedParticipant (Pair p, Participant a, Participant b){
        return p.getParticipant1().equals(a) || p.getParticipant1().equals(b) || p.getParticipant2().equals(a) || p.getParticipant2().equals(b);
    }

    public Pair makeBestPair(Participant participant) {
        Pair bestPair = null;
        double bestScore = -1;
        List<Participant> candidates = new ArrayList<>(dataList.unmatchedParticipants);
        candidates.remove(participant);
        if (participant.getKitchen() == null) {
            candidates = candidates.stream().filter(x -> x.getKitchen()!=null).collect(Collectors.toList());
        }
        if (participant.getFoodPreference().equals(FOOD_PREFERENCE.meat)) {
            candidates = candidates.stream().filter(x -> !x.getFoodPreference().equals(FOOD_PREFERENCE.vegan)).filter(x -> !x.getFoodPreference().equals(FOOD_PREFERENCE.veggie)).collect(Collectors.toList());
        }
        if (participant.getFoodPreference().equals(FOOD_PREFERENCE.vegan) || participant.getFoodPreference().equals(FOOD_PREFERENCE.veggie)) {
            candidates = candidates.stream().filter(x -> !x.getFoodPreference().equals(FOOD_PREFERENCE.meat)).collect(Collectors.toList());
        }

        for (Participant p : candidates) {
            Pair tempPair = new Pair(participant,p);
            double score = tempPair.calculatePairWeightedScore();

            if (score > bestScore) {
                bestPair = tempPair;
                bestScore = score;
            }
        }
        return bestPair;
    }


    //creating the best possible Group
    public void makeBestGroupList() {
        List<Pair> unpairedPairList = new ArrayList<>(dataList.pairList);
        while (unpairedPairList.size() > 2) {
            boolean impossibleGroup = unpairedPairList.size() == 3 && makeBestGroup(unpairedPairList.get(0))==null;
            if (impossibleGroup) {
                break;
            } else {
                Map<Group, Double> tempGroups = new HashMap<>();
                for (Pair p : unpairedPairList) {
                    Group bestGroup = makeBestGroup(p);
                    if (bestGroup != null) {
                        tempGroups.put(bestGroup, bestGroup.calculateGroupWeightedScore());
                    }
                }
                List<Group> tempGroupList = new ArrayList<>();
                tempGroups.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                        .forEach(x -> tempGroupList.add(x.getKey()));
                List<Group> list = tempGroupList;
                for (int i = 0; i < list.size(); i++) {
                    Group a = list.get(0);
                    dataList.groupList.add(a);
                    unpairedPairList.removeAll(a.getPairs());
                    list = list.stream().filter(x -> notContainsPairedPairs(x, a.getPairs())).collect(Collectors.toList());
                }
            }
        }
        for (Pair p : unpairedPairList) {
            if (p.isPreMade()) {
                dataList.event.getPairSuccesorList().addPair(p);
            } else {
                dataList.event.getParticipantSuccessorList().addParticipant(p.getParticipant1());
                dataList.event.getParticipantSuccessorList().addParticipant(p.getParticipant2());
            }
        }
    }

    private boolean notContainsPairedPairs(Group x, List<Pair> pairs) {
        List<Pair> commonPair = new ArrayList<>(x.getPairs());
        commonPair.retainAll(pairs);
        return commonPair.isEmpty();
    }

    public Group makeBestGroup(Pair pair) {
        Group bestGroup = null;
        double bestScore = -1;
        List<Pair> unmatchedPairs = new ArrayList<>(dataList.pairList);
        unmatchedPairs.remove(pair);
        unmatchedPairs.removeAll(pair.getVisitedPairs());
        if (containsMeat(pair)) {
            unmatchedPairs = unmatchedPairs.stream().filter(x -> !containsVeganOrVeggie(x)).collect(Collectors.toList());
        }
        if (containsVeganOrVeggie(pair)) {
            unmatchedPairs = unmatchedPairs.stream().filter(x -> !containsMeat(x)).collect(Collectors.toList());
        }
        for (int i = 0; i < unmatchedPairs.size() - 1; i++) {
            for (int j = i + 1; j < unmatchedPairs.size(); j++) {
                Pair pair1 = unmatchedPairs.get(i);
                Pair pair2 = unmatchedPairs.get(j);

                if (!pair1.getVisitedPairs().contains(pair2) && !pair2.getVisitedPairs().contains(pair1)) {
                    Group tempGroup = new Group(pair, pair1, pair2);
                    double score = tempGroup.calculateGroupWeightedScore();

                    if (score > bestScore) {
                        bestScore = score;
                        bestGroup = tempGroup;
                    }
                }
            }
        }
        return bestGroup;
    }

    private boolean containsMeat (Pair pair) {
        return pair.getParticipant1().getFoodPreference().equals(FOOD_PREFERENCE.meat) || pair.getParticipant2().getFoodPreference().equals(FOOD_PREFERENCE.meat);
    }

    private boolean containsVeganOrVeggie (Pair pair){
        return pair.getParticipant1().getFoodPreference().equals(FOOD_PREFERENCE.vegan) || pair.getParticipant2().getFoodPreference().equals(FOOD_PREFERENCE.vegan)
                || pair.getParticipant1().getFoodPreference().equals(FOOD_PREFERENCE.veggie) || pair.getParticipant2().getFoodPreference().equals(FOOD_PREFERENCE.veggie);
    }
}
