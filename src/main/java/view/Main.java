package view;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import logic.ListManagement;
import logic.readCSV;
import logicWrapper.DataListWrapper;
import models.Group;
import models.Pair;
import models.Participant;
import modelsWrapper.GroupWrapper;
import modelsWrapper.PairWrapper;
import modelsWrapper.ParticipantWrapper;

public class Main {
    static ObjectMapper objectMapper = new ObjectMapper();
    static String participantCSVFile = "Resources/teilnehmerliste.csv";
    static String partyLocationCSVFile = "Resources/partylocation.csv";
    static readCSV readCSV;
    static {
        try {
            readCSV = new readCSV(new File(participantCSVFile),new File(partyLocationCSVFile));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    static List<String[]> list;
    static {
        try {
            list = readCSV.read_File(new File(participantCSVFile));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    static ListManagement lm = new ListManagement(readCSV.event.getDataList());
    public static void showParticipantList(){
        for (Participant par: lm.dataList.getParticipantList()) {
            par.show();
            System.out.println("---------------------------");
        }
    }
    public static void showParticipantSuccessorList(){
        lm.dataList.getParticipantSuccessorList().getParticipantSuccessorList().forEach(Participant::show);
    }

    public static void showPairSuccesorList(){
        lm.dataList.getPairSuccessorList().getPairSuccessorList().forEach(Pair::show);
    }

    public static void showPairList(){
        lm.dataList.getPairList().forEach(Pair::show);
    }
    public static void showIsPreMadePair(){
        lm.dataList.getPairList().stream()
                .filter(pair -> pair.getIsPreMade())
                .forEach(Pair::show);
    }
    public static void showKitchenAddressAndParticipant(){
        for (String address : readCSV.AddressTable.keySet()) {
            System.out.println("Address: " + address);
            // Get the list of participants for the current address
            List<Participant> participants = readCSV.AddressTable.get(address);
            // Iterate over the participants in the list
            for(Participant participant : participants)
                System.out.println("Participant: " + participant.getName());
            // You can print other participant details here as needed
            System.out.println(); // Add an empty line between addresses
        }
    }
    public static void showGroupListGang01(){
        for (Group group : lm.dataList.getGroupListCourse01()) {
            group.show();
            System.out.println("-----------------------");
        }
    }
    public static void showGroupListGang02(){
        for (Group group : lm.dataList.getGroupListCourse02()) {
            group.show();
            System.out.println("-----------------------");
        }
    }
    public static void showGroupListGang03(){
        for (Group group : lm.dataList.getGroupListCourse03()) {
            group.show();
            System.out.println("-----------------------");
        }
    }
    public static void showVisitedListOfEachPair(){
        for(Pair pair: lm.dataList.getPairList()){
            pair.getVisitedPairs().forEach(Pair::show);
            System.out.println("--------------------------");
        }
    }
    public static void showHasCookedPair(){
        for (Pair p : lm.dataList.getPairList()) {
            p.show();
            System.out.println(p.getHasCooked());
            System.out.println("----------------------");
        }
    }
    public static void showUnmatchedParticipant(){
        for (Participant par: readCSV.event.getDataList().getUnmatchedParticipants()) {
            par.show();
            System.out.println("---------------------------");
        }
    }

    public static void exportToJsonFile(Object object, String filePath) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            objectMapper.writeValue(new File(filePath), object);
            System.out.println("JSON file exported successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Convert the list to JSON
    public static void writeFileToJson(List<?> list) {
        try {
            // Write the JSON to a file
            objectMapper.writeValue(new File("output2.json"),list);
            System.out.println("JSON file created successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**ListGang03();

        System.out.println(lm.dataList.getGroupListCourse01().size() + " " + lm.dataList.getGroupListCourse02().size() + " " + lm.dataList.getGroupListCourse03().size());
     * Description: main class is the entry point.
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
            lm.makeBestPairList();
            lm.makeBestGroupList();
            //showGroupListGang01();
            lm.makeBestGroupList();
            //showGroupListGang02();
            lm.makeBestGroupList();
            //showGroupListGang03();

        DataListWrapper dtw = new DataListWrapper(lm.dataList);
        exportToJsonFile(dtw,"Resources/output.json");
        exportToJsonFile(dtw.makeTable(),"Resources/output2.json");



            System.out.println("Size of all Groups");
            System.out.println(lm.dataList.getGroupListCourse01().size() + " " + lm.dataList.getGroupListCourse02().size() + " " + lm.dataList.getGroupListCourse03().size());
//
//        System.out.println("Successor List size : " + lm.dataList.getParticipantSuccessorList().getParticipantSuccessorList().size());
//
//        for (Pair p : lm.dataList.getPairList()) {
//            if (p.getVisitedPairs().size() < 6) {
//                p.show();
//            }
//        }
//        System.out.println("Participant List size :" + lm.dataList.getParticipantList().size());
//        System.out.println("Pair List size :" + lm.dataList.getPairList().size());
        showParticipantSuccessorList();
    }
}