package utility;

/**
 * This is an interface for all utility methods
 */
public interface Utility {

    /**
     * This method shows the information about a Participant, Group or Pair
     */
    void show();

    int hashCode();

    /**
     * This method checks if this Pair, Group or Participant is equal to another one
     * @param o represent the Pair, Group or Participant that is chosen to compare with
     * @return true if the Pairs, Groups or Participants are equal, false otherwise
     */
    boolean equal(Object o);
}
