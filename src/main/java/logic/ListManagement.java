package logic;
import java.util.*;
import java.util.stream.Collectors;
import models.*;

/**
 * The ListManagement class handles the management of lists, including pair lists and group lists.
 * It provides methods for editing criteria, making the best pair list, and making the best group list.
 * The class maintains several lists for storing pairs, groups, and unmatched participants.
 */
public class ListManagement{
    public DataList dataList;
    public List<Pair> pairListTemp ;
    private int courseCounter = 1;
    public List<Pair> cannotFindGroup = new ArrayList<>();
    public List<Group> allCookedGroup = new ArrayList<>();
    public List<Group> pairDidntCookGroup = new ArrayList<>();
    public List<Pair> pairDidntCookList = new ArrayList<>();
    public List<Pair> unmatchedPairCourse3 = new ArrayList<>();
    public List<Pair> unCookedPairCourse3 = new ArrayList<>();
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
        // List of Participants with no Kitchen
        List<Participant> noKitchenParticipants = new ArrayList<>(dataList.getUnpairedParticipants());
        noKitchenParticipants = noKitchenParticipants.stream().filter(x -> x.getKitchen() == null).collect(Collectors.toList());
        // List of Participants with Kitchen
        List<Participant> withKitchenParticipants = new ArrayList<>(dataList.getUnpairedParticipants());
        withKitchenParticipants.removeAll(noKitchenParticipants);

