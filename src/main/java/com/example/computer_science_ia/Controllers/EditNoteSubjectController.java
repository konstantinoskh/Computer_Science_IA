package com.example.computer_science_ia.Controllers;

import com.example.computer_science_ia.Handling.LabelHandling;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;

public class EditNoteSubjectController {
    @FXML
    private TextField subjectField;
    @FXML
    private Label blankSubjectField;
    @FXML
    private Button confirmButton;
    @FXML
    private Label subjectIsTheSame;

    public void editNote() {
        Stage stage = (Stage) confirmButton.getScene().getWindow();
        String title = stage.getTitle();
        String noteTitle = title.split(":")[1].trim();
        ArrayList<Label> labels = new ArrayList<>(Arrays.asList(blankSubjectField, subjectIsTheSame));
        LabelHandling labelHandling = new LabelHandling(labels);

        MainMenuController controller = ((MainMenuController) subjectField.getScene().getWindow().getUserData());
        if (subjectField.getText().isEmpty()) {
            labelHandling.showLabel(blankSubjectField);
        }else{
            controller.setNoteSubject(noteTitle, subjectField.getText());
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
