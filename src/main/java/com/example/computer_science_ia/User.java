package com.example.computer_science_ia;

import com.fasterxml.jackson.annotation.JsonCreator;
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

    @JsonProperty("flashcards") //Specify that flashcards are a property for when the user data is serialised
    private FlashcardQueue flashcards;

    //For when I need to initialise every user detail to store in the user's JSON file
    @JsonCreator
    public User(@JsonProperty("username") String username, @JsonProperty("password") String password, @JsonProperty("subjects") ArrayList<String> subjects, @JsonProperty("notes") NotesList notes, @JsonProperty("tasks") TaskList tasks, @JsonProperty("flashcards") FlashcardQueue flashcards) throws SQLException {
        this.USERNAME = username;
        this.PASSWORD = password;
        this.subjects = subjects;
        this.notes = notes;
        this.tasks = tasks;
        this.flashcards = flashcards;
        hashedPassword = UserDataSource.getHashedPassword(username); //Uses the encryption method from the encryption class to secure passwords
    }

    //For when I need to only initialise the username and password
    public User(String username, String password) throws SQLException {
        this.USERNAME = username;
        this.PASSWORD = password;
        this.notes = new NotesList();
        this.subjects = new ArrayList<>();
        hashedPassword = UserDataSource.getHashedPassword(username); //Uses the encryption method from the encryption class to secure passwords
        this.tasks = new TaskList();
        this.flashcards = new FlashcardQueue();
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

    public FlashcardQueue getFlashcards() {
        return flashcards;
    }

    public void setFlashcards(FlashcardQueue flashcards) {
        this.flashcards = flashcards;
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
