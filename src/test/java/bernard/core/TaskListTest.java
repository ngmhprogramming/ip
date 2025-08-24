package bernard.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import bernard.exceptions.BernardException;
import bernard.tasks.Task;
import bernard.tasks.Todo;

public class TaskListTest {

    private TaskList taskList;
    private List<Task> tasks;
    private UiMock uiMock;

    @BeforeEach
    void setUp() {
        tasks = new ArrayList<>();
        uiMock = new UiMock();
        taskList = new TaskList(tasks, uiMock);
    }

    @Test
    void addTask_todoTask_success() throws BernardException {
        String[] args = {"todo", "Read", "book"};
        taskList.addTask(args);

        assertEquals(1, tasks.size());
        assertTrue(tasks.get(0) instanceof Todo);
        assertEquals("[T][ ] Read book", tasks.get(0).toString());
        assertTrue(uiMock.output.contains("> Added task:"));
    }

    @Test
    void addTask_invalidTask_throwsException() {
        String[] args = {"invalid", "task"};
        BernardException e = assertThrows(BernardException.class, () -> taskList.addTask(args));
        assertEquals("Not sure what you mean...", e.getMessage());
    }

    @Test
    void markTask_markingTask_success() throws BernardException {
        taskList.addTask(new String[]{"todo", "Read", "book"});

        taskList.markTask(0);
        assertTrue(tasks.get(0).toString().contains("[X]"));

        taskList.unmarkTask(0);
        assertTrue(tasks.get(0).toString().contains("[ ]"));
    }

    @Test
    void markTask_outOfRange_throwsException() {
        BernardException e = assertThrows(BernardException.class, () -> taskList.markTask(0));
        assertEquals("Task index out of range!", e.getMessage());
    }

    @Test
    void deleteTask_success() throws BernardException {
        taskList.addTask(new String[]{"todo", "Read", "book"});
        assertEquals(1, tasks.size());

        taskList.deleteTask(0);
        assertEquals(0, tasks.size());
        assertTrue(uiMock.output.contains("> Removing task:"));
        assertTrue(uiMock.output.contains("I've deleted the task!"));
    }

    @Test
    void deleteTask_outOfRange_throwsException() {
        BernardException e = assertThrows(BernardException.class, () -> taskList.deleteTask(0));
        assertEquals("Task index out of range!", e.getMessage());
    }

    @Test
    void listTasks_printsTasks() throws BernardException {
        taskList.addTask(new String[]{"todo", "Read", "book"});
        taskList.markTask(0);

        taskList.listTasks();
        assertTrue(uiMock.output.contains("> Task list:"));
        assertTrue(uiMock.output.contains("1. [T][X] Read book"));
    }

    @Test
    void saveTasks_callsStorageSave() throws BernardException {
        var storageMock = new StorageMock();
        taskList.addTask(new String[]{"todo", "Read", "book"});
        taskList.markTask(0);

        taskList.saveTasks(storageMock);
        assertTrue(storageMock.savedCalled);
        assertEquals(1, storageMock.savedTasks.size());
        assertEquals("[T][X] Read book", storageMock.savedTasks.get(0).toString());
    }

    // Storage mock class
    static class StorageMock extends Storage {
        boolean savedCalled = false;
        List<Task> savedTasks = null;

        StorageMock() throws BernardException {
            super(System.getProperty("java.io.tmpdir") + "/dummy.txt");
        }

        @Override
        public void save(List<Task> tasks) throws BernardException {
            savedCalled = true;
            savedTasks = tasks;
        }
    }

    // Mock Ui class
    static class UiMock extends Ui {
        String output = "";

        @Override
        public void outputLine(String line) {
            output += line + "\n"; // preserve line breaks
        }
    }
}
