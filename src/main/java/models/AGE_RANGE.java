package models;

/**
 The AGE_RANGE enum represents different age range for all participants.
 Each age range has a corresponding value associated with it.
 */
public enum AGE_RANGE {
    LessThan18(0),
    LessThan24(1),
    LessThan28(2),
    LessThan31(3),
    LessThan36(4),
    LessThan42(5),
    LessThan47(6),
    LessThan57(7),
    MoreThan56(8);

    public int getValue() {
        return value;
    }

    private int value;
    AGE_RANGE(int i) {
        this.value = i;
    }
}
