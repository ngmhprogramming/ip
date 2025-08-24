package bernard.tasks;

import bernard.exceptions.BernardException;

public abstract class Task {
    private String description;
    private boolean isDone;

    public static enum TaskType {
        TODO,
        DEADLINE,
        EVENT
    }

    public Task(String description) throws BernardException {
        if (description.equals("")) {
            throw new BernardException("Empty description!");
        }
        this.description = description;
        this.isDone = false;
    }

    public static Task of(TaskType taskType, String[] taskArgs) throws BernardException {
        if (taskType == TaskType.TODO) {
            return new Todo(taskArgs[0]);
        } else if (taskType == TaskType.DEADLINE) {
            return new Deadline(taskArgs[0], taskArgs[1]);
        } else if (taskType == TaskType.EVENT) {
            return new Event(taskArgs[0], taskArgs[1], taskArgs[2]);
        }
        throw new BernardException("Invalid task type!");
    }

    public void updateDoneStatus(boolean value) {
        this.isDone = value;
    }

    private String getDoneSymbol() {
        return (this.isDone ? "X" : " ");
    }

    /**
     * Attempts to match keyword as a substring of task description
     *
     * @param keyword Keyword to search for
     * @return Boolena indicating whether a match is found
     */
    public boolean matchesKeyword(String keyword) {
        return this.description.toLowerCase().contains(keyword.toLowerCase());
    }

    public String serialise() {
        return getDoneSymbol() + "|" + this.description;
    }

    @Override
    public String toString() {
        return "["+ this.getDoneSymbol() +"] "+ this.description;
    }
}
