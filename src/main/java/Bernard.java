import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Bernard {
    private static Scanner sc = new Scanner(System.in);
    private static TaskList taskList;
    private static Ui ui = new Ui();

    public static void main(String[] args) {
        ui.title();

        Storage storage = null;
        try {
            storage = new Storage("./data/bernard.txt");
            taskList = new TaskList(storage.load());
        } catch (BernardException e) {
            ui.outputLine("> ERROR! " + e.getMessage());
            ui.outputLine("Shutting down...");
        }

        ui.greet();

        boolean ended = false;
        while (!ended) {
            String command = ui.getUserInput();
            String[] commandArgs = command.split(" ");
            try {
                if (commandArgs[0].equals("bye")) {
                    ended = true;
                    break;
                } else if (commandArgs[0].equals("list")) {
                    taskList.listTasks();
                } else if (commandArgs[0].equals("mark")) {
                    if (commandArgs.length == 1) {
                        throw new BernardException("No task specified!");
                    }
                    try {
                        int index = Integer.parseInt(commandArgs[1]) - 1;
                        taskList.markTask(index);
                    } catch (NumberFormatException exception) {
                        throw new BernardException("Invalid task index!");
                    }
                } else if (commandArgs[0].equals("unmark")) {
                    if (commandArgs.length == 1) {
                        throw new BernardException("No task specified!");
                    }
                    try {
                        int index = Integer.parseInt(commandArgs[1]) - 1;
                        taskList.unmarkTask(index);
                    } catch (NumberFormatException exception) {
                        throw new BernardException("Invalid task index!");
                    }
                } else if (commandArgs[0].equals("delete")) {
                    if (commandArgs.length == 1) {
                        throw new BernardException("No task specified!");
                    }
                    try {
                        int index = Integer.parseInt(commandArgs[1]) - 1;
                        taskList.deleteTask(index);
                    } catch (NumberFormatException exception) {
                        throw new BernardException("Invalid task index!");
                    }
                } else {
                    taskList.addTask(commandArgs);
                }
            } catch (BernardException e) {
                ui.outputLine("> ERROR! " + e.getMessage());
            }
        }
        try {
            taskList.saveTasks(storage);
        } catch (BernardException e) {
            ui.outputLine("> ERROR! " + e.getMessage());
        }

        ui.bye();
    }
}
