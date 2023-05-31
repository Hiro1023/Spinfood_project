package controller;

import java.util.*;
import java.util.stream.Collectors;
import models.*;

public class ListManagement{
    public DataList dataList;
    public List<Pair> pairListTemp;
    public double partyLongitude;
    public double partyLatitude ;
    public ListManagement(DataList dataList){
        this.dataList = dataList;
        this.partyLongitude = dataList.event.getPartyLongitude();
        this.partyLatitude = dataList.event.getPartyLatitude();
    }


    public void editCriteria(CRITERIA criteria, int newWeight) {
        criteria.setWeight(newWeight);
    }
    private int counterGang = 1;

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
        //copy everything from data after all Participants is matched to Pairs

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
            if(score==1000){
                return tempPair;
            }
            if (score > bestScore) {
                bestPair = tempPair;
                bestScore = score;
            }
        }
        return bestPair;
    }
    
    //creating the best possible Group
    public void makeBestGroupList() {
        //find best Group and terminate if there is only 2 pairs left
        pairListTemp = new ArrayList<>(dataList.pairList);

        while (pairListTemp.size() > 2) {
            boolean impossibleGroup = pairListTemp.size()==3 && makeBestGroup(pairListTemp.get(0))==null;
            if (impossibleGroup)
                break;
            else {
                Map<Group, Double> tempGroups = new HashMap<>();
                for (Pair p : pairListTemp) { //O(n)
                    Group bestGroup = makeBestGroup(p);
                    if (bestGroup != null)
                        tempGroups.put(bestGroup, bestGroup.calculateGroupWeightedScore());
                }
                List<Group> tempGroupList = new ArrayList<>();
                //sort the tempGroup in descending order
                tempGroups.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                        .forEach(x -> tempGroupList.add(x.getKey()));

                List<Group> list = tempGroupList;

                for (int i = 0; i < list.size(); i++) {
                    Group g = list.get(0);
                    addToGroup(g);  //add this group g to the dataList->groupList 1,2 or 3
                    addMetAndCookPair(g);   //mark all pair in this group as met
                    pairListTemp.removeAll(g.getPairs()); //remove all the pairs, which was grouped
                    list = list.stream().filter(x -> notContainsPairedPairs(x, g)).collect(Collectors.toList()); //filter only the pair which is not paired
                }
            }
        }
        //increase counter, go to next Gang
        counterGang++;
        System.out.println("COUNT");
        //add the rest pair in the list to pairSuccessorList  or ParticipantSuccessorList
        for (Pair p : pairListTemp) {
            if (p.getIsPreMade()) {
                dataList.pairList.remove(p);
                dataList.event.getPairSuccesorList().addPair(p);
            } else {
                dataList.event.getParticipantSuccessorList().addAllParticipant(p.getParticipant1(), p.getParticipant2());
            }
        }
    }

    public void addToGroup(Group g){
       if(counterGang==1)
                dataList.groupListGang01.add(g);
       else if(counterGang==2)
                dataList.groupListGang02.add(g);
       else if(counterGang==3)
                dataList.groupListGang03.add(g);
    }

/*
    public boolean checkForGroup(){
        for(int i = 0; i < dataList.getGroupListGang01().size()-1 ; i++){
            for(int j = i+1; j < dataList.groupListGang01.size() ; j++){
                if(!notContainsPairedPairs(dataList.groupListGang01.get(i),dataList.groupListGang01.get(j))){
                    return false;
                }
            }
        }
        return true;
    }
*/

    private boolean notContainsPairedPairs(Group x, Group a) {
        for (Pair p : a.getPairs()) {
            if (x.getPairs().contains(p)) {
                return false;
            }
        }
        return true;
    }

    public Group makeBestGroup(Pair pair) {
        Group bestGroup = null;
        double bestScore = -1;

        List<Pair> unmatchedPairs = new ArrayList<>(pairListTemp);
        unmatchedPairs.remove(pair);
        unmatchedPairs.removeAll(pair.getVisitedPairs());

        if(unmatchedPairs.size()<2)
            return null;
        if (containsMeat(pair))
            unmatchedPairs = unmatchedPairs.stream().filter(x -> !containsVeganOrVeggie(x)).collect(Collectors.toList());
        if (containsVeganOrVeggie(pair))
            unmatchedPairs = unmatchedPairs.stream().filter(x -> !containsMeat(x)).collect(Collectors.toList());

        //O(n^2)
        for (int i = 0; i < unmatchedPairs.size() - 1; i++) {
            for (int j = i + 1; j < unmatchedPairs.size(); j++) {
                Pair pair1 = unmatchedPairs.get(i);
                Pair pair2 = unmatchedPairs.get(j);

                if (!pair1.getVisitedPairs().contains(pair2) && !pair2.getVisitedPairs().contains(pair1) ) {
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

    /**
     *
     * @param group
     * @return void
     */
    public void addMetAndCookPair(Group group){
        Pair p1 = group.getPairs().get(0);
        Pair p2 = group.getPairs().get(1);
        Pair p3 = group.getPairs().get(2);

        double distanceToParty1 = p1.calculateDistanceBetweenKitchenAndParty(partyLongitude,partyLatitude);
        double distanceToParty2 = p2.calculateDistanceBetweenKitchenAndParty(partyLongitude,partyLatitude);
        double distanceToParty3 = p3.calculateDistanceBetweenKitchenAndParty(partyLongitude,partyLatitude);
        double max = Math.max(Math.max(distanceToParty1,distanceToParty2),distanceToParty3);

        if (distanceToParty1==max)
            p1.setHasCooked(true,counterGang);
        else if(distanceToParty2==max)
            p2.setHasCooked(true,counterGang);
        else if(distanceToParty3==max)
            p3.setHasCooked(true,counterGang);

        p1.meetPair(p2,p3);
        p2.meetPair(p1,p3);
        p3.meetPair(p1,p2);
    }

    private boolean containsMeat (Pair pair) {
        return pair.getParticipant1().getFoodPreference().equals(FOOD_PREFERENCE.meat) || pair.getParticipant2().getFoodPreference().equals(FOOD_PREFERENCE.meat);
    }

    private boolean containsVeganOrVeggie (Pair pair){
        return pair.getParticipant1().getFoodPreference().equals(FOOD_PREFERENCE.vegan) || pair.getParticipant2().getFoodPreference().equals(FOOD_PREFERENCE.vegan)
                || pair.getParticipant1().getFoodPreference().equals(FOOD_PREFERENCE.veggie) || pair.getParticipant2().getFoodPreference().equals(FOOD_PREFERENCE.veggie);
    }

    private boolean allCook(Pair p1,Pair p2,Pair p3){
        if(p1.getHasCooked().isEmpty()) return false;
        if(p2.getHasCooked().isEmpty()) return false;
        if(p3.getHasCooked().isEmpty()) return false;
        return true;
    }
    public void findCookPair(){

    }

}
