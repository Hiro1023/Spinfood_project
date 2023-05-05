package controller;

import com.opencsv.CSVReader;
import models.Pair;
import models.Participant;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class readCSV {

    /**
     *
     * @param csvFile
     * @return list of participant in String[]
     * @throws Exception
     */
    public static List<String[]> readCSV(File csvFile) throws Exception {
        CSVReader reader = new CSVReader(new FileReader(csvFile));
        List<String[]> list = reader.readAll();

        return list;
    }




    public static void main(String[] args) throws Exception {
        /*
        // Create a file chooser dialog to select the CSV file
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(null);
        if (result != JFileChooser.APPROVE_OPTION)
            return; // User canceled or closed the dialog
        File selectedFile = fileChooser.getSelectedFile();
         */

        //csv file path
        String csvFile = "Dokumentation/teilnehmerliste.csv"; //for mac
        String csvFile_test = "Dokumentation/teilnehmerliste_test.csv";
        List<String[]> list = readCSV(new File(csvFile));

        //TeilnehmendeList teilnehmendeList = new TeilnehmendeList();
        //PaerchenList paerchenList = new PaerchenList();
        List<Participant> participantList = new ArrayList<>();
        List<String[]> alone_participant = new ArrayList<>();
        System.out.println("removeFirst"+Arrays.toString(list.remove(0)));
        for (int i = 0; i< list.size(); i++) {
            String[] participant = list.get(i);
            if(participant[1].equals("")||participant[2].equals("")||
                    participant[3].equals("")||participant[5].equals("")||participant[6].equals("")||
                    participant[7].equals("")||participant[8].equals("")||participant[9].equals("")){
                //System.out.println("missing infos participant: "+ Arrays.toString(participant));
                continue;
            }
            String ID = participant[1];
            String name = participant[2];
            String foodPreference = participant[3];
            String age = participant[4];
            String sex = participant[5];
            String kitchen = participant[6];
            String kitchenStory = participant[7];
            String kitchenLongitude = participant[8];
            String kitchenLatitude = participant[9];
            if (participant[10].equals("")||    //ID_2
                    participant[11].equals("")||    //name_2
                    participant[12].equals("")){    //age_2
                alone_participant.add(participant); //later this will be added to list of alone_registration Class
                Participant teilnehmer = new Participant(ID,name,foodPreference,age,sex,kitchen,kitchenStory,kitchenLongitude,kitchenLatitude);
                //teilnehmendeList.addTeilnehmer(teilnehmer);
            }
            else{ //is Pair
                String ID_2 = participant[10];
                String name_2 = participant[11];
                String age_2 = participant[12];  //convert string to double first and then to int, some age fields in csv file are in double
                String sex_2 = participant[13];
                Participant participant1 = new Participant(ID, name, foodPreference, age, sex, kitchen, kitchenStory,
                        kitchenLongitude,kitchenLatitude);
                Participant participant2 = new Participant(ID_2, name_2, foodPreference, age_2, sex_2, kitchen, kitchenStory,
                        kitchenLongitude,kitchenLatitude);
                participantList.add(participant1);
                participantList.add(participant2);

                //make Pairs
                Pair pair = new Pair(participant1, participant2);
                //paerchenList.addPairToList(pair);
            }
        }
/*
        for(Pair pair : paerchenList.getpaerchenList()){
            System.out.println(pair.getPairId());
        }
        System.out.println("size of teilnehmerList: "+ participantList.size());

 */

    }
}
