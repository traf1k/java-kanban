package manager;

import model.Epic;
import model.Status;
import model.SubTask;
import model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TaskManager {

    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, SubTask> subTasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();
    private int counter = 0;

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
            Epic epic = epics.get(removeSubTask.getEpicId());
            if (epic != null) {
                epic.getSubTasksIds().remove(Integer.valueOf(id));
                updateStatus(epic.getId());
            }
        }
    }

    public Task createTask(String name, String description, Status status) {
        Task task = new Task(name, description, status, generateCounter());
        tasks.put(task.getId(), task);
        return task;
    }

    public Epic createEpic(String name, String description, Status status) {
        Epic epic = new Epic(name, description, status, generateCounter());
        epics.put(epic.getId(), epic);
        return epic;
    }

    public SubTask createSubtask(String name, String description, Status status, int epicId) {
        Epic epic = getEpicById(epicId);
        if (epic != null) {
            SubTask subTask = new SubTask(name, description, status, generateCounter(), epicId);
            subTasks.put(subTask.getId(), subTask);
            epic.addSubTasksIds(subTask.getId());
            if (epic.getStatus() == Status.DONE) {
                epic.setStatus(Status.IN_PROGRESS);
                updateStatus(epic.getId());
            }
            return subTask;
        }
        return null;
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
            if (epic.getSubTasksIds().isEmpty() || StatusNEW == epic.getSubTasksIds().size()) {
                epic.setStatus(Status.NEW);
            } else if (StatusDONE == epic.getSubTasksIds().size()) {
                epic.setStatus(Status.DONE);
            } else {
                epic.setStatus(Status.IN_PROGRESS);
            }
        }
    }
}