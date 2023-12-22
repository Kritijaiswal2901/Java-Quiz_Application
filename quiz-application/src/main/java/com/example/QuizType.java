package com.example;

public enum QuizType {
    EASY("Easy"),
    MEDIUM("Medium"),
    HARD("Hard");

    private final String difficultyLevel;

    QuizType(String difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    @Override
    public String toString() {
        return this.difficultyLevel;
    }
}
