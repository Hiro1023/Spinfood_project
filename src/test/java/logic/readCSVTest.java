package logic;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
class readCSVTest {
    readCSV test;

    String[] p1 = {"0","004670cb-47f5-40a4-87d8-5276c18616ec","Person1","veggie","21","male","maybe","3.0","8.673368271555807","50.5941282715558","","","",""};

    public readCSVTest() {
        test = new readCSV();
    }

    /**
     * Test for read_File()
     * Whether this method can read .csv File, is checked
     * Checking, whether all lines on table is read
     * @throws Exception if the file is not found
     */
    @Test
    void read_FileTest_checkSumOfLinesOnTable() throws Exception{
        assertEquals(238,test.read_File(new File("Dokumentation/teilnehmerliste.csv")).size());
    }

    /**
     * Test for read_File()
     * checking the Data of first Participant on the Table
     * @throws Exception if the file is not found
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
    void addParticipantTest_checkSumOfAllParticipant() throws Exception {
        test.addParticipantAndPair(test.read_File(new File("Dokumentation/teilnehmerliste.csv")));
        assertEquals(310, test.countParticipant);
    }

    /**
     * Checking count of alone_participant
     * @throws Exception if the file is not found
     */
    @Test
    void addParticipantTest_checkSumOfAlone_participant() throws Exception {
        test.addParticipantAndPair(test.read_File(new File("Dokumentation/teilnehmerliste.csv")));
        assertEquals(164,test.alone_participant.size());
    }

    /**
     * Checking count of pair
     * @throws Exception if the file is not found
     */
    @Test
    void addParticipantTest_checkSumOfPair() throws Exception {
        test.addParticipantAndPair(test.read_File(new File("Dokumentation/teilnehmerliste.csv")));
        assertEquals(73,test.countPair);
    }

    /**
     * Checking, whether result between count of all participant and sum of pair and alone_participant is equal
     * @throws Exception if the file is not found
     */
    @Test
    void addParticipantTest() throws Exception {
        test.addParticipantAndPair(test.read_File(new File("Dokumentation/teilnehmerliste.csv")));
        assertEquals(test.countParticipant,2*(test.countPair)+test.alone_participant.size());
    }


    @Test
    void addPartyLocationTest_checkLongitude(){
        List<String[]> dataCSV = new ArrayList<>();
        dataCSV.add(new String[]{"Longitude","Latitude"});
        dataCSV.add(new String[]{"5.0","6.0"});
        test.addPartyLocation(dataCSV);
        assertEquals("5.0",Double.toString(test.event.getPartyLongitude()));
    }

    @Test
    void addPartyLocationTest_checkLatitude(){
        List<String[]> dataCSV = new ArrayList<>();
        dataCSV.add(new String[]{"Longitude","Latitude"});
        dataCSV.add(new String[]{"5.0","6.0"});
        test.addPartyLocation(dataCSV);
        assertEquals("6.0",Double.toString(test.event.getPartyLatitude()));
    }






}