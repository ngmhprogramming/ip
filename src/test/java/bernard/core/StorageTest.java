package bernard.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import bernard.exceptions.BernardException;
import bernard.tasks.Deadline;
import bernard.tasks.Event;
import bernard.tasks.Task;
import bernard.tasks.Todo;

public class StorageTest {

    private static String testFilePath;
    private Storage storage;

    @BeforeEach
    void setUp() throws BernardException {
        // Create a temporary file in the system temp directory
        File tempFile = new File(System.getProperty("java.io.tmpdir"), "bernard_test.txt");
        testFilePath = tempFile.getAbsolutePath();
        storage = new Storage(testFilePath);
    }

    @AfterEach
    void tearDown() {
        // Delete the temp file after each test
        File file = new File(testFilePath);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void saveAndLoad_todoTask_success() throws BernardException {
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Todo("Read book"));

        storage.save(tasks);

        List<Task> loadedTasks = storage.load();
        assertEquals(1, loadedTasks.size());
        assertTrue(loadedTasks.get(0) instanceof Todo);
        assertEquals("[T][ ] Read book", loadedTasks.get(0).toString());
    }

    @Test
    void saveAndLoad_deadlineTask_success() throws BernardException {
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Deadline("Return book", "2019-12-02T18:00"));

        storage.save(tasks);

        List<Task> loadedTasks = storage.load();
        assertEquals(1, loadedTasks.size());
        assertTrue(loadedTasks.get(0) instanceof Deadline);
        assertEquals("[D][ ] Return book (by: Dec 2 2019, 6:00PM)", loadedTasks.get(0).toString());
    }

    @Test
    void saveAndLoad_eventTask_success() throws BernardException {
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Event("Study", "2019-12-02T18:00", "2019-12-02T20:00"));

        storage.save(tasks);

        List<Task> loadedTasks = storage.load();
        assertEquals(1, loadedTasks.size());
        assertTrue(loadedTasks.get(0) instanceof Event);
        assertEquals("[E][ ] Study (from: Dec 2 2019, 6:00PM to: Dec 2 2019, 8:00PM)", loadedTasks.get(0).toString());
    }

    @Test
    void load_corruptedLine_skipped() throws Exception {
        String corruptedContent = "HEHE|HAHA";
        Files.writeString(new File(testFilePath).toPath(), corruptedContent);

        BernardException e = assertThrows(BernardException.class, () -> storage.load());
        assertTrue(e.getMessage().contains("Invalid task type"));
    }
}
