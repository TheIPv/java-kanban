import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class Task implements Comparable<Task> {

    protected String name;
    protected String description;
    protected int id;
    protected Statuses status;

    protected Duration duration = Duration.ZERO;

    protected LocalDateTime startTime = LocalDateTime.ofEpochSecond(0,0, ZoneOffset.UTC);



    public Task(String name, String description, Statuses status) {
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public Task(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Task(String name, String description, Statuses status, LocalDateTime startTime, Duration duration) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.startTime = startTime;
        this.duration = duration;
    }

    public void setId (int newId) {
        id = newId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id + '\'' +
                ", status=" + status + '\'' + '}';
    }

    public Statuses getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    public Duration getEndTime() {
        return Duration.between(startTime, startTime.plusMinutes(duration.toMinutes()));
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }
    @Override
    public int compareTo(Task o) {
        return this.getStartTime().isBefore(o.getStartTime())?-1:1;
    }
}
