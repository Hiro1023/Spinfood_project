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
        String csvFile = "D:\\Informatik_Uni_Marburg\\Software_Praktikum\\SP23_Gruppe07_Hoangkim_Nakashima_Wan_Shahwan\\Dokumentation\\teilnehmerliste.csv";//for windows
        List<String[]> list = readCSV(new File(csvFile));

        //print out to the console the list of participant from csv file
        /*
        for (String[] teilnehmer : list) {
            System.out.println(Arrays.toString(teilnehmer));
        }
         */

        //get the data of the first person on file
        String[] a = list.get(1);
        //print test this person
        System.out.println(Arrays.toString(a));
        //test if the criteria 13 qual ""
        System.out.println("test "+(a[13].equals("")));

        //print header
        System.out.println(Arrays.toString(list.get(0)));

        List<Teilnehmer> teilnehmerList = new ArrayList<>();
        for (String[] participant: list) {
            int ID = Integer.parseInt(participant[0]);
            String name = participant[1];
            int foodPreference = Integer.parseInt(participant[2]);
            int age = Integer.parseInt(participant[3]);
            int sex = Integer.parseInt(participant[4]);
            boolean kitchen = Boolean.parseBoolean(participant[5]);
            int kitchenStory = Integer.parseInt(participant[6]);
            double kitchenLongitude = Double.parseDouble(participant[7]);
            double kitchenLatitude = Double.parseDouble(participant[8]);
            int ID_2 = Integer.parseInt(participant[9]);
            String name_2 = participant[10];
            int age_2 = Integer.parseInt(participant[11]);
            int sex_2 = Integer.parseInt(participant[12]);
            Teilnehmer teilnehmer = new Teilnehmer(ID, name, foodPreference, age, sex, kitchen,kitchenStory,kitchenLongitude,kitchenLatitude , ID_2, name_2, age_2, sex_2);
            teilnehmerList.add(teilnehmer);
        }


    }
}
