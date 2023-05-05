package models;
import com.opencsv.CSVReader;

import javax.swing.*;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;

import static controller.readCSV.readCSV;

public class Main {

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
        List<String[]> list = readCSV(new File(csvFile));
        //print out to the console the list of participant from csv file
        for (String[] teilnehmer : list) {
            System.out.println(Arrays.toString(teilnehmer));
        }
    }

}