public class Subtask extends Task {

    Epic epic;

    public Subtask(String name, String description, String status, Epic epic) {
        super(name, description, status);
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
