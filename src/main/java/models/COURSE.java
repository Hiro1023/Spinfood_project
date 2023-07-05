package models;

/**
 * The COURSE enum represents three courses for this event
 * Each course has a corresponding value associated with it.
 */
public enum COURSE {
    first(1), //Gang 1
    main(2), //Gang 2
    dessert(3); //Gang 3

    public int getValue() {
        return value;
    }
    private final int value;
    COURSE(int i) {this.value = i;}

    /**
     * Returns the COURSE enum for a given value.
     * @param value the value to search for
     * @return the corresponding COURSE enum, or null if not found
     */
    public static COURSE fromValue(int value) {
        for (COURSE c : COURSE.values()) {
            if (c.getValue() == value) {
                return c;
            }
        }
        return null; // Value not found
    }
}
