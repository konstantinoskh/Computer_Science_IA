package com.example.computer_science_ia;

import java.util.ArrayList;

public class UserSession {
    private static String loggedInUsername;
    private static ArrayList<String> subjects;
    private static ArrayList<String> currentDeckList;

    // Private constructor to prevent instantiation
    private UserSession() {

    }

    public static void setLoggedInUsername(String username) {
        loggedInUsername = username;
    }

    public static String getLoggedInUsername() {
        return loggedInUsername;
    }

    public static ArrayList<String> getSubjects() {
        return subjects;
    }

    public static void setSubjects(ArrayList<String> subjects) {
        UserSession.subjects = subjects;
    }

    public static void setCurrentDeckList(ArrayList<String> currentDeckList) {
        UserSession.currentDeckList = currentDeckList;
    }

    public static ArrayList<String> getCurrentDeckList(){
        return UserSession.currentDeckList;
    }
}

