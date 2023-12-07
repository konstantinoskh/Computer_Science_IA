package com.example.computer_science_ia;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.SQLException;
import java.util.ArrayList;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {

    @JsonProperty("username") //Specify that username is a property for when the user data is serialised
    private final String USERNAME;

    @JsonIgnore
    private final String PASSWORD;

    @JsonProperty("password") //Specify that hashed_password is a property for when the user data is serialised
    private String hashedPassword;

    @JsonProperty("subjects") //Specify that subjects are a property for when the user data is serialised
    private ArrayList<String> subjects;

    @JsonProperty("notes") //Specify that notes are a property for when the user data is serialised
    private NotesList notes;


    @JsonProperty("tasks") //Specify that tasks are a property for when the user data is serialised
    private TaskList tasks;

    @JsonProperty("flashcard_deck_list") //Specify that flashcards are a property for when the user data is serialised
    private FlashcardDeckList flashcardDeckList;


    public User(){
        USERNAME = "";
        PASSWORD = "";
        notes = new NotesList();
        subjects = new ArrayList<>();
        hashedPassword = "";
        tasks = new TaskList();
        flashcardDeckList = new FlashcardDeckList();
    }

    //For when I need to only initialise the username and password
    public User(String username, String password) throws SQLException {
        this.USERNAME = username;
        this.PASSWORD = password;
        this.notes = new NotesList();
        this.subjects = new ArrayList<>();
        hashedPassword = UserDataSource.getHashedPassword(username); //Uses the encryption method from the encryption class to secure passwords
        this.tasks = new TaskList();
        this.flashcardDeckList = new FlashcardDeckList();
    }

    public String getUsername() {
        return USERNAME;
    }

    @JsonIgnore
    public String getPassword() {
        return PASSWORD;
    }

    public TaskList getTasks() {
        return tasks;
    }

    public void setTasks(TaskList tasks) {
        this.tasks = tasks;
    }

    public FlashcardDeckList getFlashcardDeckList() {
        return flashcardDeckList;
    }

    public void setFlashcardDeckList(FlashcardDeckList flashcardDeckList) {
        this.flashcardDeckList = flashcardDeckList;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public NotesList getNotes() {
        return notes;
    }

    public void setNotes(NotesList notes) {
        this.notes = notes;
    }

    public ArrayList<String> getSubjects() {
        return subjects;
    }

    public void setSubjects(ArrayList<String> subjects) {
        this.subjects = subjects;
    }
}
