package com.example.computer_science_ia.Controllers;

import com.example.computer_science_ia.Task;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;


public class SetTaskStatusController {
    @FXML
    private ComboBox<Task.TaskStatus> newTaskStatus;
    @FXML
    private Label missingTaskStatusLabel;
    @FXML
    private Button confirmButton;

    @FXML
    public void initialize() {
        newTaskStatus.setItems(FXCollections.observableArrayList(Task.TaskStatus.values()));
    }



    public void setTaskStatus(){
        MainMenuController controller = ((MainMenuController) confirmButton.getScene().getWindow().getUserData());
        Stage stage = (Stage) confirmButton.getScene().getWindow();
        String title = stage.getTitle();
        String taskTitle = title.split(":")[1].trim();

        Task.TaskStatus newStatus = Task.TaskStatus.valueOf(String.valueOf(newTaskStatus.getValue()));
        if(newStatus.toString().isEmpty()){
            missingTaskStatusLabel.setVisible(true);
        }else{
            controller.setTaskStatus(taskTitle, newStatus);
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

