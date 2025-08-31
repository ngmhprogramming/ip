package bernard.core;

import java.util.Arrays;
import java.util.List;

import bernard.exceptions.BernardException;
import bernard.tasks.Task;

/**
 * Handles tasks for the Bernard Personal Assistant
 */
public class TaskList {
    private List<Task> tasks;
    private Ui ui;

    /**
     * Construct a task list
     *
     * @param tasks List of tasks to intialise with
     * @param ui Ui manager to use for output
     */
    public TaskList(List<Task> tasks, Ui ui) {
        this.tasks = tasks;
        this.ui = ui;
    }

    /**
     * Save task list locally
     *
     * @param storage Storage manager used for local storage
     * @throws BernardException If unable to save list of tasks locally
     */
    public void saveTasks(Storage storage) throws BernardException {
        storage.save(tasks);
    }

    /**
     * Extract task args from a command and specified delimiters
     *
     * @param taskArgs User command
     * @param delimiters List of strings to split by to get arguments
     * @return List of strings split into arguments for task creation
     */
    private String[] extractTaskArgs(String[] taskArgs, String... delimiters) {
        if (delimiters.length == 0) {
            return new String[]{ String.join(" ", Arrays.copyOfRange(taskArgs, 1, taskArgs.length)) };
        }
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
            if (!output[index].equals("")) {
                output[index] += " ";
            }
            output[index] += taskArgs[i] + "";
        }
        return output;
    }

    /**
     * Add a task to the list using specified arguments
     *
     * @param taskArgs Arguments to create task
     * @throws BernardException If unable to create task from arguments
     */
    public void addTask(String[] taskArgs) throws BernardException {
        String[] parsedArgs;
        if (taskArgs[0].equals("todo")) {
            parsedArgs = extractTaskArgs(taskArgs);
            tasks.add(Task.of(Task.TaskType.TODO, parsedArgs));
        } else if (taskArgs[0].equals("deadline")) {
            parsedArgs = extractTaskArgs(taskArgs, "/by");
            tasks.add(Task.of(Task.TaskType.DEADLINE, parsedArgs));
        } else if (taskArgs[0].equals("event")) {
            parsedArgs = extractTaskArgs(taskArgs, "/from", "/to");
            tasks.add(Task.of(Task.TaskType.EVENT, parsedArgs));
        } else {
            throw new BernardException("Not sure what you mean...");
        }
        ui.outputLine("> Added task: ");
        ui.outputLine(tasks.get(tasks.size() - 1).toString());
    }

    /**
     * Mark a task as complete
     *
     * @param index Index of task to be marked in task list
     * @throws BernardException If index is out of range
     */
    public void markTask(int index) throws BernardException {
        if (index >= tasks.size()) {
            throw new BernardException("Task index out of range!");
        }
        tasks.get(index).setDoneStatus(true);
        System.out.println("> I've marked the task as done!");
        System.out.println(tasks.get(index));
    }

    /**
     * Unmark a task as complete
     *
     * @param index Index of task to be unmarked in task list
     * @throws BernardException If index is out of range
     */
    public void unmarkTask(int index) throws BernardException {
        if (index >= tasks.size()) {
            throw new BernardException("Task index out of range!");
        }
        tasks.get(index).setDoneStatus(false);
        System.out.println("> I've marked the task as undone!");
        System.out.println(tasks.get(index));
    }

    /**
     * Delete a task
     *
     * @param index Index of task to be marked in task list
     * @throws BernardException If index is out of range
     */
    public void deleteTask(int index) throws BernardException {
        if (index >= tasks.size()) {
            throw new BernardException("Task index out of range!");
        }
        ui.outputLine("> Removing task: ");
        ui.outputLine(tasks.get(index).toString());
        tasks.remove(index);
        ui.outputLine("I've deleted the task!");
    }

    /**
     * Print all tasks found in task list
     */
    public void listTasks() {
        ui.outputLine("> Task list:");
        for (int i = 0; i < tasks.size(); i++) {
            ui.outputLine((i + 1) + ". " + tasks.get(i));
        }
    }

    /**
     * Print all tasks found in task list matching a keyword
     *
     * @param keyword Keyword to be matched
     */
    public void listMatchingTasks(String keyword) {
        System.out.println("> Matching Tasks:");
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).matchesKeyword(keyword)) {
                System.out.println((i + 1) + ". " + tasks.get(i));
            }
        }
    }
}
