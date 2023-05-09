package controller;

public enum CRITERIA {
    FOOD_PREFERENCES(20),
    AGE_DIFFERENCE(10),
    GENDER_DIVERSITY(5),
    TRAVEL_DISTANCE(5);

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

