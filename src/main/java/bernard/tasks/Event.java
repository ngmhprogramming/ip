package bernard.tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

import bernard.exceptions.BernardException;

/**
 * Event task
 */
public class Event extends Task {
    /**
     * Formatters to try for parsing datetime arguments
     */
    private static final DateTimeFormatter[] FORMATTERS = new DateTimeFormatter[]{
        DateTimeFormatter.ISO_LOCAL_DATE_TIME, // e.g. 2019-12-02T18:00
        DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm") // e.g. 2019-12-02 1800
    };

    private LocalDateTime from;
    private LocalDateTime to;

    /**
     * Constructs an event task
     *
     * @param description Task description
     * @param from Event task start datetime
     * @param to Event task end datetime
     * @throws BernardException If start or end are not valid datetime strings
     */
    public Event(String description, String from, String to) throws BernardException {
        super(description);

        try {
            this.from = parseDateTime(from);
            this.to = parseDateTime(to);
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
     * Serialises an event task for file output
     *
     * @return The serialised representation of the event task
     */
    @Override
    public String serialise() {
        return "E|" + super.serialise() + "|" + this.from + "|" + this.to;
    }

    /**
     * Converts an event task to its string representation
     *
     * @return The string representation of the event task
     */
    @Override
    public String toString() {
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("MMM d yyyy, h:mma", Locale.ENGLISH);
        return "[E]" + super.toString() + " (from: " + this.from.format(outputFormatter)
                + " to: " + this.to.format(outputFormatter) + ")";
    }
}
