package logic;

import models.Group;
import models.Pair;
import models.Participant;
import org.json.JSONObject;
import java.util.List;
import java.io.FileWriter;
import java.io.IOException;

/**
 * The JsonExport class is responsible for exporting data to JSON format.
 * It provides methods to write JSON objects to a file.
 **/
public class JsonExport {

    private List<Group> groups;
    private List<Pair> pairs;
    private List<Pair> successorPairs;
    private List<Participant> successorParticipants;
    public List<Pair> getSuccessorPairs() {
        return successorPairs;
    }
    public void setSuccessorPairs(List<Pair> successorPairs) {
        this.successorPairs = successorPairs;
    }
    public List<Group> getGroups() {
        return groups;
    }
    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }
    public List<Pair> getPairs() {
        return pairs;
    }
    public void setPairs(List<Pair> pairs) {
        this.pairs = pairs;
    }

    /**
     Writes the provided JSONObject to a file with the specified file name.
     @param jsonObject The JSONObject to be written to the file.
     @param fileName The name of the file to write the JSON object.
     */
    public void writeJsonToFile(JSONObject jsonObject, String fileName) {
        try (FileWriter file = new FileWriter(fileName)) {
            file.write(jsonObject.toString(4));  // 4 is the number of spaces for indentation
            System.out.println("Successfully wrote JSON object to the file...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Participant> getSuccessorParticipants() {
        return successorParticipants;
    }
    public void setSuccessorParticipants(List<Participant> successorParticipants) {
        this.successorParticipants = successorParticipants;
    }
}