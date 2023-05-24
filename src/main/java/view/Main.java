package view;

import java.io.File;
import java.lang.reflect.Array;
import java.util.Arrays;
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
        for (Participant par: readCSV.event.getDataList().getParticipantList()) {
            //par.show();
            //System.out.println("---------------------------");
        }

        //check for the number of participant with the same kitchen
        //for ex here: print out the number of participant which have the same kitchen address
        Participant p1 = readCSV.event.getDataList().getParticipantList().get(0);
        System.out.println("number of participants with the same kitchen address: " + readCSV.AddressTable.get("8.68137201709331150.5820794170933").size());

        //check the isPremade() function
        System.out.println(readCSV.event.getDataList().getPairList().get(0).isPreMade());

/*
        //check the unmatchParticipantList
        for (Participant par: readCSV.event.getDataList().getUnmatchedParticipants()) {
            par.show();
            System.out.println("---------------------------");
        }

*/

        //print out the unmatchedParticipants from ListManagement class
        System.out.println("ListManagement getUnmatchedParticipants: "+lm.dataList.getUnmatchedParticipants().size());


        System.out.println(" dataList.pairList size before match: " +  lm.dataList.getPairList().size());   //before match: 73 pairs
        lm.makeBestPairList();
        System.out.println(" dataList.pairList size after match: " +  lm.dataList.getPairList().size());    //after match: 155 pairs, sometimes 154
        System.out.println("dataList.GroupList size before match: " + lm.dataList.getGroupList().size());   //0
        lm.makeBestGroupList();

        System.out.println("dataList.GroupList size after match: " + lm.dataList.getGroupList().size());    //51



        /*
        for (Pair pair: lm.dataList.getPairList()) {
                pair.show();

            System.out.println(pair.calculatePairWeightedScore());
            System.out.println("---------------------------");
        }

         */

        for (Group group : lm.dataList.getGroupList()) {
            group.show();
            System.out.println("-----------------------");
        }


        //print out all the visited pair from each first pair in the group list
        for(Group group: lm.dataList.getGroupList()){
            for(Pair pair: group.getPairs()){
                pair.getVisitedPairs().forEach(Pair::show);
                System.out.println("--------------------------");
            }
        }



        System.out.println("check from dataList: ");
        System.out.println("check size of pairList" + lm.dataList.getPairList().size());

        /*
        for(Pair pair: lm.dataList.getPairList()){
            pair.getVisitedPairs().forEach(Pair::show);
            System.out.println("--------------------------");
        }

         */


    }

}