package models;

public enum FOOD_PREFERENCE {
        meat(0),
        none(0),
        vegan(1),
        veggie(2);

        private int value;
        FOOD_PREFERENCE(int i) {
                this.value = i;
        }
}
