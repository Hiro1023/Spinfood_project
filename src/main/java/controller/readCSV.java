package controller;

import com.opencsv.CSVReader;
import models.Event;
import models.Pair;
import models.Participant;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class readCSV {
    public List<String[]> alone_participant = new ArrayList<>();
    public List<String[]> not_alone_participant = new ArrayList<>();
    public Event event = new Event();

    //hash map if String (as address: combination of Longtitude and Latitude)
    //
    public HashMap<String, List<Participant>> AddressTable = new HashMap<>();

    //public ListManagement listManagement = new ListManagement(event.getDataList());
    public int countParticipant = 0;
    public int countPair = 0;

    public readCSV(){};

    public readCSV(File participantCSVFile,File partyLocationCSVFile) throws Exception {
        addParticipantAndPair(read_File(participantCSVFile));
        addPartyLocation(read_File(partyLocationCSVFile));
    }
    /**
     * csvFile is read
     * @param csvFile
     * @return list of participant in String[]
     * @throws Exception
     **/
    public List<String[]> read_File(File csvFile) throws Exception {
        CSVReader reader = new CSVReader(new FileReader(csvFile));
        List<String[]> list = reader.readAll();
        return list;
    }

    public void addPartyLocation(List<String[]> list){
        System.out.println("Party Loc: ");
        String[] loc = list.get(1);
        event.setPartyLongitude(Double.parseDouble(loc[0]));
        event.setPartyLatitude(Double.parseDouble(loc[1]));
    }

    /**
     * @Discription Participant and pair are created from list
     * @param list
     */
    public void addParticipantAndPair(List<String[]> list){
        list.remove(0);

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

            String Kitchen_key = kitchenLongitude+kitchenLatitude;

            if (participant_String[10].equals("") &&
                    participant_String[11].equals("") &&
                    participant_String[12].equals("") &&
                    participant_String[13].equals("")) {
                alone_participant.add(participant_String); //later this will be added to list of alone_registration Class
                Participant participant = new Participant(ID,name,foodPreference,age,sex,kitchen,kitchenStory,
                        kitchenLongitude,kitchenLatitude);
                event.getDataList().addUnmatchedParticipantToList(participant); //add alone_participant to unmatch list
                event.getDataList().addParticipantToList(participant);
                countParticipant++;

                if(!AddressTable.containsKey(Kitchen_key)) {    //if the hashmap doesn't have the key as string
                    List<Participant> IDList = new ArrayList<>();
                    IDList.add(participant);
                    AddressTable.put(Kitchen_key,IDList);
                } else {
                    AddressTable.get(Kitchen_key).add(participant);
                }


            }else{//is Pair
                String ID_2 = participant_String[10];
                String name_2 = participant_String[11];
                String age_2 = participant_String[12];//convert string to double first and then to int, some age fields in csv file are in double
                String sex_2 = participant_String[13];
                Participant participant_1 = new Participant(ID, name, foodPreference, age, sex, kitchen, kitchenStory,
                        kitchenLongitude,kitchenLatitude);
                event.getDataList().addParticipantToList(participant_1);

                Participant participant_2 = new Participant(ID_2, name_2, foodPreference, age_2, sex_2, kitchen, kitchenStory,
                        kitchenLongitude,kitchenLatitude);
                event.getDataList().addParticipantToList(participant_2);

                not_alone_participant.add(participant_String);
                countPair +=1;
                countParticipant +=2;

                //make Pairs
                Pair pair = new Pair(participant_1, participant_2);
                pair.setPreMade(true);
                event.getDataList().addPairToList(pair);

                if(!AddressTable.containsKey(Kitchen_key)) {    //if the hashmap doesn't have the key as string
                    List<Participant> IDList = new ArrayList<>();
                    IDList.add(participant_1);
                    IDList.add(participant_2);
                    AddressTable.put(Kitchen_key,IDList);
                } else {
                    AddressTable.get(Kitchen_key).add(participant_1);
                    AddressTable.get(Kitchen_key).add(participant_2);
                }
            }

        }

        for (Participant p : event.getDataList().getParticipantList()) {
            if (p.getKitchen() != null) {
                String key = String.valueOf(p.getKitchen().getKitchenLongitude()) + String.valueOf(p.getKitchen().getKitchenLatitude());
                p.setKitchenCount(AddressTable.get(key).size());
            }
        }
        //System.out.println(event.getDataList().getPairList().size());
        //event.getDataList().getPairList().forEach(x -> x.show());
        //System.out.println("===================================================================================");
    }
}

