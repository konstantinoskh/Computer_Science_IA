package com.example.computer_science_ia.Controllers;

import com.example.computer_science_ia.Handling.FileHandling;
import com.example.computer_science_ia.Handling.JSONHandling;
import com.example.computer_science_ia.ScreenHandling;
import com.example.computer_science_ia.User;
import com.example.computer_science_ia.UserDataSource;
import com.example.computer_science_ia.UserSession;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;


import java.util.ArrayList;
import java.util.Arrays;

public class SubjectChoiceController {
    @FXML
    private TextField subjectOneField;
    @FXML
    private TextField subjectTwoField;
    @FXML
    private TextField subjectThreeField;
    @FXML
    private TextField subjectFourField;
    @FXML
    private TextField subjectFiveField;
    @FXML
    private TextField subjectSixField;
    @FXML
    private Button confirmButton;
    @FXML
    private Label enterAllSubjectsLabel;

    private Stage stage;

    @FXML
    public void initialize(){
        // Ensures that enterAllSubjectLabel is not null when stage is initialised
        Platform.runLater(() -> {
            enterAllSubjectsLabel.setVisible(false);
            stage = (Stage) enterAllSubjectsLabel.getScene().getWindow();
        });
    }

    // Method to handle the back button
    @FXML
    public void backButton() {
        ScreenHandling.loadFXMLScreen(stage, "login.fxml", "Log In", 520, 400, false, this);
    }

    // Method to handle the confirm button
    @FXML
    public void confirmButton() {
        String subjectOne = subjectOneField.getText();
        String subjectTwo = subjectTwoField.getText();
        String subjectThree = subjectThreeField.getText();
        String subjectFour = subjectFourField.getText();
        String subjectFive = subjectFiveField.getText();
        String subjectSix = subjectSixField.getText();

        if (subjectOne.isEmpty() || subjectTwo.isEmpty() || subjectThree.isEmpty() || subjectFour.isEmpty() || subjectFive.isEmpty() || subjectSix.isEmpty()) {
            enterAllSubjectsLabel.setVisible(true);
        } else {
            UserDataSource userDataSource = new UserDataSource();
            userDataSource.openConnection();
            ArrayList<String> subjects = new ArrayList<>(Arrays.asList(subjectOne, subjectTwo, subjectThree, subjectFour, subjectFive, subjectSix));
            User currentUser = JSONHandling.deserializeJSONFile(); // Returns data from JSON file and converts it into a User object
            userDataSource.closeConnection();

            assert currentUser != null;
            UserSession.setSubjects(subjects); // Sets current session's subjects so that they can be accessed from other classes
            currentUser.setSubjects(subjects); // Sets the current user's subjects so that they can be written to the JSON file
            JSONHandling.overWriteUserJSONFile(currentUser); // Writes the JSON file of the user to now contain the user's subjects
            FileHandling.createSubjectFolders(subjects); // Creates the folders for each subject


            ScreenHandling.loadFXMLScreen(stage, "mainMenu.fxml", "Main Menu", 900, 600, true, this);
        }
    }

    // Method to handle the enter key
    @FXML
    public void handleEnterKey(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            confirmButton.fire();
        }
    }
}
