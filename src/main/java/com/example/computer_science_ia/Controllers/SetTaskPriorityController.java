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


public class SetTaskPriorityController {
    @FXML
    private ComboBox<Task.TaskPriority> newTaskPriority;
    @FXML
    private Label missingTaskPriorityLabel;
    @FXML
    private Button confirmButton;

    @FXML
    public void initialize() {
        newTaskPriority.setItems(FXCollections.observableArrayList(Task.TaskPriority.values()));
    }

    public void setTaskPriority(){
        MainMenuController controller = ((MainMenuController) confirmButton.getScene().getWindow().getUserData());
        Stage stage = (Stage) confirmButton.getScene().getWindow();
        String title = stage.getTitle();
        String taskTitle = title.split(":")[1].trim();

        Task.TaskPriority newPriority = Task.TaskPriority.valueOf(newTaskPriority.getValue().toString());
        if(newPriority.toString().isEmpty()){
            missingTaskPriorityLabel.setVisible(true);
        }else{
            controller.setTaskPriority(taskTitle, newPriority);
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
