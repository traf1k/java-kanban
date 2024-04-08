import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) {
        TaskManager tm = new TaskManager();

        // Создаем задачи
        Task task1 = tm.createTask(new Task("Задача 1", "Описание задачи 1", Status.NEW));
        Task task2 = tm.createTask(new Task("Задача 2", "Описание задачи 2", Status.NEW));

        // Создаем эпик с двумя подзадачами
        Epic epic1 = tm.createEpic(new Epic(new Task("Эпик 1", "Описание эпика 1", Status.NEW), new ArrayList<>()));
        SubTask subTask1 = tm.createSubTask(new SubTask(new Task("Подзадача 1", "Описание подзадачи 1", Status.NEW), epic1));
        SubTask subTask2 = tm.createSubTask(new SubTask(new Task("Подзадача 2", "Описание подзадачи 2", Status.NEW), epic1));

        // Создаем эпик с одной подзадачей
        Epic epic2 = tm.createEpic(new Epic(new Task("Эпик 2", "Описание эпика 2", Status.NEW), new ArrayList<>()));
        SubTask subTask3 = tm.createSubTask(new SubTask(new Task("Подзадача 1", "Описание подзадачи 1", Status.NEW), epic2));

        // Изменяем статусы объектов
        task1.setStatus(Status.IN_PROGRESS);
        task2.setStatus(Status.DONE);
        subTask1.setStatus(Status.IN_PROGRESS);
        subTask2.setStatus(Status.DONE);
        subTask3.setStatus(Status.DONE);

        // Распечатываем объекты
        System.out.println("Задача 1: " + task1);
        System.out.println("Задача 2: " + task2);
        System.out.println("Подзадача 1: " + subTask1);
        System.out.println("Подзадача 2: " + subTask2);
        System.out.println("Подзадача 3: " + subTask3);
        System.out.println("Эпик 1: " + epic1);
        System.out.println("Эпик 2: " + epic2);

        // Удаляем одну из задач и один из эпиков
        tm.removeTask(task1.getId());
        tm.removeEpic(epic2.getId());
    }
}