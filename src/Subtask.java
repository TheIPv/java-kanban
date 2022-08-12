import java.time.Duration;
import java.time.LocalDateTime;

public class Subtask extends Task {

    Epic epic;

    public Subtask(String name, String description, Statuses status, Epic epic) {
        super(name, description, status);
        this.epic = epic;
    }

    public Subtask(String name, String description, Statuses status, Epic epic, LocalDateTime startTime, Duration duration) {
        super(name, description, status, startTime, duration);
        this.epic = epic;
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id + '\'' +
                ", status=" + status + '\'' +
                ", epic=" + epic.getName() + '\'' + '}';
    }

    public Epic getEpic() {
        return epic;
    }

}
