package bernard.tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import bernard.exceptions.BernardException;

public class TodoTest {

    @Test
    void constructor_validDescription_createsTodo() throws BernardException {
        Todo todo = new Todo("Read book");
        assertEquals("[T][ ] Read book", todo.toString());
    }

    @Test
    void serialise_returnsCorrectFormat() throws BernardException {
        Todo todo = new Todo("Read book");
        assertEquals("T| |Read book", todo.serialise());
    }

    @Test
    void markTask_updatesToDone() throws BernardException {
        Todo todo = new Todo("Read book");
        todo.setDoneStatus(true);
        assertEquals("[T][X] Read book", todo.toString());
    }

    @Test
    void unmarkTask_updatesToUndone() throws BernardException {
        Todo todo = new Todo("Read book");
        todo.setDoneStatus(true);
        todo.setDoneStatus(false);
        assertEquals("[T][ ] Read book", todo.toString());
    }
}
