package com.example.computer_science_ia.Handling;

import com.example.computer_science_ia.User;
import com.example.computer_science_ia.UserSession;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;

public class JSONHandling {

    // Method to serialize a user's data to a json file
    public static void createUserJSONFile(User user) {
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();

        objectMapper.registerModule(module);
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.registerModule(new JavaTimeModule());

        // Serializes a user object
        try {
            File userFile = new File(user.getUsername());
            File dataFile = new File(userFile, "data");

            if (!dataFile.exists()){
                dataFile.mkdirs();
            }

            File outputFile = new File(dataFile,"userdata.json");
            if (!outputFile.exists()) {
                objectMapper.writeValue(outputFile, user);
                System.out.println("Data written to userdata.json");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void overWriteUserJSONFile(User user) {
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();

        objectMapper.registerModule(module);
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.registerModule(new JavaTimeModule());

        // Serializes a user object
        try {
            File userFile = new File(user.getUsername());
            File dataFile = new File(userFile, "data");
            File outputFile = new File(dataFile,"userdata.json");

            objectMapper.writeValue(outputFile, user);
            System.out.println("Data written to userdata.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static User deserializeJSONFile() {
        try {
            String username = UserSession.getLoggedInUsername();
            File userFile = new File(username);
            File dataFile = new File(userFile, "data");
            File outputFile = new File(dataFile,"userdata.json");
            return JSONDeserializer.deserializeFromFile(outputFile.getPath(), User.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}