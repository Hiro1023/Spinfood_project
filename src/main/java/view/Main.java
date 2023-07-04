package view;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import logic.JsonExport;
import logic.ListManagement;
import logic.readCSV;
import models.Group;
import models.Pair;
import models.Participant;
import org.json.JSONObject;

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

    public static void showPairList(){
        System.out.println("pair List size: " + lm.dataList.getPairList().size());
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
            pair.show();
            System.out.println("visit:");
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

    public static int showHasntCookedPair(){
        int counter = 0;
        for (Pair pair: lm.dataList.getPairList()) {
            if(pair.getHasCooked().isEmpty()){
                counter++;
            }
        }
        return counter;
    }
    public static void showUnmatchedParticipant(){
        for (Participant par: readCSV.event.getDataList().getUnpairedParticipants()) {
            par.show();
            System.out.println("---------------------------");
        }
    }

    public static List<Pair> unmatchedAfterGang3 = new ArrayList<>();

    //Convert the list to JSON
    public static void writeFileToJson(List<?> list) {
        try {
            // Write the JSON to a file
            objectMapper.writeValue(new File("output2.json"), list);
            System.out.println("JSON file created successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Description: main class is the entry point.
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        /*
        //Create a file chooser dialog to select the CSV file
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(null);
        if (result != JFileChooser.APPROVE_OPTION) return; // User canceled or closed the dialog
        File selectedFile = fileChooser.getSelectedFile();
         */

        lm.pairAndGroupBuilding();

        System.out.println("Gang 1:------------------------------------------------------------");
        showGroupListGang01();
        System.out.println("Gang 2:------------------------------------------------------------");
        showGroupListGang02();
        System.out.println("Gang 3:------------------------------------------------------------");
        showGroupListGang03();

        System.out.println("size:"+lm.dataList.getGroupListCourse01().size()+""+lm.dataList.getGroupListCourse02().size()+""+lm.dataList.getGroupListCourse03().size());

        showParticipantSuccessorList();

        try {
            JsonExport jsonExport = new JsonExport();
            JSONObject jsonData = lm.dataList.toJson();
            lm.dataList.exportToJsonFile("output.json");
            Path p = Paths.get("../Resources/result_schema.json");
            System.out.println("output file created successfully!!");
            //jsonExport.validateJson("output.json", p.toString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        /*
        System.out.println("Cannot find any group: ");
        lm.cannotFindGroup.forEach(Pair::show);
        System.out.println("hasn't cooked pair: " + showHasntCookedPair());
        lm.pairDidntCookGroup.forEach(x -> x.show());
        System.out.println("all cooked group: " + lm.allCookedGroup.size());
        System.out.println("showHasn'tCookedPair:");
        int meat =0, none =0,veggie=0,vegan=0;
        for (Pair pair: lm.dataList.getPairList()) {
            if(pair.getHasCooked().isEmpty()){
                pair.show();
                System.out.println(pair.getHasCooked());
                System.out.println(pair.getFoodPreference());
                if(pair.getFoodPreference().equals(FOOD_PREFERENCE.meat))
                    meat++;
                else if(pair.getFoodPreference().equals(FOOD_PREFERENCE.none))
                    none++;
                else if (pair.getFoodPreference().equals(FOOD_PREFERENCE.veggie))
                    veggie++;
                else if (pair.getFoodPreference().equals(FOOD_PREFERENCE.vegan))
                    vegan++;
            }
        }

        System.out.println("allCookedPair:");
        for (Group group: lm.allCookedGroup) {
            group.show();
            System.out.println(group.getFoodPreference());
        }
        System.out.println("meat, none, veggie, vegan: " + meat +","+none+","+veggie+","+vegan);

        System.out.println("lm.pairDidntCookGroup.size(): "+lm.pairDidntCookGroup.size());
        System.out.println("lm.allCookedGroup.size(): "+lm.allCookedGroup.size());

        System.out.println("lm.pairDidntCookGroup: ");
        lm.pairDidntCookGroup.forEach(x -> x.show());
        System.out.println("lm.pairDidntCookList: ");
        for (Pair p : lm.dataList.getPairList()) {
            if(p.getHasCooked().isEmpty())
                p.show();
        }

        System.out.println("-------");
        //System.out.println("Show unmatchedPairGang 3, size: " + lm.unmatchedPairGang3.size());
        //lm.unmatchedPairGang3.forEach(x -> x.show());
        //System.out.println("-------");
        //System.out.println("Show unCookedPairGang 3, size: " + lm.unCookedPairGang3.size());
        //lm.unmatchedPairGang3.forEach(x -> x.show());
         */




    }



}