import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private List<SubTask> subTasks;

    public Epic(Task task, List<SubTask> subTasks) {
        super(task.getTitle(), task.getDescription(), task.getStatus());
        this.subTasks = subTasks;
    }

    public List<SubTask> getSubTasks() {
        return subTasks;
    }

    public void setSubTasks(SubTask subTask) {
        this.subTasks.add(subTask);
    }
}