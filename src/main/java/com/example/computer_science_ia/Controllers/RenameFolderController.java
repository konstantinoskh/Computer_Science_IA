package com.example.computer_science_ia.Controllers;

import com.example.computer_science_ia.Handling.FileHandling;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;

public class RenameFolderController {
    @FXML
    private TextField newFileNameField;
    @FXML
    private Button confirmButton;

    public void renameFile() {
        Platform.runLater(() -> {
            MainMenuController controller = ((MainMenuController) newFileNameField.getScene().getWindow().getUserData());
            File currentFile = controller.returnCurrentFile();
            String newFolderName = newFileNameField.getText();
            File newFileNameFile = FileHandling.createFile(newFolderName, MainMenuController.currentFilePath);
            currentFile.renameTo(newFileNameFile);
            controller.refreshFileListView(newFolderName);

        });
        Stage stage = (Stage) confirmButton.getScene().getWindow();
        stage.close();
    }
}