package com.example.computer_science_ia.Controllers;

import com.example.computer_science_ia.*;
import com.example.computer_science_ia.Flashcard.FlashcardDifficulty;
import com.example.computer_science_ia.Handling.LabelHandling;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.ArrayList;
import java.util.Arrays;

public class FlashcardDeckController {
    private Stage stage;
    private FlashcardController controller;
    private LabelHandling flashcardLabelHandling;
    private LabelHandling errorLabelHandling;
    @FXML
    private ComboBox<FlashcardDifficulty> flashcardDifficultyComboBox;
    @FXML
    private Button backButton;
    @FXML
    private Button showAnswerButton;
    @FXML
    private ContextMenu optionsContextMenu;
    @FXML
    private Button optionsButton;

    private FlashcardQueue selectedFlashcardQueue;
    private FlashcardQueue reviewedFlashcardQueue;
    @FXML
    private Label noFlashcardsLabel;
    @FXML
    private Label deckIsFinishedLabel;
    @FXML
    private Label currentDeckLabel;
    @FXML
    private Label flashcardQuestionLabel;
    @FXML
    private Label flashcardAnswerLabel;
    @FXML
    private Button nextButton;

    public void initialize(){
        Platform.runLater(() -> {
            controller = (FlashcardController) backButton.getScene().getWindow().getUserData();

            ArrayList<Label> flashcardLabels = new ArrayList<>(Arrays.asList(flashcardQuestionLabel, flashcardAnswerLabel));
            flashcardLabelHandling = new LabelHandling(flashcardLabels);
            ArrayList<Label> errorLabels = new ArrayList<>(Arrays.asList(noFlashcardsLabel, deckIsFinishedLabel));
            errorLabelHandling = new LabelHandling(errorLabels);

            stage = (Stage) backButton.getScene().getWindow();
            String deckName = stage.getTitle().split(":")[1].trim();
            currentDeckLabel.setText("Reviewing " + deckName);

            FlashcardDeckList flashcardDeckList = controller.getDeckList();
            flashcardDifficultyComboBox.setItems(FXCollections.observableArrayList(Flashcard.FlashcardDifficulty.values()));

            selectedFlashcardQueue = flashcardDeckList.getFlashcardQueueByName(deckName);
            if (selectedFlashcardQueue == null){
                selectedFlashcardQueue = new FlashcardQueue();
            }

            reviewedFlashcardQueue = new FlashcardQueue(deckName);

            if (!selectedFlashcardQueue.isEmpty()){
                Flashcard selectedFlashcard = selectedFlashcardQueue.peek();
                flashcardQuestionLabel.setText(selectedFlashcard.getQuestion());
                flashcardLabelHandling.showLabel(flashcardQuestionLabel);
            }else{
                errorLabelHandling.showLabel(noFlashcardsLabel);
            }

            stage.setOnCloseRequest((WindowEvent event) -> {
                if (!showAnswerButton.isVisible() && !selectedFlashcardQueue.isEmpty()){
                    FlashcardDifficulty difficulty = flashcardDifficultyComboBox.getValue();
                    Flashcard reviewedFlashcard = selectedFlashcardQueue.dequeue();
                    reviewedFlashcard.setDifficulty(difficulty);
                    reviewedFlashcardQueue.enqueue(reviewedFlashcard);
                }

                controller.updateDeckList(reviewedFlashcardQueue);
                controller.saveFlashcards();
            });
        });
    }

    public void showAnswer(){
        if (!selectedFlashcardQueue.isEmpty()){
            showAnswerButton.setVisible(false);
            flashcardDifficultyComboBox.setVisible(true);

            Flashcard flashcard = selectedFlashcardQueue.peek();

            if (!flashcard.getDifficulty().toString().isEmpty()){
                flashcardDifficultyComboBox.setValue(flashcard.getDifficulty());
            }

            flashcardAnswerLabel.setText(flashcard.getAnswer());
            flashcardLabelHandling.showLabel(flashcardAnswerLabel);
        }
    }

