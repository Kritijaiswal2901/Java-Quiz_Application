import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Scanner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.example.FileOperations;
import com.example.ManageQuiz;



public class ApplicationTest {

      @BeforeEach
    public void reset() {
        ManageQuiz.quizzes = new HashMap<>();
        ManageQuiz.instructors = new HashMap<>();
        ManageQuiz.participants = new HashMap<>();
    }

    // method to verify player creation
    @Test
    public void testParticipantAdd() {
        String input = "3\n100\nTestPlayer1\n0\n"; // creating new player with id = 200
        ByteArrayInputStream testInputs = new ByteArrayInputStream(input.getBytes());
        Scanner sc = new Scanner(testInputs);
        int players_before = ManageQuiz.participants.size();
        int players_before_in_csv = FileOperations.countEntries(ManageQuiz.PARTICIPANT_CSV_PATH);
        try {
            ManageQuiz.startQuizConsole(sc);
        } catch (Exception exception) {
            fail("Unexpected Exception: " + exception.getMessage());
        }
        int players_after = ManageQuiz.participants.size();
        int players_after_in_csv = FileOperations.countEntries(ManageQuiz.INSTRUCTOR_CSV_PATH);
        assertEquals(players_before + 1, players_after);
        if (!FileOperations.getAllUniqueIDs(ManageQuiz.PARTICIPANT_CSV_PATH).contains(200)) {
            assertEquals(players_before_in_csv + 1, players_after_in_csv);
        }
    }

    // method to verify instructor creation
    @Test
    public void testInstructorAdd() {
        String input = "4\n102\nTestInstructor4\n0\n"; 
        ByteArrayInputStream testInputs = new ByteArrayInputStream(input.getBytes());
        Scanner sc = new Scanner(testInputs);
        int instructors_before = ManageQuiz.instructors.size();
        int instructors_before_in_csv = FileOperations.countEntries(ManageQuiz.INSTRUCTOR_CSV_PATH);
        try {
            ManageQuiz.startQuizConsole(sc);
        } catch (Exception exception) {
            fail("Unexpected exception: " + exception.getMessage());
        }
        int instructors_after = ManageQuiz.instructors.size();
        int instructors_after_in_csv = FileOperations.countEntries(ManageQuiz.INSTRUCTOR_CSV_PATH);
        assertEquals(instructors_before + 1, instructors_after);
        if (!FileOperations.getAllUniqueIDs(ManageQuiz.INSTRUCTOR_CSV_PATH).contains(103)) {
            assertEquals(instructors_before_in_csv + 1, instructors_after_in_csv);
        }
    }

    // method to test multiple quiz addition to console
    @Test
    public void testMultipleQuizAdd() {
        // creating three quizzes with ids 1, 2 and 3 after creating instructor
        String[] createQuizzes = {
                "4\n102\nTestInstructor3\n1\n102\n102\n1\n2\nDemo Question 1\nA\nB\nC\n1\n10\nDemo Question 2\nA\nB\nC\n2\n10\n0\n"
            };

        int quizzes_before = ManageQuiz.quizzes.size();
        for (String createQuiz : createQuizzes) {
            ByteArrayInputStream testCreateQuiz = new ByteArrayInputStream(createQuiz.getBytes());
            Scanner sc = new Scanner(testCreateQuiz);
            try {
                ManageQuiz.startQuizConsole(sc);
            } catch (Exception exception) {
                fail("Unexpected exception: " + exception.getMessage());
            }
            assertEquals(quizzes_before + 1, ManageQuiz.quizzes.size());
            quizzes_before++;
        }
    }
}
