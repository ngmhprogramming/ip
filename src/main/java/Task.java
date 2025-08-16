public class Task {
    private String description;
    private boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
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
