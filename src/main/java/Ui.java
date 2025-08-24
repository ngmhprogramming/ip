import java.util.Scanner;

public class Ui {
    private Scanner scanner;

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    public void title() {
        String logo = " ____                                _ \n" +
                "| __ )  ___ _ __ _ __   __ _ _ __ __| |\n" +
                "|  _ \\ / _ \\ '__| '_ \\ / _` | '__/ _` |\n" +
                "| |_) |  __/ |  | | | | (_| | | | (_| |\n" +
                "|____/ \\___|_|  |_| |_|\\__,_|_|  \\__,_|";
        System.out.println(logo);
    }

    public void greet() {
        System.out.println("Hello! I'm Bernard, your helpful companion!");
        System.out.println("How can I help you today?");
    }

    public void bye() {
        System.out.println("Goodbye! See you again!");
    }

    public String getUserInput() {
        return scanner.nextLine();
    }

    public void outputLine(String line) {
        System.out.println(line);
    }
}
