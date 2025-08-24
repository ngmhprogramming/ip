package bernard.tasks;

import bernard.exceptions.BernardException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

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
        BernardException thrown = assertThrows(
                BernardException.class,
                () -> new TestTask("")
        );
        assertEquals("Empty description!", thrown.getMessage());
    }

    @Test
    void updateDoneStatus_UpdatesString() throws BernardException {
        Task task = new TestTask("Test task");

        assertEquals("[ ] Test task", task.toString());

        task.updateDoneStatus(true);
        assertEquals("[X] Test task", task.toString());

        task.updateDoneStatus(false);
        assertEquals("[ ] Test task", task.toString());
    }

    @Test
    void serialise_returnsCorrectString() throws BernardException {
        Task task = new TestTask("Serialize me");

        assertEquals(" |Serialize me", task.serialise()); // initial not done

        task.updateDoneStatus(true);
        assertEquals("X|Serialize me", task.serialise());
    }
}
