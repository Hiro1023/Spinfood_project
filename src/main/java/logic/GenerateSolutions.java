package logic;

public class GenerateSolutions {
    public ListManagement lm;

    public GenerateSolutions(ListManagement lm) {
        this.lm = lm;
    }

    public void generateSolutionFirstCondition() {
        lm.editCriteria(CRITERIA.FOOD_PREFERENCES, 2000);
        lm.editCriteria(CRITERIA.PATH_LENGTH, 1500);
        lm.editCriteria(CRITERIA.AGE_DIFFERENCE, 1000);
        lm.editCriteria(CRITERIA.GENDER_DIVERSITY, 500);
        lm.editCriteria(CRITERIA.OPTIMAL, 250);
        lm.pairAndGroupBuilding();

        try {
            lm.dataList.exportToJsonFile("Solutions/output_criteria_a.json");
            System.out.println("output file 1 created successfully!!");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void generateSolutionSecondCondition() {
        lm.editCriteria(CRITERIA.FOOD_PREFERENCES, 1500);
        lm.editCriteria(CRITERIA.PATH_LENGTH, 1000);
        lm.editCriteria(CRITERIA.AGE_DIFFERENCE, 250);
        lm.editCriteria(CRITERIA.GENDER_DIVERSITY, 500);
        lm.editCriteria(CRITERIA.OPTIMAL, 2000);
        lm.pairAndGroupBuilding();

        try {
            lm.dataList.exportToJsonFile("Solutions/output_criteria_b.json");
            System.out.println("output file 2 created successfully!!");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void generateSolutionThirdCondition() {
        lm.editCriteria(CRITERIA.FOOD_PREFERENCES, 1000);
        lm.editCriteria(CRITERIA.PATH_LENGTH, 500);
        lm.editCriteria(CRITERIA.AGE_DIFFERENCE, 1500);
        lm.editCriteria(CRITERIA.GENDER_DIVERSITY, 2000);
        lm.editCriteria(CRITERIA.OPTIMAL, 250);
        lm.pairAndGroupBuilding();

        try {
            lm.dataList.exportToJsonFile("Solutions/output_criteria_c.json");
            System.out.println("output file 3 created successfully!!");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
