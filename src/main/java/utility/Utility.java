package utility;
import org.json.JSONObject;

public interface Utility {

    public void show();

    public int hashCode();

    public boolean equal(Object o);

    /**
     Converts the current object state to a JSONObject.
     @return The JSONObject representing the object state.
     */
    public JSONObject toJson();


}
