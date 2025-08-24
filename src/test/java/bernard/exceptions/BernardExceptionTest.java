package bernard.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class BernardExceptionTest {
    // A helper method that always throws BernardException
    private void methodThatThrows() throws BernardException {
        throw new BernardException("Something went wrong");
    }

    @Test
    void methodThatThrows_exceptionThrown() {
        BernardException e = assertThrows(
                BernardException.class,
                () -> methodThatThrows()
        );

        assertEquals("Something went wrong", e.getMessage());
    }

    @Test
    void bernardException_messageIsStored() {
        String errorMsg = "This is a test error";
        BernardException e = new BernardException(errorMsg);

        // check that the message is stored correctly
        assertEquals(errorMsg, e.getMessage());
    }

    @Test
    void bernardException_emptyMessage() {
        BernardException e = new BernardException("");
        assertEquals("", e.getMessage());
    }
}
