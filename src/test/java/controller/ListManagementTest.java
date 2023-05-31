package controller;

import models.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ListManagementTest {

    Participant p1 = new Participant("10","P1","meat","20","male","yes","2.0","1.1","51.2");
    Participant p2 = new Participant("11","P2","meat","30","female","yes","1.0","7.0","50.0");
    Participant p3 = new Participant("12","p3","veggie","60","female","no","","","");
    Participant p4 = new Participant("13","p4","none","15","female","no","","","");
    Participant p5 = new Participant("14","p5","vegan","15","other","no","","","");
    Participant p6 = new Participant("15","p6","veggie","15","male","yes","2.0","1.1","51.2");
    Participant p7 = new Participant("16","p7","none","85","female","no","","","");



    @Test
    void makeBestPairList_unpossible() {
        ListManagement listManagement = new ListManagement(new DataList(new Event()));
        listManagement.makeBestPairList();
        assertEquals(0,listManagement.dataList.pairList.size());
    }


    @Test
    void makeBestPairList() {
        ListManagement listManagement = new ListManagement(new DataList(new Event()));
        listManagement.dataList.addUnmatchedParticipantToList(p1);
        listManagement.dataList.addUnmatchedParticipantToList(p3);
        listManagement.dataList.addUnmatchedParticipantToList(p4);
        listManagement.dataList.addUnmatchedParticipantToList(p6);
        listManagement.makeBestPairList();
        assertEquals(p4,listManagement.dataList.pairList.get(0).getParticipant1());
        assertEquals(p6,listManagement.dataList.pairList.get(0).getParticipant2());
    }
    @Test
    void containsPairedParticipant() {
    }


    @Test
    void makeBestPair_noKitchen() {
        ListManagement listManagement = new ListManagement(new DataList(new Event()));
        listManagement.dataList.addUnmatchedParticipantToList(p3);
        listManagement.dataList.addUnmatchedParticipantToList(p5);
        assertEquals(null,listManagement.makeBestPair(p3));
    }
    @Test
    void makeBestPair_MeatWithVeggie() {
        ListManagement listManagement = new ListManagement(new DataList(new Event()));
        listManagement.dataList.addUnmatchedParticipantToList(p1);
        listManagement.dataList.addUnmatchedParticipantToList(p3);
        assertEquals(null,listManagement.makeBestPair(p1));
    }

    @Test
    void makeBestPair_VeganWithVeggie() {
        ListManagement listManagement = new ListManagement(new DataList(new Event()));
        listManagement.dataList.addUnmatchedParticipantToList(p6);
        listManagement.dataList.addUnmatchedParticipantToList(p3);
        assertEquals("p3",listManagement.makeBestPair(p6).getParticipant2().getName());
    }

    /**
     * Build bestPair with meat and none
     * Other Participants are Veggie or Vegan
     * They have unmatched foodpreference with meat
     */
    @Test
    void makeBestPair_checkWithFoodPreference() {
        ListManagement listManagement = new ListManagement(new DataList(new Event()));
        listManagement.dataList.addUnmatchedParticipantToList(p1);
        listManagement.dataList.addUnmatchedParticipantToList(p3);
        listManagement.dataList.addUnmatchedParticipantToList(p4);
        listManagement.dataList.addUnmatchedParticipantToList(p5);
        String partner = listManagement.makeBestPair(p1).getParticipant2().getID();

        assertEquals(p4.getID(),partner);
    }

    /**
     * Build bestPair with meat(P1) and none
     * Two Participants hat foodPeference none
     * But ageDifference with p1 are so much
     */
    @Test
    void makeBestPair_checkWithAgeDiffeence() {
        ListManagement listManagement = new ListManagement(new DataList(new Event()));
        listManagement.dataList.addUnmatchedParticipantToList(p1);
        listManagement.dataList.addUnmatchedParticipantToList(p4);
        listManagement.dataList.addUnmatchedParticipantToList(p7);
        String partner = listManagement.makeBestPair(p1).getParticipant2().getID();
        assertEquals(p4.getID(),partner);
    }


    DataList dataList = new DataList(new Event());
    ListManagement lm = new ListManagement(dataList);

    Participant pp1 = new Participant("10","P1","meat","20","male","yes","2.0","1.1","51.2");
    Participant pp2 = new Participant("11","P2","meat","30","female","yes","1.0","7.0","50.0");
    Participant pp3 = new Participant("12","P3","veggie","60","female","no","","","");
    Participant pp4 = new Participant("13","P4","none","15","other","no","","","");
    Participant pp5 = new Participant("14","P5","meat","15","other","no","","","");

    Participant pp6 = new Participant("15","P6","meat","20","male","yes","2.0","1.1","51.2");
    Participant pp7 = new Participant("16","P7","none","30","female","yes","1.0","7.0","50.0");
    Participant pp8 = new Participant("17","P8","veggie","60","female","yes","2.0","4.4","50.7");

    Pair pair1 = new Pair(pp1,pp4);
    Pair pair2 = new Pair(pp2,pp6);
    Pair pair3 = new Pair(pp3,pp8);
    Pair pair4 = new Pair(pp5,pp7);
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

        Group expectedGroup01 = new Group(pair1,pair2,pair4);
        Group expectedGroup02 = new Group(pair3,pair5,pair6);
        List<Group> expectedGroupList= new ArrayList<>();
        expectedGroupList.add(expectedGroup01);
        expectedGroupList.add(expectedGroup02);

        int count = 0;

        for (Group g : lm.dataList.groupListCourse01) {
            for (int i = 0; i < expectedGroupList.size(); i++) {
                if (g.equal(expectedGroupList.get(i))) {
                    count++;
                    break;
                }
            }
        }
        assertEquals(count,expectedGroupList.size());
    }

    @Test
    void makeBestGroupListInvalidPairList() {
        Participant b1 = new Participant("20","Px1","vegan","45","male","yes","2.0","13.1","35.2");
        Participant b2 = new Participant("21","Px2","none","26","female","no","","","");
        Participant b3 = new Participant("22","Px3","none","30","male","yes","2.0","121.1","37.4");
        Participant b4 = new Participant("23","Px4","veggie","50","female","no","","","");
        Pair pair5 = new Pair(b1,b4);
        Pair pair6 = new Pair(b2,b3);
        lm.dataList.pairList.add(pair1);
        lm.dataList.pairList.add(pair5);
        lm.dataList.pairList.add(pair6);

        lm.makeBestGroupList();

        assertTrue(lm.dataList.groupListCourse01.isEmpty());
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

}