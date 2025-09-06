package bernard.core;

import bernard.exceptions.BernardException;

/**
 * The Bernard Personal Assistant
 */
public class Bernard {
    private static final String STORAGE_PATH = "./data/bernard.txt";
    private static final String GOODBYE_MESSAGE = "Goodbye! See you again!";

    private TaskList taskList;
    private Ui ui;
    private Parser parser;
    private Storage storage;

    /**
     * Initialises a Bernard instance
     *
     * @param ui Ui object for output display
     */
    public Bernard(Ui ui) {
        assert ui != null;
        this.ui = ui;

        try {
            storage = new Storage(STORAGE_PATH);
            taskList = new TaskList(storage.load(), ui);
            parser = new Parser(taskList, ui);
        } catch (BernardException e) {
            ui.outputErrorMessage(e);
            ui.outputLine("Shutting down...");
        }
    }

    public Bernard() {
        this(new GuiUi());
    }

    /**
     * Generates a response for the user's chat message.
     */
    public String getResponse(String input) {
        boolean isDone = parser.handleCommand(input);

        // store edited tasklist
        try {
            taskList.saveTasks(storage);
        } catch (BernardException e) {
            ui.outputErrorMessage(e);
        }

        if (isDone) {
            return GOODBYE_MESSAGE;
        }

        String output = ui.toString();
        ui.resetOutput();
        return output;
    }

    /**
     * Runs the Bernard Personal Assistant
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        Ui ui = new Ui();
        Bernard bernard = new Bernard(ui);

        // print Bernard title
        ui.title();

        // greet the user
        ui.greet();

        // parse user commands
        boolean isDone = false;
        while (!isDone) {
            String output = bernard.getResponse(ui.getUserInput());
            assert output != null;
            if (output.equals(GOODBYE_MESSAGE)) {
                isDone = true;
            }
        }

        // shutdown
        ui.bye();
    }
}
