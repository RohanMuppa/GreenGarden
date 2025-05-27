package com.example.gg2;

import java.util.List;

import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(5);
        gridPane.setHgap(5);

        // Add UI elements for user input
        Label sizeLabel = new Label("Garden Size (sq m):");
        TextField sizeTextField = new TextField();
        gridPane.add(sizeLabel, 0, 0);
        gridPane.add(sizeTextField, 1, 0);

        Label zipCodeLabel = new Label("Zip Code:");
        TextField zipCodeTextField = new TextField();
        gridPane.add(zipCodeLabel, 0, 1);
        gridPane.add(zipCodeTextField, 1, 1);

        Label sunExposureLabel = new Label("Sun Exposure:");
        ChoiceBox<String> sunExposureChoiceBox = new ChoiceBox<>();

        sunExposureChoiceBox.getItems().addAll("Less than 4 hours", "4-6 hours", "6-8 hours", "More than 8 hours");
        sunExposureChoiceBox.setValue("Less than 4 hours");
        gridPane.add(sunExposureLabel, 0, 2);
        gridPane.add(sunExposureChoiceBox, 1, 2);

        Label maintenanceLabel = new Label("Maintenance Level:");

        ChoiceBox<String> maintenanceChoiceBox = new ChoiceBox<>();
        maintenanceChoiceBox.getItems().addAll("Low", "Moderate", "High");
        maintenanceChoiceBox.setValue("Low");
        gridPane.add(maintenanceLabel, 0, 3);
        gridPane.add(maintenanceChoiceBox, 1, 3);

        Label budgetLabel = new Label("General Budget (USD):");
        TextField budgetTextField = new TextField();
        gridPane.add(budgetLabel, 0, 4);
        gridPane.add(budgetTextField, 1, 4);

        // Define the model
        ObservableList<String> plantTypes = FXCollections.observableArrayList(
                "Annuals", "Perennials", "Bulbs", "Shrubs", "Trees", "Climbers", "Herbs", "Vegetables", "Fruits", "Grasses", "Succulents", "Cacti", "Ferns", "Palms", "Ornamental Grasses");
        BooleanProperty[] typeSelected = new BooleanProperty[plantTypes.size()];
        for (int i = 0; i < typeSelected.length; i++) {
            typeSelected[i] = new SimpleBooleanProperty();
        }

        // Add Existing Plant Types label
        Label plantTypeLabel = new Label("Existing Plant Types:");
        gridPane.add(plantTypeLabel, 0, 7);

        // Add ListView for plant types
        ListView<String> plantTypeListView = new ListView<>();
        plantTypeListView.setItems(plantTypes);

        // Set the cell factory
        plantTypeListView.setCellFactory(listView -> {
            CheckBox checkBox = new CheckBox();
            ListCell<String> cell = new ListCell<String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        checkBox.selectedProperty().bindBidirectional(typeSelected[getIndex()]);
                        checkBox.setText(item);
                        setText(null);
                        setGraphic(checkBox);
                    }
                }
            };
            return cell;
        });

        gridPane.add(plantTypeListView, 1, 7);



        Button submitButton = new Button("Submit");
        gridPane.add(submitButton, 1, 15);

        submitButton.setOnAction(event -> {
                    try {
                        // Parse user inputs
                        int size = Integer.parseInt(sizeTextField.getText());
                        int zipCode = Integer.parseInt(zipCodeTextField.getText());
                        int sunExposure = sunExposureChoiceBox.getSelectionModel().getSelectedIndex() + 1;
                        int maintenanceLevel = maintenanceChoiceBox.getSelectionModel().getSelectedIndex() + 1;

                        int budget = Integer.parseInt(budgetTextField.getText());

                        ChoiceBox<String> maintenanceLevelChoiceBox = new ChoiceBox<>();
                        maintenanceLevelChoiceBox.getItems().addAll("Low", "Moderate", "High");
                        maintenanceLevelChoiceBox.setValue("Low");

                        // Get selected plant types
                        ObservableList<String> existingPlantTypes = FXCollections.observableArrayList();
                        for (int i = 0; i < typeSelected.length; i++) {
                            if (typeSelected[i].get()) {
                                existingPlantTypes.add(plantTypes.get(i));
                            }
                        }


                        // create new garden object
                        Garden garden = new Garden(size, zipCode, sunExposure, budget, existingPlantTypes, maintenanceLevel);

                        List<Plant> selectedplants = garden.getNewPlants();

                        // generate report
                        String report = garden.generateReport();
                        garden.getGardenRecommendations();


                        // show report
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Green Garden App");
                        alert.setHeaderText(null);
                        alert.setContentText(report);
                        alert.showAndWait();

                        alert.setTitle("Green Garden App");
                        alert.setHeaderText(null);
                        alert.setContentText(garden.createMaintenanceSchedule(maintenanceLevel));
                        alert.showAndWait();

                        alert.setTitle("Green Garden App");
                        alert.setHeaderText(null);
                        alert.setContentText(garden.listPlantNames());
                        alert.showAndWait();

                    } catch (NumberFormatException e) {
                        showAlert("Please enter valid inputs.", Alert.AlertType.ERROR);
                    }
                });

            // set padding and gaps
            gridPane.setPadding(new Insets(10));
            gridPane.setHgap(10);
            gridPane.setVgap(10);

            // create scene and show stage
            Scene scene = new Scene(gridPane);
            primaryStage.setTitle("Green Garden App");
            primaryStage.setScene(scene);
            primaryStage.show();

    }

    private void showAlert(String s, Alert.AlertType error) {
        Alert alert = new Alert(error);
        alert.setTitle("Green Garden App");
        alert.setHeaderText(null);
        alert.setContentText(s);
        alert.showAndWait();
    }
}
