public abstract class Task {
    private String description;
    private boolean isDone;

    public static enum TaskType {
        TODO,
        DEADLINE,
        EVENT
    }

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public static Task of(TaskType taskType, String[] taskArgs) {
        if (taskType == TaskType.TODO) {
            return new Todo(taskArgs[0]);
        } else if (taskType == TaskType.DEADLINE) {
            return new Deadline(taskArgs[0], taskArgs[1]);
        } else if (taskType == TaskType.EVENT) {
            return new Event(taskArgs[0], taskArgs[1], taskArgs[2]);
        }
        return null;
    }

    public void updateDoneStatus(boolean value) {
        this.isDone = value;
    }

    private String getDoneSymbol() {
        return (this.isDone ? "X" : " ");
    }

    @Override
    public String toString() {
        return "["+ this.getDoneSymbol() +"] "+ this.description;
    }
}
