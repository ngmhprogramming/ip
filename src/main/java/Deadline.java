public class Deadline extends Task {
    private String deadline;

    public Deadline(String description, String deadline) throws BernardException {
        super(description);
        this.deadline = deadline;
    }

    @Override
    public String serialise() {
        return "D|" + super.serialise() + "|" + this.deadline;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + this.deadline + ")";
    }
}
