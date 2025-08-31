package bernard.core;

import bernard.exceptions.BernardException;

/**
 * The Bernard Personal Assistant
 */
public class Bernard {
    private static final String STORAGE_PATH = "./data/bernard.txt";

    private TaskList taskList;
    private Ui ui;
    private Parser parser;
    private Storage storage;

    public Bernard(Ui ui) {
        this.ui = ui;

        try {
            storage = new Storage(STORAGE_PATH);
            taskList = new TaskList(storage.load(), ui);
            parser = new Parser(taskList, ui);
        } catch (BernardException e) {
            ui.outputLine("> ERROR! " + e.getMessage());
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
            ui.outputLine("> ERROR! " + e.getMessage());
        }

        if (isDone) {
            return "Goodbye! See you again!";
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
            if (output.equals("Goodbye! See you again!")) {
                isDone = true;
            }
        }

        // shutdown
        ui.bye();
    }
}
