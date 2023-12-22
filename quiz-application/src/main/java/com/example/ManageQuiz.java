package com.example;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ManageQuiz {
    public static final String INSTRUCTOR_CSV_PATH = "Instructors.csv";
    public static final String PARTICIPANT_CSV_PATH = "Participants.csv";
    public static final String QUIZZES_CSV_PATH = "Quizzes.csv";
    public static Map<Object, Quiz> quizzes = new HashMap<>();
    public static Map<Object, QuizParticipant> participants = new HashMap<>();
    public static Map<Object, QuizInstructor> instructors = new HashMap<>();
    public static HashMap<Integer, LeaderBoard> leaderBoards = new HashMap<>();

    public static void startQuizConsole(Scanner scanner) {
        FileOperations.createFile(INSTRUCTOR_CSV_PATH);
        FileOperations.createFile(PARTICIPANT_CSV_PATH);
        FileOperations.createFile(QUIZZES_CSV_PATH);

        boolean exitFlag = false;
        while (!exitFlag) {
            displayMenu();
            System.out.print("Enter your choice (enter 0 to exit): ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 0:
                    scanner.close();
                    exitFlag = true;
                    break;
                case 1:
                    try {
                        addQuiz(scanner);
                    } catch (Exception exception) {
                        handleException("Error in adding quiz", exception);
                    }
                    break;
                case 2:
                    try {
                        playQuiz(scanner);
                    } catch (Exception exception) {
                        handleException("Error in playing quiz", exception);
                    }
                    break;
                case 3:
                    createParticipant(scanner);
                    break;
                case 4:
                    createInstructor(scanner);
                    break;
                case 5:
                    try {
                        viewParticipantScore(scanner);
                    } catch (Exception exception) {
                        handleException("Error in viewing score", exception);
                    }
                    break;
                case 6:
                    try {
                        manageQuiz(scanner);
                    } catch (Exception exception) {
                        handleException("Error in managing quiz", exception);
                    }
                    break;
                case 7:
                    try {
                        viewLeaderBoard(scanner);
                    } catch (Exception exception) {
                        handleException("Error in viewing leaderboard", exception);
                    }
                    break;
                case 8:
                    try {
                        viewQuizzesByDifficulty(scanner);
                    } catch (Exception exception) {
                        handleException("Error in viewing by difficulty", exception);
                    }
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.\n");
            }
        }
    }

    private static void handleException(String string, Exception exception) {
    }

    private static void displayMenu() {
        System.out.println("Welcome! Please choose an option:");
        System.out.println("1. Add new quiz");
        System.out.println("2. Play a quiz");
        System.out.println("3. Create new participant");
        System.out.println("4. Create new instructor");
        System.out.println("5. View participant score");
        System.out.println("6. Manage quiz");
        System.out.println("7. View quiz leaderboard");
        System.out.println("8. View quizzes by difficulty");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
    }
    

    public static void addQuiz(Scanner scanner) {
        int instructorId = getInstructorId(scanner);

        if (instructors.containsKey(instructorId)) {
            int newQuizId = getNewQuizId(scanner);
            scanner.nextLine();
            if (!quizzes.containsKey(newQuizId)) {
                QuizType quizDifficulty = getQuizDifficulty(scanner);

                Quiz newQuiz = new Quiz(newQuizId, quizDifficulty, scanner);
                quizzes.put(newQuizId, newQuiz);
                FileOperations.writeQuiz(newQuiz, QUIZZES_CSV_PATH);
                instructors.get(instructorId).addQuiz(newQuizId, newQuiz);
            } else {
                System.out.println("Quiz already exists!\n");
            }
        } else {
            System.out.println("Instructor not found!\n");
        }
    }

    private static int getInstructorId(Scanner scanner) {
        System.out.print("Enter your instructor ID: ");
        return scanner.nextInt();
    }

    private static int getNewQuizId(Scanner scanner) {
        System.out.print("Enter quiz ID: ");
        return scanner.nextInt();
    }

    private static QuizType getQuizDifficulty(Scanner scanner) {
        System.out.println("Choose the quiz difficulty:");
        System.out.println("1. Easy");
        System.out.println("2. Medium");
        System.out.println("3. Hard");
        System.out.print("Enter the corresponding number for difficulty: ");

        int difficulty = scanner.nextInt();
        scanner.nextLine();

        switch (difficulty) {
            case 2:
                return QuizType.MEDIUM;
            case 3:
                return QuizType.HARD;
            default:
                return QuizType.EASY;
        }
    }

    public static void playQuiz(Scanner scanner) {
        int participantId = getParticipantId(scanner);
        scanner.nextLine();
        if (participants.containsKey(participantId)) {
            int playQuizId = getQuizId(scanner);
            scanner.nextLine();

            if (quizzes.containsKey(playQuizId)) {
                Quiz participantQuiz = quizzes.get(playQuizId);
                double score = participantQuiz.playQuiz(scanner, participantId);
                participants.get(participantId).updateScore(playQuizId, score);
                displayQuizCompletionMessage(score);
            } else {
                System.out.println("Quiz not found!\n");
            }
        } else {
            System.out.println("Participant not found!\n");
        }
    }

    private static int getParticipantId(Scanner scanner) {
        System.out.print("Enter participant ID: ");
        return scanner.nextInt();
    }

    private static int getQuizId(Scanner scanner) {
        System.out.print("Enter quiz ID: ");
        return scanner.nextInt();
    }

    private static void displayQuizCompletionMessage(double score) {
        System.out.println("\nQuiz completed successfully. Your score is " + score + "%\n");
    }

    // method to create a new participant
    public static void createParticipant(Scanner scanner) {
        int newParticipantId = getNewParticipantId(scanner);
        scanner.nextLine();
        if (!participants.containsKey(newParticipantId)) {
            String newParticipantName = getParticipantName(scanner);
            QuizParticipant newParticipant = new QuizParticipant(newParticipantId, newParticipantName);
            participants.put(newParticipantId, newParticipant);
            FileOperations.writeParticipant(newParticipant, PARTICIPANT_CSV_PATH);
            displayParticipantAddedMessage();
        } else {
            displayParticipantExistsMessage();
        }
    }

    private static int getNewParticipantId(Scanner scanner) {
        System.out.print("Enter participant ID: ");
        return scanner.nextInt();
    }

    private static String getParticipantName(Scanner scanner) {
        System.out.print("Enter participant name: ");
        return scanner.nextLine();
    }

    private static void displayParticipantAddedMessage() {
        System.out.println("Participant added successfully!\n");
    }

    private static void displayParticipantExistsMessage() {
        System.out.println("Participant already exists!\n");
    }

    // method to create a new instructor
    public static void createInstructor(Scanner scanner) {
        int newInstructorId = getNewInstructorId(scanner);
        scanner.nextLine();
        if (!instructors.containsKey(newInstructorId)) {
            String newInstructorName = getInstructorName(scanner);
            QuizInstructor newInstructor = new QuizInstructor(newInstructorId, newInstructorName);
            ManageQuiz.instructors.put(newInstructorId, newInstructor);
            FileOperations.writeInstructor(newInstructor, INSTRUCTOR_CSV_PATH);
            displayInstructorAddedMessage();
        } else {
            displayInstructorExistsMessage();
        } 
    }

    private static int getNewInstructorId(Scanner scanner) {
        System.out.print("Enter instructor ID: ");
        return scanner.nextInt();
    }

    private static String getInstructorName(Scanner scanner) {
        System.out.print("Enter instructor name: ");
        return scanner.nextLine();
    }

    private static void displayInstructorAddedMessage() {
        System.out.println("Instructor added successfully.\n");
    }

    private static void displayInstructorExistsMessage() {
        System.out.println("Instructor already exists!\n");
    }

    public static void viewParticipantScore(Scanner scanner) {
        int searchParticipantId = getParticipantId(scanner);

        if (participants.containsKey(searchParticipantId)) {
            int searchQuizId = getQuizId(scanner);

            if (quizzes.containsKey(searchQuizId)) {
                double participantScore = participants.get(searchParticipantId).getScore(searchQuizId);

                if (participantScore != Double.MIN_VALUE) {
                    displayParticipantScore(searchParticipantId, participantScore, searchQuizId);
                }
            } else {
                displayQuizNotFoundMessage();
            }
        } else {
            displayParticipantNotFoundMessage();
        }
    }

    private static void displayParticipantScore(int participantId, double score, int quizId) {
        System.out.println("Participant " + participantId + " scored " + score + " in quiz " + quizId + ".\n");
    }

    private static void displayParticipantNotFoundMessage() {
        System.out.println("Participant not found!\n");
    }

    private static void displayQuizNotFoundMessage() {
        System.out.println("Quiz not found!\n");
    }

    public static void manageQuiz(Scanner scanner) {
        int instructorIdManage = getInstructorId(scanner);

        if (instructors.containsKey(instructorIdManage)) {
            int quizIdManage = getQuizId(scanner);

            if (quizzes.containsKey(quizIdManage)) {
                instructors.get(instructorIdManage).manageQuiz(quizIdManage, scanner);
            } else {
                displayQuizNotFoundMessage();
            }
        } else {
            displayInstructorNotFoundMessage();
        }
    }

    private static void displayInstructorNotFoundMessage() {
        System.out.println("Instructor not found!\n");
    }

    public static void viewLeaderBoard(Scanner scanner) {
        int quizId = getQuizId(scanner);

        if (quizzes.containsKey(quizId)) {
            displayLeaderboardForQuiz(quizId);
        } else {
            displayQuizNotFoundMessage();
        }
    }

    private static void displayLeaderboardForQuiz(int quizId) {
        quizzes.get(quizId).leaderBoard.displayLeaderboard();
    }

    public static void viewQuizzesByDifficulty(Scanner scanner) {
        int instructorId = getInstructorId(scanner);

        if (instructors.containsKey(instructorId)) {
            int difficulty = getDifficulty(scanner);
            System.out.println("\nQuizzes for Difficulty Level " + difficulty + ":");
            instructors.get(instructorId).getQuizzesByDifficulty(difficulty);
        } else {
            displayInstructorNotFoundMessage();
        }
    }

    private static int getDifficulty(Scanner scanner) {
        System.out.print("Enter quiz difficulty (1 for easy, 2 for medium, 3 for hard): ");
        return scanner.nextInt();
    }

}
