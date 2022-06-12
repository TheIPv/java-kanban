import java.util.HashMap;
import java.util.ArrayList;

public class Manager {

    private int idGenerator = 0;

    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap<Integer, Subtask> subtasks = new HashMap<>();
    private HashMap<Integer, Epic> epics = new HashMap<>();

    public ArrayList<Task> getAllTasks() {
        ArrayList<Task> taskList = new ArrayList<>();
        for(Task currentTask : tasks.values()) {
            taskList.add(currentTask);
        }
        return taskList;
    }

    public ArrayList<Subtask> getAllSubtasks() {
        ArrayList<Subtask> subtaskList = new ArrayList<>();
        for(Subtask currentSubtask : subtasks.values()) {
            subtaskList.add(currentSubtask);
        }
        return subtaskList;
    }

    public ArrayList<Epic> getAllEpics() {
        ArrayList<Epic> epicList = new ArrayList<>();
        for(Epic currentEpic : epics.values()) {
            epicList.add(currentEpic);
        }
        return epicList;        
    }

    public void deleteAllTasks() {
        tasks.clear();
    }

    public void deleteAllSubtasks() {
        subtasks.clear();
        for(Epic epic: epics.values()) {
            epic.removeAllSubtasks();
        }
    }

    public void deleteAllEpics() {
        epics.clear();
        subtasks.clear();
    }

    public Task getTaskById(int id) {
        Task task = tasks.get(id);
        return task;
    }

    public Subtask getSubtaskById(int id) {
        Subtask subtask = subtasks.get(id);
        return subtask;
    }

    public Epic getEpicById(int id) {
        Epic epic = epics.get(id);
        return epic;
    }

    public void createTask(Task task) {
        ++idGenerator;
        task.setId(idGenerator);
        tasks.put(idGenerator, task);
    }

    public void createSubtask(Subtask subtask) {
        ++idGenerator;
        subtask.setId(idGenerator);
        subtasks.put(idGenerator, subtask);
    }

    public void createEpic(Epic epic) {
        ++idGenerator;
        epic.setId(idGenerator);
        for(Subtask currentSubtask : subtasks.values()) {
            if(currentSubtask.getEpic().equals(epic)) {
                epic.addSubtask(currentSubtask);
            }
        }
        epic.updateStatus();
        epics.put(idGenerator, epic);
    }

    public void updateTask(Task task) {
        tasks.put(task.getId(), task);
    }

    public void updateSubtask(Subtask subtask) {
        subtask.getEpic().updateStatus();
        subtasks.put(subtask.getId(), subtask);
    }

    public void updateEpic(Epic epic) {
        epic.updateStatus();
        epics.put(epic.getId(), epic);
    }

    public void removeTaskById(int id) {
        tasks.remove(id);
    }

    public void removeSubtaskById(int id) {
        Epic epic = subtasks.get(id).getEpic();
        epic.removeSubtask(subtasks.get(id));
        epic.updateStatus();
        subtasks.remove(id);
    }

    public void removeEpicById(int id) {
        Epic epic = epics.get(id);
        for(Subtask currentSubtask : epic.getSubtasks()) {
            subtasks.remove(currentSubtask.getId());
        }
        epics.remove(id);
    }

    public ArrayList<Subtask> getAllSubtaksOfEpic(Epic epic) {
        ArrayList<Subtask> subtasksList = new ArrayList<>();
        return subtasksList;
    }

}