        // Find Pairs for all Participants with no Kitchen
        while (noKitchenParticipants.size() > 0) {
            boolean impossiblePair = noKitchenParticipants.size() == 1 && makeBestPair(noKitchenParticipants.get(0), withKitchenParticipants) == null;
            if (impossiblePair) {
                break;
            } else {
                addValidPairsIntoPairList(noKitchenParticipants, withKitchenParticipants);
            }
        }
        // Pairing for remaining Participants with Kitchen
        while (withKitchenParticipants.size() > 1) {
            boolean impossiblePair = withKitchenParticipants.size() == 2 && makeBestPair(withKitchenParticipants.get(0), withKitchenParticipants) == null;
            if (impossiblePair) {
                break;
            } else {
                addValidPairsIntoPairList(withKitchenParticipants,withKitchenParticipants);
            }
            for (Participant p : dataList.getUnpairedParticipants()) {
                dataList.getParticipantSuccessorList().addParticipant(p);
            }
        }

    }

    /**
     * This method find the most optimal valid pairs and add them into the pair list
     * @param candidates the list of candidates from which the pairs are formed
     * @param partners the list of partners that are available for pairing
     */
    public void addValidPairsIntoPairList(List<Participant> candidates,List<Participant> partners) {
        Map<Pair, Double> tempPairs = new LinkedHashMap<>();
        for (Participant p : candidates) {
            Pair bestPair = makeBestPair(p,partners);
            if (bestPair != null) {
                tempPairs.put(bestPair, bestPair.calculateWeightedScore());
            }
        }

        double smallestValue = Double.POSITIVE_INFINITY;
        for (double value : tempPairs.values()) {
            if (value < smallestValue) {
                smallestValue = value;
            }
        }

        List<Pair> list = new ArrayList<>();
        for (Map.Entry<Pair, Double> entry : tempPairs.entrySet()) {
            if (entry.getValue() == smallestValue) {
                list.add(entry.getKey());
            }
        }

        while (!list.isEmpty()) {
            Pair a = list.get(0);
            dataList.getPairList().add(a);
            dataList.getUnpairedParticipants().remove(a.getParticipant1());
            dataList.getUnpairedParticipants().remove(a.getParticipant2());
            candidates.remove(a.getParticipant1());
            partners.remove(a.getParticipant2());
            list = list.stream().filter(x -> !containsPairedParticipant(x, a.getParticipant1(), a.getParticipant2())).collect(Collectors.toList());
        }
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
    public Pair makeBestPair(Participant participant,List<Participant> participants) {
        Pair bestPair = null;
        double bestScore = Double.POSITIVE_INFINITY;
        List<Participant> candidates = new ArrayList<>(participants);
        candidates.remove(participant);
        if (participant.getFoodPreference().equals(FOOD_PREFERENCE.meat))
            candidates = candidates.stream().filter(x -> !x.getFoodPreference().equals(FOOD_PREFERENCE.vegan)).filter(x -> !x.getFoodPreference().equals(FOOD_PREFERENCE.veggie)).collect(Collectors.toList());

        if (participant.getFoodPreference().equals(FOOD_PREFERENCE.vegan) || participant.getFoodPreference().equals(FOOD_PREFERENCE.veggie)) {
            candidates = candidates.stream().filter(x -> !x.getFoodPreference().equals(FOOD_PREFERENCE.meat)).collect(Collectors.toList());
        }
        for (Participant p : candidates) {
            Pair tempPair = new Pair(participant,p);
            double score = tempPair.calculateWeightedScore();
            if (score < bestScore) {
                bestPair = tempPair;
                bestScore = score;
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
                    if (bestGroup != null) {
                        tempGroups.put(bestGroup, bestGroup.calculateWeightedScore());
                    }
                }
                List<Group> tempGroupList = new ArrayList<>();
                tempGroups.forEach((key, value) -> tempGroupList.add(key));
                Collections.shuffle(tempGroupList);

                List<Group> list = tempGroupList;

                for (int i = 0; i < list.size(); i++) {
                    Group g = list.get(0);
                    if (addMetAndCookPair(g)) { //mark all pair in this group as met *** find valid pair
                        addToGroup(g);  //add this group g to the dataList->groupList 1,2 or 3
                    }
                    pairListTemp.removeAll(g.getPairs()); //remove all the pairs, which was grouped
                    list = list.stream().filter(x -> notContainsPairedPairs(x, g)).collect(Collectors.toList()); //filter only the pair which is not paired
                }
            }
        }

        if(courseCounter==3) {
            for (Pair p : unmatchedPairCourse3) {
                if(p.getHasCooked().isEmpty()){
                    unCookedPairCourse3.add(p);
                }
            }
            unmatchedPairCourse3.removeAll(unCookedPairCourse3);
        }

        while (unmatchedPairCourse3.size() >= 2 && courseCounter==3) {
            boolean impossibleGroup = unmatchedPairCourse3.size() == 3 && makeBestGroup(unmatchedPairCourse3.get(0)) == null;
            if (impossibleGroup || unCookedPairCourse3.isEmpty()) {
                break;
            }else {
                for (int i = 0; i< unCookedPairCourse3.size(); i++) {
                    Pair p = unCookedPairCourse3.get(i);
                    Group bestGroup = makeBestGroupCourse3(p, unmatchedPairCourse3);
                    if (bestGroup != null) {
                        p.setHasCooked(courseCounter);
                        addToGroup(bestGroup);  //add this group g to the dataList->groupList 1,2 or 3
                        unmatchedPairCourse3.removeAll(bestGroup.getPairs()); //remove all the pairs, which was grouped
                        unCookedPairCourse3.remove(p);
                    }
                    if(bestGroup == null){
                        //System.out.println("error");
                    }

                }
            }
        }

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

        if (courseCounter == 3) {
            for (Group g : dataList.getGroupListCourse03()) {
                for (Pair p : g.getPairs()) {
                    if (p.getHasCooked().containsKey(COURSE.dessert)) {
                        g.setCookingPair(p);
                        g.setGroupKitchen(p.getPairKitchen());
                    }
                }
            }
        }
        System.out.println("Course :" + courseCounter);
        courseCounter++;
    }

    /**
     * This method find the best group for the chosen pair in Course 3
     * @param pair the chosen pair
     * @param unmatchedPair the possible partners
     * @return the best Group that is founded
     */
    public Group makeBestGroupCourse3(Pair pair, List<Pair> unmatchedPair) {
        Group bestGroup = null;
        double bestScore = Double.POSITIVE_INFINITY;

        List<Pair> unmatchedPairs = new ArrayList<>(unmatchedPair);
        unmatchedPairs.remove(pair);
        unmatchedPairs.removeAll(pair.getVisitedPairs());

        if(unmatchedPairs.size()<2)
            return null;
        //
        for (int i = 0; i < unmatchedPairs.size() - 1; i++) {
            Pair pair1 = unmatchedPairs.get(i);
            List<Pair> candidatesPair2 = new ArrayList<>(unmatchedPairs);
            candidatesPair2.remove(pair1);
            candidatesPair2.removeAll(pair1.getVisitedPairs());

            for (Pair pair2 : candidatesPair2) {
                if (!checkValidFoodPreference(new Group(pair, pair1, pair2, courseCounter)))
                    continue;
                if (!pair1.getVisitedPairs().contains(pair2) && !pair2.getVisitedPairs().contains(pair1)) {
                    Group tempGroup = new Group(pair, pair1, pair2, courseCounter);
                    double score = tempGroup.calculateWeightedScore();
                    if (score < bestScore) {
                        bestScore = score;
                        bestGroup = tempGroup;
                    }
                }
            }
        }
        if(bestGroup==null){
            cannotFindGroup.add(pair);
        }
        return bestGroup;
    }

    /**
     * This method check if the food preference combination in a group is valid
     * @param g the chosen group
     * @return True if the group has a valid food preference combination
     */
    public boolean checkValidFoodPreference(Group g){
        Pair pair1 = g.getPairs().get(0);
        Pair pair2 = g.getPairs().get(1);
        Pair pair3 = g.getPairs().get(2);

        List<Pair> pairs = new ArrayList<>();
        pairs.add(pair1);
        pairs.add(pair2);
        pairs.add(pair3);

        //declare counter for Food Preference
        int counterForMeat = 0;
        int counterForVegan = 0;
        int counterForVeggie = 0;
        int counterForNone = 0;

        //check Food Preference of each group
        for (Pair pair : pairs) {
            FOOD_PREFERENCE foodPreference = pair.getFoodPreference();
            if (foodPreference == FOOD_PREFERENCE.meat) {
                counterForMeat++;
            } else if (foodPreference == FOOD_PREFERENCE.vegan) {
                counterForVegan++;
            } else if (foodPreference == FOOD_PREFERENCE.veggie) {
                counterForVeggie++;
            } else if (foodPreference == FOOD_PREFERENCE.none) {
                counterForNone++;
            }
        }

        if (counterForMeat == 3 || counterForVegan == 3||counterForVeggie == 3||counterForNone == 3) {
            return true;
        }
        if (counterForMeat == 2 && counterForNone == 1) {
            return true;
        }
        if (counterForMeat == 1 && counterForNone== 2) {
            return true;
        }
        if (counterForMeat == 1 && counterForVegan== 2) {
            return true;
        }
        if (counterForMeat == 1 && counterForVeggie== 2) {
            return true;
        }
        if (counterForVeggie == 2 && counterForVegan == 1) {
            return true;
        }
        if (counterForVeggie == 2 && counterForNone == 1) {
            return true;
        }
        if (counterForVegan == 2 && counterForNone == 1) {
            return true;
        }
        if (counterForVegan == 2 && counterForVeggie == 1) {
            return true;
        }
        if (counterForMeat == 1 && counterForVeggie == 1 && counterForVegan == 1) {
            return true;
        }
        return counterForNone == 1 && counterForVeggie == 1 && counterForVegan == 1;
    }


    /**
     * This method adds a given group to the appropriate group list based on the course.
     * @param g The group to be added.
     */
    public void addToGroup(Group g){
        if(courseCounter ==1)
            dataList.getGroupListCourse01().add(g);
        else if(courseCounter ==2)
            dataList.getGroupListCourse02().add(g);
        else if(courseCounter ==3)
            dataList.getGroupListCourse03().add(g);
    }

    /**
     * This method checks if a given group contains any pairs that are already included in another group.
     * @param x The group to be checked.
     * @param a The group to be compared.
     * @return boolean, returns true if the given group does not contain any pairs from the other group, false otherwise.
     */
    public boolean notContainsPairedPairs(Group x, Group a) {
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
     * @param pair The pair for whom the best group is to be found.
     * @return Group, the best group for the given pair.
     */
    public Group makeBestGroup(Pair pair) {
        Group bestGroup = null;
        double bestScore = Double.POSITIVE_INFINITY;

        List<Pair> unmatchedPairs = new ArrayList<>(pairListTemp);
        unmatchedPairs.remove(pair);
        unmatchedPairs.removeAll(pair.getVisitedPairs());

        if(unmatchedPairs.size()<2)
            return null;

        for (int i = 0; i < unmatchedPairs.size() - 1; i++) {
            Pair pair1 = unmatchedPairs.get(i);
            List<Pair> candidatesPair2 = new ArrayList<>(unmatchedPairs);
            candidatesPair2.remove(pair1);
            candidatesPair2.removeAll(pair1.getVisitedPairs());
            for (Pair pair2 : candidatesPair2) {
                if (countUncookedPair(new Group(pair, pair1, pair2, courseCounter)) >= 3 && courseCounter == 3) {
                    continue;
                }
                if (!checkValidFoodPreference(new Group(pair, pair1, pair2, courseCounter)))
                    continue;

                if (!pair1.getVisitedPairs().contains(pair2) && !pair2.getVisitedPairs().contains(pair1)) {
                    if (allCooked(new Group(pair, pair1, pair2, courseCounter)) && courseCounter <= 2) {
                        continue;
                    }
                    Group tempGroup = new Group(pair, pair1, pair2, courseCounter);
                    double score = tempGroup.calculateWeightedScore();
                    if (score < bestScore) {
                        bestScore = score;
                        bestGroup = tempGroup;
                    }
                }
            }
        }
        if(bestGroup==null){
            cannotFindGroup.add(pair);
        }
        return bestGroup;
    }

    /**
     * This method count how many pairs that haven't cooked yet
     * @param g the chosen group
     * @return the number of pairs
     */
    public int countUncookedPair(Group g){
        int count = 0;
        for(Pair p : g.getPairs()){
            if(p.getHasCooked().isEmpty())
                count++;
        }
        return count;
    }


    /**
     * This method is used to add a  pair that cooked to the group. It calculates the distance of each pair's kitchen to the party location,
     * sets the pair with the maximum distance as having cooked and which course, and updates the meetings between the pairs.
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
     * This methode decides which pair is responsible to cook in this group
     * @param group the group that is chosen to be evaluated
     */
    public boolean findCookPair(Group group) {
        Pair p1 = group.getPairs().get(0);
        Pair p2 = group.getPairs().get(1);
        Pair p3 = group.getPairs().get(2);

        double partyLongitude = dataList.getEvent().getPartyLongitude();
        double partyLatitude = dataList.getEvent().getPartyLatitude();

        double distanceToParty1 = p1.calculateDistanceBetweenKitchenAndParty(partyLongitude, partyLatitude);
        double distanceToParty2 = p2.calculateDistanceBetweenKitchenAndParty(partyLongitude, partyLatitude);
        double distanceToParty3 = p3.calculateDistanceBetweenKitchenAndParty(partyLongitude, partyLatitude);

        if(p1.getHasCooked().containsValue(true))
            distanceToParty1 = 0;
        if(p2.getHasCooked().containsValue(true))
            distanceToParty2 = 0;
        if(p3.getHasCooked().containsValue(true))
            distanceToParty3 = 0;

        List<Double> distances = new ArrayList<>();
        distances.add(distanceToParty1);
        distances.add(distanceToParty2);
        distances.add(distanceToParty3);
        distances.sort(Collections.reverseOrder());

        double maxDistance = distances.get(0);

        if (allCooked(group)) {
            allCookedGroup.add(group);
            if(courseCounter==3) {
                unmatchedPairCourse3.addAll(group.getPairs());
            }
            return false;
        }

        if (distanceToParty1 == maxDistance && !p1.getHasCooked().containsValue(true)) {
            p1.setHasCooked(courseCounter);
            group.setCookingPair(p1);
            group.setGroupKitchen(p1.getPairKitchen());
        } else if (distanceToParty2 == maxDistance && !p2.getHasCooked().containsValue(true)) {
            p2.setHasCooked(courseCounter);
            group.setCookingPair(p2);
            group.setGroupKitchen(p2.getPairKitchen());
        } else if (distanceToParty3 == maxDistance && !p3.getHasCooked().containsValue(true)) {
            p3.setHasCooked(courseCounter);
            group.setCookingPair(p3);
            group.setGroupKitchen(p3.getPairKitchen());
        }

        if (courseCounter == 3 && !allCooked(group)) {
            for (Pair p : group.getPairs()) {
                if(p.getHasCooked().containsKey(COURSE.dessert)){
                    p.clearHasCooked();
                }
                unmatchedPairCourse3.add(p);
            }


            pairDidntCookGroup.add(group);
            for (Pair p : group.getPairs()) {
                if (p.getHasCooked().isEmpty()) {
                    pairDidntCookList.add(p);
                }
            }
            return false;
        }

        return true;
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

    /**
     * This method build the Pair List and all 3 Group Lists
     */
    public void pairAndGroupBuilding() {
        makeBestPairList();
        makeBestGroupList();
        makeBestGroupList();
        makeBestGroupList();
    }

    public void setCourseCounter(int courseCounter) {this.courseCounter = courseCounter;}

}
