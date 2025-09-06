package bernard.tasks;

import java.util.Arrays;

import bernard.exceptions.BernardException;

/**
 * Template for a Task class
 */
public abstract class Task {
    private String description;
    private boolean isDone;

    /**
     * Types of tasks available
     */
    public static enum TaskType {
        TODO,
        DEADLINE,
        EVENT
    }

    /**
     * Constructs a default task
     *
     * @param description Description of task
     * @throws BernardException If description of task is left empty
     */
    public Task(String description) throws BernardException {
        if (description.equals("")) {
            throw new BernardException("Empty description!");
        }
        this.description = description;
        this.isDone = false;
    }

    /**
     * Factory method to construct a specified task type from task string arguments
     *
     * @param taskType Type of task to be used from enum
     * @param taskArgs String arguments to create task from
     * @return The constructed task
     * @throws BernardException If task type specified was invalid
     */
    public static Task of(TaskType taskType, String[] taskArgs) throws BernardException {
        switch (taskType) {
        case TODO:
            return new Todo(taskArgs[0]);
        case DEADLINE:
            return new Deadline(taskArgs[0], taskArgs[1]);
        case EVENT:
            return new Event(taskArgs[0], taskArgs[1], taskArgs[2]);
        default:
            throw new BernardException("Invalid task type!");
        }
    }

    /**
     * Update the done status of a task
     *
     * @param value New boolean value for the done status
     */
    public void setDoneStatus(boolean value) {
        this.isDone = value;
    }

    /**
     * Retrieves a symbol representing the done status of the task
     *
     * @return A character with the appropriate symbol
     */
    private String getDoneSymbol() {
        return this.isDone ? "X" : " ";
    }

    /**
     * Attempts to match keywords as a substring of task description
     *
     * @param keywords Keywords to search for
     * @return Boolean indicating whether a match is found
     */
    public boolean matchesKeyword(String... keywords) {
        return Arrays.stream(keywords)
                .anyMatch(keyword -> this.description.toLowerCase().contains(keyword.toLowerCase()));
    }

    /**
     * Serialise a task for file output
     *
     * @return The serialised form of the task
     */
    public String serialise() {
        return getDoneSymbol() + "|" + this.description;
    }

    /**
     * Converts a task to a string representation
     *
     * @return The string representation of the task
     */
    @Override
    public String toString() {
        return "[" + this.getDoneSymbol() + "] " + this.description;
    }
}
