public class Task {

    protected String name;
    protected String description;
    protected int id;
    protected Statuses status;
    
    public Task(String name, String description, Statuses status) {
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public Task(String name, String description) {
        this.name = name;
        this.description = description;
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

}
