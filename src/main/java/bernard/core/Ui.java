package bernard.core;

import java.util.Scanner;

/**
 * Handles input and output for the Bernard Personal Assistant
 */
class Ui {
    private Scanner scanner;

    /**
     * Create a Ui manager
     */
    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Print title of Bernard in fancy ASCII
     */
    public void title() {
        String logo = " ____                                _ \n"
                + "| __ )  ___ _ __ _ __   __ _ _ __ __| |\n"
                + "|  _ \\ / _ \\ '__| '_ \\ / _` | '__/ _` |\n"
                + "| |_) |  __/ |  | | | | (_| | | | (_| |\n"
                + "|____/ \\___|_|  |_| |_|\\__,_|_|  \\__,_|";
        System.out.println(logo);
    }

    /**
     * Greet user with introduction
     */
    public void greet() {
        System.out.println("Hello! I'm Bernard, your helpful companion!");
        System.out.println("How can I help you today?");
    }

    /**
     * Say goodbye to the user before leaving
     */
    public void bye() {
        System.out.println("Goodbye! See you again!");
    }

    /**
     * Get a user command from input
     *
     * @return
     */
    public String getUserInput() {
        return scanner.nextLine();
    }

    /**
     * Print output for user
     *
     * @param line String output to be printed
     */
    public void outputLine(String line) {
        System.out.println(line);
    }

    /**
     * Reset output buffer
     */
    public void resetOutput() {}
}
