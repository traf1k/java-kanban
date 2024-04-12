package manager;

import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTaskManager implements TaskManager {
    protected HashMap<Integer, Task> tasks;
    protected HashMap<Integer, Epic> epics;
    protected HashMap<Integer, Subtask> subTasks;

    protected HistoryManager historyManager;

    private int counter = 0;

    public InMemoryTaskManager() {
        this.tasks = new HashMap<>();
        this.epics = new HashMap<>();
        this.subTasks = new HashMap<>();
        this.historyManager = Managers.getDefaultHistory();
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    @Override
    public void createTasks(Task task) {
        task.setId(++counter);
        tasks.put(task.getId(), task);
    }

    @Override
    public void updateTasks(Task task) {
        if (tasks.containsKey(task.getId())) {
            tasks.put(task.getId(), task);
        }
    }

    @Override
    public Task getTasksById(int id) {
        Task task = tasks.get(id);
        historyManager.add(task);
        return task;
    }

    @Override
    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public void removeAllTasks() {
        tasks.clear();
    }

    @Override
    public void removeTasksById(int id) {
        tasks.remove(id);
    }

    @Override
    public void createEpics(Epic epic) {
        epic.setId(++counter);
        epics.put(epic.getId(), epic);
    }

    @Override
    public void updateEpics(Epic epic) {
        if (epics.containsKey(epic.getId())) {
            epics.get(epic.getId()).setName(epic.getName());
            epics.get(epic.getId()).setDescription(epic.getDescription());
        }
    }

    @Override
    public Epic getEpicsById(int id) {
        Epic epic = epics.get(id);
        historyManager.add(epic);
        return epic;
    }

    @Override
    public List<Subtask> getSubTaskFromEpic(int id) {
        Epic epic = epics.get(id);
        ArrayList<Subtask> subTaskList = new ArrayList<>();
        for (int sbId : epic.getSubtasksId()) {
            subTaskList.add(subTasks.get(sbId));
        }
        return subTaskList;
    }

    @Override
    public List<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public void removeAllEpics() {
        epics.clear();
        subTasks.clear();
    }

    @Override
    public void removeEpicsById(int id) {
        ArrayList<Integer> subTaskIdlist = epics.get(id).getSubtasksId();
        for (int key : subTaskIdlist) {
            subTasks.remove(key);
        }
        epics.remove(id);
    }

    @Override
    public void createSubTasks(Subtask subtask) {
        subtask.setId(++counter);
        subTasks.put(subtask.getId(), subtask);
        epics.get(subtask.getEpicId()).addSubtasksId(subtask.getId());
        checkEpicStatus(epics.get(subtask.getEpicId()));
    }

    @Override
    public void updateSubTasks(Subtask subtask) {
        if (subTasks.containsKey(subtask.getId())) {
            subTasks.put(subtask.getId(), subtask);
            checkEpicStatus(epics.get(subtask.getEpicId()));
        }
    }

    @Override
    public Subtask getSubTasksById(int id) {
        Subtask subtask = subTasks.get(id);
        historyManager.add(subtask);
        return subtask;
    }

    @Override
    public List<Subtask> getAllSubTasks() {
        return new ArrayList<>(subTasks.values());
    }

    @Override
    public void removeAllSubTasks() {
        subTasks.clear();
        for (Epic epic : epics.values()) {
            epics.get(epic.getId()).getSubtasksId().clear();
            checkEpicStatus(epics.get(epic.getId()));
        }
    }

    @Override
    public void removeSubTasksById(int id) {
        int sbId = subTasks.get(id).getEpicId();
        subTasks.remove(id);
        epics.get(sbId).getSubtasksId().remove((Integer) id);
        checkEpicStatus(epics.get(sbId));
    }

    @Override
    public void checkEpicStatus(Epic epic) {
        int countNew = 0;
        int countInProgress = 0;
        int countDone = 0;
        if (epic.getSubtasksId().isEmpty()) {
            epics.get(epic.getId()).setStatus(Status.NEW);
            return;
        }
        for (int sbId : epic.getSubtasksId()) {
            Subtask subtask = subTasks.get(sbId);
            if (subtask.getStatus() == Status.NEW) {
                countNew++;
            } else if (subtask.getStatus() == Status.IN_PROGRESS) {
                countInProgress++;
            } else if (subtask.getStatus() == Status.DONE) {
                countDone++;
            }
        }
        if (countNew >= 0 && countInProgress == 0 && countDone == 0) {
            epics.get(epic.getId()).setStatus(Status.NEW);
        } else if (countDone > 0 && countNew == 0 && countInProgress == 0) {
            epics.get(epic.getId()).setStatus(Status.DONE);
        } else {
            epics.get(epic.getId()).setStatus(Status.IN_PROGRESS);
        }
    }
}