import java.util.ArrayList;

public class Epic extends Task {

    private ArrayList<Subtask> subtasks = new ArrayList<>();
    private String status;

    public Epic(String name, String description) {
        super(name, description);
    }

    public void addSubtask(Subtask subtask) {
        subtasks.add(subtask);
    }

    public void removeSubtask(Subtask subtask) {
        subtasks.remove(subtask);
    }

    public ArrayList<Subtask> getSubtasks() {
        return subtasks;
    }

    @Override
    public String toString() {
        return "Epic{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id + '\'' +
                ", status=" + status + '\'' +
                ",subtasks=" + subtasks +"}";
    }
   // PostComment{text='" + comments.get(0).getText() + "', whoLiked=" + Arrays.toString(comments.get(0).getWhoLiked()) + "},"
    public void updateStatus() {
        for (Subtask currentSubtask: subtasks) {
            if (currentSubtask.getStatus().equals("NEW")) {
                status = "NEW";
                return;
            }
        }
        for (Subtask currentSubtask: subtasks) {
            if (currentSubtask.getStatus().equals("IN_PROGRESS")) {
                status = "IN_PROGRESS";
                return;
            }
        }
            status = "DONE";
    }

    public void removeAllSubtasks() {
        subtasks.clear();
    }

}