    public void nextButton(){
        if (!selectedFlashcardQueue.isEmpty()){
            Flashcard reviewedFlashcard = selectedFlashcardQueue.dequeue();
            if (flashcardDifficultyComboBox.isVisible()){
                FlashcardDifficulty difficulty = flashcardDifficultyComboBox.getValue();
                if (difficulty != null){
                    flashcardDifficultyComboBox.setVisible(false);
                    reviewedFlashcard.setDifficulty(difficulty);
                }
                showAnswerButton.setVisible(true);
            }
            reviewedFlashcardQueue.enqueue(reviewedFlashcard);
            Flashcard nextFlashcard;
            try{
                nextFlashcard = selectedFlashcardQueue.peek();
                flashcardQuestionLabel.setText(nextFlashcard.getQuestion());
                flashcardAnswerLabel.setText(nextFlashcard.getAnswer());
                flashcardLabelHandling.showLabel(flashcardQuestionLabel);
            }catch(IllegalStateException e){
                errorLabelHandling.showLabel(deckIsFinishedLabel);
                flashcardLabelHandling.hideErrorMessages();
                flashcardDifficultyComboBox.setVisible(false);
                showAnswerButton.setVisible(false);
                nextButton.setVisible(false);
                optionsButton.setVisible(false);
            }
        }
    }

    public void optionsButton(MouseEvent event){
        if (event.getButton() == MouseButton.PRIMARY || event.getButton() == MouseButton.SECONDARY){
            optionsContextMenu.show(optionsButton, event.getScreenX(), event.getScreenY());
        }
    }

    public void refreshButton(MouseEvent event){
    if (event.getButton() == MouseButton.PRIMARY || event.getButton() == MouseButton.SECONDARY){
            reviewedFlashcardQueue.getFlashcards().addAll(selectedFlashcardQueue.getFlashcards());
            selectedFlashcardQueue.getFlashcards().clear();
            selectedFlashcardQueue.getFlashcards().addAll(reviewedFlashcardQueue.getFlashcards());
            reviewedFlashcardQueue.getFlashcards().clear();

            if (flashcardDifficultyComboBox.isVisible()){
                flashcardDifficultyComboBox.setVisible(false);
                flashcardAnswerLabel.setVisible(false);
                showAnswerButton.setVisible(true);
            }

            Flashcard nextFlashcard;
            try{
                errorLabelHandling.hideErrorMessages();
                optionsButton.setVisible(true);
                nextButton.setVisible(true);
                showAnswerButton.setVisible(true);
                nextFlashcard = selectedFlashcardQueue.peek();
                flashcardQuestionLabel.setText(nextFlashcard.getQuestion());
                flashcardAnswerLabel.setText(nextFlashcard.getAnswer());
                flashcardLabelHandling.showLabel(flashcardQuestionLabel);
            }catch(IllegalStateException e){
                errorLabelHandling.showLabel(deckIsFinishedLabel);
                flashcardLabelHandling.hideErrorMessages();
                flashcardDifficultyComboBox.setVisible(false);
                showAnswerButton.setVisible(false);
                nextButton.setVisible(false);
                optionsButton.setVisible(false);
            }
        }
    }

    public void loadEditQuestionScreen(){
        ScreenHandling.loadFXMLScreen("renameFlashcardQuestionScreen.fxml", "Rename Flashcard", 359, 234,false, this);
    }

    public void loadEditAnswerScreen(){
        ScreenHandling.loadFXMLScreen("renameFlashcardAnswerScreen.fxml", "Rename Flashcard Answer", 359, 234, false, this);
    }

    public void editQuestion(String newQuestion){
        Flashcard flashcard = selectedFlashcardQueue.peek();
        flashcard.setQuestion(newQuestion);
        if(flashcardQuestionLabel.isVisible()){
            flashcardQuestionLabel.setVisible(false);
            flashcardQuestionLabel.setText(newQuestion);
            flashcardQuestionLabel.setVisible(true);
        }else{
            flashcardQuestionLabel.setText(newQuestion);
        }
    }

