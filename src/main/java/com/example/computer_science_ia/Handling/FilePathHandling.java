package com.example.computer_science_ia.Handling;


import com.example.computer_science_ia.UserSession;
import java.util.Arrays;
import java.util.LinkedList;

public class FilePathHandling {
    private final LinkedList<String> PATH_SEGMENTS;

    public FilePathHandling() {
        PATH_SEGMENTS = new LinkedList<>();
        PATH_SEGMENTS.add(UserSession.getLoggedInUsername()); // Initial root directory
    }

    public void changeDirectory(String newDir) {
        if (newDir.equals("..")) {
            if (PATH_SEGMENTS.size() > 2) {
                PATH_SEGMENTS.removeLast();
            }
        } else {
            PATH_SEGMENTS.addLast(newDir);
        }
    }

    public void setDirectory(String newDir) {
        PATH_SEGMENTS.clear();
        LinkedList<String> newPathSegments = new LinkedList<>(Arrays.asList(newDir.split("\\\\")));

        for (String pathSegment : newPathSegments) {
            PATH_SEGMENTS.addLast(pathSegment);
        }
    }

    public void setDirectoryToRoot(){
        PATH_SEGMENTS.clear();
        PATH_SEGMENTS.add(UserSession.getLoggedInUsername());
    }

    public String getCurrentPath() {
        StringBuilder pathBuilder = new StringBuilder();
        for (String segment : PATH_SEGMENTS) {
            if (!segment.isEmpty()) {
                pathBuilder.append(segment);
                pathBuilder.append("\\");
            }
        }
        return pathBuilder.toString();
    }
}

