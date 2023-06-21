package view;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import logic.JsonExport;
import logic.ListManagement;
import logic.readCSV;
import models.Group;
import models.Pair;
import models.Participant;
import org.json.JSONObject;

public class Main {
    //    static ObjectMapper objectMapper = new ObjectMapper();
    static String participantCSVFile = "Resources/teilnehmerliste.csv";
    static String partyLocationCSVFile = "Resources/partylocation.csv";
    static readCSV readCSV;
    static List<String[]> list;
    static ListManagement lm;

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please enter the participant CSV file path:");
        String participantCSVFile = scanner.nextLine();

        System.out.println("Please enter the party location CSV file path:");
        String partyLocationCSVFile = scanner.nextLine();

        try {
            readCSV = new readCSV(new File(participantCSVFile), new File(partyLocationCSVFile));
            list = readCSV.read_File(new File(participantCSVFile));
            lm = new ListManagement(readCSV.event.getDataList());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        lm.makeBestPairList();
        lm.makeBestGroupList();
        lm.makeBestGroupList();
        lm.makeBestGroupList();
        showGroupListGang03();

//        DataListWrapper dtw = new DataListWrapper(lm.dataList);
//        exportToJsonFile(dtw, "Resources/output.json");
//        exportToJsonFile(dtw.makeTable(), "Resources/output2.json");

        try {
            JsonExport jsonExport = new JsonExport();
            JSONObject jsonData = lm.dataList.toJson();
            lm.dataList.exportToJsonFile("output.json");
            Path p = Paths.get("../Resources/result_schema.json");
            System.out.println("output file created succesfully!!");
//            jsonExport.validateJson("output.json", p.toString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        System.out.println("Size of all Groups");
        System.out.println(lm.dataList.getGroupListCourse01().size() + " " + lm.dataList.getGroupListCourse02().size() + " " + lm.dataList.getGroupListCourse03().size());
        showParticipantSuccessorList();
    }

    public static void showParticipantSuccessorList() {
        lm.dataList.getParticipantSuccessorList().getParticipantSuccessorList().forEach(Participant::show);
    }

    public static void showPairSuccesorList() {
        lm.dataList.getPairSuccessorList().getPairSuccessorList().forEach(Pair::show);
    }

    public static void showPairList() {
        lm.dataList.getPairList().forEach(Pair::show);
    }

    public static void showIsPreMadePair() {
        lm.dataList.getPairList().stream()
                .filter(pair -> pair.getIsPreMade())
                .forEach(Pair::show);
    }

    public static void showKitchenAddressAndParticipant() {
        for (String address : readCSV.AddressTable.keySet()) {
            System.out.println("Address: " + address);
            // Get the list of participants for the current address
            List<Participant> participants = readCSV.AddressTable.get(address);
            // Iterate over the participants in the list
            for (Participant participant : participants)
                System.out.println("Participant: " + participant.getName());
            // You can print other participant details here as needed
            System.out.println(); // Add an empty line between addresses
        }
    }

    public static void showGroupListGang01() {
        for (Group group : lm.dataList.getGroupListCourse01()) {
            group.show();
            System.out.println("-----------------------");
        }
    }

    public static void showGroupListGang02() {
        for (Group group : lm.dataList.getGroupListCourse02()) {
            group.show();
            System.out.println("-----------------------");
        }
    }

    public static void showGroupListGang03() {
        for (Group group : lm.dataList.getGroupListCourse03()) {
            group.show();
            System.out.println("-----------------------");
        }
    }

    public static void showVisitedListOfEachPair() {
        for (Pair pair : lm.dataList.getPairList()) {
            pair.getVisitedPairs().forEach(Pair::show);
            System.out.println("--------------------------");
        }
    }

    public static void showHasCookedPair() {
        for (Pair p : lm.dataList.getPairList()) {
            p.show();
            System.out.println(p.getHasCooked());
            System.out.println("----------------------");
        }
    }

    public static void showUnmatchedParticipant() {
        for (Participant par : readCSV.event.getDataList().getUnmatchedParticipants()) {
            par.show();
            System.out.println("---------------------------");
        }
    }
}

//    public static void exportToJsonFile(Object object, String filePath) {
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        try {
//            objectMapper.writeValue(new File(filePath), object);
//            System.out.println("JSON file exported successfully!");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

// Convert the list to JSON
//    public static void writeFileToJson(List<?> list) {
//        try {
//            // Write the JSON to a file
//            objectMapper.writeValue(new File("output2.json"), list);
//            System.out.println("JSON file created successfully.");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}




