package com.example.computer_science_ia;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class FlashcardDeckList {
    @JsonProperty("flashcard_decks")
    private ArrayList<FlashcardQueue> flashcardDeckList;

    public FlashcardDeckList(ArrayList<FlashcardQueue> flashcardDeckList){
        this.flashcardDeckList = flashcardDeckList;
    }

    public FlashcardDeckList(){
        flashcardDeckList = new ArrayList<>();
    }

    public void addDeck(FlashcardQueue deck){
        flashcardDeckList.add(deck);
    }

    public boolean deckExists(String deckName){
        for (FlashcardQueue deckQueue : flashcardDeckList){
            if (deckQueue.getFlashcardQueueName().equals(deckName)){
                return true;
            }
        }
        return false;
    }

    public FlashcardQueue getFlashcardQueueByName(String name){
        for (FlashcardQueue flashcardQueue : flashcardDeckList){
            if (flashcardQueue.getFlashcardQueueName().equals(name)){
                return flashcardQueue;
            }
        }
        return null;
    }

    public void updateDeck(FlashcardQueue updatedFlashcardQueue) {
        if (updatedFlashcardQueue != null) {
            String updatedQueueName = updatedFlashcardQueue.getFlashcardQueueName();
            LinkedList<Flashcard> updatedFlashcards = updatedFlashcardQueue.getFlashcards();

            for (FlashcardQueue flashcardQueue : flashcardDeckList) {
                if (flashcardQueue.getFlashcardQueueName().equals(updatedQueueName)) {
                    LinkedList<Flashcard> existingFlashcards = flashcardQueue.getFlashcards();

                    for (Flashcard existingFlashcard : existingFlashcards) {
                        for (Flashcard updatedFlashcard : updatedFlashcards) {
                            if (existingFlashcard.getQuestion().equals(updatedFlashcard.getQuestion())) {
                                existingFlashcard.setAnswer(updatedFlashcard.getAnswer());
                                existingFlashcard.setDifficulty(updatedFlashcard.getDifficulty());
                                // Break once updated flashcard is found
                                break;
                            }
                        }
                    }

                    // Add any new flashcards that were not in the existing queue
                    for (Flashcard updatedFlashcard : updatedFlashcards) {
                        boolean flashcardExists = false;
                        for (Flashcard existingFlashcard : existingFlashcards) {
                            if (existingFlashcard.getQuestion().equals(updatedFlashcard.getQuestion())) {
                                flashcardExists = true;
                                break;
                            }
                        }
                        if (!flashcardExists) {
                            existingFlashcards.add(updatedFlashcard);
                        }
                    }

                    // No need to continue searching
                    break;
                }
            }
        }
    }


    @JsonIgnore
    public ArrayList<String> getFlashcardDeckNames(){
        ArrayList<String> flashcardDeckNames = new ArrayList<>();

        for (FlashcardQueue flashcardQueue : flashcardDeckList){
            String flashcardQueueName = flashcardQueue.getFlashcardQueueName();
            flashcardDeckNames.add(flashcardQueueName);
        }
        return flashcardDeckNames;
    }

    @JsonIgnore
    public boolean isEmpty(){
        return flashcardDeckList.isEmpty();
    }

    public ArrayList<FlashcardQueue> getFlashcardDeckList(){
        return flashcardDeckList;
    }

    public boolean flashcardExists(String deckName, String flashcardQuestion) {
        for (FlashcardQueue queue : flashcardDeckList) {
            if (queue.getFlashcardQueueName().equals(deckName)) {
                if (queueContainsFlashcard(queue, flashcardQuestion)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean queueContainsFlashcard(FlashcardQueue queue, String flashcardQuestion) {
        for (Flashcard flashcard : queue.getFlashcards()) {
            if (flashcard.getQuestion().equals(flashcardQuestion)) {
                return true;
            }
        }
        return false;
    }

    public void addFlashcard(String deckName, Flashcard flashcard) {
        for (FlashcardQueue queue : flashcardDeckList){
            if (queue.getFlashcardQueueName().equals(deckName)){
                queue.enqueue(flashcard);
            }
        }
    }

    public void renameDeck(String oldDeckName, String newDeckName) {
        for (FlashcardQueue queue : flashcardDeckList) {
            if (queue.getFlashcardQueueName().equals(oldDeckName)){
                queue.setFlashcardQueueName(newDeckName);
            }
        }
    }

    public void removeDeck(FlashcardQueue deck) {
        // Use the iterator's remove method
        flashcardDeckList.removeIf(queue -> queue.getFlashcardQueueName().equals(deck.getFlashcardQueueName()));
    }
}
