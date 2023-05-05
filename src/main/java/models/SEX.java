package models;

/**
 *Description: Enum for sex
 */
public enum SEX {
    male(0), female(1), other(2);
    private final int value;
    SEX(int value) {
        this.value = value;
    }
    public static final SEX val[] = values();

}
