package models;
import com.opencsv.CSVReader;

import javax.swing.*;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static List<String[]> readCSV(File csvFile) throws Exception {
        CSVReader reader = new CSVReader(new FileReader(csvFile));
        List<String[]> list = reader.readAll();

        return list;
    }


    public static void main(String[] args) throws Exception {
        // Create a file chooser dialog to select the CSV file
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(null);
        if (result != JFileChooser.APPROVE_OPTION) {
            return; // User canceled or closed the dialog
        }
        File selectedFile = fileChooser.getSelectedFile();

        //String csvFile = "teilnehmerliste.csv";

        List<String[]> list = readCSV(selectedFile);

        //print out to the console the list of participant from csv file
        for (String[] teilnehmer : list) {
            System.out.println(Arrays.toString(teilnehmer));
        }
    }
}