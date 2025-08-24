package bernard.tasks;

import bernard.exceptions.BernardException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TodoTest {

    @Test
    void constructor_validDescription_createsTodo() throws BernardException {
        Todo todo = new Todo("Read book");
        assertEquals("[T][ ] Read book", todo.toString());
    }

    @Test
    void constructor_emptyDescription_throwsException() {
        BernardException thrown = assertThrows(
                BernardException.class,
                () -> new Todo("")
        );
        assertEquals("Empty description!", thrown.getMessage());
    }

    @Test
    void serialise_returnsCorrectFormat() throws BernardException {
        Todo todo = new Todo("Read book");
        assertEquals("T| |Read book", todo.serialise());
    }

    @Test
    void markTask_updatesToDone() throws BernardException {
        Todo todo = new Todo("Read book");
        todo.updateDoneStatus(true);
        assertEquals("[T][X] Read book", todo.toString());
    }

    @Test
    void unmarkTask_updatesToUndone() throws BernardException {
        Todo todo = new Todo("Read book");
        todo.updateDoneStatus(true);
        todo.updateDoneStatus(false);
        assertEquals("[T][ ] Read book", todo.toString());
    }
}