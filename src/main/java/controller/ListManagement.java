package controller;
import java.util.*;
import java.util.stream.Collectors;
import models.*;

public class ListManagement{
    public DataList dataList;
    public List<Pair> pairListTemp;
    private int courseCounter = 1;
    public List<Pair> cannotFindGroup = new ArrayList<>();
    //public int allCookedGroup = 0;
    public List<Group> allCookedGroup = new ArrayList<>();
    public ListManagement(DataList dataList){
        this.dataList = dataList;
    }

    /**
     *  This method is used to give a criteria and updated weight or setting a new weight for the criteria
     * @param criteria the criteria to be modified
     * @param newWeight the weight to be given to this criteria
     * @return void
     */
    public void editCriteria(CRITERIA criteria, int newWeight) {
        criteria.setWeight(newWeight);
    }
    /**
     * This method creates the best possible list of pairs. It keeps forming pairs of participants and take the best weight for each pair until
     * no more suitable pairs can be formed.
     * After forming each pair, it removes the paired participants from the unmatched participants list and adds the pair to the pairList.
     *
     * @return void
     */
    public void makeBestPairList() {
        while (dataList.unmatchedParticipants.size() > 1) {
            boolean impossiblePair = dataList.unmatchedParticipants.size() == 2 && makeBestPair(dataList.unmatchedParticipants.get(0))==null;
            boolean atLeastOneKitchen = checkKitchen(dataList.unmatchedParticipants);
            if (impossiblePair || !atLeastOneKitchen) {
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
                    findFoodPreference(a);
                    dataList.unmatchedParticipants.remove(a.getParticipant1());
                    dataList.unmatchedParticipants.remove(a.getParticipant2());
                    list = list.stream().filter(x -> !containsPairedParticipant(x, a.getParticipant1(), a.getParticipant2())).collect(Collectors.toList());
                }
            }
        }
        for (Participant p : dataList.unmatchedParticipants) {
            dataList.event.getParticipantSuccessorList().addParticipant(p);
        }//copy everything from data after all Participants is matched to Pairs
    }

    /**
     *  this method checks a list of participants if they own a kitchen to cook in
     * @param unmatchedParticipants
     * @return boolean if a participant owns a kitchen
     */
    private boolean checkKitchen(List<Participant> unmatchedParticipants) {
        for (Participant p : unmatchedParticipants) {
            if (p.getKitchen() != null)
                return true;
        }
        return false;
    }

    /**
     * This method checks whether a given pair contains a certain participant.
     *
     * @param p The pair to be checked.
     * @param a The first participant to be checked against the pair.
     * @param b The second participant to be checked against the pair.
     * @return boolean, returns true if either of the pair's participants is equal to the given participant, false otherwise.
     */
    public boolean containsPairedParticipant (Pair p, Participant a, Participant b){
        return p.getParticipant1().equals(a) || p.getParticipant1().equals(b) || p.getParticipant2().equals(a) || p.getParticipant2().equals(b);
    }

    /**
     * This method attempts to make the best pair for a given participant. It first filters out suitable candidates based on kitchen availability and food preference.
     * Then, it calculates the pair score for each candidate and chooses the one with the highest score.
     *
     * @param participant The participant for whom the best pair is to be found.
     * @return Pair, the best pair for the given participant.
     */
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
            if (score == 1000) {
                return tempPair;
            } else {
                if (score > bestScore) {
                    bestPair = tempPair;
                    bestScore = score;
                }
            }
        }
        return bestPair;
    }

    /**
     * This method creates the best possible list of groups. It keeps forming groups of pairs until no more suitable groups can be formed.
     * After forming each group, it removes the grouped pairs from the temporary pair list and adds the group to the appropriate group list.
     * @return void
     */
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

                    if(addMetAndCookPair(g)) { //mark all pair in this group as met
                        addToGroup(g);  //add this group g to the dataList->groupList 1,2 or 3
                    }

                    pairListTemp.removeAll(g.getPairs()); //remove all the pairs, which was grouped
                    list = list.stream().filter(x -> notContainsPairedPairs(x, g)).collect(Collectors.toList()); //filter only the pair which is not paired
                }
            }
        }
        //increase counter, go to next Gang
        System.out.println("Group List Gang " + courseCounter );
        courseCounter++;
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


    /**
     * This method adds a given group to the appropriate group list based on the course.
     *
     * @param g The group to be added.
     * @return void
     */
    private void addToGroup(Group g){
       if(courseCounter ==1)
                dataList.groupListCourse01.add(g);
       else if(courseCounter ==2)
                dataList.groupListCourse02.add(g);
       else if(courseCounter ==3)
                dataList.groupListCourse03.add(g);
    }

    /**
     * This method checks if a given group contains any pairs that are already included in another group.
     *
     * @param x The group to be checked.
     * @param a The group to be compared.
     * @return boolean, returns true if the given group does not contain any pairs from the other group, false otherwise.
     */
    private boolean notContainsPairedPairs(Group x, Group a) {
        for (Pair p : a.getPairs()) {
            if (x.getPairs().contains(p)) {
                return false;
            }
        }
        return true;
    }
    /**
     * This method attempts to make the best group for a given pair. It first filters out suitable pair candidates based on food preference.
     * Then, it calculates the group score for each candidate pair and chooses the ones with the highest score.
     *
     * @param pair The pair for whom the best group is to be found.
     * @return Group, the best group for the given pair.
     */
    public Group makeBestGroup(Pair pair) {
        Group bestGroup = null;
        double bestScore = -1;

        List<Pair> unmatchedPairs = new ArrayList<>(pairListTemp);

        unmatchedPairs.remove(pair);
        unmatchedPairs.removeAll(pair.getVisitedPairs());

        if(unmatchedPairs.size()<2)
            return bestGroup;
        if (containsMeat(pair))
            unmatchedPairs = unmatchedPairs.stream().filter(x -> !containsVeganOrVeggie(x)).collect(Collectors.toList());
        if (containsVeganOrVeggie(pair))
            unmatchedPairs = unmatchedPairs.stream().filter(x -> !containsMeat(x)).collect(Collectors.toList());

        //O(n^2)
        for (int i = 0; i < unmatchedPairs.size() - 1; i++) {
            for (int j = i + 1; j < unmatchedPairs.size(); j++) {
                Pair pair1 = unmatchedPairs.get(i);
                Pair pair2 = unmatchedPairs.get(j);

                if (!pair1.getVisitedPairs().contains(pair2) && !pair2.getVisitedPairs().contains(pair1)) {
                    if(allCooked(new Group(pair,pair1,pair2)) && courseCounter<=2){
                        continue;
                    }
                    Group tempGroup = new Group(pair, pair1, pair2);
                    double score = tempGroup.calculateGroupWeightedScore();
                    if (score > bestScore) {
                        bestScore = score;
                        bestGroup = tempGroup;
                    }
                }
            }
        }
        if(bestGroup==null){
            System.out.println("cannot find any group");
            cannotFindGroup.add(pair);
        }

        return bestGroup;
    }

    /**
     * This method is used to add a  pair that cooked to the group. It calculates the distance of each pair's kitchen to the party location,
     * sets the pair with the maximum distance as having cooked and which course, and updates the meetings between the pairs.
     *
     * @param group the group of pairs participating in the cooking event.
     * @return void
     */
    public boolean addMetAndCookPair(Group group){
        //find pair that will cook in this gang
        //each pair in group
        if(findCookPair(group)) {
            Pair p1 = group.getPairs().get(0);
            Pair p2 = group.getPairs().get(1);
            Pair p3 = group.getPairs().get(2);
            //add met pair
            p1.meetPair(p2, p3);
            p2.meetPair(p1, p3);
            p3.meetPair(p1, p2);
            return true;
        }
        return false;
    }

    /**
     *
     * @param group
     */
    public boolean findCookPair(Group group) {
        Pair p1 = group.getPairs().get(0);
        Pair p2 = group.getPairs().get(1);
        Pair p3 = group.getPairs().get(2);

        double partyLongitude = dataList.event.getPartyLongitude();
        double partyLatitude = dataList.event.getPartyLatitude();

        double distanceToParty1 = p1.calculateDistanceBetweenKitchenAndParty(partyLongitude, partyLatitude);
        double distanceToParty2 = p2.calculateDistanceBetweenKitchenAndParty(partyLongitude, partyLatitude);
        double distanceToParty3 = p3.calculateDistanceBetweenKitchenAndParty(partyLongitude, partyLatitude);
        double max = Math.max(Math.max(distanceToParty1, distanceToParty2), distanceToParty3);

        if(allCooked(group)) {
            System.out.println("All cooked");
            allCookedGroup.add(group);
            return false;
        }

        if (distanceToParty1==max || (!p2.getHasCooked().isEmpty() && !p3.getHasCooked().isEmpty() && p1.getHasCooked().isEmpty()) ) {
            p1.setHasCooked(true, courseCounter);
        } else if(distanceToParty2==max || (!p1.getHasCooked().isEmpty() && !p3.getHasCooked().isEmpty() && p2.getHasCooked().isEmpty())){
            p2.setHasCooked(true, courseCounter);
        } else if(distanceToParty3==max || (!p1.getHasCooked().isEmpty() && !p2.getHasCooked().isEmpty() && p3.getHasCooked().isEmpty())) {
            p3.setHasCooked(true, courseCounter);
        }

        if(courseCounter==3 && !allCooked(group)) {
            System.out.println("not all pair are cook");
            return false;
        }

        return true;
    }

    /**
     *
     * @param pair
     */
    public void findFoodPreference(Pair pair){
        Participant p1 = pair.getParticipant1();
        Participant p2 = pair.getParticipant2();
        if(p1.getFoodPreference().equals(p2.getFoodPreference()))
            pair.setFoodPreference(p1.getFoodPreference());
        if(p1.getFoodPreference().equals(FOOD_PREFERENCE.none))
            pair.setFoodPreference(p2.getFoodPreference());
        if(p2.getFoodPreference().equals(FOOD_PREFERENCE.none))
            pair.setFoodPreference(p1.getFoodPreference());
        if(p1.getFoodPreference().equals(FOOD_PREFERENCE.meat) || p2.getFoodPreference().equals(FOOD_PREFERENCE.meat))
            pair.setFoodPreference(FOOD_PREFERENCE.meat);
        if(p1.getFoodPreference().equals(FOOD_PREFERENCE.vegan) || p2.getFoodPreference().equals(FOOD_PREFERENCE.vegan))
            pair.setFoodPreference(FOOD_PREFERENCE.vegan);
    }

    /**
     * This method checks whether the given pair contains a participant who prefers meat.
     *
     * @param pair the pair of participants to be checked.
     * @return boolean, returns true if either participant in the pair prefers meat, false otherwise.
     */
    private boolean containsMeat (Pair pair) {
        return pair.getParticipant1().getFoodPreference().equals(FOOD_PREFERENCE.meat) || pair.getParticipant2().getFoodPreference().equals(FOOD_PREFERENCE.meat);
    }
    /**
     * This method checks whether the given pair contains a participant who is vegan or veggie.
     *
     * @param pair the pair of participants to be checked.
     * @return boolean, returns true if either participant in the pair prefers meat, false otherwise.
     */
    private boolean containsVeganOrVeggie (Pair pair){
        return pair.getParticipant1().getFoodPreference().equals(FOOD_PREFERENCE.vegan) || pair.getParticipant2().getFoodPreference().equals(FOOD_PREFERENCE.vegan)
                || pair.getParticipant1().getFoodPreference().equals(FOOD_PREFERENCE.veggie) || pair.getParticipant2().getFoodPreference().equals(FOOD_PREFERENCE.veggie);
    }

    /**
     *  this method takes all pairs from a group and checks if they have cooked or not
     *
     * @return boolean , returns true if all pairs have cooked
     */
    private boolean allCooked(Group group){
        Pair p1 = group.getPairs().get(0);
        Pair p2 = group.getPairs().get(1);
        Pair p3 = group.getPairs().get(2);
        return !p1.getHasCooked().isEmpty() && !p2.getHasCooked().isEmpty() && !p3.getHasCooked().isEmpty();
    }


}
