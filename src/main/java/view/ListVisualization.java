package view;//import necessary libraries

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import logic.CRITERIA;
import logic.ListManagement;
import logic.readCSV;
import models.Group;
import models.Pair;
import models.Participant;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import logic.DataList;



public class ListVisualization extends Application {

    private File partyLocation;
    private File participantCSVFile;
    private readCSV readCSV;
    private ListManagement listManagement;

    private DataList dataList;
    private boolean isPairGenerated = false;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Cooking Event");


        // Create Button
        Button createNewEventButton = new Button("Create New Event");
        createNewEventButton.setOnAction(e -> switchToMainInterface(primaryStage));

        // Create Layout and add Button and TextField
        VBox vbox = new VBox(createNewEventButton);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(8);

        // Create a Scene with the Layout
        Scene scene = new Scene(vbox, 400, 200);

        // Set the Scene to the Stage
        primaryStage.setScene(scene);

        // Show the Stage
        primaryStage.show();
    }

    /**
     * This method contains mainfunctions(make Pair and make Group)
     * And user can do it on this window
     * @param primaryStage
     */

    private void switchToMainInterface(Stage primaryStage) {
        primaryStage.setTitle("Event Options");

        VBox vbox = new VBox();

        // Create Button to import participant.csv Data
        Button importCSVButton = new Button("Import Pariticpants");
        importCSVButton.setOnAction(e -> {
            FileChooser fileChooser_Participant = new FileChooser();
            fileChooser_Participant.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("CSV Files", "*.csv")
            );
            fileChooser_Participant.setTitle("Open CSV File");

            // Open Dialog to choose file
            participantCSVFile = fileChooser_Participant.showOpenDialog(primaryStage);

        });


        // Create TextField for event date
        TextField eventDateField = new TextField();
        eventDateField.setPromptText("Enter Event Date(dd/MM/yyyy)");

        // Create Button to import party location CSV
        Button importPartyLocationButton = new Button("Import Party Location");
        importPartyLocationButton.setOnAction(e -> {
            // Create a FileChooser
            FileChooser fileChooser_PartyLocation = new FileChooser();
            fileChooser_PartyLocation.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("CSV Files", "*.csv")
            );
            fileChooser_PartyLocation.setTitle("Open CSV File");
            // Open Dialog to choose file
            partyLocation = fileChooser_PartyLocation.showOpenDialog(primaryStage);

        });

        // Create TextField for max participants
        TextField maxParticipantsField = new TextField();
        maxParticipantsField.setPromptText("Enter Max Amount of Participants");

        // Create Button to create event
        Button createEventButton = new Button("Create Event");
        createEventButton.setOnAction(e -> {
                    // Logic to create event
                    try {
                        // Use readCSV instance to read file
                        readCSV = new readCSV(participantCSVFile, partyLocation);
                        listManagement = new ListManagement(readCSV.event.getDataList());
                    } catch(Exception a) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error Dialog");
                        alert.setHeaderText("File Reading Error");
                        alert.setContentText("There was an error while creating the event, please check if the data is correct!");
                        alert.showAndWait();
                    }
                    try {
                        // set max Amount of participants in Event
                       // lm.dataList.getEvent().setMaxParticipant(Integer.parseInt(maxParticipantsField.getText()));
                    } catch(Exception b) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error Dialog");
                        alert.setHeaderText("Format Error");
                        alert.setContentText("Please write only number in filed max participant");
                        alert.showAndWait();
                    }
                    try {
                        // set event date in event
                        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                        //lm.dataList.getEvent().setDate(format.parse(eventDateField.getText()));
                    } catch(Exception c) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error Dialog");
                        alert.setHeaderText("Format Error");
                        alert.setContentText("Please write date as dd/MM/yyyy");
                        alert.showAndWait();
                    }
                    System.out.println("import participant sucessfully");
                    for (Participant p : listManagement.dataList.getParticipantList()) {
                        p.show();
                    }
                    System.out.println(listManagement.dataList.getEvent().getPartyLatitude());
                    System.out.println(listManagement.dataList.getEvent().getPartyLongitude());
                    mainMenu(primaryStage);
                }
        );

        // Create Layout and add components
        vbox.getChildren().addAll(eventDateField, maxParticipantsField, importCSVButton, importPartyLocationButton, createEventButton);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(8);

        // Create a Scene with the Layout
        Scene scene = new Scene(vbox, 400, 300);

        // Set the Scene to the Stage
        primaryStage.setScene(scene);
    }

    /**
     * Create the Button to make best Pair List and to show the created List
     *
     * @param primaryStage
     */
    public void mainMenu(Stage primaryStage) {
        primaryStage.setTitle("Make Pair");

        VBox vbox = new VBox();



        // Create Button to make Pair
        Button makePairButton = new Button("make Pair");

        makePairButton.setOnAction( e -> {
            try{
                listManagement.makeBestPairList();
                Text text = new Text("All pairs were created successfully");
                vbox.getChildren().add(text);
                isPairGenerated = true;
            } catch (Exception e1){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setHeaderText("Error making pair");
                alert.setContentText("The participantsList s invalid to build pairs");

                alert.showAndWait();
            }
        });

        // Create Button to make Group
        Button makeGroupButton = new Button("make Group");

        makeGroupButton.setOnAction( e -> {
            try{
                if(listManagement.dataList.getGroupListCourse01().size() == 0){
                    System.out.println("Make group rein");
                    listManagement.makeBestGroupList();
                    listManagement.makeBestGroupList();
                    listManagement.makeBestGroupList();
                    Text text2 = new Text("All group for 3 courses were created successfully");
                    vbox.getChildren().add(text2);
                }
                else{

                }
            } catch (Exception e1){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setHeaderText("Error making Group");
                alert.setContentText("The Pair List is invalid to build a group");

                alert.showAndWait();
            }
        });

        // Create Button to show the pair list
        Button showPairListButton = new Button("show pairlist");

        showPairListButton.setOnAction(e -> {
            if(listManagement.dataList.getPairList() != null){
                showPairListOnScreen();
            } else{
                Text errorText = new Text();
                errorText.setText("pairList is empty yet");
                errorText.setText("Please click button make Pair");
                vbox.getChildren().add(errorText);
            }
        });

        // Create Button to show Groups
        Button showGroupListButton = new Button("show Groups");

        showGroupListButton.setOnAction(e -> {
            if (isPairGenerated) {
                showGroupListOnScreen();
            } else {
                Text errorText = new Text();
                errorText.setText("Grouplist is empty yet");
                errorText.setText("Please click button make Group");
                vbox.getChildren().add(errorText);
            }
        });

        Button setting = new Button("Setting");
        setting.setOnAction(e -> {
            showSettingWindow();
        });


        vbox.getChildren().add(makePairButton);
        vbox.getChildren().add(showPairListButton);
        vbox.getChildren().add(makeGroupButton);
        vbox.getChildren().add(showGroupListButton);
        vbox.getChildren().add(setting);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(8);
        Scene scene = new Scene(vbox,400,400);
        primaryStage.setScene(scene);

    }


    /**
     * This Method is called, if Button show PairList is clicked
     * it shows pair_id and both of participants in pair on table
     */

    public void showPairListOnScreen(){


        Stage primaryStage = new Stage();
        primaryStage.setTitle("Pair List");

        TableView<Pair> tableView = new TableView<>();

        // Create Column of pairID
        TableColumn<Pair, String> pairIDColumn = new TableColumn<>("Pair ID");
        pairIDColumn.setCellValueFactory(cellData -> {  // pick up data "Pair_ID"
            Pair pair = cellData.getValue();
            return new SimpleStringProperty(pair.getPairId());
        });

        // Create Column of Name_participant 1
        TableColumn<Pair, String> participant1Column = new TableColumn<>("Participant1");
        participant1Column.setCellValueFactory(cellData -> {  // pick up data "Pair_ID"
            Pair pair = cellData.getValue();
            return new SimpleStringProperty(pair.getParticipant1().getName());
        });

        // Create Column of Name_participant 2
        TableColumn<Pair, String> participant2Column = new TableColumn<>("Participant 2");
        participant2Column.setCellValueFactory(cellData -> {   // pick up data "Pair_ID"
            Pair pair = cellData.getValue();
            return new SimpleStringProperty(pair.getParticipant2().getName());
        });

        // Create Column of FoodPreference
        TableColumn<Pair, String> foodPreference = new TableColumn<>("FOOD PREFERENCE");
        foodPreference.setCellValueFactory(cellData -> {   // pick up data "Pair_ID"
            Pair pair = cellData.getValue();
            return new SimpleStringProperty(pair.getFoodPreference().toString());
        });

        // Create Column of Age Difference
        TableColumn<Pair, String> ageDif = new TableColumn<>("AGE DIFFERENCE");
        ageDif.setCellValueFactory(cellData -> {   // pick up data "Pair_ID"
            Pair pair = cellData.getValue();
            return new SimpleStringProperty(Double.toString(pair.calculateAgeDifference()));
        });

        // Create Column of GenderDiversity
        TableColumn<Pair, String> genderDiv = new TableColumn<>("GENDER DIVERSITY");
        genderDiv.setCellValueFactory(cellData -> {   // pick up data "Pair_ID"
            Pair pair = cellData.getValue();
            return new SimpleStringProperty(Double.toString(pair.calculateSexDiversity()));
        });

        // Create Column of FoodPreference
        TableColumn<Pair, String> pathLength = new TableColumn<>("PATH LENGTH");
        pathLength.setCellValueFactory(cellData -> {   // pick up data "Pair_ID"
            Pair pair = cellData.getValue();
            return new SimpleStringProperty(Double.toString(pair.calculatePathLength()));
        });

        // Create Column of Score
        TableColumn<Pair, String> score = new TableColumn<>("TOTAL SCORE");
        score.setCellValueFactory(cellData -> {   // pick up data "Pair_ID"
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


        for(Pair p : listManagement.dataList.getPairList()){
            tableView.getItems().add(p);
        }


        StackPane root = new StackPane();
        root.getChildren().add(tableView);

        Scene scene = new Scene(root, 1000, 600);
        primaryStage.sizeToScene();
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Setting window is showed on screen
     * User can choose some functions to change saved data on this window
     */

    public void showSettingWindow(){
        Stage primaryStage = new Stage();
        Button settingCriteriaButton = new Button("Setting Criteria");
        settingCriteriaButton.setOnAction( e -> {
            changeCriteriaWindow();
        });

        Button removeParticipantButton = new Button("Canceled");
        removeParticipantButton.setOnAction( e -> {
            removeParticipantWindow();
        });

        Button changePairButton = new Button("Change Pair");
        changePairButton.setOnAction( e -> {
            //changePairWindow();
        });

        Button changeGroupButton = new Button("Change Group");
        changeGroupButton.setOnAction( e -> {
            //changeGroupWindow();
        });

        VBox vbox = new VBox();
        vbox.getChildren().addAll(settingCriteriaButton,removeParticipantButton,changePairButton,changeGroupButton);

        Scene scene = new Scene(vbox);

        primaryStage.setScene(scene);

        primaryStage.show();

    }

    /**
     * GroupList is shoed on screen
     */

    public void showGroupListOnScreen() {
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Group List for All Courses");

        // Create three separate tables for each course
        TableView<Group> tableViewCourse01 = createTableView("Pair names for Starter");
        TableView<Group> tableViewCourse02 = createTableView("Pair names for main course");
        TableView<Group> tableViewCourse03 = createTableView("Pair names for dessert");



        // Adding groups to the tables
        for (Group g : listManagement.dataList.getGroupListCourse01()) {

            tableViewCourse01.getItems().add(g);
        }

        for (Group g : listManagement.dataList.getGroupListCourse02()) {

            tableViewCourse02.getItems().add(g);
        }

        for (Group g : listManagement.dataList.getGroupListCourse03()) {

            tableViewCourse03.getItems().add(g);
        }

        // VBox to stack tables vertically
        VBox vbox = new VBox(tableViewCourse01, tableViewCourse02, tableViewCourse03);

        StackPane root = new StackPane();
        root.getChildren().add(vbox);

        Scene scene = new Scene(root, 1000, 600);
        primaryStage.sizeToScene();
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Helper method to create a TableView for Groups with predefined columns
     * @return A TableView configured for displaying Group objects
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
        foodPreference.setCellValueFactory(cellData -> {   // pick up data "Pair_ID"
            Group g = cellData.getValue();
            return new SimpleStringProperty(g.getFoodPreference().toString());
        });

        // Create Column of Age Difference
        TableColumn<Group, String> ageDif = new TableColumn<>("AGE DIFFERENCE");
        ageDif.setCellValueFactory(cellData -> {   // pick up data "Pair_ID"
            Group g = cellData.getValue();
            return new SimpleStringProperty(Double.toString(g.calculateAgeDifference()));
        });

        // Create Column of GenderDiversity
        TableColumn<Group, String> genderDiv = new TableColumn<>("GENDER DIVERSITY");
        genderDiv.setCellValueFactory(cellData -> {   // pick up data "Pair_ID"
            Group g = cellData.getValue();
            return new SimpleStringProperty(Double.toString(g.calculateSexDiversity()));
        });



        // Create Column of Place
        TableColumn<Group, String> place = new TableColumn<>("PLACE(Latitude / Longitude)");
        place.setCellValueFactory(cellData -> {   // pick up data "Pair_ID"
            Group g = cellData.getValue();
            String latitude = Double.toString(g.getCookingPair().getPairKitchen().getKitchenLatitude());
            String longitude = Double.toString(g.getCookingPair().getPairKitchen().getKitchenLongitude());
            //return new SimpleStringProperty(placeString.append(latitude+longitude).append(" , ").toString());
            return new SimpleStringProperty(latitude);
        });



        /*
        // Create Column of Pathlength
        TableColumn<Group, String> pathLength = new TableColumn<>("PATH LENGTH");
        pathLength.setCellValueFactory(cellData -> {   // pick up data "Pair_ID"
            Group g = cellData.getValue();
            DecimalFormat decimalFormat = new DecimalFormat("#.##");

            return new SimpleStringProperty(Double.toString(g.calculateDistanceBetweenKitchenAndParty(Double.parseDouble(decimalFormat.format(g.getCookingPair().getPairKitchen().getKitchenLatitude()))
                    , Double.parseDouble(decimalFormat.format(g.getCookingPair().getPairKitchen().getKitchenLatitude())))));
        });

         */

        // Create Column of Score
        TableColumn<Group, String> score = new TableColumn<>("TOTAL SCORE");
        score.setCellValueFactory(cellData -> {   // pick up data "Pair_ID"
            Group g = cellData.getValue();
            return new SimpleStringProperty(Double.toString(g.calculateWeightedScore()));
        });

        // Adding columns to the table

        tableView.getColumns().addAll(groupPairsColumnName,groupPairsColumn,foodPreference,genderDiv,score);
        //System.out.println("***********************");
        //tableView.getColumns().add(place);
        return tableView;
    }

    /**
     * User can remove a patricipant in this window
     */
    public void removeParticipantWindow(){
        Stage primaryStage = new Stage();
        TextField removedParticipant = new TextField();
        removedParticipant.setPromptText("Please enter the participant's name");
        Label label = new Label("Name ");
        Button ok = new Button("OK");
        ok.setOnAction( e ->  {
            try{
                System.out.println("P ListSize : " + listManagement.dataList.getParticipantList().size());
                System.out.println("P CanceledListSize : " + listManagement.dataList.getCanceledList().getCanceledParticipants().size());
                String pName = removedParticipant.getText();
                for(Participant p : listManagement.dataList.getParticipantList()){
                    if(p.getName().equals(pName)){
                        listManagement.dataList.getCanceledList().getCanceledParticipants().add(p);
                        listManagement.dataList.getParticipantSuccessorList().addParticipant(p);
                    }
                }
                // remove the participant from list
                listManagement.dataList.getParticipantList().stream().filter(x -> !x.equals(pName)).collect(Collectors.toList());
                System.out.println("P ListSize : " + listManagement.dataList.getParticipantList().size());
                System.out.println("P CanceledListSize : " + listManagement.dataList.getCanceledList().getCanceledParticipants().size());
                primaryStage.close();
                }catch(Exception err) {
            }
            }
        );
        VBox vbox = new VBox();
        vbox.getChildren().addAll(label,removedParticipant,ok);
        Scene scene = new Scene(vbox);
        primaryStage.setTitle("remove participant");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * User can change the weights of criterias on this window
     */
    public void changeCriteriaWindow(){

        Stage primaryStage = new Stage();

        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(10);

        List<String> options = new ArrayList<>();
        options.add("So Important");
        options.add("Important");
        options.add("Not Important");

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
        ok.setOnAction( e -> {
            try{
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
            }
            catch (Exception er){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setHeaderText("Error Setting Criteria");
                alert.setContentText("Please choose one of all Options for each criteria" );
            }


        });


        vbox.getChildren().addAll(l1,foodPreference,l2,ageDiff,l3,genderDiv,l4,pathLength,ok);

        Scene scene = new Scene(vbox);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Edit Criteria");
        primaryStage.show();
    }

    /**
     * Helper method for changeCriteriaWindow
     * Choosed Options is changed to Weight(in Integer)
     * @param x Choosed Option by user in String
     * @return  So Important -> 20
     *          Important -> 10
     *          Not Important -> 5
     */

    public int optionToWeight(String x){
        if(x.equals("So Important")){
            return 20;
        }
        else if(x.equals("Important")){
            return 10;
        }
        else{
            return 5;
        }
    }



}
