package com.example;

import java.util.HashMap;

public class QuizParticipant {
    int participantId;
    private String participantName;
    private HashMap<Integer, Double>participantQuiz;

    public QuizParticipant(int participantId, String participantName) {
        this.participantId = participantId;
        this.participantName = participantName;
        this.participantQuiz = new HashMap<>();
    }

    public void updateScore(int quizId, double score) {
        this.participantQuiz.put(quizId, score);
    }

    public double getScore(int quizId) {
        if (this.participantQuiz.containsKey(quizId)) {
            return this.participantQuiz.get(quizId);
        } else {
            System.out.println("Participant did not take this quiz.\n");
            return Double.MIN_VALUE;
        }
    }

    @Override
    public String toString() {
        return this.participantId + "," + this.participantName;
    }
}

