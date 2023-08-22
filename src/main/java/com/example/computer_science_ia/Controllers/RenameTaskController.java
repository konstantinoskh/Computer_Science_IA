package com.example.computer_science_ia.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class RenameTaskController {
    @FXML
    private TextField newTaskNameField;
    @FXML
    private Label missingTaskNameLabel;
    @FXML
    private Button confirmButton;


    public void renameTask(){
        MainMenuController controller = ((MainMenuController) confirmButton.getScene().getWindow().getUserData());
        Stage stage = (Stage) confirmButton.getScene().getWindow();
        String title = stage.getTitle();
        String taskTitle = title.split(":")[1].trim();

        String newTaskName = newTaskNameField.getText();
        if(newTaskName.isEmpty()){
            missingTaskNameLabel.setVisible(true);
        }else{
            controller.setTaskTitle(taskTitle, newTaskName);
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
