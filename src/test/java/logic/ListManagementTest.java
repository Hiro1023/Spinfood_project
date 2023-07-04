package logic;

import models.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ListManagementTest {

    DataList dataList = new DataList(new Event());
    ListManagement lm = new ListManagement(dataList);
    Participant p1 = new Participant("10","P1","meat","20","male","yes","2.0","1.1","51.2");
    Participant p2 = new Participant("11","P2","meat","30","female","yes","1.0","7.0","50.0");
    Participant p3 = new Participant("12","p3","veggie","60","female","no","","","");
    Participant p4 = new Participant("13","p4","none","20","female","no","","","");
    Participant p5 = new Participant("14","p5","vegan","15","other","no","","","");
    Participant p6 = new Participant("15","p6","veggie","15","male","yes","2.0","1.1","51.2");
    Participant p7 = new Participant("16","p7","none","85","female","no","","","");

    Participant p8 = new Participant("10","P11","meat","20","male","yes","2.0","1.1","51.2");
    Participant p9 = new Participant("11","P21","meat","30","female","yes","1.0","7.0","50.0");
    Participant p10 = new Participant("12","P31","veggie","60","female","no","","","");
    Participant p11 = new Participant("13","P41","none","15","other","no","","","");
    Participant p12 = new Participant("14","P51","meat","15","other","no","","","");

    Participant p13 = new Participant("15","P61","meat","20","male","yes","2.0","1.1","51.2");
    Participant p14 = new Participant("16","P71","none","30","female","yes","1.0","7.0","50.0");
    Participant p15 = new Participant("17","P81","veggie","60","female","yes","2.0","4.4","50.7");
    Participant p16 = new Participant("18","P82","veggie","60","female","yes","2.0","4.4","50.7");

    Participant p17 = new Participant("19","P821","none","60","female","yes","2.0","4.4","50.7");
    Participant p18 = new Participant("120","P823","none","60","female","yes","2.0","4.4","50.7");
    Pair pair1 = new Pair(p8, p11);
    Pair pair2 = new Pair(p9, p13);
    Pair pair3 = new Pair(p10, p15);
    Pair pair4 = new Pair(p12, p14);
    Pair pair5 = new Pair(p1,p2);  // FoodP : meat
    Pair pair6 = new Pair(p8,p9);  // FoodP : meat
    Pair pair11 = new Pair(p12,p13); // FoodP : meat
    Pair pair7 = new Pair(p5,p6);  // FoodP : vegan
    Pair pair8 = new Pair(p4,p11); // FoodP : none
    Pair pair9 = new Pair(p15,p16); // FoodP : veggie
    Pair pair10 = new Pair(p17,p18); // FoodP : none



    /**
     * Test for makeBestPairList
     */
    @Test
    void makeBestPairList_impossible() {
        ListManagement listManagement = new ListManagement(new DataList(new Event()));
        listManagement.makeBestPairList();
        assertEquals(0,listManagement.dataList.getPairList().size());
    }

    @Test
    void makeBestPairList_impossible_noKitchen() {
        ListManagement listManagement = new ListManagement(new DataList(new Event()));
        listManagement.dataList.addUnmatchedParticipantToList(p3);
        listManagement.makeBestPairList();
        assertEquals(0,listManagement.dataList.getPairList().size());
    }

    /**
     * Impossible because of foodP(meat and vegan)
     */
    @Test
    void makeBestPairList_impossible_FoodP() {
        ListManagement listManagement = new ListManagement(new DataList(new Event()));
        listManagement.dataList.addUnmatchedParticipantToList(p1);
        listManagement.dataList.addUnmatchedParticipantToList(p5);
        listManagement.makeBestPairList();
        assertEquals(0,listManagement.dataList.getPairList().size());
    }


    /**
     * Input :
     * p1 : foodP : meat
     * p3 : foodP : veggie
     * p4 : foodP : none
     * p6 : foodP : veggie
     */
    @Test
    void makeBestPairList_meatnone() {
        ListManagement listManagement = new ListManagement(new DataList(new Event()));
        listManagement.dataList.addUnmatchedParticipantToList(p1);
        listManagement.dataList.addUnmatchedParticipantToList(p3);
        listManagement.dataList.addUnmatchedParticipantToList(p4);
        listManagement.dataList.addUnmatchedParticipantToList(p6);
        listManagement.makeBestPairList();
        assertEquals(true,checkContainsPair(listManagement.dataList.getPairList(),new Pair(p1,p4)));
    }

    @Test
    void makeBestPairList_veggieveggie() {
        ListManagement listManagement = new ListManagement(new DataList(new Event()));
        listManagement.dataList.addUnmatchedParticipantToList(p1);
        listManagement.dataList.addUnmatchedParticipantToList(p3);
        listManagement.dataList.addUnmatchedParticipantToList(p4);
        listManagement.dataList.addUnmatchedParticipantToList(p6);
        listManagement.makeBestPairList();
        assertEquals(true,checkContainsPair(listManagement.dataList.getPairList(),new Pair(p3,p6)));
    }

    /**
     * Helper method for makeBestPairList to find the pair from pairList
     * @param pairList
     * @param p
     * @return if pairlist contains pair -> true
     */
    public boolean checkContainsPair(List<Pair> pairList, Pair p){
        String pName1 = p.getParticipant1().getName();
        String pName2 = p.getParticipant2().getName();

        for(Pair pair : pairList){
            if(pair.getParticipant1().getName().equals(pName1)){
                if(pair.getParticipant2().getName().equals(pName2)){
                    return true;
                }
            }
            if(pair.getParticipant1().getName().equals(pName2)){
                if(pair.getParticipant2().getName().equals(pName1)){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Test for addValidPairsIntoPairList
     * Making Pair with p1 and p4 is possible
     * size of pairList in Datalist is 1 after call this method
     */
    @Test
    void addValidPairsIntoPairList_bestPair(){
        lm.dataList.addUnmatchedParticipantToList(p1);
        lm.dataList.addUnmatchedParticipantToList(p4);
        lm.dataList.addUnmatchedParticipantToList(p6);
        List<Participant> candidates = new ArrayList<>();
        candidates.add(p4);
        List<Participant> partner = new ArrayList<>();
        partner.add(p1);
        partner.add(p6);
        lm.addValidPairsIntoPairList(candidates,partner);
        assertEquals(1,lm.dataList.getPairList().size());



    }



    /**
     * check method of makeBestPair
     */


    /**
     * Both doesn't have kitchen
     * Making Pair is impossible
     * list is empty after call this method
     */
    @Test
    void makeBestPair_noKitchen() {
        lm.dataList.addUnmatchedParticipantToList(p5);
        lm.makeBestPair(p3,lm.dataList.getUnpairedParticipants());
        assertEquals(0,lm.dataList.getPairList().size());
    }

    /**
     * Making Pair of meat and veggie
     * list is empty after call this method
     */
    @Test
    void makeBestPair_MeatWithVeggie() {
        ListManagement listManagement = new ListManagement(new DataList(new Event()));
        listManagement.dataList.addUnmatchedParticipantToList(p3);
        assertEquals(null,listManagement.makeBestPair(p1,listManagement.dataList.getUnpairedParticipants()));
    }

    /**
     * Making Pair of vegan and veggie
     * Either
     * list is empty after call this method
     */
    @Test
    void makeBestPair_VeganWithVeggie() {
        ListManagement listManagement = new ListManagement(new DataList(new Event()));
        listManagement.dataList.addUnmatchedParticipantToList(p3);
        assertEquals("p3",listManagement.makeBestPair(p5,listManagement.dataList.getUnpairedParticipants()).getParticipant2().getName());
    }

    /**
     * Build bestPair with meat and none
     * Other Participants are Veggie or Vegan
     * They have unmatched foodpreference with meat
     */
    @Test
    void makeBestPair_checkWithFoodPreference() {
        lm.dataList.addUnmatchedParticipantToList(p3);
        lm.dataList.addUnmatchedParticipantToList(p4);
        lm.dataList.addUnmatchedParticipantToList(p5);
        String partner = lm.makeBestPair(p1,lm.dataList.getUnpairedParticipants()).getParticipant2().getID();

        assertEquals(p4.getID(),partner);
    }

    /**
     * Build bestPair with meat(P1) and none
     * Two Participants hat foodPeference none
     * But ageDifference with p1 are so much
     */
    @Test
    void makeBestPair_checkWithAgeDiffeence() {
        lm.dataList.addUnmatchedParticipantToList(p1);
        lm.dataList.addUnmatchedParticipantToList(p4);
        lm.dataList.addUnmatchedParticipantToList(p7);
        String partner = lm.makeBestPair(p1,lm.dataList.getUnpairedParticipants()).getParticipant2().getID();
        assertEquals(p4.getID(),partner);
    }

    @Test
    void makeBestGroupListValidPairList() {
        Participant b1 = new Participant("20","Px1","vegan","45","male","yes","2.0","13.1","35.2");
        Participant b2 = new Participant("21","Px2","none","26","female","no","","","");
        Participant b3 = new Participant("22","Px3","none","30","male","yes","2.0","121.1","37.4");
        Participant b4 = new Participant("23","Px4","veggie","50","female","no","","","");
        Pair pair5 = new Pair(b1,b4);
        Pair pair6 = new Pair(b2,b3);
        lm.dataList.addPairToList(pair1);
        lm.dataList.addPairToList(pair2);
        lm.dataList.addPairToList(pair3);
        lm.dataList.addPairToList(pair4);
        lm.dataList.addPairToList(pair5);
        lm.dataList.addPairToList(pair6);

        lm.makeBestGroupList();
        assertEquals(2,lm.dataList.getGroupListCourse01().size());
    }

    @Test
    void makeBestGroupListInvalidPairList() {
        Participant b1 = new Participant("20","Px1","vegan","45","male","yes","2.0","13.1","35.2");
        Participant b2 = new Participant("21","Px2","none","26","female","no","","","");
        Participant b3 = new Participant("22","Px3","none","30","male","yes","2.0","121.1","37.4");
        Participant b4 = new Participant("23","Px4","veggie","50","female","no","","","");
        Pair pair5 = new Pair(b1,b4);
        Pair pair6 = new Pair(b2,b3);
        lm.dataList.getPairList().add(pair1);
        lm.dataList.getPairList().add(pair5);
        lm.dataList.getPairList().add(pair6);

        lm.makeBestGroupList();

        assertTrue(lm.dataList.getGroupListCourse01().isEmpty());
    }


    /**
     * pair1 cannot find a group, because there is only pair2 in the pair list
     * return null
     */
    @Test
    void makeBestGroup_invalidPairList() {
        lm.pairListTemp = new ArrayList<>();
        lm.pairListTemp.add(pair2);
        assertNull(lm.makeBestGroup(pair1));
    }

    /**
     * This test ensures that visited pairs will not be considered while forming a group
     */
    @Test
    void makeBestGroup_checkVisitedPair() {
        lm.pairListTemp = new ArrayList<>();
        lm.pairListTemp.add(pair5);
        lm.pairListTemp.add(pair6);
        lm.pairListTemp.add(pair8);
        pair11.getVisitedPairs().add(pair6);

        Group result = new Group(pair5,pair11,pair8,1);
        assertTrue(lm.makeBestGroup(pair11).equal(result));
    }

    /**
     * This test checks that Group with invalid Food Preference Pattern (meat, meat, veggie) will
     * not be formed
     */
    @Test
    void makeBestGroup_checkFoodPreference() {
        lm.pairListTemp = new ArrayList<>();
        lm.pairListTemp.add(pair5);
        lm.pairListTemp.add(pair6);

        assertNull(lm.makeBestGroup(pair7));
    }

    /**
     * This test checks that the method will find the best group for the chosen pair
     */
    @Test
    void makeBestGroup_optimalGroup() {
        lm.pairListTemp = new ArrayList<>();
        lm.pairListTemp.add(pair7);
        lm.pairListTemp.add(pair9);
        lm.pairListTemp.add(pair5);
        lm.pairListTemp.add(pair6);

        Group option1 = new Group(pair8,pair7,pair9,1);
        Group option2 = new Group(pair8,pair5,pair6,1);

        Group result = option1;
        if (option2.calculateWeightedScore() < result.calculateWeightedScore()) {
            result = option2;
        }

        Assertions.assertTrue(lm.makeBestGroup(pair8).equal(result));
    }
    /**
     * pair1 cannot find a group, because there is only pair2 in the pair list
     * return null
     */
    @Test
    void makeBestGroupCourse3_invalidPairList() {
        lm.unmatchedPairCourse3 = new ArrayList<>();
        lm.unCookedPairCourse3.add(pair2);
        assertNull(lm.makeBestGroupCourse3(pair1,lm.unmatchedPairCourse3));
    }

    /**
     * This test ensures that visited pairs will not be considered while forming a group
     */
    @Test
    void makeBestGroupCourse3_checkVisitedPair() {
        lm.unmatchedPairCourse3 = new ArrayList<>();
        lm.unmatchedPairCourse3.add(pair5);
        lm.unmatchedPairCourse3.add(pair6);
        lm.unmatchedPairCourse3.add(pair8);
        pair11.getVisitedPairs().add(pair6);

        Group result = new Group(pair5,pair11,pair8,1);
        assertTrue(lm.makeBestGroupCourse3(pair11,lm.unmatchedPairCourse3).equal(result));
    }

    /**
     * This test checks that Group with invalid Food Preference Pattern (meat, meat, veggie) will
     * not be formed
     */
    @Test
    void makeBestGroupCourse3_checkFoodPreference() {
        lm.unmatchedPairCourse3 = new ArrayList<>();
        lm.unmatchedPairCourse3.add(pair5);
        lm.unmatchedPairCourse3.add(pair6);

        assertNull(lm.makeBestGroupCourse3(pair7,lm.unmatchedPairCourse3));
    }

    /**
     * This test checks that the method will find the best group for the chosen pair
     */
    @Test
    void makeBestGroupCourse3_optimalGroup() {
        lm.unmatchedPairCourse3 = new ArrayList<>();
        lm.unmatchedPairCourse3.add(pair7);
        lm.unmatchedPairCourse3.add(pair9);
        lm.unmatchedPairCourse3.add(pair5);
        lm.unmatchedPairCourse3.add(pair6);

        Group option1 = new Group(pair8,pair7,pair9,1);
        Group option2 = new Group(pair8,pair5,pair6,1);

        Group result = option1;
        if (option2.calculateWeightedScore() < result.calculateWeightedScore()) {
            result = option2;
        }

        Assertions.assertTrue(lm.makeBestGroupCourse3(pair8,lm.unmatchedPairCourse3).equal(result));
    }


    @Test
    void makeBestGroupForMeatPair() {
        lm.pairListTemp = new ArrayList<>();
        lm.pairListTemp.add(pair1);
        lm.pairListTemp.add(pair2);
        lm.pairListTemp.add(pair3);
        lm.pairListTemp.add(pair4);

        // Invoke the method under test
        Group testGroup = lm.makeBestGroup(pair1);

        // Prepare expected result
        List<Pair> expectedResult = new ArrayList<>();
        expectedResult.add(pair1);
        expectedResult.add(pair2);
        expectedResult.add(pair4);

        // Convert to sets for unordered comparison
        Set<Pair> expectedSet = new HashSet<>(expectedResult);
        Set<Pair> resultSet = new HashSet<>(testGroup.getPairs());

        // Assert the expected result
        assertEquals(expectedSet, resultSet);
    }

    @Test
    void makeBestGroupForVeganOrVeggiePair() {
        Participant b1 = new Participant("20","Px1","vegan","45","male","yes","2.0","13.1","35.2");
        Participant b2 = new Participant("21","Px2","none","26","female","no","","","");
        Participant b3 = new Participant("22","Px3","none","30","male","yes","2.0","121.1","37.4");
        Participant b4 = new Participant("23","Px4","veggie","50","female","no","","","");
        Pair pair5 = new Pair(b1,b4);
        Pair pair6 = new Pair(b2,b3);
        lm.pairListTemp = new ArrayList<>();
        lm.pairListTemp.add(pair1);
        lm.pairListTemp.add(pair2);
        lm.pairListTemp.add(pair3);
        lm.pairListTemp.add(pair4);
        lm.pairListTemp.add(pair5);
        lm.pairListTemp.add(pair6);

        // Invoke the method under test
        Group testGroup = lm.makeBestGroup(pair3);

        // Prepare expected result
        List<Pair> expectedResult = new ArrayList<>();
        expectedResult.add(pair3);
        expectedResult.add(pair5);
        expectedResult.add(pair6);

        // Convert to sets for unordered comparison
        Set<Pair> expectedSet = new HashSet<>(expectedResult);
        Set<Pair> resultSet = new HashSet<>(testGroup.getPairs());

        // Assert the expected result
        assertEquals(expectedSet, resultSet);
    }

    @Test
    void MakeBestGroupNotEnoughPair() {
        lm.pairListTemp = new ArrayList<>();
        lm.pairListTemp.add(pair1);
        lm.pairListTemp.add(pair2);

        assertNull(lm.makeBestGroup(pair1));
    }



    /**
     * Test for checkValidFoodPreference
     * invalid Group -> Count of meat or none is two with one veggie or vegan
     */

    /**
     * FoodPreferences of each pairs are meat, meat and vegan
     * this group is invalid
     * return false
     */
    @Test
    void checkValidFoodPreferenceTest_invalidM(){
        Group g = new Group(pair5,pair6,pair7,1);  // meat meat vegan
        assertEquals(false,lm.checkValidFoodPreference(g));
    }

    /**
     * FoodPreferences of each pairs are none none Veggie
     * This group is invalid
     * return false
     */
    @Test
    void checkValidFoodPreferenceTest_invalidN(){
        Group g = new Group(pair8,pair9,pair10,1);
        assertEquals(false,lm.checkValidFoodPreference(g));
    }

    /**
     * FoodPreferences of each pairs are meat meat meat
     * This group is valid
     * return true
     */
    @Test
    void checkValidFoodPreferenceTest_valid3M(){
        Group g = new Group(pair5,pair6,pair11,1);
        assertEquals(true,lm.checkValidFoodPreference(g));
    }


    /**
     * FoodPreferences of each pairs are meat meat none
     * This group is valid
     * return true
     */
    @Test
    void checkValidFoodPreferenceTest_valid2M1N(){
        Group g = new Group(pair5,pair6,pair8,1);
        assertEquals(true,lm.checkValidFoodPreference(g));
    }

    /**
     * FoodPreferences of each pairs are meat none none
     * This group is valid
     * return true
     */
    @Test
    void checkValidFoodPreferenceTest_valid1M2N(){
        Group g = new Group(pair5,pair10,pair8,1);
        assertEquals(true,lm.checkValidFoodPreference(g));
    }


    /**
     * Test for addToGroup
     * add a group in course01
     * size of course01 is 1 after calling method
     */

    @Test
    void addToGroup(){
        Group g = new Group(pair5,pair10,pair8,1);
        lm.addToGroup(g);
        assertEquals(1,lm.dataList.getGroupListCourse01().size());
    }


    /**
     * Test for notContainsPairedPairs
     */

    /**
     * g1 and g2 contains the same pair (pair5)
     * return false
     */
    @Test
    void notContainsPairedPairs_false(){
        Group g1 = new Group(pair5,pair10,pair8,1);
        Group g2 = new Group(pair5,pair11,pair7,1);
        assertEquals(false,lm.notContainsPairedPairs(g1,g2));
    }

    /**
     * g1 and g2 doesn't contain same pair
     * return true
     */
    @Test
    void notContainsPairedPairs_true(){
        Group g1 = new Group(pair6,pair10,pair8,1);
        Group g2 = new Group(pair5,pair11,pair7,1);
        assertEquals(true,lm.notContainsPairedPairs(g1,g2));
    }


    /**
     * Test for findCookPair
     */

    /**
     * all pairs has benn already cooked in course01
     * return false
     */
    @Test
    void findCookPair_false(){
        pair6.setHasCooked(1);
        pair10.setHasCooked(1);
        pair8.setHasCooked(1);
        Group g = new Group(pair6,pair10,pair8,2);
        assertEquals(false,lm.findCookPair(g));
    }

    /**
     * pair6 has not cooked in course1
     * pair6 can cook in this course
     * return true
     */
    @Test
    void findCookPair_true(){
        pair10.setHasCooked(1);
        pair8.setHasCooked(1);
        Group g = new Group(pair6,pair10,pair8,2);
        assertEquals(true,lm.findCookPair(g));
    }

    /**
     * for course03
     * all pairs has benn cooked
     * add this pairs in unmatchedPairCourse03 list
     */
    @Test
    void findCookPair_false_course03(){
        lm.setCourseCounter(3);
        pair6.setHasCooked(1);
        pair10.setHasCooked(1);
        pair8.setHasCooked(3);
        Group g = new Group(pair6,pair10,pair8,3);
        lm.findCookPair(g);
        assertEquals(3,lm.unmatchedPairCourse3.size());
    }

    /**
     * for course03
     * but pair6 can cook
     * add this pairs in unmatchedPairCourse03 list
     */
    @Test
    void findCookPair_true_course03(){
        lm.setCourseCounter(3);
        pair10.setHasCooked(1);
        pair8.setHasCooked(3);
        Group g = new Group(pair6,pair10,pair8,3);
        assertEquals(true,lm.findCookPair(g));
    }





}