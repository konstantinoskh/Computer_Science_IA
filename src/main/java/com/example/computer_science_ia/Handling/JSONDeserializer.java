package com.example.computer_science_ia.Handling;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;

public class JSONDeserializer{
    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.registerModule(new JavaTimeModule()); // Register the java time module
    }


    public static <T> T deserializeFromFile(String filePath, Class<T> valueType) throws IOException {
        File jsonFile = new File(filePath);
        return objectMapper.readValue(jsonFile, valueType);
    }
}

