import controller.readCSV;
import models.Participant;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
class readCSVTest {
    readCSV test;

    String[] p1 = {"0","004670cb-47f5-40a4-87d8-5276c18616ec","Person1","veggie","21","male","maybe","3.0","8.673368271555807","50.5941282715558","","","",""};

    public readCSVTest() throws Exception {
        test = new readCSV();
    }

    /**
     * Test for read_File()
     * Whether this method can read .csv File, is checked
     * @throws Exception
     */
    @Test
    void read_FileTest() throws Exception{
        assertEquals(238,test.read_File(new File("Dokumentation/teilnehmerliste.csv")).size());
        assertArrayEquals(p1,test.read_File(new File("Dokumentation/teilnehmerliste.csv")).get(1));
    }


    /**
     * Test for addParticipant
     * Count of all participant, alone_participant and pair are checked
     */
    @Test
    void addParticipantTest() throws Exception {
        test.addParticipantAndPair(test.read_File(new File("Dokumentation/teilnehmerliste.csv")));
        assertEquals(310, test.countParticipant);
        assertEquals(164,test.alone_participant.size());
        assertEquals(73,test.countPair);
        assertEquals(test.countParticipant,2*(test.countPair)+test.alone_participant.size());
    }





}