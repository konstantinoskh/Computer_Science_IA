package com.example.computer_science_ia.Controllers;

import com.example.computer_science_ia.Handling.LabelHandling;
import com.example.computer_science_ia.Task;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;

public class AddTaskController {
    @FXML
    private TextField taskTitleField;
    @FXML
    private TextArea taskDescriptionField;
    @FXML
    private DatePicker taskDueDate;
    @FXML
    private ComboBox<Task.TaskStatus> taskStatusComboBox;
    @FXML
    private ComboBox<Task.TaskPriority> taskPriorityComboBox;
    @FXML
    private Label missingInformationLabel;
    @FXML
    private Label taskAlreadyExistsLabel;
    @FXML
    private Button confirmButton;

    @FXML
    public void initialize() {
        taskStatusComboBox.setItems(FXCollections.observableArrayList(Task.TaskStatus.values()));
        taskPriorityComboBox.setItems(FXCollections.observableArrayList(Task.TaskPriority.values()));
    }

    @FXML
    public void createNote() {
        MainMenuController controller = ((MainMenuController) confirmButton.getScene().getWindow().getUserData());
        Stage stage = (Stage) confirmButton.getScene().getWindow();
        ArrayList<Label> labels = new ArrayList<>(Arrays.asList(taskAlreadyExistsLabel, missingInformationLabel));
        LabelHandling labelHandling = new LabelHandling(labels);

        Task.TaskStatus status = Task.TaskStatus.valueOf(String.valueOf(taskStatusComboBox.getValue()));
        Task.TaskPriority priority = Task.TaskPriority.valueOf(String.valueOf(taskPriorityComboBox.getValue()));


        Task task = new Task(taskTitleField.getText(), taskDescriptionField.getText(), taskDueDate.getValue(), status, priority);
        if (taskTitleField.getText().isEmpty() || taskDescriptionField.getText().isEmpty() || taskDueDate.getValue() == null || taskStatusComboBox.getValue() == null || taskPriorityComboBox.getValue() == null) {
            labelHandling.showErrorMessage(missingInformationLabel);
        }else if (controller.taskExists(task)) {
            labelHandling.showErrorMessage(taskAlreadyExistsLabel);
        }else {
            controller.addTask(task);
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

