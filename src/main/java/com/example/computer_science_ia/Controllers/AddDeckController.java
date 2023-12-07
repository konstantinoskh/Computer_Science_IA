package com.example.computer_science_ia.Controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;


public class AddDeckController {
    @FXML
    private TextField deckNameField;
    @FXML
    private Button confirmButton;
    @FXML
    private Label noUserInputLabel;
    @FXML
    private Label deckAlreadyExistsLabel;

    public void createDeck(){
        FlashcardController controller = (FlashcardController) deckNameField.getScene().getWindow().getUserData();
        String deckName = deckNameField.getText();
        Stage stage = (Stage) confirmButton.getScene().getWindow();

        Platform.runLater(() -> {
            if (deckNameField.getText().isEmpty()){
                noUserInputLabel.setVisible(true);
            }else if (controller.deckExists(deckName)) {
                deckAlreadyExistsLabel.setVisible(true);
            }else {
                controller.addDeck(deckName);
                controller.saveNewDeck();
                stage.close();
            }
        });
    }

    @FXML
    public void handleEnterKey(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            confirmButton.fire();
        }
    }
}
