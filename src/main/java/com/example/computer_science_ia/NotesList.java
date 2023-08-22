package com.example.computer_science_ia;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

// Define the NotesManager class to manage the linked list of notes
public class NotesList {
    @JsonProperty("head")
    private Note head; // The head of the linked list

    // Constructor to initialize the notes manager
    public NotesList() {
        head = null;
    }

    public Note getHead() {
        return head;
    }

    public Note getNoteByTitle(String newValue) {
        if (head == null) {
            return null;
        }

        if (head.title.equals(newValue)) {
            return head;
        }

        Note current = head;
        while (current.next!= null) {
            if (current.next.title.equals(newValue)) {
                return current.next;
            }
            current = current.next;
        }
        return null;
    }

    // Method to add a new note
    public void addNote(String subject, String title, String content) {
        Note newNote = new Note(subject, title, content);
        if (head == null) {
            head = newNote;
        } else {
            Note current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNote;
        }
    }

    // Method to delete a note based on its title
    public void deleteNoteByTitle(String title) {
        if (head == null) {
            return; // No notes to delete
        }

        if (head.title.equals(title)) {
            head = head.next;
            return;
        }

        Note current = head;
        while (current.next != null) {
            if (current.next.title.equals(title)) {
                current.next = current.next.next;
                return;
            }
            current = current.next;
        }
    }

    @JsonIgnore
    public boolean isEmpty() {
        return head == null;
    }
}
