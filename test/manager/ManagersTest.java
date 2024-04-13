package manager;

import manager.InMemoryHistoryManager;
import manager.InMemoryTaskManager;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ManagersTest {

    @Test
    public void assertEqualsInMemoryHistoryManagerTest() {
        HistoryManager expected = new InMemoryHistoryManager();
        HistoryManager actual = Managers.getDefaultHistory();
        Assertions.assertNotNull(actual, "Объект не был создан.");
        assertEquals(expected.getHistory(), actual.getHistory(), ", history");
    }

    @Test
    public void assertEqualsTaskManagerTest() {
        TaskManager expected = new InMemoryTaskManager();
        TaskManager actual = Managers.getDefault();
        Assertions.assertNotNull(actual, "Объект не был создан.");
        assertEquals(expected.getAllTasks(), actual.getAllTasks(), ", tasks");
    }
}