package logic;
import java.util.*;
import java.util.stream.Collectors;
import models.*;

public class ListManagement{
    public DataList dataList;
    public List<Pair> pairListTemp ;
    private int courseCounter = 1;
    public List<Pair> UngroupedPair = new ArrayList<>();
    public List<Group> allCookedGroup = new ArrayList<>();
    public List<Group> pairDidntCookGroup = new ArrayList<>();
    public List<Pair> pairDidntCookList = new ArrayList<>();
    public ListManagement(DataList dataList){
        this.dataList = dataList;
    }

    /**
     *  This method is used to give a criteria and updated weight or setting a new weight for the criteria
     * @param criteria the criteria to be modified
     * @param newWeight the weight to be given to this criteria
     */
    public void editCriteria(CRITERIA criteria, int newWeight) {
        criteria.setWeight(newWeight);
    }

    /**
     * This method creates the best possible list of pairs. It keeps forming pairs of participants and take the best weight for each pair until
     * no more suitable pairs can be formed.
     * After forming each pair, it removes the paired participants from the unmatched participants list and adds the pair to the pairList.
     */
    public void makeBestPairList() {
        while (dataList.getUnmatchedParticipants().size() > 1) {
            boolean impossiblePair = dataList.getUnmatchedParticipants().size() == 2 && makeBestPair(dataList.getUnmatchedParticipants().get(0))==null;
            boolean atLeastOneKitchen = checkKitchen(dataList.getUnmatchedParticipants());
            if (impossiblePair || !atLeastOneKitchen) {
                break;
            } else {
                Map<Pair, Double> tempPairs = new HashMap<>();
                List<Participant> tempParticipants = new ArrayList<>(dataList.getUnmatchedParticipants());
                for (Participant p : tempParticipants) {
                    Pair bestPair = makeBestPair(p);
                    if (bestPair != null) {
                        tempPairs.put(bestPair, bestPair.calculateWeightedScore());
                    }
                }
                List<Pair> tempPairList = new ArrayList<>();
                tempPairs.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                        .forEach(x -> tempPairList.add(x.getKey()));
                List<Pair> list = tempPairList;
                for (int i = 0; i < list.size(); i++) {
                    Pair a = list.get(0);
                    dataList.getPairList().add(a);
                    dataList.getUnmatchedParticipants().remove(a.getParticipant1());
                    dataList.getUnmatchedParticipants().remove(a.getParticipant2());
                    list = list.stream().filter(x -> !containsPairedParticipant(x, a.getParticipant1(), a.getParticipant2())).collect(Collectors.toList());
                }
            }
        }
        for (Participant p : dataList.getUnmatchedParticipants()) {
            dataList.getParticipantSuccessorList().addParticipant(p);
        }//copy everything from data after all Participants is matched to Pairs
    }

    /**
     *  this method checks a list of participants if they own a kitchen to cook in
     * @param Participants the chosen participant list
     * @return boolean if a participant owns a kitchen
     */
    private boolean checkKitchen(List<Participant> Participants) {
        for (Participant p : Participants) {
            if (p.getKitchen() != null)
                return true;
        }
        return false;
    }

