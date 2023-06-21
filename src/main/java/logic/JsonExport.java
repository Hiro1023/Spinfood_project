package logic;


import models.Group;
import models.Pair;
import models.Participant;
import org.json.JSONObject;
//import org.leadpony.justify.api.JsonSchema;
//import org.leadpony.justify.api.JsonValidationService;
//import org.leadpony.justify.api.ProblemHandler;
//import javax.json.stream.JsonParser;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.io.FileWriter;
import java.io.IOException;

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

    public List<Participant> getSuccessorParticipants() {
        return successorParticipants;
    }

    public void setSuccessorParticipants(List<Participant> successorParticipants) {
        this.successorParticipants = successorParticipants;
    }


    public void writeJsonToFile(JSONObject jsonObject, String fileName) {
        try (FileWriter file = new FileWriter(fileName)) {
            file.write(jsonObject.toString(4));  // 4 is the number of spaces for indentation
            System.out.println("Successfully wrote JSON object to the file...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


//    public void validateJson(String outputFile, String schemafile) throws FileNotFoundException {
//        JsonValidationService service = JsonValidationService.newInstance();
//        // Reads the JSON schema
//        JsonSchema schema = service.readSchema(Paths.get(schemafile));
//
//        // Problem handler which will print problems found.
//        ProblemHandler handler = service.createProblemPrinter(System.out::println);
//
//        Path path = Paths.get(outputFile);
//        // Parses the JSON instance by JsonParser
//        try (JsonParser parser = (JsonParser) service.createParser(path, schema, handler)) {
//            while (parser.hasNext()) {
//                JsonParser.Event event = parser.next();
//            }
//        }
//    }


//    public void exportAndValidateData(String outputFileName, String schemaFileName) {
//        try {
//            validateJson(outputFileName, schemaFileName);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//    }
}
