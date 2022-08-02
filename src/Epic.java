import java.util.LinkedList;

public class Epic extends Task {

    private LinkedList<Subtask> subtasks = new LinkedList<>();
    private Statuses status;

    public Epic(String name, String description) {
        super(name, description);
    }

    public void addSubtask(Subtask subtask) {
        subtasks.add(subtask);
    }

    public void removeSubtask(Subtask subtask) {
        subtasks.remove(subtask);
    }

    public LinkedList<Subtask> getSubtasks() {
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
            if (currentSubtask.getStatus().equals(Statuses.NEW)) {
                status = Statuses.NEW;
                return;
            }
        }
        for (Subtask currentSubtask: subtasks) {
            if (currentSubtask.getStatus().equals(Statuses.IN_PROGRESS)) {
                status = Statuses.IN_PROGRESS;
                return;
            }
        }
            status = Statuses.DONE;
    }

    @Override
    public Statuses getStatus() {
        updateStatus();
        return status;
    }

    public void removeAllSubtasks() {
        subtasks.clear();
    }

}
