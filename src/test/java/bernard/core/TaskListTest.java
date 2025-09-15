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
        assertTrue(uiMock.getOutput().contains("> Added task:"));
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
        assertTrue(uiMock.getOutput().contains("> Removing task:"));
        assertTrue(uiMock.getOutput().contains("I've deleted the task!"));
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
        assertTrue(uiMock.getOutput().contains("> Task list:"));
        assertTrue(uiMock.getOutput().contains("1. [T][X] Read book"));
    }

    @Test
    void listMatchingTasks_printsTasks() throws BernardException {
        taskList.addTask(new String[]{"todo", "Read", "book"});
        taskList.addTask(new String[]{"todo", "Read", "things"});
        taskList.markTask(0);
        uiMock.clearOutput();

        taskList.listMatchingTasks("book");

        assertTrue(uiMock.getOutput().contains("> Matching Tasks:"));
        assertTrue(uiMock.getOutput().contains("1. [T][X] Read book"));
    }

    // Method written by ChatGPT, supplied with listMatchingTasks_printsTasks() test
    // from above as well as implementation of TaskList.listMatchingTasks()
    @Test
    void listMatchingTasks_multipleKeywords_printsTasks() throws BernardException {
        // Arrange
        taskList.addTask(new String[]{"todo", "Read", "book"});
        taskList.addTask(new String[]{"todo", "Write", "report"});
        taskList.addTask(new String[]{"todo", "Play", "football"});
        taskList.markTask(1); // mark "Write report" as done

        uiMock.clearOutput();

        // Act
        taskList.listMatchingTasks("book|report");

        // Assert
        String output = uiMock.getOutput();
        assertTrue(output.contains("> Matching Tasks:"), "Should print the matching tasks header");
        assertTrue(output.contains("1. [T][ ] Read book"), "Should list 'Read book'");
        assertTrue(output.contains("2. [T][X] Write report"), "Should list 'Write report'");
        assertTrue(!output.contains("Play football"), "Non-matching tasks should not be listed");
    }

    @Test
    void saveTasks_callsStorageSave() throws BernardException {
        var storageMock = new StorageMock();
        taskList.addTask(new String[]{"todo", "Read", "book"});
        taskList.markTask(0);

        taskList.saveTasks(storageMock);
        assertTrue(storageMock.getSavedCalled());
        assertEquals(1, storageMock.getSavedTasks().size());
        assertEquals("[T][X] Read book", storageMock.getSavedTasks().get(0).toString());
    }

    // Storage mock class
    static class StorageMock extends Storage {
        private boolean savedCalled = false;
        private List<Task> savedTasks = null;

        StorageMock() throws BernardException {
            super(System.getProperty("java.io.tmpdir") + "/dummy.txt");
        }

        public boolean getSavedCalled() {
            return savedCalled;
        }

        public List<Task> getSavedTasks() {
            return savedTasks;
        }

        @Override
        public void save(List<Task> tasks) throws BernardException {
            savedCalled = true;
            savedTasks = tasks;
        }
    }

    // Mock Ui class
    static class UiMock extends Ui {
        private String output = "";

        public String getOutput() {
            return output;
        }

        public void clearOutput() {
            output = "";
        }

        @Override
        public void outputLine(String line) {
            output += line + "\n"; // preserve line breaks
        }
    }
}
