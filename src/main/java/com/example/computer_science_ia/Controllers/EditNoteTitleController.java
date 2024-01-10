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

public class EditNoteTitleController {
    @FXML
    private TextField titleField;
    @FXML
    private Label blankTitleField;
    @FXML
    private Button confirmButton;
    @FXML
    private Label noteExists;

    public void editNote() {
        Stage stage = (Stage) confirmButton.getScene().getWindow();
        String title = stage.getTitle();
        String noteTitle = title.split(":")[1].trim();
        ArrayList<Label> labels = new ArrayList<>(Arrays.asList(blankTitleField, noteExists));
        LabelHandling labelHandling = new LabelHandling(labels);

        MainMenuController controller = ((MainMenuController) titleField.getScene().getWindow().getUserData());
        if (titleField.getText().isEmpty()) {
            labelHandling.showLabel(blankTitleField);
        }else if (controller.noteExists(titleField.getText())) {
            labelHandling.showLabel(noteExists);
        }else{
            controller.setNoteTitle(noteTitle, titleField.getText());
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
