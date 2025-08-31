package bernard.tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import bernard.exceptions.BernardException;

public class DeadlineTest {

    @Test
    void constructor_isoFormat_correctToString() throws BernardException {
        Deadline d = new Deadline("Finish homework", "2019-12-02T18:00");
        assertEquals("[D][ ] Finish homework (by: Dec 2 2019, 6:00pm)", d.toString());
    }

    @Test
    void constructor_customFormat_correctToString() throws BernardException {
        Deadline d = new Deadline("Finish homework", "2019-12-02 1800");
        assertEquals("[D][ ] Finish homework (by: Dec 2 2019, 6:00pm)", d.toString());
    }

    @Test
    void constructor_invalidFormat_exceptionThrown() {
        Exception e = assertThrows(BernardException.class, () ->
                new Deadline("Test", "12/02/2019 6pm"));
        assertTrue(e.getMessage().contains("Invalid datetime format"));
    }

    @Test
    void updateDoneStatus_serialise_reflectsStatus() throws BernardException {
        Deadline d = new Deadline("Finish homework", "2019-12-02T18:00");
        assertEquals("D| |Finish homework|2019-12-02T18:00", d.serialise()); // initially not done
        d.setDoneStatus(true);
        assertEquals("D|X|Finish homework|2019-12-02T18:00", d.serialise());
    }
}
