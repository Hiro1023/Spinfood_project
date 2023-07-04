package logic;

import java.io.File;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
public class GenerateSolutions {
    static ObjectMapper objectMapper = new ObjectMapper();
    static String participantCSVFile = "Resources/teilnehmerliste.csv";
    static String partyLocationCSVFile = "Resources/partylocation.csv";
    static readCSV readCSV;
    static readCSV readCSV1;
    static readCSV readCSV2;
    static {
        try {
            readCSV = new readCSV(new File(participantCSVFile),new File(partyLocationCSVFile));
            readCSV1 = new readCSV(new File(participantCSVFile),new File(partyLocationCSVFile));
            readCSV2 = new readCSV(new File(participantCSVFile),new File(partyLocationCSVFile));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    static List<String[]> list;
    static List<String[]> list1;
    static List<String[]> list2;
    static {
        try {
            list = readCSV.read_File(new File(participantCSVFile));
            list1 = readCSV1.read_File(new File(participantCSVFile));
            list2 = readCSV2.read_File(new File(participantCSVFile));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    static ListManagement lm = new ListManagement(readCSV.event.getDataList());
    static ListManagement lm1 = new ListManagement(readCSV1.event.getDataList());
    static ListManagement lm2 = new ListManagement(readCSV2.event.getDataList());
    public static void main(String[] args) throws Exception {
        lm.editCriteria(CRITERIA.FOOD_PREFERENCES,2000);
        lm.editCriteria(CRITERIA.PATH_LENGTH,1500);
        lm.editCriteria(CRITERIA.AGE_DIFFERENCE,1000);
        lm.editCriteria(CRITERIA.GENDER_DIVERSITY,500);
        lm.editCriteria(CRITERIA.OPTIMAL,250);
        lm.pairAndGroupBuilding();

        try {
            lm.dataList.exportToJsonFile("Solutions/output_criteria_a.json");
            System.out.println("output file 1 created successfully!!");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        lm1.editCriteria(CRITERIA.FOOD_PREFERENCES,1500);
        lm1.editCriteria(CRITERIA.PATH_LENGTH,1000);
        lm1.editCriteria(CRITERIA.AGE_DIFFERENCE,250);
        lm1.editCriteria(CRITERIA.GENDER_DIVERSITY,500);
        lm1.editCriteria(CRITERIA.OPTIMAL,2000);
        lm1.pairAndGroupBuilding();

        try {
            lm1.dataList.exportToJsonFile("Solutions/output_criteria_b.json");
            System.out.println("output file 2 created successfully!!");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        lm2.editCriteria(CRITERIA.FOOD_PREFERENCES,1000);
        lm2.editCriteria(CRITERIA.PATH_LENGTH,500);
        lm2.editCriteria(CRITERIA.AGE_DIFFERENCE,1500);
        lm2.editCriteria(CRITERIA.GENDER_DIVERSITY,2000);
        lm2.editCriteria(CRITERIA.OPTIMAL,250);
        lm2.pairAndGroupBuilding();

        try {
            lm2.dataList.exportToJsonFile("Solutions/output_criteria_c.json");
            System.out.println("output file 3 created successfully!!");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
