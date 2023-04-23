package models;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        String file = "Dokumentation/teilnehmerliste.csv";
        List<String> parts = new ArrayList<>();
        parts = readParticipants(file);
        for(String p : parts){
            System.out.println(p);
        }
    }



    public static List<String> readParticipants(String file) {
        List<String> lines = new ArrayList<>();
        //read files from csv using bufferedreader
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            //while file is not empty add each line to lines
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return lines;
    }
}

