package bernard.tasks;

import bernard.exceptions.BernardException;
import org.junit.jupiter.api.Test;

import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class DeadlineTest {

    @Test
    void constructor_ISOFormat_correctToString() throws BernardException {
        Deadline d = new Deadline("Finish homework", "2019-12-02T18:00");
        assertEquals("[D][ ] Finish homework (by: Dec 2 2019, 6:00pm)", d.toString());
    }

    @Test
    void constructor_CustomFormat_correctToString() throws BernardException {
        Deadline d = new Deadline("Finish homework", "2019-12-02 1800");
        assertEquals("[D][ ] Finish homework (by: Dec 2 2019, 6:00pm)", d.toString());
    }

    @Test
    void constructor_InvalidFormat_exceptionThrown() {
        Exception e = assertThrows(BernardException.class, () ->
                new Deadline("Test", "12/02/2019 6pm"));
        assertTrue(e.getMessage().contains("Invalid datetime format"));
    }

    @Test
    void updateDoneStatus_serialise_reflectsStatus() throws BernardException {
        Deadline d = new Deadline("Finish homework", "2019-12-02T18:00");
        assertEquals("D| |Finish homework|2019-12-02T18:00", d.serialise()); // initially not done
        d.updateDoneStatus(true);
        assertEquals("D|X|Finish homework|2019-12-02T18:00", d.serialise());
    }
}
