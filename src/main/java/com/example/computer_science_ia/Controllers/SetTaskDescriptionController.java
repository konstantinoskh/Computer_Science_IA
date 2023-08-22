package com.example.computer_science_ia.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class SetTaskDescriptionController {
    @FXML
    private TextArea newTaskDescriptionField;
    @FXML
    private Label missingTaskDescriptionLabel;
    @FXML
    private Button confirmButton;


    public void setTaskDescription(){
        MainMenuController controller = ((MainMenuController) confirmButton.getScene().getWindow().getUserData());
        Stage stage = (Stage) confirmButton.getScene().getWindow();
        String title = stage.getTitle();
        String taskTitle = title.split(":")[1].trim();

        String newDescription = newTaskDescriptionField.getText();
        if(newDescription.isEmpty()){
            missingTaskDescriptionLabel.setVisible(true);
        }else{
            controller.setTaskDescription(taskTitle, newDescription);
            stage.close();
        }
    }

    @FXML
    public void handleEnterKey(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            confirmButton.fire();
        }
    }
}
