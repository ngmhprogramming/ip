package bernard.tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import bernard.exceptions.BernardException;

public class TaskTest {

    // Minimal concrete subclass for testing
    static class TestTask extends Task {
        public TestTask(String description) throws BernardException {
            super(description);
        }

        @Override
        public String serialise() {
            return super.serialise();
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }

    @Test
    void constructor_emptyDescription_throwsException() {
        BernardException thrown = assertThrows(BernardException.class, () -> new TestTask(""));
        assertEquals("Empty description!", thrown.getMessage());
    }

    @Test
    void setDoneStatus_updatesString() throws BernardException {
        Task task = new TestTask("Test task");

        assertEquals("[ ] Test task", task.toString());

        task.setDoneStatus(true);
        assertEquals("[X] Test task", task.toString());

        task.setDoneStatus(false);
        assertEquals("[ ] Test task", task.toString());
    }

    @Test
    void serialise_returnsCorrectString() throws BernardException {
        Task task = new TestTask("Serialize me");

        assertEquals(" |Serialize me", task.serialise()); // initial not done

        task.setDoneStatus(true);
        assertEquals("X|Serialize me", task.serialise());
    }

    @Test
    void matchesKeyword_returnsCorrectly() throws BernardException {
        Task task = new TestTask("Hello World!");
        assertTrue(task.matchesKeyword("Hello"));
        assertTrue(task.matchesKeyword("hello"));
        assertTrue(task.matchesKeyword("hel"));
        assertTrue(!task.matchesKeyword("help"));
    }
}
