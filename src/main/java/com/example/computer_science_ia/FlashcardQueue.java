package com.example.computer_science_ia;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.LinkedList;

public class FlashcardQueue {

    @JsonProperty("flashcard_queue")
    private LinkedList<Flashcard> flashcards;

    public FlashcardQueue() {
        flashcards = new LinkedList<>();
    }

    public void enqueue(Flashcard flashcard) {
        flashcards.addLast(flashcard);
    }

    public Flashcard dequeue() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }
        return flashcards.removeFirst();
    }

    public Flashcard peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }
        return flashcards.getFirst();
    }

    @JsonIgnore
    public boolean isEmpty() {
        return flashcards.isEmpty();
    }

    public int size() {
        return flashcards.size();
    }

    @Override
    public String toString() {
        return flashcards.toString();
    }
}

