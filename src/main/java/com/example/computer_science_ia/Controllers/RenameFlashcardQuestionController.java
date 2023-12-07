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

public class RenameFlashcardQuestionController {
    @FXML
    private Button confirmButton;
    @FXML
    private Label missingQuestionLabel;
    @FXML
    private Label questionAlreadyExistsLabel;
    @FXML
    private TextArea newQuestionTextArea;


    public void editQuestion(){
        Stage stage = (Stage) confirmButton.getScene().getWindow();
        FlashcardDeckController controller = (FlashcardDeckController) confirmButton.getScene().getWindow().getUserData();
        ArrayList<Label> labels = new ArrayList<>(Arrays.asList(missingQuestionLabel, questionAlreadyExistsLabel));
        LabelHandling labelHandling = new LabelHandling(labels);
        String newQuestion = newQuestionTextArea.getText();
        if (newQuestion.isEmpty()){
            labelHandling.showLabel(missingQuestionLabel);
        }else if (controller.questionExists(newQuestion.trim())){
            labelHandling.showLabel(questionAlreadyExistsLabel);
        }else{
            controller.editQuestion(newQuestion.trim());
            stage.close();
        }
    }

    public void handleEnterKey(KeyEvent event){
        if(event.getCode() == KeyCode.ENTER){
            confirmButton.fire();
        }
    }
}
