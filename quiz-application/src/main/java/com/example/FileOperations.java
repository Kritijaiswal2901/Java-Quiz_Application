package com.example;

import java.nio.file.Path;
import java.util.HashSet;
import java.io.IOException;
import java.nio.file.Files;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.nio.file.Paths;

public class FileOperations {
    // method to create file
    public static void createFile(String filepath) {
        if (exists(filepath)) {
            System.out.println("File already exists!");
            return;
        }

        Path path = Paths.get(filepath);
        try {
            Files.createFile(path);
            System.out.println("File created successfully: " + filepath);
        } catch (IOException exception) {
            System.out.println("Error creating file: " + exception.getMessage());
        }
    }

    public static void writeInstructor(QuizInstructor instructor, String filepath) {
        if (!exists(filepath)) {
            System.out.println("Error: File does not exist.");
            return;
        }

        HashSet<Integer> uniqueIds = getAllUniqueIDs(filepath);
        if (uniqueIds == null || uniqueIds.contains(instructor.instructorId)) {
            System.out.println("Instructor ID already exists .");
            return;
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(filepath, true))) {
            if (countEntries(filepath) == 0) {
                writer.println("Instructor ID,Instructor Name");
            }
            writer.println(instructor.toString());
            System.out.println("Instructor data written successfully.");
        } catch (IOException exception) {
            System.out.println("Error writing instructor data: " + exception.getMessage());
        }
    }

    public static void writeParticipant(QuizParticipant participant, String filepath) {
        if (!exists(filepath)) {
            System.out.println("Error: File does not exist.");
            return;
        }

        HashSet<Integer> uniqueIDs = getAllUniqueIDs(filepath);
        if (uniqueIDs == null || uniqueIDs.contains(participant.participantId)) {
            System.out.println("Participant ID already exists .");
            return;
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(filepath, true))) {
            if (countEntries(filepath) == 0) {
                writer.println("Participant ID,Participant Name");
            }
            writer.println(participant.toString());
            System.out.println("Participant data written successfully.");
        } catch (IOException exception) {
            System.out.println("Error writing participant data: " + exception.getMessage());
        }
    }

    public static void writeQuiz(Quiz quiz, String filepath) {
        if (!exists(filepath)) {
            System.out.println("Error: File does not exist.");
            return;
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(filepath, true))) {
            if (countEntries(filepath) == 0) {
                writer.println("Quiz ID,Question,Options,Answer");
            }
            writer.println(quiz.toStoreFormat());
            System.out.println("Quiz data written successfully.");
        } catch (IOException exception) {
            System.out.println("Error writing quiz data: " + exception.getMessage());
        }
    }

    public static HashSet<Integer> getAllUniqueIDs(String filepath) {
        if (!exists(filepath)) {
            System.out.println("Error: File does not exist.");
            return null;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            reader.readLine();
            HashSet<Integer> uniqueIDs = new HashSet<>();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] lineData = line.split(",");
                int id;
                try {
                    id = Integer.parseInt(lineData[0]);
                    uniqueIDs.add(id);
                } catch (NumberFormatException e) {
                    System.out.println("Error parsing ID from file: " + e.getMessage());
                }
            }
            return uniqueIDs;
        } catch (IOException exception) {
            System.out.println("Error reading file: " + exception.getMessage());
            return null;
        }
    }

    public static int countEntries(String filepath) {
        if (!exists(filepath)) {
            System.out.println("Error: File does not exist.");
            return -1;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            int lines = 0;
            while (reader.readLine() != null) {
                lines++;
            }
            return lines;
        } catch (IOException exception) {
            System.out.println("Error reading file: " + exception.getMessage());
            return -1;
        }
    }

    public static boolean exists(String filepath) {
        Path path = Paths.get(filepath);
        boolean fileExists = Files.exists(path);

        if (fileExists) {
            System.out.println("The file exists: " + filepath);
        } else {
            System.out.println("The file does not exist: " + filepath);
        }

        return fileExists;
    }
}
