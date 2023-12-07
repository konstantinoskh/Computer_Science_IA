package com.example.computer_science_ia.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class RenameFlashcardAnswerController {
    @FXML
    private Button confirmButton;
    @FXML
    private Label missingAnswerLabel;
    @FXML
    private TextArea newAnswerTextArea;


    public void editAnswer(){
        Stage stage = (Stage) confirmButton.getScene().getWindow();
        FlashcardDeckController controller = (FlashcardDeckController) confirmButton.getScene().getWindow().getUserData();
        String newAnswer = newAnswerTextArea.getText();
        if (newAnswer.isEmpty()){
            missingAnswerLabel.setVisible(true);
        }else{
            controller.editAnswer(newAnswer);
            stage.close();
        }
    }

    public void handleEnterKey(KeyEvent event){
        if(event.getCode() == KeyCode.ENTER){
            confirmButton.fire();
        }
    }
}
