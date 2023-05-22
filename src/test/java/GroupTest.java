import models.Group;
import models.Pair;
import models.Participant;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GroupTest {

    Participant p1 = new Participant("10","P1","meat","20","male","yes","2.0","1.1","51.2");
    Participant p2 = new Participant("11","P2","meat","30","female","yes","1.0","7.0","50.0");
    Participant p3 = new Participant("12","p3","veggie","60","female","no","","","");
    Participant p4 = new Participant("13","p4","veggie","15","other","no","","","");
    Participant p5 = new Participant("14","p5","vegan","15","other","no","","","");
    Participant p6 = new Participant("15","P6","none","34","male","yes","2.0","1.1","51.2");
    Participant p7 = new Participant("16","P7","none","23","male","yes","2.0","1.1","51.2");
    Pair pair1 = new Pair(p1,p2);
    Pair pair2 = new Pair(p3,p6);
    Pair pair3 = new Pair(p4,p7);

    /**
     * Test for calculateFoodMatchScore
     * This methode calculates the Sum of FoodMatchScore for all Pairs
     */
    @Test
    public void calculateFoodMatchScoreTest(){
        Group g = new Group(pair1,pair2,pair3);
        double calc = pair1.calculateFoodPreference() + pair2.calculateFoodPreference() + pair3.calculateFoodPreference();
        assertEquals(calc,g.calculateFoodPreference());
    }

    /**
     * Test for calculateFoodMatchScore
     * This methode calculates the Sum of genderdiversity for all Pairs
     */
    @Test
    public void calculateSexDiversityTest(){
        Group g = new Group(pair1,pair2,pair3);
        double calc = pair1.calculateSexDiversity() + pair2.calculateSexDiversity() + pair3.calculateSexDiversity();
        assertEquals(calc,g.calculateSexDiversity());
    }
}
