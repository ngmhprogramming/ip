package bernard.tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import bernard.exceptions.BernardException;

/**
 * Deadline task
 */
public class Deadline extends Task {
    /**
     * Formatters to try for parsing datetime arguments
     */
    private static final DateTimeFormatter[] FORMATTERS = new DateTimeFormatter[]{
            DateTimeFormatter.ISO_LOCAL_DATE_TIME,         // e.g. 2019-12-02T18:00
            DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm") // e.g. 2019-12-02 1800
    };

    private LocalDateTime deadline;

    /**
     * Constructs a deadline task
     *
     * @param description Task description
     * @param deadline Task deadline
     * @throws BernardException If deadline is not a valid datetime string
     */
    public Deadline(String description, String deadline) throws BernardException {
        super(description);
        try {
            this.deadline = parseDateTime(deadline);
        } catch (DateTimeParseException e) {
            throw new BernardException("Invalid datetime format! Use yyyy-MM-dd HHmm, e.g., 2019-12-02 1800");
        }
    }

    /**
     * Parses a datetime string
     *
     * @param datetime Datetime string to be parsed
     * @return Datetime object parsed from string
     * @throws BernardException If unable to parse datetime string
     */
    private static LocalDateTime parseDateTime(String datetime) throws BernardException {
        for (DateTimeFormatter fmt : FORMATTERS) {
            try {
                return LocalDateTime.parse(datetime, fmt);
            } catch (DateTimeParseException e) {
                // try next formatter
            }
        }
        throw new BernardException("Invalid datetime format! Use yyyy-MM-dd HHmm, e.g., 2019-12-02 1800");
    }

    /**
     * Serialises a deadline task for file output
     *
     * @return The serialised representation of the deadline task
     */
    @Override
    public String serialise() {
        return "D|" + super.serialise() + "|" + this.deadline;
    }

    /**
     * Converts a deadline task to its string representation
     *
     * @return The string representation of the deadline task
     */
    @Override
    public String toString() {
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("MMM d yyyy, h:mma");
        return "[D]" + super.toString() + " (by: " + this.deadline.format(outputFormatter) + ")";
    }
}
