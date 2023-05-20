package view;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import controller.ListManagement;
import controller.readCSV;
import models.Pair;
import models.Participant;

public class Main {

    public static Pair makeBestPair_Testing(ListManagement lm,Participant participant){
        Pair test_pair = lm.makeBestPair(participant);
        return test_pair;
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

        String csvFile = "Dokumentation/teilnehmerliste.csv";
        readCSV readCSV = new readCSV(new File(csvFile));
        List<String[]> list = readCSV.read_File(new File(csvFile));
        //print out to the console the list of participant from csv file
        for (Participant par: readCSV.event.getDataList().getParticipantList()) {
            //par.show();
            //System.out.println("---------------------------");
        }

        //check for the number of participant with the same kitchen
        //for ex here: print out the number of participant which have the same kitchen address
        Participant p1 = readCSV.event.getDataList().getParticipantList().get(0);
        System.out.println(readCSV.AddressTable.get("8.68137201709331150.5820794170933").size());

        //check the isPremade() function
        System.out.println(readCSV.event.getDataList().getPairList().get(0).isPreMade());

        //check the unmatchParticipantList
        for (Participant par: readCSV.event.getDataList().getUnmatchedParticipants()) {
            par.show();
            System.out.println("---------------------------");
        }

        //print out the unmatchedParticipants from readCSV class
        System.out.println("readCSV getUnmatchedParticipants: " + readCSV.event.getDataList().getUnmatchedParticipants().size());

        //print out the unmatchedParticipants from ListManagement class
        ListManagement lm = new ListManagement(readCSV.event.getDataList());
        System.out.println("ListManagement getUnmatchedParticipants "+lm.dataList.getUnmatchedParticipants().size());

        lm.dataList.getUnmatchedParticipants().get(0).show();

        Participant firstUnmatchedParticipant = lm.dataList.getUnmatchedParticipants().get(4);
        Pair test_pair = makeBestPair_Testing(lm,firstUnmatchedParticipant);
        test_pair.show();







    }

}