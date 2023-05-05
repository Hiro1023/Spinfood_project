package models;
import com.opencsv.CSVReader;

import javax.swing.*;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;

public class Main {

    /**
     * @param csvFile
     * @return List of Participant with type String[]
     * @throws Exception
     */
    public static List<String[]> readCSV(File csvFile) throws Exception {
        CSVReader reader = new CSVReader(new FileReader(csvFile));
        List<String[]> list = reader.readAll();

        return list;
    }

    /**
     * Description: main class is the entry point.
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        /*
        // Create a file chooser dialog to select the CSV file
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(null);
        if (result != JFileChooser.APPROVE_OPTION) return; // User canceled or closed the dialog
        File selectedFile = fileChooser.getSelectedFile();
        
         */

        String csvFile = "Dokumentation/teilnehmerliste.csv";
        List<String[]> list = readCSV(new File(csvFile));
        //print out to the console the list of participant from csv file
        for (String[] teilnehmer : list) {
            System.out.println(Arrays.toString(teilnehmer));
        }
    }

}