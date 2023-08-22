package com.example.computer_science_ia;

import javafx.application.Application;
import javafx.stage.Stage;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HomeworkWizz extends Application {

    @Override
    public void start(Stage stage) {
        if (!checkDrivers()) { //Check if SQLite Drivers are installed and returns the method if they're not.
            return;
        }
        ScreenHandling.loadFXMLScreen("login.fxml", "Log In", 520, 400, false, this); //Load first screen
    }

    public static void main(String[] args) {
        launch();
    }

    //Checks if SQLite Drivers are installed and returns true if they are installed, false if not.
    private static boolean checkDrivers() {
        try {
            Class.forName("org.sqlite.JDBC");
            DriverManager.registerDriver(new org.sqlite.JDBC());
            return true;
        } catch (ClassNotFoundException | SQLException classNotFoundException) {
            Logger.getAnonymousLogger().log(Level.SEVERE, LocalDateTime.now() + ": Could not start SQLite Drivers");
            return false;
        }
    }
}