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
        assertTrue(taskListMock.getListCalled());
    }

    @Test
    void handleCommand_find_callsListMatchingTasks() {
        parser.handleCommand("find me");
        assertTrue(taskListMock.getListMatchingCalled());
    }

    @Test
    void handleCommand_mark_callsMarkTask() {
        parser.handleCommand("mark 1");
        assertTrue(taskListMock.getMarkCalled());
        assertEquals(0, taskListMock.getLastIndex());
    }

    @Test
    void handleCommand_markInvalidIndex_showsError() {
        parser.handleCommand("mark abc");
        assertTrue(uiMock.getOutput().contains("> ERROR! Invalid task index!"));
    }

    @Test
    void handleCommand_unmark_callsUnmarkTask() {
        parser.handleCommand("unmark 2");
        assertTrue(taskListMock.getUnmarkCalled());
        assertEquals(1, taskListMock.getLastIndex());
    }

    @Test
    void handleCommand_delete_callsDeleteTask() {
        parser.handleCommand("delete 3");
        assertTrue(taskListMock.getDeleteCalled());
        assertEquals(2, taskListMock.getLastIndex());
    }

    @Test
    void handleCommand_addTask_callsAddTask() {
        parser.handleCommand("todo Read book");
        assertTrue(taskListMock.getAddCalled());
        assertArrayEquals(new String[]{"todo", "Read", "book"}, taskListMock.getLastArgs());
    }

    @Test
    void handleCommand_addTask_exceptionShowsError() {
        taskListMock.shouldThrow = true;
        parser.handleCommand("todo ");
        assertTrue(uiMock.getOutput().contains("> ERROR! Not sure what you mean..."));
    }

    // Mock TaskList class
    static class TaskListMock extends TaskList {
        private boolean listCalled = false;
        private boolean listMatchingCalled = false;
        private boolean markCalled = false;
        private boolean unmarkCalled = false;
        private boolean deleteCalled = false;
        private boolean addCalled = false;
        private boolean shouldThrow = false;
        private int lastIndex = -1;
        private String[] lastArgs = null;

        TaskListMock() {
            super(new ArrayList<>(), new UiMock());
        }

        public boolean getListCalled() {
            return listCalled;
        }

        public boolean getListMatchingCalled() {
            return listMatchingCalled;
        }

        public boolean getMarkCalled() {
            return markCalled;
        }

        public boolean getUnmarkCalled() {
            return unmarkCalled;
        }

        public boolean getDeleteCalled() {
            return deleteCalled;
        }

        public boolean getAddCalled() {
            return addCalled;
        }

        private int getLastIndex() {
            return lastIndex;
        }

        private String[] getLastArgs() {
            return lastArgs;
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
        private String output = "";

        public String getOutput() {
            return output;
        }

        @Override
        public void outputLine(String line) {
            output += line;
        }
    }
}
