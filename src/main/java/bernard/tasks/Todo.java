package bernard.tasks;

import bernard.exceptions.BernardException;

public class Todo extends Task {
    /**
     * Constructs a todo task
     *
     * @param description Task description
     * @throws BernardException If task description is left empty
     */
    public Todo(String description) throws BernardException {
        super(description);
    }
    /**
     * Serialises a todo task for file output
     *
     * @return The serialised representation of the todo task
     */
    @Override
    public String serialise() {
        return "T|" + super.serialise();
    }

    /**
     * Converts a todo task to its string representation
     *
     * @return The string representation of the todo task
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
