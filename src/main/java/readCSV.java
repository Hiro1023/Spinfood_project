import com.opencsv.CSVReader;

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

        //csv file path
        String csvFile = "/Users/tommy/Desktop/SoftwarePraktikum23/Dokumentation/teilnehmerliste.csv"; //for mac
        String csvFile_test = "/Users/tommy/Desktop/teilnehmerliste_test.csv";
        //String csvFile = "C:\\Users\\Hirotaka\\IdeaProjects\\SP23_Gruppe07_Hoangkim_Nakashima_Wan_Shahwan\\Dokumentation\\teilnehmerliste.csv"; //for windows
        //String csvFile_test = "C:\\Users\\Hirotaka\\IdeaProjects\\SP23_Gruppe07_Hoangkim_Nakashima_Wan_Shahwan\\Dokumentation\\teilnehmerliste_test.csv";
        //String csvFile = "D:\\Informatik_Uni_Marburg\\Software_Praktikum\\SP23_Gruppe07_Hoangkim_Nakashima_Wan_Shahwan\\Dokumentation\\teilnehmerliste.csv";//for windows
        List<String[]> list = readCSV(new File(csvFile));

        //print header
        //System.out.println("header" + Arrays.toString(list.get(0)));
        //System.out.println("size: " + list.size());

        /*
        //print out to the console the list of participant from csv file
        for (String[] teilnehmer : list) {
            System.out.println(Arrays.toString(teilnehmer));
        }
         */

        TeilnehmendeList teilnehmendeList = new TeilnehmendeList();
        PaerchenList paerchenList = new PaerchenList();
        List<Teilnehmer> teilnehmerList = new ArrayList<>();
        List<String[]> alone_participant = new ArrayList<>();
        System.out.println("remove"+Arrays.toString(list.remove(0)));
        for (int i = 0; i< list.size(); i++) {
            String[] participant = list.get(i);
            if(participant[1].equals("")||participant[2].equals("")||
                    participant[3].equals("")||participant[5].equals("")||participant[6].equals("")||
                    participant[7].equals("")||participant[8].equals("")||participant[9].equals("")){
                //System.out.println("missing infos participant: "+ Arrays.toString(participant));
                continue;
            }
            String ID = participant[1];
            String name = participant[2];
            String foodPreference = participant[3];
            String age = participant[4];
            String sex = participant[5];
            String kitchen = participant[6];
            String kitchenStory = participant[7];
            String kitchenLongitude = participant[8];
            String kitchenLatitude = participant[9];
            if (participant[10].equals("")||    //ID_2
                    participant[11].equals("")||    //name_2
                    participant[12].equals("")){    //age_2
                alone_participant.add(participant); //later this will be added to list of alone_registration Class
                Teilnehmer teilnehmer = new Teilnehmer(ID,name,foodPreference,age,sex,kitchen,kitchenStory,kitchenLongitude,kitchenLatitude,"","","","");
                teilnehmendeList.addTeilnehmer(teilnehmer);
            }
            else{ //is Pair
                String ID_2 = participant[10];
                String name_2 = participant[11];
                String age_2 = participant[12];  //convert string to double first and then to int, some age fields in csv file are in double
                String sex_2 = participant[13];
                Teilnehmer teilnehmer1 = new Teilnehmer(ID, name, foodPreference, age, sex, kitchen, kitchenStory,
                        kitchenLongitude,kitchenLatitude , ID_2, name_2, age_2, sex_2);
                Teilnehmer teilnehmer2 = new Teilnehmer(ID_2, name_2, foodPreference, age_2, sex_2, kitchen, kitchenStory,
                        kitchenLongitude,kitchenLatitude , ID, name, age, sex);
                teilnehmerList.add(teilnehmer1);
                teilnehmerList.add(teilnehmer2);

                //make Pairs
                Paerchen paerchen = new Paerchen(teilnehmer1,teilnehmer2);
                paerchenList.addPairToList(paerchen);
            }
        }

        //print out
        /*
        System.out.println("print the alone Participants: ");
        for (String[] f_p : alone_participant) {
            System.out.println(Arrays.toString(f_p));
        }

         */

        for(Paerchen pair : paerchenList.getpaerchenList()){
            System.out.println(pair.getPairID());
        }
        System.out.println("size of teilnehmerList: "+ teilnehmerList.size());




    }
}
