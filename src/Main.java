import manager.TaskManager;
import model.Epic;
import model.Status;
import model.SubTask;
import model.Task;

public class Main {

    public static void main(String[] args) {

        TaskManager taskManager = new TaskManager();

        Task task1 = taskManager.createTask("Задача 1", "Описание задачи 1", Status.NEW);
        Task task2 = taskManager.createTask("Задача 2", "Описание задачи 2", Status.NEW);

        Epic epic1 = taskManager.createEpic("Эпик 1", "Описание эпика 1", Status.NEW);
        SubTask subTask1 = taskManager.createSubtask("Подзадача 1", "Описание подзадачи 1", Status.NEW, epic1.getId());
        SubTask subTask2 = taskManager.createSubtask("Подзадача 2", "Описание подзадачи 2", Status.NEW, epic1.getId());

        Epic epic2 = taskManager.createEpic("Эпик 2", "Описание эпика 2", Status.NEW);
        SubTask subTask3 = taskManager.createSubtask("Подзадача 3", "Описание подзадачи 3", Status.NEW, epic2.getId());

        System.out.println("Список эпиков:");
        for (Epic epic : taskManager.getEpicList()) {
            System.out.println(epic.getName());
        }

        System.out.println("Список задач:");
        for (Task task : taskManager.getTasksList()) {
            System.out.println(task.getName());
        }

        System.out.println("Список подзадач для эпика 1:");
        for (SubTask subTask : taskManager.getSubTasksEpicsIds(epic1.getId())) {
            System.out.println(subTask.getName());
        }

        System.out.println("Список подзадач для эпика 2:");
        for (SubTask subTask : taskManager.getSubTasksEpicsIds(epic2.getId())) {
            System.out.println(subTask.getName());
        }

        Epic createdEpic = taskManager.getEpicById(epic1.getId());
        if (createdEpic != null) {
            System.out.println("Статус эпика 1: " + createdEpic.getStatus());
        } else {
            System.out.println("Эпик не найден.");
        }

        task1.setStatus(Status.IN_PROGRESS);
        subTask1.setStatus(Status.IN_PROGRESS);
        taskManager.updateSubTask(subTask1);
        subTask2.setStatus(Status.DONE);
        taskManager.updateSubTask(subTask2);
        subTask3.setStatus(Status.IN_PROGRESS);
        taskManager.updateSubTask(subTask3);

        System.out.println("Статус после его изменения:");

        Epic updatedEpic = taskManager.getEpicById(epic1.getId());
        if (updatedEpic != null) {
            System.out.println("Статус эпика 1: " + updatedEpic.getStatus());
        } else {
            System.out.println("Эпик не найден.");
        }

        taskManager.removeTaskId(task1.getId());
        taskManager.removeEpicId(epic2.getId());
        taskManager.removeSubTaskId(subTask1.getId());

        System.out.println("Список задач после удаления задачи 1:");
        for (Task task : taskManager.getTasksList()) {
            System.out.println(task.getName());
        }

        System.out.println("Список эпиков после удаления эпика 2:");
        for (Epic epics : taskManager.getEpicList()) {
            System.out.println(epics.getName());
        }

        System.out.println("Список подзадач для эпика 1 после удаления подзадачи 1:");
        for (SubTask subtask : taskManager.getSubTasksEpicsIds(epic1.getId())) {
            System.out.println(subtask.getName());
        }
    }
}