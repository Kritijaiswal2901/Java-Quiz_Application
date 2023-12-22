package com.example;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;

public class Quiz {
    int quizId;
    private ArrayList<QuizQuestion> questions;
    private int[] timers;
    LeaderBoard leaderBoard;
    QuizType difficulty;

    public Quiz(int quizId, QuizType difficulty, Scanner scanner) {
        this.quizId = quizId;
        this.difficulty = difficulty;
        this.questions = new ArrayList<>();
        this.leaderBoard = new LeaderBoard();
        // create the quiz
        this.createQuiz(scanner);
        this.timers = new int[this.questions.size()];
        initializeTimers();
    }

    private void createQuiz(Scanner scanner) {
        System.out.print("Enter the number of questions for the quiz: ");
        int numberOfQuestions = scanner.nextInt();
        scanner.nextLine();
    
        int questionNumber = 1;
        while (questionNumber <= numberOfQuestions) {
            System.out.println("\nEnter details for question " + questionNumber + ": ");
            
            System.out.print("Question: ");
            String question = scanner.nextLine();
    
            System.out.print("Option 1: ");
            String option1 = scanner.nextLine();
    
            System.out.print("Option 2: ");
            String option2 = scanner.nextLine();
    
            System.out.print("Option 3: ");
            String option3 = scanner.nextLine();
    
            System.out.print("Correct answer option (enter 1/2/3): ");
            int correctOption = scanner.nextInt();
            scanner.nextLine();
    
            System.out.print("Timer duration for this question (in seconds): ");
            int timerDuration = scanner.nextInt();
            scanner.nextLine();
    
            QuizQuestion q = new QuizQuestion(question, option1, option2, option3, correctOption, timerDuration);
            this.questions.add(q);
            System.out.println("Question added successfully!\n");
    
            questionNumber++;
        }
        System.out.println("Quiz created successfully!\n");
    }
    

    public double playQuiz(Scanner scanner, int participantId) {
        Collections.shuffle(questions);
        int[] choices = new int[this.questions.size()];
        
        System.out.println("Welcome to the Quiz! Please provide your answers:");
    
        for (int i = 0; i < this.questions.size(); i++) {
            System.out.println("\nQuestion " + (i + 1) + ":\n" + questions.get(i));
            System.out.print("Your answer (enter 1/2/3): ");
            choices[i] = scanner.nextInt();
            scanner.nextLine();
        }
    
        System.out.println("\nQuiz completed. Here are the results:");
        return scoreQuiz(choices, participantId);
    }
    

    public double scoreQuiz(int[] choices, int participantId) {
        System.out.println("\nQuiz Results: ");
        double correct = 0;
        double totalQuestions = (double) choices.length;
    
        for (int i = 0; i < choices.length; i++) {
            QuizQuestion currentQuestion = this.questions.get(i);
            System.out.println("\nQuestion " + (i + 1) + ": " + currentQuestion);
            System.out.println("Your Answer: " + (choices[i] != 0 ? choices[i] : "Not answered"));
    
            if (choices[i] != 0 && currentQuestion.isCorrect(choices[i])) {
                correct += 1.0;
                System.out.println("Correct! Well done!\n");
            } else {
                System.out.println("Incorrect! The correct answer was: " + currentQuestion.getCorrectOption() + "\n");
            }
        }
    
        double score = (correct / totalQuestions) * 100;
        this.leaderBoard.updateLeaderboard(participantId, score);
    
        System.out.println("Your Final Score: " + score + "%");
        return score;
    }
    

    private void initializeTimers() {
        for (int i = 0; i < this.timers.length; i++) {
            this.timers[i] = this.questions.get(i).getTimerDuration();
        }
    }

    public void addNewQuestion(Scanner scanner) {
        System.out.println("\nEnter the details for the new question:");
    
        System.out.print("Question: ");
        String question = scanner.nextLine();
    
        System.out.print("\tOption 1: ");
        String option1 = scanner.nextLine();
    
        System.out.print("\tOption 2: ");
        String option2 = scanner.nextLine();
    
        System.out.print("\tOption 3: ");
        String option3 = scanner.nextLine();
    
        int correctOption;
        do {
            System.out.print("Correct answer option (1/2/3): ");
            correctOption = scanner.nextInt();
            scanner.nextLine();
        } while (correctOption < 1 || correctOption > 3);
    
        System.out.print("Timer duration for this question (in seconds): ");
        int timerDuration = scanner.nextInt();
        scanner.nextLine();
    
        QuizQuestion newQuestion = new QuizQuestion(question, option1, option2, option3, correctOption, timerDuration);
        this.questions.add(newQuestion);
        System.out.println("\nQuestion added successfully!\n");
    }
    

    public void editQuestion(int questionNumber, Scanner scanner) {
        deleteQuestion(questionNumber);
        addNewQuestionAtIndex(questionNumber - 1, scanner);
    }
    public void addNewQuestionAtIndex(int index, Scanner scanner) {
        System.out.println("\nEnter the details for the new question:");
    
        System.out.print("Question: ");
        String question = scanner.nextLine();
    
        System.out.print("\tOption 1: ");
        String option1 = scanner.nextLine();
    
        System.out.print("\tOption 2: ");
        String option2 = scanner.nextLine();
    
        System.out.print("\tOption 3: ");
        String option3 = scanner.nextLine();
    
        int correctOption;
        do {
            System.out.print("Correct answer option (1/2/3): ");
            correctOption = scanner.nextInt();
            scanner.nextLine();
        } while (correctOption < 1 || correctOption > 3);
    
        System.out.print("Timer duration for this question (in seconds): ");
        int timerDuration = scanner.nextInt();
        scanner.nextLine();
    
        QuizQuestion newQuestion = new QuizQuestion(question, option1, option2, option3, correctOption, timerDuration);
        this.questions.add(index, newQuestion);
        System.out.println("\nQuestion added successfully at index " + index + "!\n");
    }
    

    public void deleteQuestion(int questionNumber) {
        this.questions.remove(questionNumber - 1);
    }
    @Override
    public String toString() {
        if (this.questions.isEmpty()) {
            return "Quiz is empty.";
        }
    
        StringBuilder quizBuilder = new StringBuilder("Quiz Questions:\n");
        for (int i = 0; i < this.questions.size(); i++) {
            QuizQuestion question = this.questions.get(i);
            quizBuilder.append(String.format("[%d]\n", i + 1))
                       .append(question)
                       .append("\n----------------\n");
        }
    
        return quizBuilder.toString();
    }

    public String toStoreFormat() {
        if (this.questions.isEmpty()) {
            return "Quiz is empty.";
        }
        StringBuilder quizBuilder = new StringBuilder();

        for (int i = 0; i < this.questions.size(); i++) {
            QuizQuestion question = this.questions.get(i);
            StringBuilder questionBuilder = new StringBuilder();
            questionBuilder.append(this.quizId);
            questionBuilder.append(",");
            questionBuilder.append(question.getQuestionText());
            questionBuilder.append(",");

            questionBuilder.append(String.join("|", question.getAnswerOptions()));
            questionBuilder.append(",");

            questionBuilder.append(question.getCorrectOption());
            questionBuilder.append(",");
            quizBuilder.append(questionBuilder.toString());
            quizBuilder.append("\n");
        }

        return quizBuilder.toString();
    }
}    