    public void sortByDifficultyAscending(){
        if (!selectedFlashcardQueue.isEmpty()) {
            selectedFlashcardQueue.getFlashcards().addAll(reviewedFlashcardQueue.getFlashcards());
            reviewedFlashcardQueue.getFlashcards().clear();
            selectedFlashcardQueue.ascendingHeapSort();

            if (flashcardDifficultyComboBox.isVisible()){
                flashcardDifficultyComboBox.setVisible(false);
                flashcardAnswerLabel.setVisible(false);
                showAnswerButton.setVisible(true);
            }

            Flashcard nextFlashcard;
            try{
                nextFlashcard = selectedFlashcardQueue.peek();
                flashcardQuestionLabel.setText(nextFlashcard.getQuestion());
                flashcardAnswerLabel.setText(nextFlashcard.getAnswer());
                flashcardLabelHandling.showLabel(flashcardQuestionLabel);
            }catch(IllegalStateException e){
                errorLabelHandling.showLabel(deckIsFinishedLabel);
                flashcardLabelHandling.hideErrorMessages();
                flashcardDifficultyComboBox.setVisible(false);
                showAnswerButton.setVisible(false);
                nextButton.setVisible(false);
                optionsButton.setVisible(false);
            }
        }
    }

    public void sortByDifficultyDescending(){
        if (!selectedFlashcardQueue.isEmpty()) {
            selectedFlashcardQueue.getFlashcards().addAll(reviewedFlashcardQueue.getFlashcards());
            reviewedFlashcardQueue.getFlashcards().clear();
            selectedFlashcardQueue.descendingHeapSort();

            if (flashcardDifficultyComboBox.isVisible()){
                flashcardDifficultyComboBox.setVisible(false);
                flashcardAnswerLabel.setVisible(false);
                showAnswerButton.setVisible(true);
            }

            Flashcard nextFlashcard;
            try{
                nextFlashcard = selectedFlashcardQueue.peek();
                flashcardQuestionLabel.setText(nextFlashcard.getQuestion());
                flashcardAnswerLabel.setText(nextFlashcard.getAnswer());
                flashcardLabelHandling.showLabel(flashcardQuestionLabel);
            }catch(IllegalStateException e){
                errorLabelHandling.showLabel(deckIsFinishedLabel);
                flashcardLabelHandling.hideErrorMessages();
                flashcardDifficultyComboBox.setVisible(false);
                showAnswerButton.setVisible(false);
                nextButton.setVisible(false);
                optionsButton.setVisible(false);
            }
        }
    }

    public void editAnswer(String newAnswer){
        Flashcard flashcard = selectedFlashcardQueue.peek();
        flashcard.setAnswer(newAnswer);
        if(flashcardAnswerLabel.isVisible()){
            flashcardAnswerLabel.setVisible(false);
            flashcardAnswerLabel.setText(newAnswer);
            flashcardAnswerLabel.setVisible(true);
        }else{
            flashcardAnswerLabel.setText(newAnswer);
        }
    }

    public boolean questionExists(String question){
        return selectedFlashcardQueue.flashcardExists(question) || reviewedFlashcardQueue.flashcardExists(question);
    }

    public void backButton() {
        if (!selectedFlashcardQueue.isEmpty()) {
            Flashcard reviewedFlashcard = selectedFlashcardQueue.dequeue();
            if (flashcardDifficultyComboBox.isVisible()) {
                FlashcardDifficulty difficulty = flashcardDifficultyComboBox.getValue();
                reviewedFlashcard.setDifficulty(difficulty);
            }
            reviewedFlashcardQueue.enqueue(reviewedFlashcard);
        }
        controller.updateDeckList(reviewedFlashcardQueue);
        controller.saveFlashcards();
        ScreenHandling.loadFXMLScreen(stage, "flashcardScreen.fxml", "Flashcard Screen", 900, 600, true, this);
    }
}
