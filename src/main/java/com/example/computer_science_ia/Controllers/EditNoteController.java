package com.example.computer_science_ia.Controllers;

import com.example.computer_science_ia.Handling.LabelHandling;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;

public class EditNoteController {
    @FXML
    private TextField subjectField;
    @FXML
    private TextField titleField;
    @FXML
    private TextArea contentTextArea;
    @FXML
    private Label missingInformationLabel;
    @FXML
    private Button confirmButton;
    @FXML
    private Label noteExists;

    public void editNote() {
        Stage stage = (Stage) confirmButton.getScene().getWindow();
        String title = stage.getTitle();
        String noteTitle = title.split(":")[1].trim();
        ArrayList<Label> labels = new ArrayList<>(Arrays.asList(missingInformationLabel, noteExists));
        LabelHandling labelHandling = new LabelHandling(labels);

        MainMenuController controller = ((MainMenuController) subjectField.getScene().getWindow().getUserData());
        if (subjectField.getText().isEmpty() || titleField.getText().isEmpty() || contentTextArea.getText().isEmpty()) {
            labelHandling.showLabel(missingInformationLabel);
        }else if (controller.noteExists(noteTitle)) {
            labelHandling.showLabel(noteExists);
        }else{
            controller.setNoteDetails(noteTitle, subjectField.getText(), titleField.getText(), contentTextArea.getText());
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
