package models;

/**
 * The FOOD_PREFERENCE enum represents different food preference for all participants.
 * Each preference has a corresponding value associated with it.
 */
public enum FOOD_PREFERENCE {
        meat(0),
        none(0),
        vegan(2),
        veggie(1);

        public int getValue() {
                return value;
        }
        private final int value;
        FOOD_PREFERENCE(int i) {
                this.value = i;
        }

        /**
         * Returns the FOOD_PREFERENCE enum for a given value.
         * @param value the value to search for
         * @return the corresponding FOOD_PREFERENCE enum, or null if not found
         */
        public static FOOD_PREFERENCE fromValue(int value) {
                for (FOOD_PREFERENCE foodPreference : FOOD_PREFERENCE.values()) {
                        if (foodPreference.getValue() == value) {
                                return foodPreference;
                        }
                }
                return null; // Value not found
        }
}
