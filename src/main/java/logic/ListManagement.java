package logic;
import java.util.*;
import java.util.stream.Collectors;
import models.*;

/**
 * The ListManagement class manages the creation of pairs and groups based on given criteria and participant data.
 */
public class ListManagement{
    public DataList dataList;
    public List<Pair> pairListTemp;
    private int courseCounter = 1;

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
            boolean impossiblePair = dataList.getUnmatchedParticipants().size() == 2 &&
                    makeBestPair(dataList.getUnmatchedParticipants().get(0))==null;
            boolean atLeastOneKitchen = checkKitchen(dataList.getUnmatchedParticipants());

            if (impossiblePair || !atLeastOneKitchen) {
                break;
            } else {
                // Create a Hashmap that stores Temporary best Pairs and their corresponding weight Scores
                Map<Pair, Double> tempPairs = new HashMap<>();
                List<Participant> tempParticipants = new ArrayList<>(dataList.getUnmatchedParticipants());
                for (Participant p : tempParticipants) {
                    Pair bestPair = makeBestPair(p);
                    if (bestPair != null) {
                        tempPairs.put(bestPair, bestPair.calculateWeightedScore());
                    }
                }
                List<Pair> tempPairList = new ArrayList<>();
                // Sort the Hashmap according to the weight Score in descending order, then add them into a List
                tempPairs.entrySet().stream()
                        .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                        .forEach(x -> tempPairList.add(x.getKey()));

                List<Pair> list = tempPairList;

                // Add Pair into pairList in DataList, then remove all Pairs that contain the paired Participants
                for (int i = 0; i < list.size(); i++) {
                    Pair a = list.get(0);
                    dataList.getPairList().add(a);
                    dataList.getUnmatchedParticipants().remove(a.getParticipant1());
                    dataList.getUnmatchedParticipants().remove(a.getParticipant2());
                    list = list.stream()
                            .filter(x -> !containsPairedParticipant(x, a.getParticipant1(), a.getParticipant2()))
                            .collect(Collectors.toList());
                }
            }
        }
        // Add all unpaired Participants into the Participant Successor List
        for (Participant p : dataList.getUnmatchedParticipants()) {
            dataList.getParticipantSuccessorList().addParticipant(p);
        }
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

        // Filter all impossible candidates
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

        // Find the FIRST most optimal partner for participant
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
        List<Pair> invalidPair = new ArrayList<>(pairListTemp);

        while (pairListTemp.size() > 2) {
            // first course
            if (courseCounter == 1 || courseCounter == 2) {
                invalidPair.clear();
                boolean impossibleGroup = pairListTemp.size() == 3 && makeBestGroup(pairListTemp.get(0), pairListTemp) == null;
                if (impossibleGroup)
                    break;
                else {
                    // Create a Hashmap that contains the temporary Groups and their corresponding Scores
                    Map<Group, Double> tempGroups = new HashMap<>();
                    for (Pair p : pairListTemp) { //O(n)
                        Group bestGroup = makeBestGroup(p,pairListTemp);
                        if (bestGroup != null)
                            tempGroups.put(bestGroup, bestGroup.calculateWeightedScore());
                    }
                    List<Group> tempGroupList = new ArrayList<>();
                    //sort the tempGroup in descending order
                    tempGroups.entrySet().stream()
                            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                            .forEach(x -> tempGroupList.add(x.getKey()));

                    List<Group> list = tempGroupList;

                    for (int i = 0; i < list.size(); i++) {
                        Group g = list.get(0);
                        addToGroup(g);  //add this group g to the dataList->groupList 1,2 or 3
                        addMetAndCookPair(g);   //mark all pair in this group as met
                        pairListTemp.removeAll(g.getPairs()); //remove all the pairs, which was grouped
                        list = list.stream()
                                .filter(x -> notContainsPairedPairs(x, g))
                                .collect(Collectors.toList()); //filter only the pair which is not paired
                    }
                }
            // third course
            } else {
                invalidPair = invalidPair.stream()
                        .filter(x -> x.getVisitedPairs().size() < 4)
                        .collect(Collectors.toList());
                pairListTemp.removeAll(invalidPair);
                List<Pair> possible_partners = new ArrayList<>(pairListTemp);
                // we choose pairs that haven't cooked yet as our candidates
                List<Pair> candidates = pairListTemp.stream()
                        .filter(x -> !x.getHasCooked().containsKey(true))
                        .collect(Collectors.toList());
                // we choose cooked pairs as the possible candidates. so that we can make sure all pairs at least cook once
                possible_partners.removeAll(candidates);

                boolean impossibleGroup = candidates.size() == 1
                        && possible_partners.size() == 2
                        && makeBestGroup(candidates.get(0), possible_partners) == null;
                if (impossibleGroup)
                    break;
                else {
                    // Create a Hashmap that contains the temporary Groups and their corresponding Scores
                    Map<Group, Double> tempGroups = new HashMap<>();
                    for (Pair p : candidates) { //O(n)
                        Group bestGroup = makeBestGroup(p, possible_partners);
                        if (bestGroup != null)
                            tempGroups.put(bestGroup, bestGroup.calculateWeightedScore());
                    }
                    List<Group> tempGroupList = new ArrayList<>();
                    //sort the tempGroup in descending order
                    tempGroups.entrySet().stream()
                            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                            .forEach(x -> tempGroupList.add(x.getKey()));

                    List<Group> list = tempGroupList;

                    for (int i = 0; i < list.size(); i++) {
                        Group g = list.get(0);
                        addToGroup(g);  //add this group g to the dataList->groupList 1,2 or 3
                        addMetAndCookPair(g);   //mark all pair in this group as met
                        pairListTemp.removeAll(g.getPairs()); //remove all the pairs, which was grouped
                        list = list.stream()
                                .filter(x -> notContainsPairedPairs(x, g))
                                .collect(Collectors.toList()); //filter only
                    }
                }
            }

        }
        //increase counter, go to next Gang
        System.out.println("Group List Gang " + courseCounter );
        courseCounter++;
        //add the rest pair in the list to pairSuccessorList  or ParticipantSuccessorList
        addSuccessorWithoutDuplicate(invalidPair);
        addSuccessorWithoutDuplicate(pairListTemp);

    }

    private void addSuccessorWithoutDuplicate(List<Pair> invalidPair) {
        for (Pair p : invalidPair) {
            if (p.getIsPreMade()) {
                dataList.getPairList().remove(p);
                if (!dataList.getPairSuccessorList().getPairSuccessorList().contains(p)) {
                    dataList.getPairSuccessorList().addPair(p);
                }
            } else {
                if (!dataList.getParticipantSuccessorList().getParticipantSuccessorList().contains(p.getParticipant1()))
                    dataList.getParticipantSuccessorList().addParticipant(p.getParticipant1());
                if (!dataList.getParticipantSuccessorList().getParticipantSuccessorList().contains(p.getParticipant2()))
                    dataList.getParticipantSuccessorList().addParticipant(p.getParticipant2());
            }
        }
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
     * @param pair The pair for whom the best group is to be found.
     * @return Group, the best group for the given pair.
     */
    public Group makeBestGroup(Pair pair, List<Pair> candidates) {
        Group bestGroup = null;
        double bestScore = -1;

        List<Pair> unmatchedPairs = new ArrayList<>(candidates);

        // filter out all impossible candidates
        unmatchedPairs.remove(pair);
        unmatchedPairs.removeAll(pair.getVisitedPairs());

        if(unmatchedPairs.size()<2) {
            return bestGroup;
        }

        if (pair.getFoodPreference().equals(FOOD_PREFERENCE.meat))
            unmatchedPairs = unmatchedPairs.stream().filter(x -> !(x.getFoodPreference().equals(FOOD_PREFERENCE.vegan) || x.getFoodPreference().equals(FOOD_PREFERENCE.veggie))).collect(Collectors.toList());
        if (pair.getFoodPreference().equals(FOOD_PREFERENCE.vegan) || pair.getFoodPreference().equals(FOOD_PREFERENCE.veggie))
            unmatchedPairs = unmatchedPairs.stream().filter(x -> !x.getFoodPreference().equals(FOOD_PREFERENCE.meat)).collect(Collectors.toList());

        //O(n^2)
        for (int i = 0; i < unmatchedPairs.size() - 1; i++) {
            for (int j = i + 1; j < unmatchedPairs.size(); j++) {
                Pair pair1 = unmatchedPairs.get(i);
                Pair pair2 = unmatchedPairs.get(j);
                if (pair1.getFoodPreference().equals(FOOD_PREFERENCE.meat) && (pair2.getFoodPreference().equals(FOOD_PREFERENCE.vegan) || pair2.getFoodPreference().equals(FOOD_PREFERENCE.veggie))) {
                    continue;
                }
                if (pair2.getFoodPreference().equals(FOOD_PREFERENCE.meat) && (pair1.getFoodPreference().equals(FOOD_PREFERENCE.vegan) || pair1.getFoodPreference().equals(FOOD_PREFERENCE.veggie))) {
                    continue;
                }
                if (!pair1.getVisitedPairs().contains(pair2) && !pair2.getVisitedPairs().contains(pair1)) {
                    Group tempGroup = new Group(pair, pair1, pair2, courseCounter);
                    double score = tempGroup.calculateWeightedScore();
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

        if (distanceToParty1 == 0 && distanceToParty2 == 0 && distanceToParty3 == 0){
            System.out.println("ERROR");
            return;
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
