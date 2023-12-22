package com.example;
import java.util.Scanner;
public class Main {
    public static void main(String[] args)
            throws InstructorNotFoundException, QuizNotFoundException, ParticipantNotFoundException {
        Scanner sc = new Scanner(System.in);
        ManageQuiz.startQuizConsole(sc);
    }
}