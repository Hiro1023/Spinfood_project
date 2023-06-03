package models;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ParticipantTest {

    //Participant pError = new Participant("13","p4","none","-1","other","no","","","");
    Participant p0 = new Participant("13","p4","none","0","other","no","","","");
    Participant p1 = new Participant("10","P1","meat","17","male","yes","2.0","1.1","51.2");
    Participant p2 = new Participant("11","P2","meat","23","female","yes","1.0","7.0","50.0");
    Participant p3 = new Participant("12","p3","veggie","24","female","no","","","");
    Participant p4 = new Participant("13","p4","none","31","other","no","","","");
    Participant p5 = new Participant("14","p5","vegan","36","other","no","","","");
    Participant p6 = new Participant("15","P6","meat","41","male","yes","2.0","1.1","51.2");
    Participant p7 = new Participant("11","P2","meat","42","female","yes","1.0","7.0","50.0");
    Participant p8 = new Participant("12","p3","veggie","46","female","no","","","");
    Participant p9 = new Participant("13","p4","none","47","other","no","","","");
    Participant p10 = new Participant("10","P1","meat","56","male","yes","2.0","1.1","51.2");
    Participant p11 = new Participant("11","P2","meat","57","female","yes","1.0","7.0","50.0");
    Participant p12 = new Participant("12","p3","veggie","80","female","no","","","");
    Participant p13 = new Participant("12","p3","veggie","28","female","no","","","");
    Participant p14 = new Participant("12","p3","veggie","30","female","no","","","");


    /**
     * Test for AgeRange
     */
    @Test
    public void ageRangeTest_age0_return0(){
        assertEquals(0,p0.getAgerange().getValue());
    }
    @Test
    public void ageRangeTest_age17_return0(){
        assertEquals(0,p1.getAgerange().getValue());
    }
    @Test
    public void ageRangeTest_age23_return1(){
        assertEquals(1,p2.getAgerange().getValue());
    }
    @Test
    public void ageRangeTest_age24_return2(){
        assertEquals(2,p3.getAgerange().getValue());
    }
    @Test
    public void ageRangeTest_age28_return3(){
        assertEquals(3,p13.getAgerange().getValue());
    }
    @Test
    public void ageRangeTest_age30_return3(){
        assertEquals(3,p14.getAgerange().getValue());
    }
    @Test
    public void ageRangeTest_age31_return4(){
        assertEquals(4,p4.getAgerange().getValue());
    }
    @Test
    public void ageRangeTest_age36_return5(){
        assertEquals(5,p5.getAgerange().getValue());
    }
    @Test
    public void ageRangeTest_age41_return5(){
        assertEquals(5,p6.getAgerange().getValue());
    }
    @Test
    public void ageRangeTest_age42_return6(){
        assertEquals(6,p7.getAgerange().getValue());
    }
    @Test
    public void ageRangeTest_age46_return6(){
        assertEquals(6,p8.getAgerange().getValue());
    }
    @Test
    public void ageRangeTest_age47_return7(){
        assertEquals(7,p9.getAgerange().getValue());
    }
    @Test
    public void ageRangeTest_age56_return7(){
        assertEquals(7,p10.getAgerange().getValue());
    }
    @Test
    public void ageRangeTest_age57_return8(){
        assertEquals(8,p11.getAgerange().getValue());
    }

    @Test
    public void ageRangeTest_age80_return8(){
        assertEquals(8,p12.getAgerange().getValue());
    }

    /**
     * Test for assignFoodPreference
     */
    @Test
    public void assignFoodPreferenceTest_returnFOOD_PREFERENCEMeat(){
        assertEquals(FOOD_PREFERENCE.meat,p1.getFoodPreference());
    }
    @Test
    public void assignFoodPreferenceTest_returnFOOD_PREFERENCENone(){
        assertEquals(FOOD_PREFERENCE.none,p0.getFoodPreference());
    }
    @Test
    public void assignFoodPreferenceTest_returnFOOD_PREFERENCEVeggie(){
        assertEquals(FOOD_PREFERENCE.veggie,p3.getFoodPreference());
    }
    @Test
    public void assignFoodPreferenceTest_returnFOOD_PREFERENCEVegan(){
        assertEquals(FOOD_PREFERENCE.vegan,p5.getFoodPreference());
    }
}
