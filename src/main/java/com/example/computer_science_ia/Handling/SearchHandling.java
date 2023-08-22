package com.example.computer_science_ia.Handling;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class SearchHandling {
    public static ArrayList<File> find(File directory, String userInput) {
        File[] files = directory.listFiles();
        ArrayList<File> output = new ArrayList<>();
        if (files != null) {
            for (File file : files) {
                if (fullTextSearch(file.getName(), userInput)) {
                    output.add(file);
                }
                if (file.isDirectory()) {
                    output.addAll(SearchHandling.find(file, userInput));
                }
            }
        }
        return output;
    }

    public static ArrayList<File> listFiles (File file) {
        File[] files = file.listFiles();
        ArrayList<File> output = new ArrayList<>();

        if (file.isDirectory()) {
            if (files != null) {
                output.addAll(Arrays.asList(files));
            }else
                return null;
        }
        return output;
    }

    private static boolean fullTextSearch(String text, String query) {
        // Convert query to lowercase for case-insensitive search
        String lowercaseQuery = query.toLowerCase();

        // Convert word to lowercase for case-insensitive comparison
        String lowercaseWord = text.toLowerCase();

        // Check if the query is present in the word
        return lowercaseWord.contains(lowercaseQuery);
    }
}
