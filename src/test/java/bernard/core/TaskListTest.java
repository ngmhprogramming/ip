package bernard.core;

import bernard.exceptions.BernardException;
import bernard.tasks.Task;
import bernard.tasks.Todo;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TaskListTest {

    private TaskList taskList;
    private List<Task> tasks;

    @BeforeEach
    void setUp() {
        tasks = new ArrayList<>();
        taskList = new TaskList(tasks);
    }

    @Test
    void addTask_todoTask_success() throws BernardException {
        String[] args = {"todo", "Read", "book"};
        taskList.addTask(args);

        assertEquals(1, tasks.size());
        assertTrue(tasks.get(0) instanceof Todo);
        assertEquals("[T][ ] Read book", tasks.get(0).toString());
    }

    @Test
    void addTask_invalidTask_throwsException() {
        String[] args = {"invalid", "task"};
        BernardException e = assertThrows(BernardException.class, () -> taskList.addTask(args));
        assertEquals("Not sure what you mean...", e.getMessage());
    }

    @Test
    void markTask_markingTask_success() throws BernardException {
        String[] args = {"todo", "Read", "book"};
        taskList.addTask(args);

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
        String[] args = {"todo", "Read", "book"};
        taskList.addTask(args);
        assertEquals(1, tasks.size());

        taskList.deleteTask(0);
        assertEquals(0, tasks.size());
    }

    @Test
    void deleteTask_outOfRange_throwsException() {
        BernardException e = assertThrows(BernardException.class, () -> taskList.deleteTask(0));
        assertEquals("Task index out of range!", e.getMessage());
    }

    @Test
    void listTasks_printsTasks() throws BernardException {
        var outContent = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outContent));

        taskList.addTask(new String[]{"todo", "Read", "book"});
        taskList.markTask(0);
        taskList.listTasks();

        String output = outContent.toString();
        assertTrue(output.contains("> Task list:"));
        assertTrue(output.contains("1. [T][X] Read book"));
    }

    @Test
    void saveTasks_callsStorageSave() throws BernardException {
        // Mock Storage using a simple class
        var mockStorage = new StorageMock();
        taskList.addTask(new String[]{"todo", "Read", "book"});
        taskList.markTask(0);

        taskList.saveTasks(mockStorage);
        assertTrue(mockStorage.savedCalled);
        assertEquals(1, mockStorage.savedTasks.size());
        assertEquals("[T][X] Read book", mockStorage.savedTasks.get(0).toString());
    }

    // Simple Storage mock class
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
}
