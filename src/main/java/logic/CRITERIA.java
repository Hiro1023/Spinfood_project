package logic;

/**
 The CRITERIA enum represents different criteria for evaluating matches.
 Each criterion has a corresponding weight associated with it.
 */
public enum CRITERIA {
    FOOD_PREFERENCES(2000),
    AGE_DIFFERENCE(100),
    GENDER_DIVERSITY(50),
    PATH_LENGTH(100),
    OPTIMAL(0);

    //Criteria combination that work: 200-100-50-100

    private int weight;

    CRITERIA(int weight) {
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    public static CRITERIA getMaxCriteria() {
        CRITERIA[] criteria = CRITERIA.values();
        CRITERIA criterionWithHighestWeight = criteria[0];

        for (CRITERIA criterion : criteria) {
            if (criterion.getWeight() > criterionWithHighestWeight.getWeight()) {
                criterionWithHighestWeight = criterion;
            }
        }

        return criterionWithHighestWeight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}

