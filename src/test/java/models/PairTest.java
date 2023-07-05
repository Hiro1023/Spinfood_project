package models;

import logic.DataList;
import logic.ListManagement;
import models.Pair;
import models.Participant;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;



public class PairTest {

    /**
     * create test participants for testing
     * each participant have different preference and information
     */
    Participant p1 = new Participant("10","P1","meat","20","male","yes","2.0","1.1","51.2");
    Participant p2 = new Participant("11","P2","meat","30","female","yes","1.0","7.0","50.0");
    Participant p3 = new Participant("12","p3","veggie","60","female","no","","","");
    Participant p4 = new Participant("13","p4","none","15","other","no","","","");
    Participant p5 = new Participant("14","p5","vegan","15","other","no","","","");
    Participant p6 = new Participant("15","p6","vegan","15","male","no","","","");
    Participant p7 = new Participant("34","p7","veggie","60","female","no","","","");


    /**
     * Test for find Food preference for p1, p4
     */
    @Test
    void findFoodPreference_P1withNone(){
        Pair withNone = new Pair(p1,p4);
        assertEquals(FOOD_PREFERENCE.meat,withNone.getFoodPreference());
    }

    /**
     *  Test for find Food preference for p4, p1
     *  one of the Participant has FP als none
     */
    @Test
    void findFoodPreference_P2withNone(){
        Pair withNone = new Pair(p4,p1);
        assertEquals(FOOD_PREFERENCE.meat,withNone.getFoodPreference());
    }

    /**
     *  Test for find Food preference for p2, p1
     *  both have FP als meat
     */
    @Test
    void findFoodPreference_withMeat(){
        Pair withMeat = new Pair(p2,p1);
        assertEquals(FOOD_PREFERENCE.meat,withMeat.getFoodPreference());
    }

    /**
     *  Test for find Food preference for p3, p5
     *  both have FP als vegan
     */
    @Test
    void findFoodPreference_withVegan(){
        Pair withMeat = new Pair(p3,p5);
        assertEquals(FOOD_PREFERENCE.vegan,withMeat.getFoodPreference());
    }

    /**
     *  Test for find Food preference for p3, p5
     *  both have FP als veggie
     */
    @Test
    void findFoodPreference_onlyVeggie(){
        Pair withMeat = new Pair(p3,p7);
        assertEquals(FOOD_PREFERENCE.veggie,withMeat.getFoodPreference());
    }


    /**
     * Test for calculatePairAgeDifference
     */
    @Test
    void calculatePairAgeDifference_return1(){
        Pair ageDiff = new Pair(p1,p2);
        assertEquals(10,ageDiff.calculateAgeDifference());
    }
    /**
     * Test for method "calculateSexDiversity"
     * with Female -> the Result 0.0
     * without Female -> the result 1.0
     */
    @Test
    void calculateSexDiversityTest_withFemale_return0(){
        Pair differentSex = new Pair(p1,p2);
        assertEquals(0.0,differentSex.calculateSexDiversity());
    }

    /**
     * test for calculateSexDiversity
     * pair without female
     */
    @Test
    void calculateSexDiversityTest_withoutFemale_return1(){
        Pair equalSex = new Pair(p1,p6);
        assertEquals(1.0,equalSex.calculateSexDiversity());
    }

    /**
     * Test for calculateDistanceBetweenKitchens
     * two kitchen -> the result of calculation with Haversine formula
     * one kitchen -> the result  0
     */
    @Test
    void calculateDistanceBetweenKitchens_bothHasKitchen_return437(){
        Pair both = new Pair(p1,p2);
        assertEquals(437,Math.floor(both.calculatePathLength()));
    }

    /**
     * test for calculatePathLength
     * both participant have kitchen
     */
    @Test
    void calculateDistanceBetweenKitchens_eitherHasKitchen_return0(){
        Pair either = new Pair(p1,p3);
        assertEquals(0,either.calculatePathLength());
    }

    /**
     * Test for calculateFoodMatchScore
     * Check all combination of foodPreference
     */
    @Test
    void foodMatchScore_meatmeat_return0(){
        Pair mm = new Pair(p1,p2);
        assertEquals(0.0,mm.calculateFoodPreference());
    }
    @Test
    void foodMatchScore_meatnone_return0() {
        Pair mn = new Pair(p1, p4);
        assertEquals(0.0, mn.calculateFoodPreference());
    }

    @Test
    void foodMatchScore_nonemeat_return0(){
        Pair nm = new Pair(p4,p1);
        Pair mn = new Pair(p1, p4);
        assertEquals(mn.calculateFoodPreference(),nm.calculateFoodPreference());
    }
    @Test
    void foodMatchScore_nonevegan_return2(){
        Pair nveg = new Pair(p4,p5);
        assertEquals(2.0,nveg.calculateFoodPreference());
    }
    @Test
    void foodMatchScore_nonevegie_return1() {
        Pair nveggi = new Pair(p4, p3);
        assertEquals(1.0, nveggi.calculateFoodPreference());
    }
    @Test
    void foodMatchScore_nonenone_return0() {
        Pair nn = new Pair(p4, p4);
        assertEquals(0.0, nn.calculateFoodPreference());
    }


    @Test
    void  foodMatchScore_veganvegan_return0() {
        Pair vv = new Pair(p5, p5);
        assertEquals(0.0, vv.calculateFoodPreference());
    }

    @Test
    void  foodMatchScore_vegannone_return2() {
        Pair vn = new Pair(p5, p4);
        Pair nv = new Pair(p4, p5);
        assertEquals(vn.calculateFoodPreference(), nv.calculateFoodPreference());
    }

    @Test
    void calculateFoodMatchScore_veganveggie_return1() {
        Pair vvegi = new Pair(p5, p3);
        assertEquals(1.0, vvegi.calculateFoodPreference());
    }

    /**
     * Test for calculatePairWeightedScore
     * Priority(Weight) of Criteria is default
     * Check numbers to three decimal places.
     * food_Prefernce : 2000
     * age_difference : 100
     * gender_diversity : 50
     * path_length : 100
     * Each Score of the pair (p1 and p2)
     * - foodpreference 0
     * - betweenKitchen 432
     * - sexDiversity 0
     * - ageDiff 10
     */
    @Test
    void calculatePairWeightedScore_return114(){
        Pair pair = new Pair(p1,p2);

        assertEquals(44712,Math.floor(pair.calculateWeightedScore()));
    }

}