    /**
     * This method checks whether a given pair contains a certain participant.
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
     * @param participant The participant for whom the best pair is to be found.
     * @return Pair, the best pair for the given participant.
     */
    public Pair makeBestPair(Participant participant) {
        Pair bestPair = null;
        double bestScore = -1;
        List<Participant> candidates = new ArrayList<>(dataList.getUnmatchedParticipants());
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
            double score = tempPair.calculateWeightedScore();
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
     */
    public void makeBestGroupList() {
        //find best Group and terminate if there is only 2 pairs left
        pairListTemp = new ArrayList<>(dataList.getPairList());

        while (pairListTemp.size() > 2) {
            boolean impossibleGroup = pairListTemp.size() == 3 && makeBestGroup(pairListTemp.get(0)) == null;
            if (impossibleGroup)
                break;
            else {
                Map<Group, Double> tempGroups = new HashMap<>();
                for (Pair p : pairListTemp) { //O(n)
                    Group bestGroup = makeBestGroup(p);
                    if (bestGroup != null)
                        tempGroups.put(bestGroup, bestGroup.calculateWeightedScore());
                }
                List<Group> tempGroupList = new ArrayList<>();
                //sort the tempGroup in descending order
                tempGroups.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                        .forEach(x -> tempGroupList.add(x.getKey()));

                List<Group> list = tempGroupList;

                for (int i = 0; i < list.size(); i++) {
                    Group g = list.get(0);
                    addToGroup(g);
                    pairListTemp.removeAll(g.getPairs()); //remove all the pairs, which was grouped
                    list = list.stream().filter(x -> notContainsPairedPairs(x, g)).collect(Collectors.toList()); //filter only the pair which is not paired
                }
            }
        }


        if (courseCounter == 3 && !allCookedGroup.isEmpty()) {
            List<Group> pairDidntCookGroupTemp = new ArrayList<>(pairDidntCookGroup);
            for (Group group1 : pairDidntCookGroup) {
                for (Group group2 : allCookedGroup) {
                    boolean sameFoodPreference = group1.getFoodPreference().equals(group2.getFoodPreference())
                            || group1.getFoodPreference().equals(FOOD_PREFERENCE.none)
                            || group2.getFoodPreference().equals(FOOD_PREFERENCE.none)
                            || ( group1.getFoodPreference().equals(FOOD_PREFERENCE.vegan) && (group2.getFoodPreference().equals(FOOD_PREFERENCE.veggie)) )
                            || ( group1.getFoodPreference().equals(FOOD_PREFERENCE.veggie) && (group2.getFoodPreference().equals(FOOD_PREFERENCE.vegan)) );

                    if (sameFoodPreference) {
                        List<Group> bestGroup = makeBestRestGroupGang3(group2, group1);
                        dataList.getGroupListCourse03().remove(group1);
                        dataList.getGroupListCourse03().remove(group2);
                        pairDidntCookGroupTemp.remove(group1);
                        addMetAndCookPair(bestGroup.get(0));
                        addMetAndCookPair(bestGroup.get(1));
                        dataList.getGroupListCourse03().add(bestGroup.get(0));//add this group g to the dataList->groupList 1,2 or 3
                        dataList.getGroupListCourse03().add(bestGroup.get(1));
                        allCookedGroup.remove(group2);
                        break;
                    }
                }
            }
            dataList.getGroupListCourse03().addAll(new ArrayList<>(pairDidntCookGroupTemp));
            pairDidntCookGroup = pairDidntCookGroupTemp;
            dataList.getGroupListCourse03().addAll(allCookedGroup);
        }

        //increase counter, go to next Gang
        System.out.println("Group List Gang " + courseCounter);
        courseCounter++;
        //add the rest pair in the list to pairSuccessorList  or ParticipantSuccessorList

        if(courseCounter==1) {
            for (Pair p : pairListTemp) {
                dataList.getPairList().remove(p);
                if (p.getIsPreMade()) {
                    dataList.getPairSuccessorList().addPair(p);
                } else {
                    dataList.getParticipantSuccessorList().addAllParticipant(p.getParticipant1(), p.getParticipant2());
                }
            }
        }
    }

    public List<Group> makeBestRestGroupGang3(Group allCookedGroup,Group didntCookGroup){
        Pair didntCookPair = null;
        int count = 0;
        for (int i=0;i<didntCookGroup.getPairs().size();i++) {
            Pair pair = didntCookGroup.getPairs().get(i);
            if(pair.getHasCooked().isEmpty()) {
                didntCookPair = pair;
                didntCookPair.setHasCooked(true,3);
                count=i;
            }
        }

        Pair p1_1 = allCookedGroup.getPairs().get(0);
        Pair p2_1 = allCookedGroup.getPairs().get(1);
        Pair p3_1 = allCookedGroup.getPairs().get(2);

        Pair p1_2 = didntCookGroup.getPairs().get(0);
        Pair p2_2 = didntCookGroup.getPairs().get(1);
        Pair p3_2 = didntCookGroup.getPairs().get(2);

        double g1 = new Group(didntCookPair, p1_1, p2_1,courseCounter).calculateWeightedScore();
        double g2 = new Group(didntCookPair, p1_1, p3_1,courseCounter).calculateWeightedScore();
        double g3 = new Group(didntCookPair, p2_1, p3_1,courseCounter).calculateWeightedScore();

        double max = Math.max(Math.max(g1,g2),g3);
        List<Group> res = new ArrayList<>();
        if(max==g1){
            addValidatedGroupIntoGroupList(didntCookPair, count, p1_1, p2_1, p3_1, p1_2, p2_2, p3_2, res);
        } else if (max==g2) {
            addValidatedGroupIntoGroupList(didntCookPair, count, p1_1, p3_1, p2_1, p1_2, p2_2, p3_2, res);
        } else if (max==g3) {
            addValidatedGroupIntoGroupList(didntCookPair, count, p2_1, p3_1, p1_1, p1_2, p2_2, p3_2, res);
        }
        return res;
    }

    /**
     * This method add the two new validated Groups into the Group List
     * @param didntCookPair the chosen Pair that has not cooked
     * @param count the current count
     * @param p1_1 the first pair in first group
     * @param p2_1 the second pair in first group
     * @param p3_1 the third pair in first group
     * @param p1_2 the first pair in second group
     * @param p2_2 the second pair in second group
     * @param p3_2 the third pair in second group
     * @param res the group list
     */
    private void addValidatedGroupIntoGroupList(Pair didntCookPair, int count, Pair p1_1, Pair p2_1, Pair p3_1, Pair p1_2, Pair p2_2, Pair p3_2, List<Group> res) {
        Group g1 = new Group(didntCookPair, p1_1, p2_1,this.courseCounter);
        g1.setCourse(COURSE.fromValue(courseCounter));
        res.add(g1);
        if(count==0)
            res.add(new Group(p3_1,p2_2,p3_2,this.courseCounter));
        if(count==1)
            res.add(new Group(p3_1,p1_2,p3_2,this.courseCounter));
        if(count==2)
            res.add(new Group(p3_1,p1_2,p2_2,this.courseCounter));
    }


    /**
     * This method adds a given group to the appropriate group list based on the course.
     * @param g The group to be added.
     */
    private void addToGroup(Group g){
        if(courseCounter ==1)
            dataList.getGroupListCourse01().add(g);
        else if(courseCounter ==2)
            dataList.getGroupListCourse02().add(g);
        else if(courseCounter ==3)
            dataList.getGroupListCourse03().add(g);
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

        if(unmatchedPairs.size()<2) return bestGroup;
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
                    if(allCooked(new Group(pair,pair1,pair2,this.courseCounter)) && courseCounter<=2){
                        continue;
                    }
                    Group tempGroup = new Group(pair, pair1, pair2,courseCounter);
                    double score = tempGroup.calculateWeightedScore();
                    if (score > bestScore) {
                        bestScore = score;
                        bestGroup = tempGroup;
                    }
                }
            }
        }
        if(bestGroup==null){
            UngroupedPair.add(pair);
        }
        bestGroup.setCourse(COURSE.fromValue(courseCounter));
        return bestGroup;
    }


    /**
     * This method is used to add a  pair that cooked to the group. It calculates the distance of each pair's kitchen to the party location,
     * sets the pair with the maximum distance as having cooked and which course, and updates the meetings between the pairs.
     * @param group the group of pairs participating in the cooking event.
     */
    public void addMetAndCookPair(Group group){
        //each pair in group
        Pair p1 = group.getPairs().get(0);
        Pair p2 = group.getPairs().get(1);
        Pair p3 = group.getPairs().get(2);
        //add met pair
        p1.meetPair(p2,p3);
        p2.meetPair(p1,p3);
        p3.meetPair(p1,p2);
        //find pair that will cook in this gang
        findCookPair(group);
    }

    /**
     * This methode decides which pair is responsible to cook in this group
     * @param group the group that is chosen to be evaluated
     */
    public void findCookPair(Group group) {
        Pair p1 = group.getPairs().get(0);
        Pair p2 = group.getPairs().get(1);
        Pair p3 = group.getPairs().get(2);

        double partyLongitude = dataList.getEvent().getPartyLongitude();
        double partyLatitude = dataList.getEvent().getPartyLatitude();

        double distanceToParty1 = p1.calculateDistanceBetweenKitchenAndParty(partyLongitude, partyLatitude);
        double distanceToParty2 = p2.calculateDistanceBetweenKitchenAndParty(partyLongitude, partyLatitude);
        double distanceToParty3 = p3.calculateDistanceBetweenKitchenAndParty(partyLongitude, partyLatitude);
        double max = Math.max(Math.max(distanceToParty1, distanceToParty2), distanceToParty3);

        if(allCooked(group)) {
            allCookedGroup.add(group);
        }

        if (distanceToParty1==max || (!p2.getHasCooked().isEmpty() && !p3.getHasCooked().isEmpty() && p1.getHasCooked().isEmpty()) ) {
            p1.setHasCooked(true, courseCounter);
            group.setCookingPair(p1);
        } else if(distanceToParty2==max || (!p1.getHasCooked().isEmpty() && !p3.getHasCooked().isEmpty() && p2.getHasCooked().isEmpty())){
            p2.setHasCooked(true, courseCounter);
            group.setCookingPair(p2);
        } else if(distanceToParty3==max || (!p1.getHasCooked().isEmpty() && !p2.getHasCooked().isEmpty() && p3.getHasCooked().isEmpty())) {
            p3.setHasCooked(true, courseCounter);
            group.setCookingPair(p3);
        }

        if(courseCounter==3 && !allCooked(group)) {
            pairDidntCookGroup.add(group);
            for (Pair p : group.getPairs()) {
                if(p.getHasCooked().isEmpty()){
                    pairDidntCookList.add(p);
                }
            }
        }
    }

    /**
     * This method checks whether the given pair contains a participant who prefers meat.
     * @param pair the pair of participants to be checked.
     * @return boolean, returns true if either participant in the pair prefers meat, false otherwise.
     */
    private boolean containsMeat (Pair pair) {
        return pair.getParticipant1().getFoodPreference().equals(FOOD_PREFERENCE.meat) || pair.getParticipant2().getFoodPreference().equals(FOOD_PREFERENCE.meat);
    }
    /**
     * This method checks whether the given pair contains a participant who is vegan or veggie.
     * @param pair the pair of participants to be checked.
     * @return boolean, returns true if either participant in the pair prefers meat, false otherwise.
     */
    private boolean containsVeganOrVeggie (Pair pair){
        return pair.getParticipant1().getFoodPreference().equals(FOOD_PREFERENCE.vegan) || pair.getParticipant2().getFoodPreference().equals(FOOD_PREFERENCE.vegan)
                || pair.getParticipant1().getFoodPreference().equals(FOOD_PREFERENCE.veggie) || pair.getParticipant2().getFoodPreference().equals(FOOD_PREFERENCE.veggie);
    }

    /**
     *  this method takes all pairs from a group and checks if they have cooked or not
     * @return boolean , returns true if all pairs have cooked
     */
    private boolean allCooked(Group group){
        Pair p1 = group.getPairs().get(0);
        Pair p2 = group.getPairs().get(1);
        Pair p3 = group.getPairs().get(2);
        return !p1.getHasCooked().isEmpty() && !p2.getHasCooked().isEmpty() && !p3.getHasCooked().isEmpty();
    }


}