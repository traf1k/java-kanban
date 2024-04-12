package manager;

import model.Epic;
import model.Subtask;
import model.Task;

import java.util.List;

public interface TaskManager {
    List<Task> getHistory();

    void createTasks(Task task);

    void updateTasks(Task task);

    Task getTasksById(int id);

    List<Task> getAllTasks();

    void removeAllTasks();

    void removeTasksById(int id);

    void createEpics(Epic epic);

    void updateEpics(Epic epic);

    Epic getEpicsById(int id);

    List<Subtask> getSubTaskFromEpic(int id);

    List<Epic> getAllEpics();

    void removeAllEpics();

    void removeEpicsById(int id);

    void createSubTasks(Subtask subtask);

    void updateSubTasks(Subtask subtask);

    Subtask getSubTasksById(int id);

    List<Subtask> getAllSubTasks();

    void removeAllSubTasks();

    void removeSubTasksById(int id);

    void checkEpicStatus(Epic epic);
}