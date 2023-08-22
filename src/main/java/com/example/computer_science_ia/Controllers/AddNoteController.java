package com.example.computer_science_ia.Controllers;

import com.example.computer_science_ia.Handling.LabelHandling;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;

public class AddNoteController {
    @FXML
    private TextField subjectField;
    @FXML
    private TextField titleField;
    @FXML
    private TextArea contentTextArea;
    @FXML
    private Label missingInformationLabel;
    @FXML
    private Label noteAlreadyExistsLabel;
    @FXML
    private Button confirmButton;

    @FXML
    public void createNote() {
        MainMenuController controller = ((MainMenuController) subjectField.getScene().getWindow().getUserData());
        Stage stage = (Stage) confirmButton.getScene().getWindow();
        ArrayList<Label> labels = new ArrayList<>(Arrays.asList(noteAlreadyExistsLabel, missingInformationLabel));
        LabelHandling labelHandling = new LabelHandling(labels);
        if (subjectField.getText().isEmpty() || titleField.getText().isEmpty() || contentTextArea.getText().isEmpty()) {
            labelHandling.showErrorMessage(missingInformationLabel);
        }else if (controller.noteExists(titleField.getText())) {
            labelHandling.showErrorMessage(noteAlreadyExistsLabel);
        }else {
            controller.addNote(subjectField.getText(), titleField.getText(), contentTextArea.getText());
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
