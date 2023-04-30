import com.opencsv.CSVReader;

import javax.swing.*;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class readCSV {

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
        if (result != JFileChooser.APPROVE_OPTION) {
            return; // User canceled or closed the dialog
        }
        File selectedFile = fileChooser.getSelectedFile();
         */

        //String csvFile = "/Users/tommy/Desktop/SoftwarePraktikum23/teilnehmerliste.csv"; //for mac
        //String csvFile_test = "/Users/tommy/Desktop/teilnehmerliste_test.csv";
        String csvFile = "C:\\Users\\Hirotaka\\IdeaProjects\\SP23_Gruppe07_Hoangkim_Nakashima_Wan_Shahwan\\Dokumentation\\teilnehmerliste.csv"; //for windows
        String csvFile_test = "C:\\Users\\Hirotaka\\IdeaProjects\\SP23_Gruppe07_Hoangkim_Nakashima_Wan_Shahwan\\Dokumentation\\teilnehmerliste_test.csv";
        //String csvFile = "D:\\Informatik_Uni_Marburg\\Software_Praktikum\\SP23_Gruppe07_Hoangkim_Nakashima_Wan_Shahwan\\Dokumentation\\teilnehmerliste.csv";//for windows
        List<String[]> list = readCSV(new File(csvFile_test));

        //print out to the console the list of participant from csv file
        /*
        for (String[] teilnehmer : list) {
            System.out.println(Arrays.toString(teilnehmer));
        }
         */

        //get the data of the first person on file
        //String[] a = list.get(55);
        //print test this person
        //System.out.println(Arrays.toString(a));
        //test if the criteria 13 qual ""
        //System.out.println("test "+(a[13].equals("")));

        //print header
        System.out.println("header" + Arrays.toString(list.get(0)));

        System.out.println("remove"+Arrays.toString(list.remove(0)));
        System.out.println("size" + list.size());
        //String[] par = list
        List<Teilnehmer> teilnehmerList = new ArrayList<>();
        List<String[]> false_teilnehmer = new ArrayList<>();
        for (int i = 0; i< list.size()-2; i++) {
            String[] participant = list.get(i);
            if(participant[1].equals("")||participant[2].equals("")||participant[3].equals("")||
                    participant[4].equals("")||participant[5].equals("")||participant[6].equals("")||
                    participant[7].equals("")||participant[8].equals("")||participant[9].equals("")){
                continue;
            }
            String ID = participant[1];
            String name = participant[2];
            String foodPreference = participant[3];
            System.out.println("participant "+participant[4]);
            int age = Integer.parseInt(participant[4]);
            String sex = participant[5];
            String kitchen = participant[6];
            double kitchenStory = Double.parseDouble(participant[7]);
            double kitchenLongitude = Double.parseDouble(participant[8]);
            double kitchenLatitude = Double.parseDouble(participant[9]);
            if(participant[10].equals("")||participant[11].equals("")||participant[12].equals("")){
                //Teilnehmer teilnehmer = new Teilnehmer(ID, name, foodPreference, age, sex, kitchen,kitchenStory,kitchenLongitude,kitchenLatitude , "", "", 0, null);
                false_teilnehmer.add(participant);
            }
            else{
                String ID_2 = participant[10];
                String name_2 = participant[11];
                int age_2 = (int) Double.parseDouble(participant[12]);  //convert string to double first and then to int, some age fields in csv file are in double
                String sex_2 = participant[13];
                Teilnehmer teilnehmer = new Teilnehmer(ID, name, foodPreference, age, sex, kitchen,kitchenStory,kitchenLongitude,kitchenLatitude , ID_2, name_2, age_2, sex_2);
                teilnehmerList.add(teilnehmer);
            }
            /*
            String ID_2 = participant[10];
            String name_2 = participant[11];
            int age_2 = (int) Double.parseDouble(participant[12]);  //convert string to double first and then to int, some age fields in csv file are in double
            String sex_2 = participant[13];
            Teilnehmer teilnehmer = new Teilnehmer(ID, name, foodPreference, age, sex, kitchen,kitchenStory,kitchenLongitude,kitchenLatitude , ID_2, name_2, age_2, sex_2);
            teilnehmerList.add(teilnehmer);

             */
            System.out.println("end");
        }



        System.out.println(teilnehmerList.get(0).getSex());
        System.out.println(Arrays.toString(false_teilnehmer.get(0)));




    }
}
