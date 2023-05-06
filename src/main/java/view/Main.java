package view;
import com.opencsv.CSVReader;
import controller.readCSV;
import models.Participant;

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
        //csv file path
        String csvFile = "Dokumentation/teilnehmerliste.csv"; //for mac
        List<String[]> list = readCSV(new File(csvFile));

        readCSV.addParticipantAndPair(list);

        for (Participant parList: readCSV.dataList.getParticipantList()) {
            parList.show();
            System.out.println("________________");
        }
        System.out.println("check list length " + list.size ());
        System.out.println("size of participant: "+ readCSV.dataList.getParticipantList().size());
        System.out.println("Alone Size: " + readCSV.alone_participant.size());
        System.out.println("Not alone Size: " + readCSV.not_alone_participant.size());
        System.out.println(readCSV.countParticipant);


    }

}