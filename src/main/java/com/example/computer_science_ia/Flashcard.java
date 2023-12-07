package com.example.computer_science_ia;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Comparator;
import java.util.Locale;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Flashcard {

    @JsonProperty("question")
    private String question;

    @JsonProperty("answer")
    private String answer;

    @JsonProperty("difficulty")
    private FlashcardDifficulty difficulty;

    public Flashcard(String question, String answer, FlashcardDifficulty difficulty) {
        this.question = question;
        this.answer = answer;
        this.difficulty = difficulty;
    }

    public Flashcard(){
        this.question = "";
        this.answer = "";
        this.difficulty = FlashcardDifficulty.EASY;
    }

    public enum FlashcardDifficulty {
        EASY,
        MEDIUM,
        HARD
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public FlashcardDifficulty getDifficulty() {
        return difficulty;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setDifficulty(FlashcardDifficulty difficulty) {
        this.difficulty = difficulty;
    }
}

class FlashcardDifficultyComparator implements Comparator<Flashcard> {

    @Override
    public int compare(Flashcard card1, Flashcard card2) {
        String difficulty1 = card1.getDifficulty().toString().toLowerCase();
        String difficulty2 = card2.getDifficulty().toString().toLowerCase();

        // Compare difficulty levels in descending order
        if (difficulty1.equals("hard") && difficulty2.equals("medium")) {
            return -1;
        } else if (difficulty1.equals("medium") && difficulty2.equals("easy")) {
            return -1;
        } else if (difficulty1.equals("hard") && difficulty2.equals("easy")) {
            return -1;
        } else if (difficulty1.equals(difficulty2)) {
            return 0;
        } else {
            return 1;
        }
    }
}
