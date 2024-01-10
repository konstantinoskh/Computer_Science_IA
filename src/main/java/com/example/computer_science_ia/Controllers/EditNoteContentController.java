package com.example.computer_science_ia.Controllers;

import com.example.computer_science_ia.Handling.LabelHandling;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;

public class EditNoteContentController {
    @FXML
    private TextArea newContent;
    @FXML
    private Label blankContentField;
    @FXML
    private Button confirmButton;

    public void editNote() {
        Stage stage = (Stage) confirmButton.getScene().getWindow();
        String title = stage.getTitle();
        String noteTitle = title.split(":")[1].trim();
        ArrayList<Label> labels = new ArrayList<>(Arrays.asList(blankContentField));
        LabelHandling labelHandling = new LabelHandling(labels);

        MainMenuController controller = ((MainMenuController) newContent.getScene().getWindow().getUserData());
        if (newContent.getText().isEmpty()) {
            labelHandling.showLabel(blankContentField);
        }else{
            controller.setNoteContent(noteTitle, newContent.getText());
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
