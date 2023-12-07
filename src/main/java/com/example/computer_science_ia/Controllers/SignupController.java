package com.example.computer_science_ia.Controllers;

import com.example.computer_science_ia.Handling.LabelHandling;
import com.example.computer_science_ia.ScreenHandling;
import com.example.computer_science_ia.User;
import com.example.computer_science_ia.UserDataSource;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.ArrayList;

public class SignupController {

    @FXML
    private Button signupButton;
    @FXML
    private TextField signupUsername;
    @FXML
    private PasswordField signupPassword;
    @FXML
    private PasswordField confirmedSignupPassword;
    @FXML
    private Label passwordLengthLabel;
    @FXML
    private Label accountAlreadyExistsLabel;
    @FXML
    private Label confirmPasswordLabel;
    @FXML
    private Label accountSuccessfullyCreatedLabel;
    @FXML
    private Label usernameLengthLabel;

    // Create a stage to be able to display a screen on the same window
    private Stage stage;

    //Sets all the error messages to be invisible
    public void initialize() {

        //Ensures stage is not null before initialisation
        Platform.runLater(() -> {
            passwordLengthLabel.setVisible(false);
            accountAlreadyExistsLabel.setVisible(false);
            confirmPasswordLabel.setVisible(false);
            accountSuccessfullyCreatedLabel.setVisible(false);
            usernameLengthLabel.setVisible(false);
            stage = (Stage) usernameLengthLabel.getScene().getWindow();
        });
    }

    // Method to handle the signup
    public void signupButton() throws SQLException {
        String username = signupUsername.getText().trim();
        String password = signupPassword.getText().trim();
        String confirmedPassword = confirmedSignupPassword.getText().trim();

        //Creates connection to the database and creates it if it doesn't already exist
        UserDataSource userDataSource = new UserDataSource();
        userDataSource.openConnection();
        UserDataSource.createUserDatabase();

        // Error message handling for easier readability within the code
        ArrayList<Label> errorMessages = new ArrayList<>();
        errorMessages.add(passwordLengthLabel);
        errorMessages.add(accountAlreadyExistsLabel);
        errorMessages.add(confirmPasswordLabel);
        errorMessages.add(accountSuccessfullyCreatedLabel);
        errorMessages.add(usernameLengthLabel);
        LabelHandling errorMessageHandling = new LabelHandling(errorMessages);

        // Validation of the username, password and confirmed password
        if (username.isEmpty()){
            errorMessageHandling.showLabel(usernameLengthLabel);
        }else if (userDataSource.userExists(username)){
            errorMessageHandling.showLabel(accountAlreadyExistsLabel);
        }else if(password.length() < 8){
            errorMessageHandling.showLabel(passwordLengthLabel);
        }else if (!password.equals(confirmedPassword)){
            errorMessageHandling.showLabel(confirmPasswordLabel);
        }else {
            User user = new User(username, password);
            userDataSource.insertUser(user); // Inserts the user into the database
            userDataSource.closeConnection(); // Closes the connection
            ScreenHandling.loadFXMLScreen(stage, "login.fxml", "Log In", 520, 400, false, this);
        }
    }

    // Method to handle the back button
    public void backButton() {
        ScreenHandling.loadFXMLScreen(stage, "login.fxml", "Log In", 520, 400, false, this);
    }

    // Method to handle the enter key
    @FXML
    public void handleEnterKey(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            signupButton.fire();
        }
    }
}
