package models;

/**
 * The SEX enum represents three courses for this event
 * Each sex has a corresponding value associated with it.
 */
public enum SEX {
    male(0), female(1), other(0);

    public int getValue() {
        return value;
    }

    private final int value;
    SEX(int value) {
        this.value = value;
    }

}
