import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public interface TaskManager {

    HashMap<Integer, Task> tasks = new HashMap<>();
    HashMap<Integer, Subtask> subtasks = new HashMap<>();
    HashMap<Integer, Epic> epics = new HashMap<>();

    LinkedList<Task> getAllTasks();
    LinkedList<Subtask> getAllSubtasks();
    LinkedList<Epic> getAllEpics();

    void deleteAllTasks();
    void deleteAllSubtasks();
    void deleteAllEpics();

    Task getTaskById(int id);
    Subtask getSubtaskById(int id);
    Epic getEpicById(int id);

    public void createTask(Task task);
    public void createSubtask(Subtask subtask);
    public void createEpic(Epic epic);

    void updateTask(Task task);
    void updateEpic(Epic epic);
    void updateSubtask(Subtask subtask);

    void removeTaskById(int id);
    void removeSubtaskById(int id);
    void removeEpicById(int id);

    LinkedList<Subtask> getAllSubtaksOfEpic(Epic epic);

    List<Task> getHistory();

    LinkedList<Task> getPrioritizedTasks();
}