package bernard.core;

import bernard.exceptions.BernardException;
import bernard.tasks.Task;

import java.util.List;

public class TaskList {
    private List<Task> tasks;

    public TaskList(List<Task> tasks) {
        this.tasks = tasks;
    }

    public void saveTasks(Storage storage) throws BernardException {
        storage.save(tasks);
    }

    private String[] extractTaskArgs(String[] taskArgs, String[] delimiters) {
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

    public void addTask(String[] taskArgs) throws BernardException{
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

    public void markTask(int index) throws BernardException {
        if (index >= tasks.size()) {
            throw new BernardException("Task index out of range!");
        }
        tasks.get(index).updateDoneStatus(true);
        System.out.println("> I've marked the task as done!");
        System.out.println(tasks.get(index));
    }

    public void unmarkTask(int index) throws BernardException {
        if (index >= tasks.size()) {
            throw new BernardException("Task index out of range!");
        }
        tasks.get(index).updateDoneStatus(false);
        System.out.println("> I've marked the task as undone!");
        System.out.println(tasks.get(index));
    }

    public void deleteTask(int index) throws BernardException {
        if (index >= tasks.size()) {
            throw new BernardException("Task index out of range!");
        }
        System.out.println("> Removing task: ");
        System.out.println(tasks.get(index));
        tasks.remove(index);
        System.out.println("I've deleted the task!");
    }

    public void listTasks() {
        System.out.println("> Task list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i));
        }
    }
}
