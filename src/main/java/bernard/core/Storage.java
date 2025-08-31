package bernard.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import bernard.exceptions.BernardException;
import bernard.tasks.Task;

/**
 * Handles file storage for task list
 */

class Storage {
    private final String filePath;

    /**
     * Construct a Storage manager
     *
     * @param filePath File to use for saving task list
     * @throws BernardException If failure occurs when accessing file
     */
    public Storage(String filePath) throws BernardException {
        this.filePath = filePath;
        checkFileExists();
    }

    /**
     * Checks if a file exists and creates it if it does not
     *
     * @throws BernardException If unable to create and use specified storage file
     */
    private void checkFileExists() throws BernardException {
        try {
            File file = new File(filePath);
            File parentDir = file.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            throw new BernardException("Could not create storage file!");
        }
    }

    /**
     * Loads task list from specified file
     *
     * @return List of tasks found in specified file
     * @throws BernardException If storage file does not exist
     */
    public List<Task> load() throws BernardException {
        List<Task> tasks = new ArrayList<>();
        File file = new File(filePath);

        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                tasks.add(parseLine(line));
            }
        } catch (FileNotFoundException e) {
            throw new BernardException("Could not find storage file!");
        }

        return tasks;
    }

    /**
     * Saves task list to local file
     *
     * @param tasks List of tasks to save
     * @throws BernardException If storage file does not exist
     */
    public void save(List<Task> tasks) throws BernardException {
        FileWriter writer = null;
        try {
            writer = new FileWriter(filePath);
            for (Task task : tasks) {
                writer.write(task.serialise() + System.lineSeparator());
            }
        } catch (IOException e) {
            throw new BernardException("Error saving file: " + e.getMessage());
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
                throw new BernardException("Error saving file: " + e.getMessage());
            }
        }
    }

    /**
     * Parse a line found in the storage file
     *
     * @param line Line read from storage file
     * @return Task constructed from line
     * @throws BernardException If line is an invalid task
     */
    private Task parseLine(String line) throws BernardException {
        String[] parts = line.split("\\|");
        Task.TaskType type;
        switch (parts[0]) {
        case "T":
            type = Task.TaskType.TODO;
            break;
        case "D":
            type = Task.TaskType.DEADLINE;
            break;
        case "E":
            type = Task.TaskType.EVENT;
            break;
        default:
            throw new BernardException("Invalid task type!");
        }
        boolean isDone = parts[1].equals("X");
        String[] args = Arrays.copyOfRange(parts, 2, parts.length);
        Task task = Task.of(type, args);
        task.setDoneStatus(isDone);
        return task;
    }
}
