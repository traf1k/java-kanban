package manager;

import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InMemoryTaskManagerTest {

    static TaskManager manager;

    @BeforeEach
    void creatingAndAddingTaskSubTaskEpic() {
        manager = Managers.getDefault();
        Task task1 = new Task("Задача 1", "Описание задачи 1");
        Task task2 = new Task("Задача 2", "Описание задачи 2");
        manager.createTasks(task1);
        manager.createTasks(task2);

        Epic epic1 = new Epic("Эпик 1", "Описание эпика 1");
        manager.createEpics(epic1);
        int idEpic1 = epic1.getId();

        Subtask subtask1 = new Subtask("Подзадача 1", "Описание подзадачи 1", Status.NEW, idEpic1);
        Subtask subtask2 = new Subtask("Подзадача 2", "Описание подзадачи 2", Status.NEW, idEpic1);
        Subtask subtask3 = new Subtask("Подзадача 3", "Описание подзадачи 3", Status.NEW, idEpic1);
        manager.createSubTasks(subtask1);
        manager.createSubTasks(subtask2);
        manager.createSubTasks(subtask3);

        Epic epic2 = new Epic("Эпик 2", "Описание эпика 2");
        manager.createEpics(epic2);
        int idEpic2 = epic2.getId();
        Subtask subtask2_1 = new Subtask("Подзадача 1", "Описание подзадачи 1", Status.NEW, idEpic2);
        Subtask subtask2_2 = new Subtask("Подзадача 2", "Описание подзадачи 2", Status.NEW, idEpic2);
        Subtask subtask2_3 = new Subtask("Подзадача 3", "Описание подзадачи 3", Status.NEW, idEpic2);
        manager.createSubTasks(subtask2_1);
        manager.createSubTasks(subtask2_2);
        manager.createSubTasks(subtask2_3);
    }

    @Test
    public void cheсkAddAllTasks() {
        assertFalse(manager.getAllTasks().isEmpty(), "Ошибка при добавлении Задач.");
        assertFalse(manager.getAllEpics().isEmpty(),"Ошибка при добавлении Эпиков.");
        assertFalse(manager.getAllSubTasks().isEmpty(),"Ошибка при добавлении Подзадач.");
    }

    @Test
    public void fieldsMustBeEqualBeforeAndAfterAddingToTheManager() {
        Task testTask = new Task("Задача 3", "Описание задачи 3");
        String name = testTask.getName();
        String description = testTask.getDescription();
        Status status = testTask.getStatus();
        manager.createTasks(testTask);
        int testTaskid = testTask.getId();

        assertEquals(manager.getTasksById(testTaskid).getName(), name,
                "Несовпадение имени.");
        assertEquals(manager.getTasksById(testTaskid).getDescription(), description,
                "Несовпадение описания.");
        assertEquals(manager.getTasksById(testTaskid).getStatus(), status,
                "Несовпадение статуса.");
    }

    @Test
    public void checkUpdateTasksEpicSubTask() {
        Task updateTask = new Task("Обновленная Задача", "Обновленное описание задачи");
        updateTask.setId(1);
        Epic updateEpic = new Epic("Обновленый Эпик", "Обновленное описание эпика");
        updateEpic.setId(3);
        Subtask updateSubtask = new Subtask("Обновленная Подзадача", "Обновленное описание подзадачи", Status.DONE, updateEpic.getId());
        updateSubtask.setId(4);
        manager.updateTasks(updateTask);
        manager.updateEpics(updateEpic);
        manager.updateSubTasks(updateSubtask);

        assertEquals(updateTask, manager.getTasksById(1), "Ошибка при обновлении Задачи.");
        assertEquals(updateEpic, manager.getEpicsById(3), "Ошибка при обновлении Эпика.");
        assertEquals(updateSubtask, manager.getSubTasksById(4), "Ошибка при обновлении Подзадачи.");

    }

    @Test
    public void checkHistoryAndCheckSizeHistory() {
        Task testTask = manager.getTasksById(2);
        manager.getTasksById(1);
        manager.getEpicsById(7);
        manager.getSubTasksById(5);
        manager.getSubTasksById(4);
        manager.getSubTasksById(4);
        manager.getSubTasksById(6);
        manager.getSubTasksById(10);
        manager.getSubTasksById(9);
        Task testTask1 = manager.getTasksById(1);

        List<Task> history = manager.getHistory();
        assertEquals(testTask, history.get(0), "Ошибка при соотвествии задач1");
        assertEquals(testTask1, history.get(9), "Ошибка при соответсвии задач2");
        assertEquals(10, manager.getHistory().size());
        Task testTask2 = manager.getEpicsById(3);
        assertEquals(testTask2, manager.getHistory().get(9), "Ошибка при соответсвии задач3");
    }

    @Test
    public void checkUpdateEpicStatus() {
        Subtask newSubtask1 = new Subtask("Подзадача 1", "Описание подзадачи 1", Status.DONE, 7);
        assertSame(manager.getEpicsById(7).getStatus(), Status.NEW, "Ошибка при обновлении.");
        newSubtask1.setId(9);
        manager.updateSubTasks(newSubtask1);
        assertSame(manager.getEpicsById(7).getStatus(), Status.IN_PROGRESS, "Ошибка при обновлении задачи.");
        Subtask newSubtask2 = new Subtask("Подзадача 2", "Описание подзадачи 2", Status.DONE, 7);
        newSubtask2.setId(8);
        manager.updateSubTasks(newSubtask2);
        Subtask newSubtask3 = new Subtask("Подзадача 3", "Описание подзадачи 3", Status.DONE, 7);
        newSubtask3.setId(10);
        manager.updateSubTasks(newSubtask3);
        assertSame(manager.getEpicsById(7).getStatus(), Status.DONE, "Ошибка при обновлении.");
    }

    @Test
    void checkDeleteTaskEpicSubTask() {
        manager.removeAllTasks();
        manager.removeAllSubTasks();
        manager.removeAllEpics();

        assertTrue(manager.getAllTasks().isEmpty(), "Ошибка при удалении всех Задач.");
        assertTrue(manager.getAllEpics().isEmpty(), "Ошибка при удалении всех Эпиков.");
        assertTrue(manager.getAllSubTasks().isEmpty(),"Ошибка при удалении всех Подзадач.");
    }
}