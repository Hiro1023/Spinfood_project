package models;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {


    @org.junit.jupiter.api.Test
    void readParticipants() throws IOException {
        //create a cvs and place data in it
        String data = "Rname,RLname,Email\nSaif,Shahwan,Smooth.btc@outlook.com";
        Files.write(Paths.get("test.csv"), data.getBytes());
        //retrieve data from cvs into a list
        List<String> lines = Main.readParticipants("test.csv");
        //list test
        assertEquals("Rname,RLname,Email",lines.get(0));
        assertEquals("Saif,Shahwan,Smooth.btc@outlook.com",lines.get(1));
        //cleanup
        Files.deleteIfExists(Paths.get("test.csv"));
    }
}