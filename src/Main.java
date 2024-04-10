import manager.TaskManager;
import model.Epic;
import model.Status;
import model.SubTask;
import model.Task;

public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();

        Task task1 = new Task("Задача 1", "Описание задачи 1", Status.NEW, 0);
        Task task2 = new Task("Задача 2", "Описание задачи 2", Status.NEW, 0);
        taskManager.createTask(task1);
        taskManager.createTask(task2);

        Epic epic1 = new Epic("Эпик 1", "Описание эпика 1", Status.NEW, 0);
        taskManager.createEpic(epic1);
        SubTask subTask1 = new SubTask("Подзадача 1", "Описание подзадачи 1", Status.NEW, 0, epic1.getId());
        taskManager.createSubtask(subTask1);
        SubTask subTask2 = new SubTask("Подзадача 2", "Описание подзадача 2", Status.NEW, 0, epic1.getId());
        taskManager.createSubtask(subTask2);

        Epic epic2 = new Epic("Эпик 2", "Описание эпика 2", Status.NEW, 0);
        taskManager.createEpic(epic2);
        SubTask subTask3 = new SubTask("Подзадача 3", "Описание подзадачи 3", Status.NEW, 0, epic2.getId());
        taskManager.createSubtask(subTask3);


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