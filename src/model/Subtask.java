package model;

public class Subtask extends Task {
    protected int epicId;

    public Subtask(String name, String description,Status status, int id) {
        super(name, description, status);
        this.epicId = id;
    }

    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int id) {
        this.epicId = id;
    }
}