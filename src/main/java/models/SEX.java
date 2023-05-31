package models;

/**
 *Description: Enum for sex
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
