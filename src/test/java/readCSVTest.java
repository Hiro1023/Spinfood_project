import java.io.File;
import java.lang.reflect.Array;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
class readCSVTest {

    String path = "/Users/tommy/Desktop/SoftwarePraktikum23/teilnehmerliste.csv";

    String header = "[, ID, Name, FoodPreference, Age, Sex, Kitchen, Kitchen_Story, Kitchen_Longitude, Kitchen_Latitude, ID_2, Name_2, Age_2, Sex_2]";
    /*
    String person01 = ;
    String person50 = ;
    String person123 = ;


     */
    @org.junit.jupiter.api.Test
    void headerTest() throws Exception {
        assertEquals(header, Arrays.toString(readCSV.readCSV(new File(path)).get(0)));
    }

    @org.junit.jupiter.api.Test
    void readCSV() {






    }
}