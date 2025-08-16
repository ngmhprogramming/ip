import java.util.Scanner;

public class Bernard {
    private static Scanner sc = new Scanner(System.in);
    private static Task[] tasks = new Task[100];
    private static int lastTask = 0;

    private static String getUserInput() {
        return sc.nextLine();
    }

    private static void addTask(String task) {
        tasks[lastTask] = new Task(task);
        lastTask++;
        System.out.println("> Added task: " + task);
    }

    private static void markTask(int index) {
        tasks[index].updateDoneStatus(true);
        System.out.println("I've marked the task as done!");
        System.out.println(tasks[index]);
    }

    private static void unmarkTask(int index) {
        tasks[index].updateDoneStatus(false);
        System.out.println("I've marked the task as undone!");
        System.out.println(tasks[index]);
    }

    private static void listTasks() {
        System.out.println("> Task list:");
        for (int i = 0; i < lastTask; i++) {
            System.out.println((i + 1) + ". " + tasks[i]);
        }
    }

    public static void main(String[] args) {
        String logo = " ____                                _ \n" +
                "| __ )  ___ _ __ _ __   __ _ _ __ __| |\n" +
                "|  _ \\ / _ \\ '__| '_ \\ / _` | '__/ _` |\n" +
                "| |_) |  __/ |  | | | | (_| | | | (_| |\n" +
                "|____/ \\___|_|  |_| |_|\\__,_|_|  \\__,_|";
        System.out.println(logo);
        System.out.println("Hello! I'm Bernard, your helpful companion!");
        System.out.println("How can I help you today?");

        boolean ended = false;
        while (!ended) {
            String command = getUserInput();
            String[] commandArgs = command.split(" ");
            if (commandArgs[0].equals("bye")) {
                ended = true;
                break;
            } else if (commandArgs[0].equals("list")) {
                listTasks();
            } else if (commandArgs[0].equals("mark") && commandArgs.length > 1) {
                int index = Integer.parseInt(commandArgs[1]) - 1;
                markTask(index);
            } else if (commandArgs[0].equals("unmark") && commandArgs.length > 1) {
                int index = Integer.parseInt(commandArgs[1]) - 1;
                unmarkTask(index);
            } else {
                addTask(command);
            }
        }
        System.out.println("Goodbye! See you again!");
    }
}
