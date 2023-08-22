package com.example.computer_science_ia.Handling;

import com.example.computer_science_ia.UserSession;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.Desktop;

public class FileHandling {

    // Method to create the user folder if it does not exist
    public static File createUserFolder(String username) {
        File userFolder = new File(username);
        if (!userFolder.exists()) {
            userFolder.mkdir();
        }
        return userFolder;
    }

    public static void createSubjectFolders(ArrayList<String> folders){
        String username = UserSession.getLoggedInUsername();
        File userFile = new File(username);

        for (String folder : folders) {
            File subjectFolder = new File(userFile, folder);
            if (!subjectFolder.exists()) {
                subjectFolder.mkdir();
            }
        }
    }

    public static void openFile(File fileToOpen){
        Desktop desktop = Desktop.getDesktop();
        try{
            desktop.open(fileToOpen);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
