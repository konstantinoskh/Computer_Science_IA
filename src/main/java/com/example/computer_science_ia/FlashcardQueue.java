package com.example.computer_science_ia;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.LinkedList;
import java.util.PriorityQueue;

public class FlashcardQueue {

    @JsonProperty("flashcard_queue")
    private LinkedList<Flashcard> flashcards;
    private String flashcardQueueName;

    public FlashcardQueue() {
        flashcards = new LinkedList<>();
        flashcardQueueName = "";
    }

    public FlashcardQueue(String flashcardQueueName){
        this.flashcardQueueName = flashcardQueueName;
        flashcards = new LinkedList<>();
    }

    public FlashcardQueue(String flashcardQueueName, LinkedList<Flashcard> existingFlashcards) {
        this.flashcards = existingFlashcards;
        this.flashcardQueueName = flashcardQueueName;
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

    //Checks if a flashcard question exists more than once in a given deck
    public boolean flashcardExists(String question){
        for (Flashcard flashcard : flashcards){
            if (flashcard.getQuestion().equals(question)){
                return true;
            }
        }
        return false;
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

    public String getFlashcardQueueName(){
        return flashcardQueueName;
    }

    public LinkedList<Flashcard> getFlashcards(){
        return flashcards;
    }

    public void setFlashcardQueueName(String flashcardQueueName) {
        this.flashcardQueueName = flashcardQueueName;
    }

    public void ascendingHeapSort() {
        // Create a max heap using the custom comparator
        PriorityQueue<Flashcard> maxHeap = new PriorityQueue<>(new FlashcardDifficultyComparator().reversed());

        // Add all elements from the LinkedList to the max heap
        maxHeap.addAll(flashcards);

        // Clear the original LinkedList
        flashcards.clear();

        // Remove elements from the max heap and add them back to the LinkedList
        while (!maxHeap.isEmpty()) {
            flashcards.add(maxHeap.poll());
        }
    }

    public void descendingHeapSort(){

        // Create a max heap using the custom comparator
        PriorityQueue<Flashcard> maxHeap = new PriorityQueue<>(new FlashcardDifficultyComparator());

        // Add all elements from the LinkedList to the max heap
        maxHeap.addAll(flashcards);

        // Clear the original LinkedList
        flashcards.clear();

        // Remove elements from the max heap and add them back to the LinkedList
        while (!maxHeap.isEmpty()) {
            flashcards.add(maxHeap.poll());
        }

    }

}

