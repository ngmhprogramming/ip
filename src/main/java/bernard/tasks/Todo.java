package bernard.tasks;

import bernard.exceptions.BernardException;

public class Todo extends Task {
    public Todo(String description) throws BernardException {
        super(description);
    }

    @Override
    public String serialise() {
        return "T|" + super.serialise();
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
