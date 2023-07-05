package models;

import models.Group;
import models.Pair;
import models.Participant;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The symbol 0c5 means 0.5 (c:comma)
 */

public class GroupTest {
    /**
     * create test participants for testing
     * each participant have different preference and information
     */
    Participant p1 = new Participant("10","P1","meat","20","male","yes","2.0","1.1","51.2");
    Participant p2 = new Participant("11","P2","meat","30","female","yes","1.0","7.0","50.0");
    Participant p3 = new Participant("12","p3","veggie","60","female","no","","","");
    Participant p4 = new Participant("13","p4","veggie","15","other","no","","","");
    Participant p5 = new Participant("14","p5","vegan","15","other","no","","","");
    Participant p6 = new Participant("15","P6","none","34","male","yes","2.0","1.1","51.2");
    Participant p7 = new Participant("16","P7","none","23","male","yes","2.0","1.1","51.2");
    Participant p8 = new Participant("17","P8","none","23","male","yes","5.0","3.1","41.2");
    Pair pair1 = new Pair(p1,p2);  // foodP : meat     AgeDiff : 10
    Pair pair2 = new Pair(p3,p6);  // foodP : veggie   AgeDiff: 26
    Pair pair3 = new Pair(p4,p7);  // foodP : veggie   AgeDiff : 8
    Pair pair4 = new Pair(p1,p6);
    Pair pair5 = new Pair(p4,p5);
    Pair pair6 = new Pair(p8,p7);
    Group g1 = new Group(pair1,pair2,pair3,1);

    MathContext mtx = new MathContext(3, RoundingMode.DOWN);  //Rounded down to the fourth decimal place
    BigDecimal foodPreference = new BigDecimal(g1.calculateFoodPreference());
    BigDecimal kitchenDistance = new BigDecimal(g1.calculatePathLength());

    /**
     * Test for calculateFoodMatchScore(meat(0),meat(0),veggie(2))
     * This methode calculates the Sum of FoodMatchScore for all Pairs
     */
    @Test
    public void calculateFoodMatchScoreTest_return0c666(){
        assertEquals("0.666",foodPreference.round(mtx).toString());
    }

    /**
     * Test for calculateSexdiversity when all pair in group have female
     * This methode calculates the Sum of genderdiversity for all Pairs
     */
    @Test
    public void sexDiversity_allPairHaveFemale_return0(){
        assertEquals(0.0,g1.calculateSexDiversity());
    }

    /**
     * Test for calculateSexDiversity
     * When one pair has female
     */
    @Test
    public void sexDiversity_onePairHasFemale_return2(){
        Group g2 = new Group(pair1,pair4,pair5,1);
        assertEquals(2.0,g2.calculateSexDiversity());
    }

    /**
     * Test for calculateSexDiversity
     * When 2 pairs have female
     */
    @Test
    public void sexDiversity_twoPairHaveFemale_return1(){
        Group g3 = new Group(pair1,pair2,pair5,1);
        assertEquals(1.0,g3.calculateSexDiversity());
    }

    /**
     * Test for calculateSexDiversity
     * when no pair have female
     */
    @Test
    public void sexDiversity_noFemale_return3(){
        Group g4 = new Group(pair6,pair4,pair5,1);
        assertEquals(3,g4.calculateSexDiversity());
    }

    /**
     * Test for calculateDistanceBetweenKitchens
     */
    @Test
    public void distanceBetweenKitchens_return437(){
        double calc = g1.getPairs().get(0).calculatePathLength()
                                            + g1.getPairs().get(1).calculatePathLength()
                                                                    + g1.getPairs().get(2).calculatePathLength();
        BigDecimal calc_decimal = new BigDecimal(calc);
        assertEquals(calc_decimal.round(mtx).toString(),kitchenDistance.round(mtx).toString());
    }

    /**
     * Test for calculateGroupAgeDifference (AgeDiff of each pairs : 10 , 26 , 8)
     */
    @Test
    public void pairAgeDifference(){assertEquals(14,Math.floor(g1.calculateAgeDifference()));}


    /**
     * Test for 2 pair if they are not equal
     */
    @Test
    public void equalTest_NotEqual(){
        Group g2 = new Group(pair1,pair4,pair5,1);
        assertEquals(false,g1.equal(g2));
    }

    /**
     * Test for 2 pair if they are  equal
     */
    @Test
    public void equalTest_Equal(){
        Group g2 = new Group(pair2,pair1,pair3,1);
        assertEquals(true,g1.equal(g2));
    }

}
