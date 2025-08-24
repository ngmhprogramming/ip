import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Bernard {
    private static final String STORAGE_PATH = "./data/bernard.txt";

    private static TaskList taskList;
    private static Ui ui = new Ui();
    private static Parser parser;
    private static Storage storage;

    public static void main(String[] args) {
        ui.title();

        try {
            storage = new Storage(STORAGE_PATH);
            taskList = new TaskList(storage.load());
        } catch (BernardException e) {
            ui.outputLine("> ERROR! " + e.getMessage());
            ui.outputLine("Shutting down...");
        }

        ui.greet();

        parser = new Parser(taskList, ui);
        boolean ended = false;
        while (!ended) {
            ended = parser.handleCommand(ui.getUserInput());
        }

        try {
            taskList.saveTasks(storage);
        } catch (BernardException e) {
            ui.outputLine("> ERROR! " + e.getMessage());
        }

        ui.bye();
    }
}
