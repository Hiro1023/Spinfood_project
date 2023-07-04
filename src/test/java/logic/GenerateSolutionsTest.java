package logic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GenerateSolutionsTest {
    private GenerateSolutions generateSolutions;
    private ListManagement listManagement;

    @BeforeEach
    void setUp() {
        String participantCSVFile = "Resources/teilnehmerliste.csv";
        String partyLocationCSVFile = "Resources/partylocation.csv";
        readCSV readCSV;
        try {
            readCSV = new readCSV(new File(participantCSVFile),new File(partyLocationCSVFile));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        listManagement = new ListManagement(readCSV.event.getDataList());
        generateSolutions = new GenerateSolutions(listManagement);
    }

    @Test
    void testGenerateSolutionFirstCondition() {
        generateSolutions.generateSolutionFirstCondition();

        String directoryPath = "Solutions"; // Specify the directory path
        String fileName = "output_criteria_a.json"; // Specify the file name
        File directory = new File(directoryPath);
        File file = new File(directory, fileName);
        // Verify the result
        assertTrue(file.exists());
    }

    @Test
    void testGenerateSolutionSecondCondition() {
        // Perform the operation
        generateSolutions.generateSolutionSecondCondition();
        String directoryPath = "Solutions"; // Specify the directory path
        String fileName = "output_criteria_b.json"; // Specify the file name
        File directory = new File(directoryPath);
        File file = new File(directory, fileName);
        // Verify the result
        assertTrue(file.exists());
    }

    @Test
    void testGenerateSolutionThirdCondition() {
        // Perform the operation
        generateSolutions.generateSolutionThirdCondition();
        String directoryPath = "Solutions"; // Specify the directory path
        String fileName = "output_criteria_c.json"; // Specify the file name
        File directory = new File(directoryPath);
        File file = new File(directory, fileName);
        // Verify the result
        assertTrue(file.exists());
    }
}
