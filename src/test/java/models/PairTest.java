package models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;



public class PairTest {

    Participant p1 = new Participant("10","P1","meat","20","male","yes","2.0","1.1","51.2");
    Participant p2 = new Participant("11","P2","meat","30","female","yes","1.0","7.0","50.0");
    Participant p3 = new Participant("12","p3","veggie","60","female","no","","","");
    Participant p4 = new Participant("13","p4","none","15","other","no","","","");
    Participant p5 = new Participant("14","p5","vegan","15","other","no","","","");

    /**
     * Test for calculatePairAgeDifference
     */
    @Test
    void calculatePairAgeDifference_return1(){
        Pair ageDiff = new Pair(p1,p5);
        assertEquals(1,ageDiff.calculateAgeDifference());
    }
    /**
     * Test for method "calculateSexDiversity"
     * with Female -> the Result 0.0
     * without Female -> the result 0.5
     */
    @Test
    void calculateSexDiversityTest_withFemale_return0(){
        Pair differentSex = new Pair(p1,p2);
        assertEquals(0.0,differentSex.calculateSexDiversity());
    }

    @Test
    void calculateSexDiversityTest_withoutFemale_return05(){
        Pair equalSex = new Pair(p1,p4);
        assertEquals(0.5,equalSex.calculateSexDiversity());
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

    @Test
    void calculateDistanceBetweenKitchens_eitherHasKitchen_return0(){
        Pair either = new Pair(p1,p3);
        assertEquals(0,either.calculatePathLength());
    }

    /**
     * Test for calculateFoodMatchScore()
     * Check all combination of foodPreference
     */

    @Test
    void foodMatchScore_meatAndMeat_return0(){
        Pair mm = new Pair(p1,p2);
        assertEquals(0.0,mm.calculateFoodPreference());
    }
    @Test
    void foodMatchScore_meatAndNone_return0() {
        Pair mn = new Pair(p1, p4);
        assertEquals(0.0, mn.calculateFoodPreference());
    }

    @Test
    void foodMatchScore_noneAndMeat_return0(){
        Pair nm = new Pair(p4,p1);
        Pair mn = new Pair(p1, p4);
        assertEquals(mn.calculateFoodPreference(),nm.calculateFoodPreference());
    }
    @Test
    void foodMatchScore_noneAndVegan_return2(){
        Pair n_vegan = new Pair(p4,p5);
        assertEquals(2.0,n_vegan.calculateFoodPreference());
    }
    @Test
    void foodMatchScore_noneAndVeggie_return1() {
        Pair n_veggie = new Pair(p4, p3);
        assertEquals(1.0, n_veggie.calculateFoodPreference());
    }
    @Test
    void foodMatchScore_noneAndNone_return0() {
        Pair nn = new Pair(p4, p4);
        assertEquals(0.0, nn.calculateFoodPreference());
    }


    @Test
    void  foodMatchScore_veganAndVegan_return0() {
        Pair vv = new Pair(p5, p5);
        assertEquals(0.0, vv.calculateFoodPreference());
    }

    @Test
    void  foodMatchScore_veganAndNone_return2() {
        Pair vn = new Pair(p5, p4);
        Pair nv = new Pair(p4, p5);
        assertEquals(vn.calculateFoodPreference(), nv.calculateFoodPreference());
    }

    @Test
    void calculateFoodMatchScore_veganAndVeggie_return1() {
        Pair veg_veggie = new Pair(p5, p3);
        assertEquals(1.0, veg_veggie.calculateFoodPreference());
    }

    /**
     * Test for calculatePairWeightedScore
     * Priority(Weight) of Criteria is default
     * Check numbers to three decimal places.
     * food_Preference : 200
     * age_difference : 100
     * gender_diversity : 50
     * path_length : 50
     */
    @Test
    void calculatePairWeightedScore_return114(){
        Pair pair = new Pair(p1,p2);
        System.out.print(Double.POSITIVE_INFINITY);
        assertEquals(11,Math.floor(pair.calculateWeightedScore()*1000));
    }

}
