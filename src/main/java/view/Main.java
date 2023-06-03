package view;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import controller.ListManagement;
import controller.readCSV;
import models.Group;
import models.Pair;
import models.Participant;

public class Main {
    static String participantCSVFile = "Dokumentation/teilnehmerliste.csv";
    static String partyLocationCSVFile = "Dokumentation/partylocation.csv";
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
        lm.dataList.getEvent().getParticipantSuccessorList().getParticipantSuccessorList().forEach(Participant::show);
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
        for (Participant par: readCSV.event.getDataList().getUnmatchedParticipants()) {
            par.show();
            System.out.println("---------------------------");
        }
    }

    public static List<Pair> unmatchedAfterGang3 = new ArrayList<>();


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

        //System.out.println(" dataList.pairList size before match: " +  lm.dataList.getPairList().size());   //before match: 73 pairs
        lm.makeBestPairList();
        //System.out.println(" dataList.pairList size after match: " +  lm.dataList.getPairList().size());
        //lm.dataList.getPairList().forEach(Pair::show);//after match: 155 pairs, sometimes 154
        //System.out.println("dataList.GroupList size before match: " + lm.dataList.getGroupListCourse01().size());   //0
        //System.out.println("dataList.GroupList size after match: " + lm.dataList.getGroupListCourse01().size());    //51
        lm.makeBestGroupList();
        lm.makeBestGroupList();
        lm.makeBestGroupList();





        System.out.println("Gang 1:");
        showGroupListGang01();
        System.out.println("Gang 2:");
        showGroupListGang02();
        System.out.println("Gang 3:");
        showGroupListGang03();

        showHasCookedPair();

        System.out.println("Cannot find any group: ");
        lm.cannotFindGroup.forEach(Pair::show);
        System.out.println("hasnt cooked pair: " + showHasntCookedPair());
        System.out.println("all cooked group: " + lm.allCookedGroup.size());

        System.out.println(lm.dataList.getGroupListCourse01().size()+""+lm.dataList.getGroupListCourse02().size()+""+lm.dataList.getGroupListCourse03().size());
        lm.dataList.getPairList().get(100).show();
        System.out.println(lm.dataList.getPairList().get(100).getFoodPreference());
    }



}