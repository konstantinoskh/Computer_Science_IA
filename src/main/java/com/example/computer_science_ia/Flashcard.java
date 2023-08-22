package com.example.computer_science_ia;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Flashcard {

    @JsonProperty("question")
    private String question;

    @JsonProperty("answer")
    private String answer;

    @JsonProperty("category")
    private String category;

    @JsonProperty("difficulty")
    private FlashcardDifficulty difficulty;

    public Flashcard(String question, String answer, String category, FlashcardDifficulty difficulty) {
        this.question = question;
        this.answer = answer;
        this.category = category;
        this.difficulty = difficulty;
    }

    public Flashcard(){
        this.question = "";
        this.answer = "";
        this.category = "";
        this.difficulty = FlashcardDifficulty.EASY;
    }

    public enum FlashcardDifficulty {
        EASY,
        MEDIUM,
        HARD
    }
}
