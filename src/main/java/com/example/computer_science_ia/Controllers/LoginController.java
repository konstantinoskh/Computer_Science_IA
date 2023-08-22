package com.example.computer_science_ia.Controllers;

import com.example.computer_science_ia.*;
import com.example.computer_science_ia.Handling.LabelHandling;
import com.example.computer_science_ia.Handling.FileHandling;
import com.example.computer_science_ia.Handling.JSONHandling;
import com.example.computer_science_ia.ScreenHandling;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;

public class LoginController {
    @FXML
    private Button loginButton;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label userDoesntExistLabel;
    @FXML
    private Label passwordIsIncorrectLabel;
    @FXML
    private Label enterUsernameLabel;
    @FXML
    private Label enterPasswordLabel;

    // Create a stage to be able to display a screen on the same window
    private Stage stage;

    @FXML
    public void initialize(){

        // Ensures enterUsernameLabel isn't null when the stage field is initialised
        Platform.runLater(() -> {
            enterUsernameLabel.setVisible(false);
            passwordIsIncorrectLabel.setVisible(false);
            userDoesntExistLabel.setVisible(false);
            stage = (Stage) enterUsernameLabel.getScene().getWindow();
        });
    }

    public void login() throws SQLException {

        // Create the database if it doesn't exist and open a connection
        UserDataSource.createUserDatabase();
        UserDataSource userDataSource = new UserDataSource();
        userDataSource.openConnection();

        // Error message handling for easier readability within the code
        ArrayList<Label> errorMessages = new ArrayList<>();
        errorMessages.add(userDoesntExistLabel);
        errorMessages.add(passwordIsIncorrectLabel);
        errorMessages.add(enterUsernameLabel);
        errorMessages.add(enterPasswordLabel);
        LabelHandling errorMessageHandling = new LabelHandling(errorMessages);

        String username = usernameTextField.getText();
        String password = passwordField.getText();

        User user = new User(username, password);

        if (username.isEmpty()){
            errorMessageHandling.showErrorMessage(enterUsernameLabel);
        }else if (!userDataSource.userExists(username)) {
            errorMessageHandling.showErrorMessage(userDoesntExistLabel);
        }else if (password.isEmpty()){
            errorMessageHandling.showErrorMessage(enterPasswordLabel);
        }else if (!userDataSource.verifyPassword(user)){
            errorMessageHandling.showErrorMessage(passwordIsIncorrectLabel);
        }else {
            File userFolder = FileHandling.createUserFolder(user.getUsername()); //Create the user folder if it doesn't exist
            JSONHandling.createUserJSONFile(user); //Serialize the user's data to a JSON file
            UserSession.setLoggedInUsername(user.getUsername()); //Set the user's username for the current session; accessible by all other classes
            File[] files = userFolder.listFiles();
            assert files != null;
            if (files.length == 1){
                userDataSource.closeConnection();

                ScreenHandling.loadFXMLScreenInSameWindow(stage, "subjectChoice.fxml", "Subject Choice", 900, 600, true);
            }else {
                User sessionUser = JSONHandling.deserializeJSONFile();
                assert sessionUser != null;
                UserSession.setSubjects(sessionUser.getSubjects());
                userDataSource.closeConnection();

                ScreenHandling.loadFXMLScreenInSameWindow(stage,"mainMenu.fxml", "Main Menu", 900, 600, true);
            }
        }
    }

    // Method to handle signup button press
    public void signup() {
        ScreenHandling.loadFXMLScreenInSameWindow(stage, "signup.fxml", "Sign Up", 520, 400, false);
    }

    // Method to handle enter key press
    @FXML
    public void handleEnterKey(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            loginButton.fire();
        }
    }
}
