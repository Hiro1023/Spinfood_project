package view;

import java.io.File;
import java.util.List;

import controller.ListManagement;
import controller.readCSV;
import models.Group;
import models.Pair;
import models.Participant;

public class Main {
    static String csvFile = "Dokumentation/teilnehmerliste.csv";
    static readCSV readCSV;
    static {
        try {
            readCSV = new readCSV(new File(csvFile));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static List<String[]> list;
    static {
        try {
            list = readCSV.read_File(new File(csvFile));
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

    public static void showParticipantSuccesorList(){
        lm.dataList.getEvent().getParticipantSuccessorList().getParticipantSuccessorList().forEach(Participant::show);
    }
    public static void showGroupListGang01(){
        for (Group group : lm.dataList.getGroupListGang01()) {
            group.show();
            System.out.println("-----------------------");
        }
    }
    public static void showGroupListGang02(){
        for (Group group : lm.dataList.getGroupListGang02()) {
            group.show();
            System.out.println("-----------------------");
        }
    }

    public static void showGroupListGang03(){
        for (Group group : lm.dataList.getGroupListGang03()) {
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

    public static void showUnmatchedParticipant(){
        for (Participant par: readCSV.event.getDataList().getUnmatchedParticipants()) {
            par.show();
            System.out.println("---------------------------");
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

        //print out to the console the list of participant from csv file
        //showParticipantList();

        //check for the number of participant with the same kitchen
        //for ex here: print out the number of participant which have the same kitchen address
        Participant p1 = readCSV.event.getDataList().getParticipantList().get(0);
        System.out.println("number of participants with the same kitchen address: " + readCSV.AddressTable.get("8.68137201709331150.5820794170933").size());

        //check the isPremade() function
        //System.out.println(readCSV.event.getDataList().getPairList().get(0).isPreMade());


        //print out the unmatchedParticipants from ListManagement class
        System.out.println("ListManagement getUnmatchedParticipants: "+lm.dataList.getUnmatchedParticipants().size());

        System.out.println(" dataList.pairList size before match: " +  lm.dataList.getPairList().size());   //before match: 73 pairs
        lm.makeBestPairList();
        System.out.println(" dataList.pairList size after match: " +  lm.dataList.getPairList().size());    //after match: 155 pairs, sometimes 154
        System.out.println("dataList.GroupList size before match: " + lm.dataList.getGroupListGang01().size());   //0
        lm.makeBestGroupList();
        System.out.println("dataList.GroupList size after match: " + lm.dataList.getGroupListGang01().size());    //51

        //show group list for the Gang 1
        //showGroupListGang01();
        //showGroupListGang03();


        //check the visited Pair for the first pair in the List: only for debuging
        System.out.println("--------------------");
        lm.dataList.getPairList().get(0).show();
        lm.dataList.getPairList().get(0).getVisitedPairs().forEach(Pair::show);

        //make the best group for Gang 2
        lm.makeBestGroupList();

        //check the visited Pair for the first pair in the List: only for debuging
        //the visited Paired has increased from 2 Pairs (Gang1) to 4 pairs (Gang2)
        System.out.println("--------------------");
        lm.dataList.getPairList().get(0).show();
        lm.dataList.getPairList().get(0).getVisitedPairs().forEach(Pair::show);

        //make the best group for Gang 3
        lm.makeBestGroupList();

        //check the visited Pair for the first pair in the List: only for debuging
        //the visited Paired has increased from 4 Pairs (Gang2) to 6 pairs (Gang3)
        System.out.println("--------------------");
        lm.dataList.getPairList().get(0).show();
        lm.dataList.getPairList().get(0).getVisitedPairs().forEach(Pair::show);


        showGroupListGang03();




    }

}