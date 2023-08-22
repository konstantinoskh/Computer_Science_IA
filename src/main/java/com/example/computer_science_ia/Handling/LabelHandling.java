package com.example.computer_science_ia.Handling;

import javafx.scene.control.Label;

import java.util.ArrayList;

public class LabelHandling {
    private final ArrayList<Label> LABELS;

    // Stores all error message labels for use
    public LabelHandling(ArrayList<Label> errorMessages) {
        this.LABELS = errorMessages;
    }

    // Method to only display wanted error message
    public void showErrorMessage (Label errorMessage) {
        for (Label label : LABELS) {
            label.setVisible(label.getText().equals(errorMessage.getText()));
        }
    }

    // Method to hide all error messages
    public void hideErrorMessages() {
        for (Label label : LABELS) {
            label.setVisible(false);
        }
    }

    // Method to set the text of a label
    private void setText(Label label, String text){
        for (Label existingLabel : LABELS) {
            if (existingLabel.getText().equals(label.getText())) {
                existingLabel.setText(text);
            }
        }
    }

    public static void setWelcomeMessage(Label welcomeLabel, String username) {
        welcomeLabel.setText("Welcome " + username);
    }

    public void setSubjectLabels(ArrayList<Label> subjectLabels, ArrayList<String> subjectName) {
        for (int i = 0; i < subjectLabels.size(); i++) {
            setText(subjectLabels.get(i), subjectName.get(i));
        }
    }
}

