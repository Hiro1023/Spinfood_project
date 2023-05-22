import models.Pair;
import models.Participant;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;



public class PairTest {

    Participant p1 = new Participant("10","P1","meat","20","male","yes","2.0","1.1","51.2");
    Participant p2 = new Participant("11","P2","meat","30","female","yes","1.0","7.0","50.0");
    Participant p3 = new Participant("12","p3","veggie","60","female","no","","","");
    Participant p4 = new Participant("13","p4","none","15","other","no","","","");
    Participant p5 = new Participant("14","p5","vegan","15","other","no","","","");

    /**
     * Test for method "calculateSexDiversity"
     * if sex of Pair is different, the result is 0.5
     * if there are equal, the result is 1.5
     */
    @Test
    void calculateSexDiversityTest_differentSex(){
        Pair differentSex = new Pair(p1,p4);
        assertEquals(0.5,differentSex.calculateSexDiversity());
    }

    @Test
    void calculateSexDiversityTest_equalSex(){
        Pair equalSex = new Pair(p3,p2);
        assertEquals(1.5,equalSex.calculateSexDiversity());
    }

    /**
     * Test for calculateDistanceBetweenKitchens
     * if only a participant has kitchen, this result is Double.Maxvalue(1.7976931348623157E308)
     */
    @Test
    void calculateDistanceBetweenKitchens_bothHasKitchen(){
        Pair both = new Pair(p1,p2);
        assertEquals(6.0,Math.floor(both.calculateDistanceBetweenKitchens()));
    }

    @Test
    void calculateDistanceBetweenKitchens_eitherHasKitchen(){
        Pair either = new Pair(p1,p3);
        assertEquals(Double.MAX_VALUE,either.calculateDistanceBetweenKitchens());
    }

    /**
     * Test for calculateFoodMatchScore()
     * Check all combination of foodPreference
     */

    @Test
    void calculateFoodMatchScore_meat_meat(){
        Pair mm = new Pair(p1,p2);
        assertEquals(3.0,mm.calculateFoodPreference());
    }
    @Test
    void calculateFoodMatchScore_meat_veggi(){
        Pair mveg = new Pair(p1,p3);
        assertEquals(1.0,mveg.calculateFoodPreference());
    }
    @Test
    void calculateFoodMatchScore_meat_vegan() {
        Pair mvegan = new Pair(p1, p5);
        assertEquals(0.0, mvegan.calculateFoodPreference());
    }
    @Test
    void calculateFoodMatchScore_meat_none() {
        Pair mn = new Pair(p1, p4);
        assertEquals(2.0, mn.calculateFoodPreference());
    }

    @Test
    void calculateFoodMatchScore_none_meat(){
        Pair nm = new Pair(p4,p1);
        Pair mn = new Pair(p1, p4);
        assertEquals(mn.calculateFoodPreference(),nm.calculateFoodPreference());
    }
    @Test
    void calculateFoodMatchScore_none_vegan(){
        Pair nveg = new Pair(p4,p5);
        assertEquals(2.0,nveg.calculateFoodPreference());
    }
    @Test
    void calculateFoodMatchScore_none_vegie() {
        Pair nveggi = new Pair(p4, p3);
        assertEquals(2.0, nveggi.calculateFoodPreference());
    }
    @Test
    void calculateFoodMatchScore_none_none() {
        Pair nn = new Pair(p4, p4);
        assertEquals(3.0, nn.calculateFoodPreference());
    }

    @Test
    void calculateFoodMatchScore_vegan_meat() {
        Pair vm = new Pair(p5, p1);
        Pair mv = new Pair(p1, p5);
        assertEquals(mv.calculateFoodPreference(), vm.calculateFoodPreference());
    }

    @Test
    void calculateFoodMatchScore_vegan_vegan() {
        Pair vv = new Pair(p5, p5);
        assertEquals(vv.calculateFoodPreference(), vv.calculateFoodPreference());
    }

    @Test
    void calculateFoodMatchScore_vegan_none() {
        Pair vn = new Pair(p5, p4);
        Pair nv = new Pair(p4, p5);
        assertEquals(vn.calculateFoodPreference(), nv.calculateFoodPreference());
    }

    @Test
    void calculateFoodMatchScore_vegan_veggie() {
        Pair vvegi = new Pair(p5, p3);
        assertEquals(1.0, vvegi.calculateFoodPreference());
    }

    /**
     * Test for calculatePairWeightedScore
     * Priority(Weight) of Criteria is default
     * food_Prefernce : 20
     * age_difference : 10
     * gender_diversity : 5
     * path_length : 5
     */
    @Test
    void calculatePairWeightedScore(){
        Pair pair = new Pair(p1,p2);
        double calc = pair.calculateFoodPreference()*20 + pair.calculatePairAgeDifference()*10
                + pair.calculateSexDiversity()*5 + pair.calculateDistanceBetweenKitchens()*5;
        assertEquals(calc,pair.calculatePairWeightedScore());
    }

}
