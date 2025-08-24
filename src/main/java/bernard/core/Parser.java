package bernard.core;

import bernard.exceptions.BernardException;

/**
 * Handles parsing of user commands for the Bernard Personal Assistant
 */
public class Parser {
    private TaskList taskList;
    private Ui ui;

    /**
     * Constructs a Parser
     *
     * @param taskList Task List used by commands
     * @param ui Ui used by commands
     */
    public Parser(TaskList taskList, Ui ui) {
        this.taskList = taskList;
        this.ui = ui;
    }

    /**
     * Parse user command and execute accordingly
     *
     * @param command Command sent by user
     * @return Boolean indicating whether command is terminal
     */
    public boolean handleCommand(String command) {
        String[] commandArgs = command.split(" ");
        try {
            if (commandArgs[0].equals("bye")) {
                return true;
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
        return false;
    }
}
