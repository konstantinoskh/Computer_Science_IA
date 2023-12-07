package com.example.computer_science_ia.Controllers;

import com.example.computer_science_ia.Flashcard;
import com.example.computer_science_ia.Handling.LabelHandling;
import com.example.computer_science_ia.UserSession;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;


public class AddFlashcardController {
    @FXML
    private Button confirmButton;
    @FXML
    private TextArea questionTextArea;
    @FXML
    private TextArea answerTextArea;
    @FXML
    private ComboBox<Flashcard.FlashcardDifficulty> flashcardDifficultyComboBox;
    @FXML
    private ComboBox<String> deckComboBox;
    @FXML
    private Label missingInformationLabel;
    @FXML
    private Label flashcardAlreadyExistsLabel;
    private LabelHandling labelHandling;
    private FlashcardController flashcardController;
    Stage stage;

    public void initialize() {
        Platform.runLater(() -> {
            flashcardController = (FlashcardController) confirmButton.getScene().getWindow().getUserData();
            stage = (Stage) confirmButton.getScene().getWindow();
            ArrayList<String> deckNames = UserSession.getCurrentDeckList();
            flashcardDifficultyComboBox.setItems(FXCollections.observableArrayList(Flashcard.FlashcardDifficulty.values()));
            deckComboBox.setItems(FXCollections.observableArrayList(deckNames));
            ArrayList<Label> labels = new ArrayList<>(Arrays.asList(missingInformationLabel, flashcardAlreadyExistsLabel));
            labelHandling = new LabelHandling(labels) ;
        });
    }
    public void createFlashcard(){
        String flashcardQuestion = questionTextArea.getText();
        String flashcardAnswer = answerTextArea.getText();
        Flashcard.FlashcardDifficulty flashcardDifficulty = flashcardDifficultyComboBox.getValue();
        String deckName = deckComboBox.getValue();

        if (flashcardQuestion.isEmpty() || flashcardAnswer.isEmpty() || flashcardDifficulty == null || deckName == null)  {
            labelHandling.showLabel(missingInformationLabel);
        }else if (flashcardController.flashcardExists(deckName, flashcardQuestion)){
            labelHandling.showLabel(flashcardAlreadyExistsLabel);
        }else {
            Flashcard flashcard = new Flashcard(flashcardQuestion, flashcardAnswer, flashcardDifficulty);
            flashcardController.addFlashcard(deckName, flashcard);
            flashcardController.saveFlashcards();
            stage.close();
        }
    }

    public void handleEnterKey(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER){
            confirmButton.fire();
        }
    }
}
