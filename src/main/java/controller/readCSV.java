package controller;

import com.opencsv.CSVReader;
import models.Event;
import models.Pair;
import models.Participant;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class readCSV {
    public static List<String[]> alone_participant = new ArrayList<>();
    public static List<String[]> not_alone_participant = new ArrayList<>();
    public static Event event = new Event(1000); //test Event
    public static DataList dataList = new DataList(event);
    public static Pair pair = null;
    public static int countParticipant = 0;


    /**
     *
     * @param csvFile
     * @return list of participant in String[]
     * @throws Exception
     */
    public static List<String[]> readCSV(File csvFile) throws Exception {
        CSVReader reader = new CSVReader(new FileReader(csvFile));
        return reader.readAll();
    }

    /**
     *
     * @param list
     * @return number of Participants
     */
    public static int ParticipantLength(List<String[]> list){
        return list.size();
    }

    public static void addParticipantAndPair(List<String[]> list){
        System.out.println("removeFirst"+Arrays.toString(list.remove(0)));

        for (int i = 0; i< list.size(); i++) {
            String[] participant_String = list.get(i);

            String ID = participant_String[1];
            String name = participant_String[2];
            String foodPreference = participant_String[3];
            String age = participant_String[4];
            String sex = participant_String[5];

            String kitchen = participant_String[6];
            String kitchenStory = (participant_String[7].equals(""))? "0":participant_String[7]; //kitchen floor
            String kitchenLongitude = (participant_String[8].equals(""))? "0":participant_String[8];    //kitchen Longitude
            String kitchenLatitude = (participant_String[9].equals(""))? "0":participant_String[9];     //Kitchen Latitude

            if (participant_String[10].equals("") && //ID_2
                    participant_String[11].equals("") &&
                    participant_String[12].equals("") &&
                    participant_String[13].equals("")) {
                alone_participant.add(participant_String); //later this will be added to list of alone_registration Class
                Participant participant = new Participant(ID,name,foodPreference,age,sex,kitchen,kitchenStory,
                        kitchenLongitude,kitchenLatitude);
                dataList.addParticipantToList(participant);
                countParticipant++;
            }else{//is Pair
                String ID_2 = participant_String[10];
                String name_2 = participant_String[11];
                String age_2 = participant_String[12];//convert string to double first and then to int, some age fields in csv file are in double
                String sex_2 = participant_String[13];
                Participant participant_1 = new Participant(ID, name, foodPreference, age, sex, kitchen, kitchenStory,
                        kitchenLongitude,kitchenLatitude);
                dataList.addParticipantToList(participant_1);

                Participant participant_2 = new Participant(ID_2, name_2, foodPreference, age_2, sex_2, kitchen, kitchenStory,
                        kitchenLongitude,kitchenLatitude);
                dataList.addParticipantToList(participant_2);

                not_alone_participant.add(participant_String);
                countParticipant +=2;

                //make Pairs
                pair = new Pair(participant_1, participant_2);
                dataList.addPairToList(pair);
            }
        }
    }

}
