package com.example.computer_science_ia.Controllers;

import com.example.computer_science_ia.*;
import com.example.computer_science_ia.Handling.JSONHandling;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.ArrayList;


public class FlashcardController {
    @FXML
    private Button createDeckButton;
    @FXML
    private ListView<String> deckListView;
    @FXML
    private FlashcardDeckList deckList;
    private Stage stage;

    public void initialize() {

        Platform.runLater(() -> {
            if (deckList == null){
                deckList = new FlashcardDeckList();
                loadFlashcardDecks();
            }

            UserSession.setCurrentDeckList(deckList.getFlashcardDeckNames());
            stage = (Stage) createDeckButton.getScene().getWindow();
        });
    }

    public void loadCreateDeckScreen(){
        ScreenHandling.loadFXMLScreen("addDeckScreen.fxml", "Create Deck", 359, 234, false, this);
    }

    public void addDeck(String deckName){
        deckListView.getItems().add(deckName);
        FlashcardQueue deck = new FlashcardQueue(deckName);
        deckList.addDeck(deck);
    }

    public void backButton(){
        saveFlashcards();
        ScreenHandling.loadFXMLScreen(stage, "mainMenu.fxml", "Main Menu", 900, 600, true, this);
    }

    public void loadFlashcardDecks() {
        UserDataSource userSource = new UserDataSource();
        userSource.openConnection();

        User user = JSONHandling.deserializeJSONFile();
        assert user != null;
        if (user.getFlashcardDeckList() != null || !user.getFlashcardDeckList().isEmpty() || !user.getFlashcardDeckList().equals(deckList)) {
            deckList = user.getFlashcardDeckList();
            ArrayList<String> flashcardDeckNames = deckList.getFlashcardDeckNames();
            for (String flashcardDeckName : flashcardDeckNames) {
                deckListView.getItems().add(flashcardDeckName);
            }
        }
        userSource.closeConnection();
    }


    public void saveNewDeck(){
        UserDataSource userSource = new UserDataSource();
        userSource.openConnection();

        User user = JSONHandling.deserializeJSONFile();
        assert user!= null;
        if (!deckListExists(user.getFlashcardDeckList())) {
            user.setFlashcardDeckList(deckList);
            JSONHandling.overWriteUserJSONFile(user);
        }
        userSource.closeConnection();
    }

    public void saveFlashcards(){
        UserDataSource userSource = new UserDataSource();
        userSource.openConnection();

        User user = JSONHandling.deserializeJSONFile();
        assert user!= null;
        user.setFlashcardDeckList(deckList);
        JSONHandling.overWriteUserJSONFile(user);
        userSource.closeConnection();
    }

    public boolean flashcardExists(String deckName, String flashcardQuestion){
        return deckList.flashcardExists(deckName, flashcardQuestion);
    }
    public boolean deckExists(String deckName) {
        return deckList.deckExists(deckName);
    }

    public boolean deckListExists(FlashcardDeckList newDeckList) {
        ArrayList<String> newDeckListNames = newDeckList.getFlashcardDeckNames();
        ArrayList<String> existingDeckNames = deckList.getFlashcardDeckNames();

        return newDeckListNames.equals(existingDeckNames);
    }

    public void flashcardDeckClick(MouseEvent event){
        String deckName = deckListView.getSelectionModel().getSelectedItem();
        if (event.getClickCount() == 2) {
            if (deckListView.getSelectionModel().getSelectedItem() != null) {
                saveNewDeck();
                ScreenHandling.loadFXMLScreen(stage, "flashcardDeckScreen.fxml", "Review Deck: " + deckName, 900, 600, true, this);
            }
        }
    }

    public void updateDeckList(FlashcardQueue updatedQueue){
        deckList.updateDeck(updatedQueue);
        ArrayList<String> deckNames = deckList.getFlashcardDeckNames();
        for (String deckName : deckNames){
            deckListView.getItems().add(deckName);
        }
        deckListView.refresh();
    }

    public FlashcardDeckList getDeckList(){
        return deckList;
    }

    public void addFlashcard(String deck, Flashcard flashcard) {
        deckList.addFlashcard(deck, flashcard);
    }

    public void loadNewFlashcardScreen(){
        ScreenHandling.loadFXMLScreen("addNewFlashcardScreen.fxml", "Add New Flashcard", 359, 234, false, this);
    }

    public void loadRenameDeckScreen(){
        if (deckListView.getSelectionModel().getSelectedItem() == null){
            return;
        }
        String deckName = deckListView.getSelectionModel().getSelectedItem();
        ScreenHandling.loadFXMLScreen("renameDeckScreen.fxml", "Rename Deck: " + deckName, 359, 234, false,this);
    }

    public void deleteDeck(){
        if (deckListView.getSelectionModel().getSelectedItem() == null){
            return;
        }
        String deckName = deckListView.getSelectionModel().getSelectedItem();
        FlashcardQueue deck = deckList.getFlashcardQueueByName(deckName);
        deckList.removeDeck(deck);
        refreshDeckListView(deckName, "");
    }

    public void renameDeck(String oldDeckName, String newDeckName) {
        deckList.renameDeck(oldDeckName, newDeckName);
    }

    public void refreshDeckListView(String oldDeckName, String newDeckName) {
        if (newDeckName.isEmpty()){
            deckListView.getItems().removeIf(name -> name.equals(oldDeckName));
            return;
        }
        deckListView.getItems().removeIf(name -> name.equals(oldDeckName));
        deckListView.getItems().add(newDeckName);
    }
}
