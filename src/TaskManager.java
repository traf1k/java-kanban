import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// Переписал с нуля
public class TaskManager {
    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, SubTask> subTasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();
    private int counter = 0;

    public TaskManager() {
    }

    public List<Task> getTasksList() {
        return new ArrayList<>(tasks.values());
    }

    public List<Epic> getEpicList() {
        return new ArrayList<>(epics.values());
    }

    public List<SubTask> getSubtaskList() {
        return new ArrayList<>(subTasks.values());
    }

    private int generateCounter() {
        return ++counter;
    }

    public void removeAllTasks() {
        tasks.clear();
    }

    public void removeAllEpics() {
        epics.clear();
        subTasks.clear();
    }

    public void removeAllSubtasks() {
        subTasks.clear();
        for (Epic epic : epics.values()) {
            updateStatus(epic.getId());
        }
    }

    public void removeTaskId(int id) {
        tasks.remove(id);
    }

    public void removeEpicId(int id) {
        Epic removeEpic = epics.remove(id);
        if (removeEpic != null) {
            for (int subtaskId : removeEpic.getSubTasksIds()) {
                subTasks.remove(subtaskId);
            }
        }
    }

    public void removeSubTaskId(int id) {
        SubTask removeSubTask = subTasks.remove(id);
        if (removeSubTask != null) {
            Epic epic = getEpicById(removeSubTask.getEpicId());
            if (epic != null) {
                epic.getSubTasksIds().remove(Integer.valueOf(id));
                updateStatus(epic.getId());
            }
        }
    }

    public Task createTask(Task task) {
        task.setId(generateCounter());
        tasks.put(task.getId(), task);
        return task;
    }

    public Epic createEpic(Epic epic) {
        epic.setId(generateCounter());
        epics.put(epic.getId(), epic);
        return epic;
    }

    public SubTask createSubtask(SubTask subTask) {
        Epic epic = getEpicById(subTask.getEpicId());
        if (epic != null) {
            subTask.setId(generateCounter());
            subTasks.put(subTask.getId(), subTask);
            epic.addSubTasksIds(subTask.getId());
            if (epic.getStatus() == Status.DONE) {
                epic.setStatus(Status.IN_PROGRESS);
                updateEpic(epic);
            }
            return subTask;
        }
        return null;
    }

    public void updateTask(Task task) {
        if (!tasks.containsKey(task.getId())) {
            return;
        }
        tasks.put(task.getId(), task);
    }

    public void updateEpic(Epic epic) {
        Epic saved = epics.get(epic.getId());
        if (saved == null) {
            return;
        }
        saved.setName(epic.getName());
        saved.setDescription(epic.getDescription());
        epics.put(epic.getId(), saved);
    }

    public void updateSubTask(SubTask subTask) {
        if (subTasks.containsKey(subTask.getId())) {
            subTasks.put(subTask.getId(), subTask);
            updateStatus(subTask.getEpicId());
        }
    }

    public List<SubTask> getSubTasksEpicsIds(int epicId) {
        Epic epic = epics.get(epicId);
        if (epic != null) {
            List<SubTask> subtasks = new ArrayList<>();
            for (int subtaskId : epic.getSubTasksIds()) {
                SubTask subtask = subTasks.get(subtaskId);
                if (subtask != null) {
                    subtasks.add(subtask);
                }
            }
            return subtasks;
        } else {
            return new ArrayList<>();
        }
    }

    public Task get(int id) {
        return tasks.get(id);
    }

    public Epic getEpicById(int id) {
        return epics.get(id);
    }

    private void updateStatus(int epicId) {
        Epic epic = epics.get(epicId);
        if (epic != null) {
            int tasksSize = epics.size();
            int StatusNEW = 0;
            int StatusDONE = 0;

            for (int subTaskId : epic.getSubTasksIds()) {
                SubTask subTaskInEpic = subTasks.get(subTaskId);
                if (subTaskInEpic.getStatus() == Status.NEW) {
                    StatusNEW++;
                } else if (subTaskInEpic.getStatus() == Status.DONE) {
                    StatusDONE++;
                }
            }
            if (tasksSize == 0 || tasksSize == StatusNEW) {
                epic.setStatus(Status.NEW);
            } else if (tasksSize == StatusDONE) {
                epic.setStatus(Status.DONE);
            } else {
                epic.setStatus(Status.IN_PROGRESS);
            }
        }
    }
}