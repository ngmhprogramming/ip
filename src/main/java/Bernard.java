import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Bernard {
    private static Scanner sc = new Scanner(System.in);
    private static TaskList taskList;

    private static String getUserInput() {
        return sc.nextLine();
    }

    public static void main(String[] args) {
        String logo = " ____                                _ \n" +
                "| __ )  ___ _ __ _ __   __ _ _ __ __| |\n" +
                "|  _ \\ / _ \\ '__| '_ \\ / _` | '__/ _` |\n" +
                "| |_) |  __/ |  | | | | (_| | | | (_| |\n" +
                "|____/ \\___|_|  |_| |_|\\__,_|_|  \\__,_|";
        System.out.println(logo);

        Storage storage = null;
        try {
            storage = new Storage("./data/bernard.txt");
            taskList = new TaskList(storage.load());
        } catch (BernardException e) {
            System.out.println("> ERROR! " + e.getMessage());
            System.out.println("Shutting down...");
        }

        System.out.println("Hello! I'm Bernard, your helpful companion!");
        System.out.println("How can I help you today?");

        boolean ended = false;
        while (!ended) {
            String command = getUserInput();
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
                System.out.println("> ERROR! " + e.getMessage());
            }
        }
        try {
            taskList.saveTasks(storage);
        } catch (BernardException e) {
            System.out.println("> ERROR! " + e.getMessage());
        }
        System.out.println("Goodbye! See you again!");
    }
}
