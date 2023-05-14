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
     * Checking, whether all lines on table is readed
     * @throws Exception
     */
    @Test
    void read_FileTest_checkSumOfLinesOnTable() throws Exception{
        assertEquals(238,test.read_File(new File("Dokumentation/teilnehmerliste.csv")).size());
    }

    /**
     * Test for read_File()
     * checking the Data of first Participant on the Table
     * @throws Exception
     */
    @Test
    void read_FileTest_checkFirstParticipantOnTable() throws Exception{
        assertArrayEquals(p1,test.read_File(new File("Dokumentation/teilnehmerliste.csv")).get(1));
    }


    /**
     * Test for addParticipant
     * Checking count of all participant
     */
    @Test
    void addParticipantTest_checkSumOfAllparticipant() throws Exception {
        test.addParticipantAndPair(test.read_File(new File("Dokumentation/teilnehmerliste.csv")));
        assertEquals(310, test.countParticipant);
    }

    /**
     * Checking count of alone_participant
     * @throws Exception
     */
    @Test
    void addParticipantTest_checkSumOfAlone_participant() throws Exception {
        test.addParticipantAndPair(test.read_File(new File("Dokumentation/teilnehmerliste.csv")));
        assertEquals(164,test.alone_participant.size());
    }

    /**
     * Checking count of pair
     * @throws Exception
     */
    @Test
    void addParticipantTest_checkSumOfPair() throws Exception {
        test.addParticipantAndPair(test.read_File(new File("Dokumentation/teilnehmerliste.csv")));
        assertEquals(73,test.countPair);
    }

    /**
     * Checking, whether result between count of all patricipant and sum of pair and alone_participant is equal
     * @throws Exception
     */
    @Test
    void addParticipantTest() throws Exception {
        test.addParticipantAndPair(test.read_File(new File("Dokumentation/teilnehmerliste.csv")));
        assertEquals(test.countParticipant,2*(test.countPair)+test.alone_participant.size());
    }






}