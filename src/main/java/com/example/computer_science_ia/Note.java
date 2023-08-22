package com.example.computer_science_ia;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

// Define the Note class to represent individual notes
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Note {
    @JsonProperty(value = "subject")
    String subject;
    @JsonProperty(value = "title")
    String title;
    @JsonProperty(value = "content")
    String content;
    @JsonProperty(value = "next")
    Note next;

    // Default constructor
    public Note() {
    }

    // Constructor to initialize a note
    public Note(String subject, String title, String content) {
        this.subject = subject;
        this.title = title;
        this.content = content;
        this.next = null;
    }

    public String getTitle() {
        return title;
    }

    public String getSubject() {
        return subject;
    }

    public String getContent() {
        return content;
    }

    public Note getNext() {
        return next;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }
}