package bernard.core;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import bernard.exceptions.BernardException;

public class ParserTest {
    private Parser parser;
    private TaskListMock taskListMock;
    private UiMock uiMock;

    @BeforeEach
    void setUp() {
        taskListMock = new TaskListMock();
        uiMock = new UiMock();
        parser = new Parser(taskListMock, uiMock);
    }

    @Test
    void handleCommand_bye_returnsTrue() {
        boolean result = parser.handleCommand("bye");
        assertTrue(result);
    }

    @Test
    void handleCommand_list_callsListTasks() {
        parser.handleCommand("list");
        assertTrue(taskListMock.listCalled);
    }

    @Test
    void handleCommand_find_callsListMatchingTasks() {
        parser.handleCommand("find me");
        assertTrue(taskListMock.listMatchingCalled);
    }

    @Test
    void handleCommand_mark_callsMarkTask() {
        parser.handleCommand("mark 1");
        assertTrue(taskListMock.markCalled);
        assertEquals(0, taskListMock.lastIndex);
    }

    @Test
    void handleCommand_markInvalidIndex_showsError() {
        parser.handleCommand("mark abc");
        assertTrue(uiMock.output.contains("> ERROR! Invalid task index!"));
    }

    @Test
    void handleCommand_unmark_callsUnmarkTask() {
        parser.handleCommand("unmark 2");
        assertTrue(taskListMock.unmarkCalled);
        assertEquals(1, taskListMock.lastIndex);
    }

    @Test
    void handleCommand_delete_callsDeleteTask() {
        parser.handleCommand("delete 3");
        assertTrue(taskListMock.deleteCalled);
        assertEquals(2, taskListMock.lastIndex);
    }

    @Test
    void handleCommand_addTask_callsAddTask() {
        parser.handleCommand("todo Read book");
        assertTrue(taskListMock.addCalled);
        assertArrayEquals(new String[]{"todo", "Read", "book"}, taskListMock.lastArgs);
    }

    @Test
    void handleCommand_addTask_exceptionShowsError() {
        taskListMock.shouldThrow = true;
        parser.handleCommand("todo ");
        assertTrue(uiMock.output.contains("> ERROR! Not sure what you mean..."));
    }

    // Mock TaskList class
    static class TaskListMock extends TaskList {
        boolean listCalled = false;
        boolean listMatchingCalled = false;
        boolean markCalled = false;
        boolean unmarkCalled = false;
        boolean deleteCalled = false;
        boolean addCalled = false;
        boolean shouldThrow = false;
        int lastIndex = -1;
        String[] lastArgs = null;

        TaskListMock() {
            super(new ArrayList<>(), new UiMock());
        }

        @Override
        public void listTasks() {
            listCalled = true;
        }

        @Override
        public void listMatchingTasks(String keyword) {
            listMatchingCalled = true;
        }

        @Override
        public void markTask(int index) throws BernardException {
            if (shouldThrow) {
                throw new BernardException("Test exception");
            }
            markCalled = true;
            lastIndex = index;
        }

        @Override
        public void unmarkTask(int index) throws BernardException {
            if (shouldThrow) {
                throw new BernardException("Test exception");
            }
            unmarkCalled = true;
            lastIndex = index;
        }

        @Override
        public void deleteTask(int index) throws BernardException {
            if (shouldThrow) {
                throw new BernardException("Test exception");
            }
            deleteCalled = true;
            lastIndex = index;
        }

        @Override
        public void addTask(String[] taskArgs) throws BernardException {
            if (shouldThrow) {
                throw new BernardException("Not sure what you mean...");
            }
            addCalled = true;
            lastArgs = taskArgs;
        }
    }

    // Mock Ui class
    static class UiMock extends Ui {
        String output = "";

        @Override
        public void outputLine(String line) {
            output += line;
        }
    }
}
