package com.example.computer_science_ia.Controllers;

import com.example.computer_science_ia.Handling.FileHandling;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;

public class AddFolderController {
    @FXML
    private TextField folderNameField;
    @FXML
    private Button confirmButton;

    public void createFolder() {
        Platform.runLater(() -> {
            MainMenuController controller = ((MainMenuController) folderNameField.getScene().getWindow().getUserData());
            String folderName = folderNameField.getText();
            controller.addFile(folderName);

            File newFolder = FileHandling.createFile(folderName, MainMenuController.currentFilePath);
            newFolder.mkdir();
        });
        Stage stage = (Stage) confirmButton.getScene().getWindow();
        stage.close();
    }
}
