package bernard.tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import bernard.exceptions.BernardException;

public class Event extends Task {
    private static final DateTimeFormatter[] FORMATTERS = new DateTimeFormatter[]{
            DateTimeFormatter.ISO_LOCAL_DATE_TIME,         // e.g. 2019-12-02T18:00
            DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm") // e.g. 2019-12-02 1800
    };

    private LocalDateTime from;
    private LocalDateTime to;

    public Event(String description, String from, String to) throws BernardException {
        super(description);

        try {
            this.from = parseDateTime(from);
            this.to = parseDateTime(to);
        } catch (DateTimeParseException e) {
            throw new BernardException("Invalid datetime format! Use yyyy-MM-dd HHmm, e.g., 2019-12-02 1800");
        }
    }

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

    @Override
    public String serialise() {
        return "E|" + super.serialise() + "|" + this.from + "|" + this.to;
    }

    @Override
    public String toString() {
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("MMM d yyyy, h:mma");
        return "[E]" + super.toString() + " (from: " + this.from.format(outputFormatter) + " to: " + this.to.format(outputFormatter) + ")";
    }
}
