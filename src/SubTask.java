public class SubTask extends Task {
    private Epic epic;

    public SubTask(Task task, Epic epic) {
        super(task.getTitle(), task.getDescription(), task.getStatus());
        this.epic = epic;
    }

    public void setEpic(Epic epic) {
        this.epic = epic;
    }

    public Epic getEpic() {
        return epic;
    }

    @Override
    public String toString() {
        return super.toString() + ", Эпик: " + epic.getTitle();
    }
}