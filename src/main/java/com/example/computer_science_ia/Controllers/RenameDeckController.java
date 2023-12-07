package com.example.computer_science_ia.Controllers;


import com.example.computer_science_ia.Handling.LabelHandling;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;

public class RenameDeckController {
    @FXML
    private Button confirmButton;
    @FXML
    private TextField newDeckNameField;
    @FXML
    private Label missingDeckNameLabel;
    @FXML
    private Label deckAlreadyExistsLabel;
    private Stage stage;
    LabelHandling labelHandling;


    public void renameDeck() {
        Platform.runLater(() -> {
            FlashcardController controller = ((FlashcardController) confirmButton.getScene().getWindow().getUserData());
            ArrayList<Label> labels = new ArrayList<>(Arrays.asList(missingDeckNameLabel, deckAlreadyExistsLabel));
            labelHandling = new LabelHandling(labels);
            if (!newDeckNameField.getText().isEmpty() && !controller.deckExists(newDeckNameField.getText())) {
                stage = (Stage) confirmButton.getScene().getWindow();
                String newDeckName = newDeckNameField.getText();
                String oldDeckName = stage.getTitle().split(":")[1].trim();
                controller.renameDeck(oldDeckName, newDeckName);
                controller.refreshDeckListView(oldDeckName, newDeckName);
                controller.saveFlashcards();
                stage.close();
            }else if (newDeckNameField.getText().isEmpty()){
                labelHandling.showLabel(missingDeckNameLabel);
            }else if (controller.deckExists(newDeckNameField.getText())){
                labelHandling.showLabel(deckAlreadyExistsLabel);
            }
        });
    }

    public void handleEnterKey(KeyEvent keyEvent){
        if (keyEvent.getCode() == KeyCode.ENTER){
            confirmButton.fire();
        }
    }
}
