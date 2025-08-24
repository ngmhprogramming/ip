package bernard.tasks;

import bernard.exceptions.BernardException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EventTest {

    @Test
    void constructor_ISOFormat_correctToString() throws BernardException {
        Event e = new Event("Study session", "2019-12-02T18:00", "2019-12-02T20:00");
        assertEquals("[E][ ] Study session (from: Dec 2 2019, 6:00pm to: Dec 2 2019, 8:00pm)", e.toString());
    }

    @Test
    void constructor_CustomFormat_correctToString() throws BernardException {
        Event e = new Event("Study session", "2019-12-02 1800", "2019-12-02 2000");
        assertEquals("[E][ ] Study session (from: Dec 2 2019, 6:00pm to: Dec 2 2019, 8:00pm)", e.toString());
    }

    @Test
    void constructor_InvalidFormat_exceptionThrown() {
        Exception e = assertThrows(BernardException.class, () ->
                new Event("Test event", "12/02/2019 6pm", "12/02/2019 8pm"));
        assertTrue(e.getMessage().contains("Invalid datetime format"));
    }

    @Test
    void updateDoneStatus_serialise_reflectsStatus() throws BernardException {
        Event e = new Event("Study session", "2019-12-02T18:00", "2019-12-02T20:00");
        assertEquals("E| |Study session|2019-12-02T18:00|2019-12-02T20:00", e.serialise()); // initially not done
        e.updateDoneStatus(true);
        assertEquals("E|X|Study session|2019-12-02T18:00|2019-12-02T20:00", e.serialise());
    }
}
