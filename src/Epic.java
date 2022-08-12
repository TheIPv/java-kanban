import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;

import static java.time.ZoneOffset.UTC;

public class Epic extends Task {

    private LinkedList<Subtask> subtasks = new LinkedList<>();
    private Statuses status;

    public Epic(String name, String description) {
        super(name, description);
    }

    public Epic(String name, String description, LocalDateTime startTime, Duration duration) {
        super(name, description, Statuses.NEW, startTime, duration);
        updateStatus();
    }


    public void addSubtask(Subtask subtask) {
        subtasks.add(subtask);
        updateStatus();
    }

    public void removeSubtask(Subtask subtask) {
        subtasks.remove(subtask);
        updateStatus();
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

    public Duration updateStartTimeAndEndTime() {
        LocalDateTime startTime = LocalDateTime.ofEpochSecond(0, 0, UTC);
        Duration endTime = Duration.ZERO;
        for(Subtask currentSubtask: subtasks) {
            if(currentSubtask.getStartTime().isBefore(startTime)) {
                startTime = currentSubtask.getStartTime();
            }
            if(currentSubtask.getEndTime().compareTo(endTime) > 0) {
                endTime = currentSubtask.getEndTime();
            }
        }
        this.startTime = startTime;
        return endTime;
    }
    @Override
    public Duration getEndTime() {
        updateStartTimeAndEndTime();
        return Duration.between(startTime, startTime.plusMinutes(duration.toMinutes()));
    }

    public Duration getDuration() {
        updateStartTimeAndEndTime();
        return Duration.between(getStartTime(), startTime.plusMinutes(duration.toMinutes()));
    }
    @Override
    public LocalDateTime getStartTime() {
        updateStartTimeAndEndTime();
        return startTime;
    }

}
