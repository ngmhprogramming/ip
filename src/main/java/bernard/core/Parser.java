package bernard.core;

import bernard.exceptions.BernardException;

public class Parser {
    private TaskList taskList;
    private Ui ui;

    public Parser(TaskList taskList, Ui ui) {
        this.taskList = taskList;
        this.ui = ui;
    }

    // parse user command and execute accordingly
    public boolean handleCommand(String command) {
        // skip empty command
        if (command.isEmpty()) {
            return false;
        }
        String[] commandArgs = command.split("\\s+");
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
            } else if (commandArgs[0].equals("find")) {
                if (commandArgs.length == 1) {
                    throw new BernardException("No keyword specified!");
                }
                String keyword = String.join(" ", java.util.Arrays.copyOfRange(commandArgs, 1, commandArgs.length)).trim();
                taskList.listMatchingTasks(keyword);
            } else {
                taskList.addTask(commandArgs);
            }
        } catch (BernardException e) {
            ui.outputLine("> ERROR! " + e.getMessage());
        }
        return false;
    }
}
