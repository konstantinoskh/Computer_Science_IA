package com.example.computer_science_ia.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.time.LocalDate;

public class SetTaskDueDateController {
    @FXML
    private DatePicker newDueDate;
    @FXML
    private Label missingTaskDueDateLabel;
    @FXML
    private Button confirmButton;


    public void setTaskDueDate(){
        MainMenuController controller = ((MainMenuController) confirmButton.getScene().getWindow().getUserData());
        Stage stage = (Stage) confirmButton.getScene().getWindow();
        String title = stage.getTitle();
        String taskTitle = title.split(":")[1].trim();

        LocalDate newDate = newDueDate.getValue();
        if(newDate == null){
            missingTaskDueDateLabel.setVisible(true);
        }else{
            controller.setTaskDueDate(taskTitle, newDate);
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
