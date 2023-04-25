import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
class readCSVTest {

    final int participantLength = 237;
    final String path = "/Users/tommy/Desktop/SoftwarePraktikum23/teilnehmerliste.csv";

    final String header = "[, ID, Name, FoodPreference, Age, Sex, Kitchen, Kitchen_Story, Kitchen_Longitude, Kitchen_Latitude, ID_2, Name_2, Age_2, Sex_2]";
    final String person01 = "[0, 004670cb-47f5-40a4-87d8-5276c18616ec, Person1, veggie, 21, male, maybe, 3.0, 8.673368271555807, 50.5941282715558, , , , ]";
    final String person50 = "[50, 2e4c6d4f-caf8-4b18-96c0-748a4011a738, Person51, vegan, 24, female, yes, , 8.668340710895189, 50.583463310895176, 4f3626bf-e765-4456-8c3d-d363f991114f, Personx24, 21.0, other]";
    final String person123 = "[123, 73eae26f-b26f-4d07-9fa0-5b279cbe93af, Person124, vegan, 27, male, no, 3.0, , , , , , ]";



    @org.junit.jupiter.api.Test
    void headerTest() throws Exception {
        assertEquals(header, Arrays.toString(readCSV.readCSV(new File(path)).get(0)));
    }

    @org.junit.jupiter.api.Test
    void participantCSV() throws Exception {
        assertEquals(person01, Arrays.toString(readCSV.readCSV(new File(path)).get(1)));
        assertEquals(person50, Arrays.toString(readCSV.readCSV(new File(path)).get(51)));
        assertEquals(person123, Arrays.toString(readCSV.readCSV(new File(path)).get(124)));
    }
    @Test
    void participantLengthTest() throws Exception{
        assertEquals(participantLength,readCSV.readCSV(new File(path)).size()-1); //-1 header
    }

}