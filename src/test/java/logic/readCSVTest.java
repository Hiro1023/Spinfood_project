package logic;

import models.Participant;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
class readCSVTest {
    readCSV test;

    String[] columm = {};
    String[] p1 = {"0","004670cb-47f5-40a4-87d8-5276c18616ec","Person1","veggie","21","male","maybe","3.0","8.673368271555807","50.5941282715558","","","",""};
    String[] p2 = {"2","01be5c1f-4aa1-458d-a530-b1c109ffbb55","Person3","vegan","22","male","yes","1.0","8.681372017093311","50.5820794170933","117ee996-14d3-44e8-8bcb-eb2d29fddda5","Personx1","25.0","male"};

    String[] p3 = {"3","01c1372d-d120-4459-9b65-39d56d1ad430","Person4","veggie","23","male","yes","1.0","8.683278559866123","50.58156255986612","ab81bb52-28b6-47dc-8d54-8d7c42ceaea1","Personx2","24.0","female"};
    List<String[]> participantList = new ArrayList<>();

    File participantCSV = new File("Resources/teilnehmerliste.csv");

    File partyLocCSV = new File("Resources/partylocation.csv");



    /**
     * Test for read_File()
     * Whether this method can read .csv File, is checked
     * Checking, whether all lines on table is readed
     * @throws Exception
     */
    @Test
    void read_FileTest_checkSumOfLinesOnTable() throws Exception{
        test = new readCSV();
        assertEquals(238,test.read_File(participantCSV).size());
    }

    /**
     * Test for read_File()
     * checking the Data of first Participant on the Table
     * @throws Exception
     */
    @Test
    void read_FileTest_checkFirstParticipantOnTable() throws Exception{
        assertEquals("Person1",new readCSV().read_File(participantCSV).get(1)[2]);

    }



    /**
     * Test for addParticipant
     * Checking count of all participant
     */
    @Test
    void addParticipantAndPairTest_checkSumOfAllparticipant() throws Exception {
        participantList.add(columm);
        participantList.add(p1);
        participantList.add(p2);
        test = new readCSV();
        test.addParticipantAndPair(participantList);
        assertEquals(3, test.event.getDataList().getParticipantList().size());
    }

    /**
     * Checking count of alone_participant
     * @throws Exception
     */
    @Test
    void addParticipantTest_checkSumOfAlone_participant() throws Exception {
        participantList.add(columm);
        participantList.add(p1);
        participantList.add(p2);
        test = new readCSV();
        test.addParticipantAndPair(participantList);
        assertEquals(1,test.event.getDataList().getUnpairedParticipants().size());
    }

    /**
     * Checking count of pair
     * 2 Pairs in list
     * @throws Exception
     */
    @Test
    void addParticipantTest_checkSumOfPair() throws Exception {
        participantList.add(columm);
        participantList.add(p1);
        participantList.add(p2);
        participantList.add(p3);
        test = new readCSV();
        test.addParticipantAndPair(participantList);
        assertEquals(2,test.event.getDataList().getPairList().size());
    }

    /**
     * Checking, whether result between count of all patricipant and sum of pair and alone_participant is equal
     * @throws Exception
     */
    @Test
    void addParticipantTest() throws Exception {
        participantList.add(columm);
        participantList.add(p1);
        participantList.add(p2);
        participantList.add(p3);
        test = new readCSV();
        test.addParticipantAndPair(participantList);
        assertEquals(test.event.getDataList().getParticipantList().size(),2*(test.event.getDataList().getPairList().size())+test.event.getDataList().getUnpairedParticipants().size());
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