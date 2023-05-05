package models;

public enum FOOD_PREFERENCE {
        meat("meat"),
        none("none"),
        vegan("vegan"),
        veggie("veggie");

        private final String text;
        FOOD_PREFERENCE(final String text) {
            this.text = text;
        }
        @Override
        public String toString() {
            return text;
        }
}
