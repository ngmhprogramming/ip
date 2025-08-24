package bernard.core;

import bernard.exceptions.BernardException;

public class Bernard {
    private static final String STORAGE_PATH = "./data/bernard.txt";

    private static TaskList taskList;
    private static Ui ui = new Ui();
    private static Parser parser;
    private static Storage storage;

    public static void main(String[] args) {
        // print Bernard title
        ui.title();

        // try to load storage file and parse tasks
        try {
            storage = new Storage(STORAGE_PATH);
            taskList = new TaskList(storage.load());
        } catch (BernardException e) {
            ui.outputLine("> ERROR! " + e.getMessage());
            ui.outputLine("Shutting down...");
        }

        // greet the user
        ui.greet();

        // parse user commands
        parser = new Parser(taskList, ui);
        boolean ended = false;
        while (!ended) {
            ended = parser.handleCommand(ui.getUserInput());
        }

        // store edited tasklist
        try {
            taskList.saveTasks(storage);
        } catch (BernardException e) {
            ui.outputLine("> ERROR! " + e.getMessage());
        }

        // shutdown
        ui.bye();
    }
}
