package models;

public enum FOOD_PREFERENCE {
        meat(0),
        none(0),
        vegan(2),
        veggie(1);

        public int getValue() {
                return value;
        }

        private int value;
        FOOD_PREFERENCE(int i) {
                this.value = i;
        }
}
