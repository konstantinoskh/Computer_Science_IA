package com.example.computer_science_ia;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;

public class ScreenHandling {

    //Method to load a FXML screen in order to encapsulate and make the code more readable
    public static void loadFXMLScreen(String fxmlFilePath, String title, int width, int height, boolean isResizable, Object userData) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ScreenHandling.class.getResource(fxmlFilePath));
            Stage stage = new Stage();
            stage.initStyle(StageStyle.DECORATED);
            stage.setTitle(title);
            stage.setResizable(isResizable);
            stage.setUserData(userData);

            String imagePath = "/Images/HomeworkWizz.jpg"; // Relative path to the image
            URL imageUrl = ScreenHandling.class.getResource(imagePath);

            if (imageUrl != null) {
                Image icon = new Image(imageUrl.toString());
                stage.getIcons().add(icon); // Add the icon to the stage
            } else {
                System.out.println("Image not found");
            }


            Scene scene = new Scene(fxmlLoader.load(), width, height);
            stage.setScene(scene);
            stage.show();
        } catch (IOException | RuntimeException e) {
            e.printStackTrace();
        }
    }

    public static void loadFXMLScreenInSameWindow(Stage stage, String fxmlFilePath, String title, int width, int height, boolean isResizable) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ScreenHandling.class.getResource(fxmlFilePath));

            stage.setTitle(title);
            stage.setResizable(isResizable);

            String imagePath = "/Images/HomeworkWizz.jpg"; // Relative path to the image
            URL imageUrl = ScreenHandling.class.getResource(imagePath);

            if (imageUrl != null) {
                Image icon = new Image(imageUrl.toString());
                stage.getIcons().add(icon); // Add the icon to the stage
            } else {
                System.out.println("Image not found");
            }

            Scene scene = new Scene(fxmlLoader.load(), width, height);
            stage.setScene(scene);
        } catch (IOException | RuntimeException e) {
            e.printStackTrace();
        }
    }


}
