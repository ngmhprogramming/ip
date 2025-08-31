package bernard.exceptions;

/**
 * Custom Exception class for the Bernard Personal Assistant
 */
public class BernardException extends Exception {
    /**
     * Constructs a BernardException
     *
     * @param message Error message
     */
    public BernardException(String message) {
        super(message);
    }
}
