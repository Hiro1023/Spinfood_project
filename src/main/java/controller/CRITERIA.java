package controller;

public enum CRITERIA {
    FOOD_PREFERENCES(200),
    AGE_DIFFERENCE(100),
    GENDER_DIVERSITY(50),
    PATH_LENGTH(50);

    private int weight;

    CRITERIA(int weight) {
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}

