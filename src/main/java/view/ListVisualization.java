package view;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.Duration;
import logic.CRITERIA;
import logic.ListManagement;
import logic.readCSV;
import models.Group;
import models.Pair;
import models.Participant;
import java.io.File;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import com.jfoenix.controls.JFXButton;

public class ListVisualization extends Application {

    private File partyLocation;
    private File participantCSVFile;
    private readCSV readCSV;
    private ListManagement listManagement;
    private boolean isPairGenerated = false;

    private TextField maxParticipantsField;

    DropShadow shadow = new DropShadow();

    /**
     * The main entry point for the application
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Starts the JavaFX application.
     *
     * @param primaryStage the primary stage for this application, onto which the
     *                     application scene can be set.
     */
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Cooking Event");
        setupInitialInterface(primaryStage);
    }

    /**
     * This method contains main functions (make Pair and make Group) and user can
     * do it on this window
     *
     * @param primaryStage the stage
     */
    private void switchToMainInterface(Stage primaryStage) {
        primaryStage.setTitle("Event Options");
        setupEventInterface(primaryStage);
    }

    /**
     * Creates and shows the initial interface.
     *
     * @param primaryStage the stage
     */
    private void setupInitialInterface(Stage primaryStage) {
        // Create Layout and add ImageView and Button
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(8);

        // Load the image
        Image logoImage = new Image(
                "C:\\Users\\saif_\\SP23_Gruppe07_Hoangkim_Nakashima_Wan_Shahwan\\Resources\\pic.png");
        ImageView imageView = new ImageView(logoImage);

        // Create the button
        Button button = createNewEventButton(primaryStage);

        // Add the image and button to the VBox
        vbox.getChildren().addAll(imageView, button);

        // Create a Scene with the Layout
        Scene scene = new Scene(vbox, 400, 200);

        // Set the Scene to the Stage
        primaryStage.setScene(scene);

        // Show the Stage
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.show();
    }

    /**
     * Creates and returns a new event button.
     *
     * @param primaryStage the stage
     * @return the new event button
     */
    private Button createNewEventButton(Stage primaryStage) {

        // Create Button
        Button createNewEventButton = new Button("Create New Event");
        createNewEventButton.setStyle("-fx-background-color: #007bff; -fx-text-fill: white;");
        createNewEventButton.setOnAction(e -> switchToMainInterface(primaryStage));

        // Set margin for the button
        VBox.setMargin(createNewEventButton, new Insets(20, 0, 0, 0));

        return createNewEventButton;
    }

    /**
     * Sets up the event interface.
     *
     * @param primaryStage the stage
     */
    private void setupEventInterface(Stage primaryStage) {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        // Add components to layout
        gridPane.add(createEventDatePicker(), 0, 0);
        gridPane.add(createMaxParticipantsField(), 0, 1);
        gridPane.add(createImportCSVButton(primaryStage), 0, 2);
        gridPane.add(createImportPartyLocationButton(primaryStage), 0, 3);
        gridPane.add(createEventButton(primaryStage), 0, 4);

        // Center the gridPane
        gridPane.setAlignment(Pos.CENTER);

        VBox vbox = new VBox(gridPane);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(10, 10, 10, 10));

        // Create a Scene with the Layout
        Scene scene = new Scene(vbox, 500, 400);

        // Set the Scene to the Stage
        primaryStage.setScene(scene);

    }

    /**
     * Creates and returns a date picker for entering the event date.
     *
     * @return the event date picker
     */
    private DatePicker createEventDatePicker() {
        DatePicker eventDatePicker = new DatePicker();
        eventDatePicker.setValue(LocalDate.now()); // default value is set to today's date
        final Callback<DatePicker, DateCell> dayCellFactory = new Callback<>() {
            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item.isBefore(LocalDate.now())) { // disable past dates
                            setDisable(true);
                            setStyle("-fx-background-color: #ffc0cb;");
                        }
                    }
                };
            }
        };
        eventDatePicker.setDayCellFactory(dayCellFactory);
        return eventDatePicker;
    }

    /**
     * Creates and returns a text field for entering the maximum number of
     * participants.
     *
     * @return the max participants text field
     */
    private TextField createMaxParticipantsField() {
        maxParticipantsField = new TextField();
        maxParticipantsField.setPromptText("Enter Max Amount of Participants");
        return maxParticipantsField;
    }

    /**
     * Creates and returns a button for importing CSV data.
     *
     * @param primaryStage the stage
     * @return the import CSV button
     */
    private JFXButton createImportCSVButton(Stage primaryStage) {
        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.LIGHTGREY);
        JFXButton importCSVButton = new JFXButton("Import Participants");
        importCSVButton.setStyle("-fx-background-color: #007bff; -fx-text-fill: white;");

        importCSVButton.setOnMouseEntered(e -> {
            importCSVButton.setEffect(shadow);
        });

        importCSVButton.setOnMouseExited(e -> {
            importCSVButton.setEffect(null);
        });

        importCSVButton.setOnAction(e -> {
            FileChooser fileChooser_Participant = new FileChooser();
            fileChooser_Participant.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
            fileChooser_Participant.setTitle("Open CSV File");

            // Open Dialog to choose file
            participantCSVFile = fileChooser_Participant.showOpenDialog(primaryStage);
        });
        return importCSVButton;
    }

    /**
     * Creates and returns a button for importing party location data.
     *
     * @param primaryStage the stage
     * @return the import party location button
     */
    private Button createImportPartyLocationButton(Stage primaryStage) {

        Button importPartyLocationButton = new Button("Import Party Location");

        importPartyLocationButton.setStyle("-fx-background-color: #007bff; -fx-text-fill: white;");

        importPartyLocationButton.setOnMouseEntered(e -> {
            importPartyLocationButton.setEffect(shadow);
        });

        importPartyLocationButton.setOnMouseExited(e -> {
            importPartyLocationButton.setEffect(null);
        });

        importPartyLocationButton.setOnAction(e -> {
            FileChooser fileChooser_PartyLocation = new FileChooser();
            fileChooser_PartyLocation.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
            fileChooser_PartyLocation.setTitle("Open CSV File");

            // Open Dialog to choose file
            partyLocation = fileChooser_PartyLocation.showOpenDialog(primaryStage);
        });
        return importPartyLocationButton;
    }

    /**
     * Creates and returns a button for creating the event.
     *
     * @param primaryStage the stage
     * @return the create event button
     */
    private Button createEventButton(Stage primaryStage) {

        // Create Button to create event
        Button createEventButton = new Button("Create Event");

        createEventButton.setStyle("-fx-background-color: #007bff; -fx-text-fill: white;");

        createEventButton.setOnMouseEntered(e -> {
            createEventButton.setEffect(shadow);
        });

        createEventButton.setOnMouseExited(e -> {
            createEventButton.setEffect(null);
        });

        createEventButton.setOnAction(e -> {
            // Logic to create event
            try {
                // Use readCSV instance to read file
                readCSV = new readCSV(participantCSVFile, partyLocation);
                listManagement = new ListManagement(readCSV.event.getDataList());
            } catch (Exception a) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setHeaderText("File Reading Error");
                alert.setContentText(
                        "There was an error while creating the event, please check if the data is correct!");
                alert.showAndWait();
            }
            try {
                listManagement.dataList.getEvent().setMaxParticipant(Integer.parseInt(maxParticipantsField.getText()));
            } catch (Exception b) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setHeaderText("Format Error");
                alert.setContentText("Please write only number in filed max participant");
                alert.showAndWait();
            }
            try {
                // set event date in event
                LocalDate eventDate = createEventDatePicker().getValue();
                Date date = Date.from(eventDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
                listManagement.dataList.getEvent().setDate(date);
            } catch (Exception c) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setHeaderText("Format Error");
                alert.setContentText("Please select a valid date!");
                alert.showAndWait();
            }

            System.out.println("import participant sucessfully");
            for (Participant p : listManagement.dataList.getParticipantList()) {
                p.show();
            }
            System.out.println(listManagement.dataList.getEvent().getPartyLatitude());
            System.out.println(listManagement.dataList.getEvent().getPartyLongitude());
            mainMenu(primaryStage);
        });

        return createEventButton;
    }

    /**
     * This method sets up the main menu of the application.
     *
     * @param primaryStage the primary stage of the application.
     */
    public void mainMenu(Stage primaryStage) {
        GridPane gridPane = setupGridPane();

        // Clear existing children from the gridPane
        gridPane.getChildren().clear();

        // Add logo image to the gridPane
        ImageView logoImageView = new ImageView(
                new Image("C:\\Users\\saif_\\SP23_Gruppe07_Hoangkim_Nakashima_Wan_Shahwan\\Resources\\logo.png")); // Replace
                                                                                                                   // "path/to/your/logo.png"
                                                                                                                   // with
                                                                                                                   // the
                                                                                                                   // actual
                                                                                                                   // path
                                                                                                                   // to
                                                                                                                   // your
                                                                                                                   // logo
                                                                                                                   // image
        gridPane.add(logoImageView, 0, 0, 2, 1);

        BorderPane borderPane = new BorderPane();
        // Create a Text object to display messages
        Text messageText = new Text();
        borderPane.setBottom(messageText); // Add it to the bottom of the BorderPane

        setupButtons(gridPane, messageText); // Pass the Text object to the button setup method
        borderPane.setCenter(gridPane); // Put the GridPane in the center of the BorderPane

        setupPrimaryStage(primaryStage, borderPane); // Use the BorderPane as root
    }

    private GridPane setupGridPane() {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        return gridPane;
    }

    private void setupButtons(GridPane grid, Text messageText) {
        Button makePairButton = createAnimatedButton("Make Pair", grid, 0, 0);
        Button makeGroupButton = createAnimatedButton("Make Group", grid, 1, 0);
        Button showPairListButton = createAnimatedButton("Pairs List", grid, 0, 1);
        Button showGroupListButton = createAnimatedButton("Groups List", grid, 1, 1);
        Button showPSuccessorListButton = createAnimatedButton("Participant SuccessorList", grid, 0, 2);
        Button showPairSuccessorListButton = createAnimatedButton("Pair SuccessorList", grid, 1, 2);
        Button setting = createAnimatedButton("Settings", grid, 0, 3);

        setupMakePairButton(makePairButton, messageText);
        setupMakeGroupButton(makeGroupButton, messageText);
        setupShowPairListButton(showPairListButton, grid);
        setupShowGroupListButton(showGroupListButton, grid);
        setupShowPSuccessorListButton(showPSuccessorListButton, grid);
        setupShowPairSuccessorListButton(showPairSuccessorListButton, grid);
        setupSettingButton(setting);

        // Add spacing between buttons
        GridPane.setMargin(makePairButton, new Insets(10, 0, 0, 20));
        GridPane.setMargin(makeGroupButton, new Insets(10, 0, 0, 20));
        GridPane.setMargin(showPairListButton, new Insets(10, 0, 0, 20));
        GridPane.setMargin(showGroupListButton, new Insets(10, 0, 0, 20));
        GridPane.setMargin(showPSuccessorListButton, new Insets(10, 0, 0, 20));
        GridPane.setMargin(showPairSuccessorListButton, new Insets(10, 0, 0, 20));
        GridPane.setMargin(showPairListButton, new Insets(10, 0, 0, 20));
        GridPane.setMargin(setting, new Insets(10, 0, 0, 125));

        // Set grid constraints for buttons
        GridPane.setConstraints(makePairButton, 0, 1);
        GridPane.setConstraints(makeGroupButton, 1, 1);
        GridPane.setConstraints(showPairListButton, 0, 2);
        GridPane.setConstraints(showGroupListButton, 1, 2);
        GridPane.setConstraints(showPSuccessorListButton, 0, 3);
        GridPane.setConstraints(showPairSuccessorListButton, 1, 3);
        GridPane.setConstraints(setting, 0, 4, 2, 1); // Spanning two columns

    }

    private Button createAnimatedButton(String text, GridPane gridPane, int col, int row) {
        Button button = new Button(text);

        // Adding some style
        button.setStyle("-fx-background-color: #ff7f50; -fx-text-fill: white; -fx-font-size: 11.5px;");

        // Adding a tooltip
        Tooltip tooltip = new Tooltip(text);
        button.setTooltip(tooltip);

        // Adding an animation
        final Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.seconds(0),
                        new KeyValue(button.scaleXProperty(), 1),
                        new KeyValue(button.scaleYProperty(), 1)),
                new KeyFrame(
                        Duration.seconds(0.2),
                        new KeyValue(button.scaleXProperty(), 1.1),
                        new KeyValue(button.scaleYProperty(), 1.1)),
                new KeyFrame(
                        Duration.seconds(0.4),
                        new KeyValue(button.scaleXProperty(), 1),
                        new KeyValue(button.scaleYProperty(), 1)));
        timeline.setAutoReverse(true);
        button.setOnMouseEntered(e -> timeline.play());
        button.setOnMouseExited(e -> timeline.stop());

        // Add button to GridPane
        gridPane.add(button, col, row);

        return button;
    }

    private void setupMakePairButton(Button makePairButton, Text messageText) {
        makePairButton.setOnAction(e -> {
            try {
                listManagement.makeBestPairList();
                messageText.setText("All pairs were created successfully");
                isPairGenerated = true;
            } catch (Exception e1) {
                messageText.setText("Error making pair: The participantsList is invalid to build pairs");
            }
        });
    }

    private void setupMakeGroupButton(Button makeGroupButton, Text messageText) {
        makeGroupButton.setOnAction(e -> {
            try {
                if (listManagement.dataList.getGroupListCourse01().size() == 0) {
                    listManagement.makeBestGroupList();
                    listManagement.makeBestGroupList();
                    listManagement.makeBestGroupList();

                    messageText.setText("All groups for 3 courses were created successfully");
                }
            } catch (Exception e1) {
                showErrorDialog("Error making Group", "The Pair List is invalid to build a group");
            }
        });
    }

    public void setupShowPSuccessorListButton(Button showPSListButton, GridPane vBox) {
        showPSListButton.setOnAction(e -> {
            if (listManagement.dataList.getParticipantSuccessorList() != null) {
                System.out.println("show Participant Successor list");
                showParticipantSuccessorListOnScreen();
            } else {
                Text errorText = new Text();
                errorText.setText("Participant Successor list is empty yet");
                vBox.getChildren().add(errorText);
            }

        });
    }

    public void showParticipantSuccessorListOnScreen() {
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Participant Successor List");

        TableView<Participant> tableView = new TableView<>();
        // Create Column of Participant ID
        TableColumn<Participant, String> participantIDColumn = new TableColumn<>("Participant ID");
        participantIDColumn.setCellValueFactory(cellData -> { // pick up data "Participant_ID"
            Participant p = cellData.getValue();
            return new SimpleStringProperty(p.getID());
        });

        // Create Column of Participant Name
        TableColumn<Participant, String> participantNameColumn = new TableColumn<>("Participant Name");
        participantNameColumn.setCellValueFactory(cellData -> { // pick up data "Participant_Name"
            Participant p = cellData.getValue();
            return new SimpleStringProperty(p.getName());
        });

        // Create Column of FoodPreference
        TableColumn<Participant, String> foodPreference = new TableColumn<>("FOOD PREFERENCE");
        foodPreference.setCellValueFactory(cellData -> { // pick up data "Pair_ID"
            Participant p = cellData.getValue();
            return new SimpleStringProperty(p.getFoodPreference().toString());
        });

        tableView.getColumns().add(participantIDColumn);
        tableView.getColumns().add(participantNameColumn);
        tableView.getColumns().add(foodPreference);

        for (Participant p : listManagement.dataList.getParticipantSuccessorList().getParticipantSuccessorList()) {
            tableView.getItems().add(p);
        }

        StackPane root = new StackPane();
        root.getChildren().add(tableView);

        Scene scene = new Scene(root, 1000, 600);
        primaryStage.sizeToScene();
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public void setupShowPairSuccessorListButton(Button showPairSuccessorListButton, GridPane vBox) {
        showPairSuccessorListButton.setOnAction(e -> {
            if (listManagement.dataList.getPairSuccessorList().getPairSuccessorList() != null) {
                showPairSuccessorListOnScreen();
            } else {
                Text errorText = new Text();
                errorText.setText("successor pair List is empty yet");
                errorText.setText("Please click button make Pair");
                vBox.getChildren().add(errorText);
            }
        });
    }

    public void showPairSuccessorListOnScreen() {
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Pair Successor List");

        TableView<Pair> tableView = new TableView<>();

        // Create Column of pairID
        TableColumn<Pair, String> pairIDColumn = new TableColumn<>("Pair ID");
        pairIDColumn.setCellValueFactory(cellData -> { // pick up data "Pair_ID"
            Pair pair = cellData.getValue();
            return new SimpleStringProperty(pair.getPairId());
        });

        // Create Column of Name_participant 1
        TableColumn<Pair, String> participant1Column = new TableColumn<>("Participant1");
        participant1Column.setCellValueFactory(cellData -> { // pick up data "Pair_ID"
            Pair pair = cellData.getValue();
            return new SimpleStringProperty(pair.getParticipant1().getName());
        });

        // Create Column of Name_participant 2
        TableColumn<Pair, String> participant2Column = new TableColumn<>("Participant 2");
        participant2Column.setCellValueFactory(cellData -> { // pick up data "Pair_ID"
            Pair pair = cellData.getValue();
            return new SimpleStringProperty(pair.getParticipant2().getName());
        });

        // Create Column of FoodPreference
        TableColumn<Pair, String> foodPreference = new TableColumn<>("FOOD PREFERENCE");
        foodPreference.setCellValueFactory(cellData -> { // pick up data "Pair_ID"
            Pair pair = cellData.getValue();
            return new SimpleStringProperty(pair.getFoodPreference().toString());
        });

        // Create Column of Age Difference
        TableColumn<Pair, String> ageDif = new TableColumn<>("AGE DIFFERENCE");
        ageDif.setCellValueFactory(cellData -> { // pick up data "Pair_ID"
            Pair pair = cellData.getValue();
            return new SimpleStringProperty(Double.toString(pair.calculateAgeDifference()));
        });

        // Create Column of GenderDiversity
        TableColumn<Pair, String> genderDiv = new TableColumn<>("GENDER DIVERSITY");
        genderDiv.setCellValueFactory(cellData -> { // pick up data "Pair_ID"
            Pair pair = cellData.getValue();
            return new SimpleStringProperty(Double.toString(pair.calculateSexDiversity()));
        });

        // Create Column of FoodPreference
        TableColumn<Pair, String> pathLength = new TableColumn<>("PATH LENGTH");
        pathLength.setCellValueFactory(cellData -> { // pick up data "Pair_ID"
            Pair pair = cellData.getValue();
            return new SimpleStringProperty(Double.toString(pair.calculatePathLength()));
        });

        // Create Column of Score
        TableColumn<Pair, String> score = new TableColumn<>("TOTAL SCORE");
        score.setCellValueFactory(cellData -> { // pick up data "Pair_ID"
            Pair pair = cellData.getValue();
            return new SimpleStringProperty(Double.toString(pair.calculateWeightedScore()));
        });

        tableView.getColumns().add(pairIDColumn);
        tableView.getColumns().add(participant1Column);
        tableView.getColumns().add(participant2Column);
        tableView.getColumns().add(foodPreference);
        tableView.getColumns().add(ageDif);
        tableView.getColumns().add(genderDiv);
        tableView.getColumns().add(pathLength);
        tableView.getColumns().add(score);

        for (Pair p : listManagement.dataList.getPairSuccessorList().getPairSuccessorList()) {
            tableView.getItems().add(p);
        }

        StackPane root = new StackPane();
        root.getChildren().add(tableView);

        Scene scene = new Scene(root, 1000, 600);
        primaryStage.sizeToScene();
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void setupShowPairListButton(Button showPairListButton, GridPane vBox) {
        showPairListButton.setOnAction(e -> {
            if (listManagement.dataList.getPairList() != null) {
                showPairListOnScreen();
            } else {
                Text errorText = new Text();
                errorText.setText("pairList is empty yet");
                errorText.setText("Please click button make Pair");
                vBox.getChildren().add(errorText);
            }
        });
    }

    private void setupShowGroupListButton(Button showGroupListButton, GridPane vBox) {
        showGroupListButton.setOnAction(e -> {
            if (isPairGenerated) {
                showGroupListOnScreen();
            } else {
                Text errorText = new Text();
                errorText.setText("Grouplist is empty yet");
                errorText.setText("Please click button make Group");
                vBox.getChildren().add(errorText);
            }
        });
    }

    private void setupSettingButton(Button setting) {
        setting.setOnAction(e -> {
            showSettingWindow();
        });
    }

    private void showErrorDialog(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText(title);
        alert.setContentText(message);

        alert.showAndWait();
    }

    private void setupPrimaryStage(Stage primaryStage, BorderPane gridPane) {
        primaryStage.setTitle("Make Pair");
        Scene scene = new Scene(gridPane, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * This Method is called, if Button show PairList is clicked
     * it shows pair_id and both of participants in pair on table
     */

    public void showPairListOnScreen() {

        Stage primaryStage = new Stage();
        primaryStage.setTitle("Pair List");

        TableView<Pair> tableView = new TableView<>();

        tableView.setRowFactory(tv -> {
            TableRow<Pair> detailRow = new TableRow<>();
            detailRow.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!detailRow.isEmpty())) {
                    Pair rowData = detailRow.getItem();
                    showPairToCompair(rowData);
                }
            });
            return detailRow;
        });

        // Create Column of pairID
        TableColumn<Pair, String> pairIDColumn = new TableColumn<>("Pair ID");
        pairIDColumn.setCellValueFactory(cellData -> { // pick up data "Pair_ID"
            Pair pair = cellData.getValue();
            return new SimpleStringProperty(pair.getPairId());
        });

        // Create Column of Name_participant 1
        TableColumn<Pair, String> participant1Column = new TableColumn<>("Participant1");
        participant1Column.setCellValueFactory(cellData -> { // pick up data "Pair_ID"
            Pair pair = cellData.getValue();
            return new SimpleStringProperty(pair.getParticipant1().getName());
        });

        // Create Column of Name_participant 2
        TableColumn<Pair, String> participant2Column = new TableColumn<>("Participant 2");
        participant2Column.setCellValueFactory(cellData -> { // pick up data "Pair_ID"
            Pair pair = cellData.getValue();
            return new SimpleStringProperty(pair.getParticipant2().getName());
        });

        // Create Column of FoodPreference
        TableColumn<Pair, String> foodPreference = new TableColumn<>("FOOD PREFERENCE");
        foodPreference.setCellValueFactory(cellData -> { // pick up data "Pair_ID"
            Pair pair = cellData.getValue();
            return new SimpleStringProperty(pair.getFoodPreference().toString());
        });

        // Create Column of Age Difference
        TableColumn<Pair, String> ageDif = new TableColumn<>("AGE DIFFERENCE");
        ageDif.setCellValueFactory(cellData -> { // pick up data "Pair_ID"
            Pair pair = cellData.getValue();
            return new SimpleStringProperty(Double.toString(pair.calculateAgeDifference()));
        });

        // Create Column of GenderDiversity
        TableColumn<Pair, String> genderDiv = new TableColumn<>("GENDER DIVERSITY");
        genderDiv.setCellValueFactory(cellData -> { // pick up data "Pair_ID"
            Pair pair = cellData.getValue();
            return new SimpleStringProperty(Double.toString(pair.calculateSexDiversity()));
        });

        // Create Column of FoodPreference
        TableColumn<Pair, String> pathLength = new TableColumn<>("PATH LENGTH");
        pathLength.setCellValueFactory(cellData -> { // pick up data "Pair_ID"
            Pair pair = cellData.getValue();
            return new SimpleStringProperty(Double.toString(pair.calculatePathLength()));
        });

        // Create Column of Score
        TableColumn<Pair, String> score = new TableColumn<>("TOTAL SCORE");
        score.setCellValueFactory(cellData -> { // pick up data "Pair_ID"
            Pair pair = cellData.getValue();
            return new SimpleStringProperty(Double.toString(pair.calculateWeightedScore()));
        });

        tableView.getColumns().add(pairIDColumn);
        tableView.getColumns().add(participant1Column);
        tableView.getColumns().add(participant2Column);
        tableView.getColumns().add(foodPreference);
        tableView.getColumns().add(ageDif);
        tableView.getColumns().add(genderDiv);
        tableView.getColumns().add(pathLength);
        tableView.getColumns().add(score);

        for (Pair p : listManagement.dataList.getPairList()) {
            tableView.getItems().add(p);
        }

        StackPane root = new StackPane();
        root.getChildren().add(tableView);

        Scene scene = new Scene(root, 1000, 600);
        primaryStage.sizeToScene();
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void showPairToCompair(Pair pair) {
        // to be implemented
    }

    /**
     * Setting window is showed on screen
     * User can choose some functions to change saved data on this window
     */

    public void showSettingWindow() {

        Stage primaryStage = new Stage();
        GridPane gridPane = setupGridPane();

        Button settingCriteriaButton = createAnimatedButton("Setting Criteria", gridPane, 0, 0);
        Button removeParticipantButton = createAnimatedButton("Cancelled", gridPane, 0, 1);
        Button changePairButton = createAnimatedButton("Change Pair", gridPane, 0, 2);
        Button changeGroupButton = createAnimatedButton("Change Group", gridPane, 0, 3);

        settingCriteriaButton.setOnAction(e -> {
            changeCriteriaWindow();
        });
        removeParticipantButton.setOnAction(e -> {
            removeParticipantWindow();
        });
        changePairButton.setOnAction(e -> {
            /* ... */ });
        changeGroupButton.setOnAction(e -> {
            /* ... */ });

        VBox vbox = new VBox();
        vbox.getChildren().addAll(settingCriteriaButton, removeParticipantButton, changePairButton, changeGroupButton);
        vbox.setAlignment(Pos.CENTER); // Center alignment
        vbox.setSpacing(10);

        Scene scene = new Scene(vbox, 400, 500);
        primaryStage.sizeToScene();
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Display the list of groups on the screen.
     */
    public void showGroupListOnScreen() {
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Group List for All Courses");

        VBox vbox = new VBox();
        vbox.getChildren().addAll(
                createAndFillTableView("Pair names for Starter", listManagement.dataList.getGroupListCourse01()),
                createAndFillTableView("Pair names for main course", listManagement.dataList.getGroupListCourse02()),
                createAndFillTableView("Pair names for dessert", listManagement.dataList.getGroupListCourse03()));

        Scene scene = new Scene(new StackPane(vbox), 1000, 600);
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.show();
    }

    /**
     * Helper method to create and fill a TableView for Groups with predefined
     * columns.
     * 
     * @param courseName The name of the course for which the table is being
     *                   created.
     * @param groupList  The list of groups to be added to the table.
     * @return A TableView configured for displaying Group objects.
     */
    private TableView<Group> createAndFillTableView(String courseName, List<Group> groupList) {
        TableView<Group> tableView = createTableView(courseName);
        tableView.getItems().addAll(groupList);

        tableView.setRowFactory(tv -> {
            TableRow<Group> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    showGroupDetail(row.getItem());
                }
            });
            return row;
        });

        return tableView;
    }

    /**
     * Helper method to create a TableView for Groups with predefined columns.
     * 
     * @param courseName The name of the course for which the table is being
     *                   created.
     * @return A TableView configured for displaying Group objects.
     */
    private TableView<Group> createTableView(String courseName) {
        TableView<Group> tableView = new TableView<>();

        // Create Column for Group Pairs
        TableColumn<Group, String> groupPairsColumnName = new TableColumn<>(courseName);
        groupPairsColumnName.setCellValueFactory(cellData -> {
            Group group = cellData.getValue();
            StringBuilder pairsString = new StringBuilder();
            for (Pair pair : group.getPairs()) {
                pairsString.append(pair.getParticipant1().getName() + pair.getParticipant2().getName()).append(", ");
            }
            return new SimpleStringProperty(pairsString.toString());
        });

        // Create Column for Group Pairs
        TableColumn<Group, String> groupPairsColumn = new TableColumn<>("Pair IDs");
        groupPairsColumn.setCellValueFactory(cellData -> {
            Group group = cellData.getValue();
            StringBuilder pairsString = new StringBuilder();
            for (Pair pair : group.getPairs()) {
                pairsString.append(pair.getPairId()).append(", ");
            }
            return new SimpleStringProperty(pairsString.toString());
        });

        TableColumn<Group, String> foodPreference = new TableColumn<>("FOOD");
        foodPreference.setCellValueFactory(cellData -> { // pick up data "Pair_ID"
            Group g = cellData.getValue();
            return new SimpleStringProperty(g.getFoodPreference().toString());
        });

        // Create Column of Age Difference
        TableColumn<Group, String> ageDif = new TableColumn<>("AGE DIFFERENCE");
        ageDif.setCellValueFactory(cellData -> { // pick up data "Pair_ID"
            Group g = cellData.getValue();
            return new SimpleStringProperty(Double.toString(g.calculateAgeDifference()));
        });

        // Create Column of GenderDiversity
        TableColumn<Group, String> genderDiv = new TableColumn<>("GENDER DIVERSITY");
        genderDiv.setCellValueFactory(cellData -> { // pick up data "Pair_ID"
            Group g = cellData.getValue();
            return new SimpleStringProperty(Double.toString(g.calculateSexDiversity()));
        });

        // Create Column of Place
        TableColumn<Group, String> place = new TableColumn<>("PLACE(Latitude / Longitude)");
        place.setCellValueFactory(cellData -> { // pick up data "Pair_ID"
            Group g = cellData.getValue();
            String latitude = Double.toString(g.getCookingPair().getPairKitchen().getKitchenLatitude());
            String longitude = Double.toString(g.getCookingPair().getPairKitchen().getKitchenLongitude());
            // return new
            // SimpleStringProperty(placeString.append(latitude+longitude).append(" ,
            // ").toString());
            return new SimpleStringProperty(latitude);
        });

        // Create Column of Score
        TableColumn<Group, String> score = new TableColumn<>("TOTAL SCORE");
        score.setCellValueFactory(cellData -> { // pick up data "Pair_ID"
            Group g = cellData.getValue();
            return new SimpleStringProperty(Double.toString(g.calculateWeightedScore()));
        });

        // Adding columns to the table

        tableView.getColumns().addAll(groupPairsColumnName, groupPairsColumn, foodPreference, genderDiv, score);
        // System.out.println("***********************");
        // tableView.getColumns().add(place);
        return tableView;
    }

    /**
     * Shows the detail of a group in a new stage.
     * 
     * @param group The group for which the detail should be shown.
     */
    private void showGroupDetail(Group group) {
        Stage detailStage = new Stage();

        TableView<Pair> tableView = new TableView<>();

        // Create columns and add to the TableView as per your requirement
        // Assuming Pair class has a property 'name'

        TableColumn<Pair, String> pairNameColumn = new TableColumn<>("Pair Names");
        pairNameColumn.setCellValueFactory(cellData -> {
            Pair pair = cellData.getValue();
            String name = pair.getParticipant1().getName() + " - " + pair.getParticipant2().getName();
            return new SimpleStringProperty(name);
        });

        TableColumn<Pair, String> ageDiffColumn = new TableColumn<>("Age Differences");
        ageDiffColumn.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(Double.toString(group.calculateAgeDifference()));
        });

        TableColumn<Pair, String> genderDivColumn = new TableColumn<>("Gender Diversity");
        genderDivColumn.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(Double.toString(group.calculateSexDiversity()));
        });

        TableColumn<Pair, String> pathColumn = new TableColumn<>("Path Length");
        pathColumn.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(Double.toString(group.calculatePathLength()));
        });

        TableColumn<Pair, String> scoreColumn = new TableColumn<>("Total Score");
        scoreColumn.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(Double.toString(group.calculateWeightedScore()));
        });

        tableView.getColumns().add(pairNameColumn);
        tableView.getColumns().add(ageDiffColumn);
        tableView.getColumns().add(genderDivColumn);
        tableView.getColumns().add(pathColumn);
        tableView.getColumns().add(scoreColumn);

        // Adding pairs of the group to the TableView
        for (Pair pair : group.getPairs()) {
            tableView.getItems().add(pair);
        }

        VBox vbox = new VBox(tableView);
        Scene scene = new Scene(vbox, 500, 500);

        detailStage.setScene(scene);
        detailStage.show();
    }

    /**
     * User can remove a patricipant in this window
     */
    public void removeParticipantWindow() {
        Stage primaryStage = new Stage();
        TextField removedParticipant = new TextField();
        removedParticipant.setPromptText("Please enter the participant's name");
        Label label = new Label("Name ");
        Button ok = new Button("OK");
        ok.setOnAction(e -> {
            try {
                System.out.println("P ListSize : " + listManagement.dataList.getParticipantList().size());
                System.out.println("P CanceledListSize : "
                        + listManagement.dataList.getCanceledList().getCanceledParticipants().size());
                String pName = removedParticipant.getText();
                for (Participant p : listManagement.dataList.getParticipantList()) {
                    if (p.getName().equals(pName)) {
                        listManagement.dataList.getCanceledList().getCanceledParticipants().add(p);
                        listManagement.dataList.getParticipantSuccessorList().addParticipant(p);
                    }
                }
                // remove the participant from list
                listManagement.dataList.getParticipantList().stream().filter(x -> !x.equals(pName))
                        .collect(Collectors.toList());
                System.out.println("P ListSize : " + listManagement.dataList.getParticipantList().size());
                System.out.println("P CanceledListSize : "
                        + listManagement.dataList.getCanceledList().getCanceledParticipants().size());
                primaryStage.close();
            } catch (Exception err) {
            }
        });
        VBox vbox = new VBox();
        vbox.getChildren().addAll(label, removedParticipant, ok);
        Scene scene = new Scene(vbox);
        primaryStage.setTitle("remove participant");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Changes the weights of the criteria by displaying a window with selection
     * options.
     */
    public void changeCriteriaWindow() {

        Stage primaryStage = new Stage();

        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(10);

        List<String> options = new ArrayList<>();
        options.add("So Important");
        options.add("Important");
        options.add("Not Important");
        options.add("Not Neccessary");

        // Food preference
        Label l1 = new Label("Food Preference");
        ComboBox<String> foodPreference = new ComboBox<>();
        foodPreference.getItems().addAll(options);

        // Age Difference
        Label l2 = new Label("Age Diferrence");
        ComboBox<String> ageDiff = new ComboBox<>();
        ageDiff.getItems().addAll(options);

        // Gender Diversity
        Label l3 = new Label("Gender Diversity");
        ComboBox<String> genderDiv = new ComboBox<>();
        genderDiv.getItems().addAll(options);

        // Path Length
        Label l4 = new Label("Path Length");
        ComboBox<String> pathLength = new ComboBox<>();
        pathLength.getItems().addAll(options);

        Button ok = new Button("OK");
        ok.setOnAction(e -> {
            try {
                int foodPreferences = optionToWeight(foodPreference.getValue());
                int ageDifferences = optionToWeight(ageDiff.getValue());
                int genderDiversity = optionToWeight(genderDiv.getValue());
                int path = optionToWeight(pathLength.getValue());
                CRITERIA.FOOD_PREFERENCES.setWeight(foodPreferences);
                CRITERIA.AGE_DIFFERENCE.setWeight(ageDifferences);
                CRITERIA.GENDER_DIVERSITY.setWeight(genderDiversity);
                CRITERIA.PATH_LENGTH.setWeight(path);
                System.out.println("FoodP : " + CRITERIA.FOOD_PREFERENCES.getWeight());
                System.out.println("age : " + CRITERIA.AGE_DIFFERENCE.getWeight());
                System.out.println("gender : " + CRITERIA.GENDER_DIVERSITY.getWeight());
                System.out.println("path : " + CRITERIA.PATH_LENGTH.getWeight());
                primaryStage.close();
            } catch (Exception er) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setHeaderText("Error Setting Criteria");
                alert.setContentText("Please choose one of all Options for each criteria");
            }

        });

        vbox.getChildren().addAll(l1, foodPreference, l2, ageDiff, l3, genderDiv, l4, pathLength, ok);

        Scene scene = new Scene(vbox);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Edit Criteria");
        primaryStage.show();
    }

    /**
     * Converts user's choice of criteria importance to a weight.
     * 
     * @param x The user's choice of the importance of a criterion.
     * @return The weight corresponding to the user's choice.
     */
    public int optionToWeight(String x) {
        if (x.equals("So Important")) {
            return 2000;
        } else if (x.equals("Important")) {
            return 100;
        } else if (x.equals("Not Important")) {
            return 50;
        } else {
            return 0;
        }
    }

}
