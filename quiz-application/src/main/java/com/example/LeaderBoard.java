package com.example;

import java.util.Arrays;

public class LeaderBoard {
    private int[] participantIDs;
    private double[] scores;
    private static final int MAX_SIZE = 5;
    private int size;

    public LeaderBoard() {
        this.participantIDs = new int[MAX_SIZE];
        this.scores = new double[MAX_SIZE];
        Arrays.fill(participantIDs, -1);
        Arrays.fill(scores, -1.0);
        this.size = 0;
    }

    public void updateLeaderboard(int participantID, double score) {
        if (size < MAX_SIZE || score > scores[0]) {
            int insertIndex = findInsertIndex(score);
            shiftElementsDown(insertIndex);
            participantIDs[insertIndex] = participantID;
            scores[insertIndex] = score;
            if (size < MAX_SIZE) {
                size++;
            }
        }
    }

    private int findInsertIndex(double score) {
        int index = 0;
        while (index < size && score <= scores[index]) {
            index++;
        }
        return index;
    }
    private void shiftElementsDown(int startIndex) {
        int elementsToMove = size - startIndex;
    
        if (elementsToMove > 0) {
            System.arraycopy(participantIDs, startIndex, participantIDs, startIndex + 1, elementsToMove);
            System.arraycopy(scores, startIndex, scores, startIndex + 1, elementsToMove);
        }
    }
    

    public void displayLeaderboard() {
        System.out.println("Leaderboard:");
    
        for (int i = 0; i < size; i++) {
            System.out.printf("Rank %d: Participant %d with score %.2f%%%n", i + 1, participantIDs[i], scores[i]);
        }
    
        System.out.println();
    }
    
}


