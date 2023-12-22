package com.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class QuizInstructor {
    int instructorId;
    private String instructorName;
    private HashMap<Integer, Quiz> quizzes;

    public QuizInstructor(int instructorId, String instructorName) {
        this.instructorId = instructorId;
        this.instructorName = instructorName;
        quizzes = new HashMap<>();
    }

    public void addQuiz(int quizId, Quiz quiz) {
        this.quizzes.put(quizId, quiz);
    }

    public void manageQuiz(int quizId, Scanner scanner) {
        if (this.quizzes.containsKey(quizId)) {
            Quiz quiz = quizzes.get(quizId);
            System.out.println("Options for managing the quiz:\n" +
                    "[1] Add a new question.\n" +
                    "[2] Edit an existing question.\n" +
                    "[3] Delete an existing question.\n" +
                    "[0] Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 0) {
                return;
            } else if (choice == 1) {
                quiz.addNewQuestion(scanner);
            } else if (choice == 2) {
                System.out.println("Current quiz:");
                System.out.println(quiz);
                System.out.print("\nEnter the question number to edit: ");
                int editQuestionNumber = scanner.nextInt();
                scanner.nextLine();
                quiz.editQuestion(editQuestionNumber, scanner);
            } else if (choice == 3) {
                System.out.println("\nCurrent quiz:");
                System.out.println(quiz);
                System.out.print("\nEnter the question number to delete: ");
                int deleteQuestionNumber = scanner.nextInt();
                scanner.nextLine();
                quiz.deleteQuestion(deleteQuestionNumber);
                System.out.println("Question deleted successfully!\n");
            } else {
                System.out.println("Invalid choice.");
            }
        } else {
            System.out.println("Instructor does not have this quiz.");
        }
    }

    public void getQuizzesByDifficulty(int difficulty) {
        if (difficulty < 1 || difficulty > 3) {
            System.out.println("Please enter a valid difficulty.\n");
            return;
        }

        QuizType quizDifficulty;
        if (difficulty == 1) {
            quizDifficulty = QuizType.EASY;
        } else if (difficulty == 2) {
            quizDifficulty = QuizType.MEDIUM;
        } else {
            quizDifficulty = QuizType.HARD;
        }

        List<Quiz> filteredQuizzes = new ArrayList<>();
        for (Quiz quiz : quizzes.values()) {
            if (quiz.difficulty == quizDifficulty) {
                filteredQuizzes.add(quiz);
            }
        }

        if (!filteredQuizzes.isEmpty()) {
            System.out.println("Quizzes of difficulty " + quizDifficulty + ":");
            for (Quiz quiz : filteredQuizzes) {
                System.out.println("-- Quiz ID: " + quiz.quizId);
            }
            System.out.println();
        } else {
            System.out.println("No quizzes of difficulty " + quizDifficulty + " found!\n");
        }
    }

    @Override
    public String toString() {
        return this.instructorId + "," + this.instructorName;
    }
}
