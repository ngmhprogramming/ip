import java.util.Scanner;

public class Bernard {
    private static Scanner sc = new Scanner(System.in);
    private static String[] tasks = new String[100];
    private static int lastTask = 0;

    private static String getUserInput() {
        return sc.nextLine();
    }

    private static void addTask(String task) {
        tasks[lastTask] = task;
        lastTask++;
    }

    private static void listTasks() {
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

        String command = "";
        boolean ended = false;
        while (!ended) {
            command = getUserInput();
            if (command.equals("bye")) {
                ended = true;
                break;
            } else if (command.equals("list")) {
                System.out.println("> Task list:");
                listTasks();
            } else {
                System.out.println("> Added task: " + command);
                addTask(command);
            }
        }
        System.out.println("Goodbye! See you again!");
    }
}
