import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Bernard {
    private static Scanner sc = new Scanner(System.in);
    private static List<Task> tasks = new ArrayList<>();

    private static String getUserInput() {
        return sc.nextLine();
    }

    private static String[] extractTaskArgs(String[] taskArgs, String[] delimiters) {
        String[] output = new String[delimiters.length + 1];
        for (int i = 0; i < output.length; i++) {
            output[i] = "";
        }
        int index = 0;
        for (int i = 1; i < taskArgs.length; i++) {
            if (index < delimiters.length && taskArgs[i].equals(delimiters[index])) {
                index++;
                continue;
            }
            if (output[index] != "") {
                output[index] += " ";
            }
            output[index] += taskArgs[i] + "";
        }
        return output;
    }

    private static void addTask(String[] taskArgs) throws BernardException{
        String[] parsedArgs;
        if (taskArgs[0].equals("todo")) {
            parsedArgs = extractTaskArgs(taskArgs, new String[]{});
            tasks.add(Task.of(Task.TaskType.TODO, parsedArgs));
        } else if (taskArgs[0].equals("deadline")) {
            parsedArgs = extractTaskArgs(taskArgs, new String[]{ "/by" });
            tasks.add(Task.of(Task.TaskType.DEADLINE, parsedArgs));
        } else if (taskArgs[0].equals("event")){
            parsedArgs = extractTaskArgs(taskArgs, new String[]{ "/from", "/to" });
            tasks.add(Task.of(Task.TaskType.EVENT, parsedArgs));
        } else {
            throw new BernardException("Not sure what you mean...");
        }
        System.out.println("> Added task: ");
        System.out.println(tasks.get(tasks.size() - 1));
    }

    private static void markTask(int index) throws BernardException {
        if (index >= tasks.size()) {
            throw new BernardException("Task index out of range!");
        }
        tasks.get(index).updateDoneStatus(true);
        System.out.println("> I've marked the task as done!");
        System.out.println(tasks.get(index));
    }

    private static void unmarkTask(int index) throws BernardException {
        if (index >= tasks.size()) {
            throw new BernardException("Task index out of range!");
        }
        tasks.get(index).updateDoneStatus(false);
        System.out.println("> I've marked the task as undone!");
        System.out.println(tasks.get(index));
    }

    private static void deleteTask(int index) throws BernardException {
        if (index >= tasks.size()) {
            throw new BernardException("Task index out of range!");
        }
        System.out.println("> Removing task: ");
        System.out.println(tasks.get(index));
        tasks.remove(index);
        System.out.println("I've deleted the task!");
    }

    private static void listTasks() {
        System.out.println("> Task list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i));
        }
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
            tasks = storage.load();
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
                    listTasks();
                } else if (commandArgs[0].equals("mark")) {
                    if (commandArgs.length == 1) {
                        throw new BernardException("No task specified!");
                    }
                    try {
                        int index = Integer.parseInt(commandArgs[1]) - 1;
                        markTask(index);
                    } catch (NumberFormatException exception) {
                        throw new BernardException("Invalid task index!");
                    }
                } else if (commandArgs[0].equals("unmark")) {
                    if (commandArgs.length == 1) {
                        throw new BernardException("No task specified!");
                    }
                    try {
                        int index = Integer.parseInt(commandArgs[1]) - 1;
                        unmarkTask(index);
                    } catch (NumberFormatException exception) {
                        throw new BernardException("Invalid task index!");
                    }
                } else if (commandArgs[0].equals("delete")) {
                    if (commandArgs.length == 1) {
                        throw new BernardException("No task specified!");
                    }
                    try {
                        int index = Integer.parseInt(commandArgs[1]) - 1;
                        deleteTask(index);
                    } catch (NumberFormatException exception) {
                        throw new BernardException("Invalid task index!");
                    }
                } else {
                    addTask(commandArgs);
                }
            } catch (BernardException e) {
                System.out.println("> ERROR! " + e.getMessage());
            }
        }
        try {
            storage.save(tasks);
        } catch (BernardException e) {
            System.out.println("> ERROR! " + e.getMessage());
        }
        System.out.println("Goodbye! See you again!");
    }
}
