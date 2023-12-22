package com.example;
import java.util.Arrays;

public class QuizQuestion {
    private String questionText;
    private String[] answerOptions;
    private int correctOption;
    private int timerDuration;

    public QuizQuestion(String questionText, String option1, String option2, String option3, int correctOption,
                        int timerDuration) {
        this.questionText = questionText;
        this.answerOptions = new String[]{option1, option2, option3};
        this.correctOption = correctOption;
        this.timerDuration = timerDuration;
    }

    public String getQuestionText() {
        return questionText;
    }
    public boolean isCorrect(int choice) {
       return choice == this.correctOption;
     }

    public String[] getAnswerOptions() {
        return Arrays.copyOf(answerOptions, answerOptions.length);
    }

    public int getCorrectOption() {
        return correctOption;
    }

    public int getTimerDuration() {
        return timerDuration;
    }

    @Override
    public String toString() {
        StringBuilder questionDetails = new StringBuilder();
        questionDetails.append(questionText).append("\n");
        for (int i = 0; i < answerOptions.length; i++) {
            questionDetails.append("\tOption ").append(i + 1).append(": ").append(answerOptions[i]).append("\n");
        }
        questionDetails.append("Timer Duration: ").append(timerDuration).append(" seconds\n");
        return questionDetails.toString();
    }
}



