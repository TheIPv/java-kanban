import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class InMemoryTaskManager implements TaskManager {

    private static int uniqueId = 0;

    HistoryManager historyManager = Managers.getDefaultHistory();

    @Override
    public LinkedList<Task> getAllTasks() {
        LinkedList<Task> taskList = new LinkedList<>();
        for(Task currentTask : tasks.values()) {
            taskList.add(currentTask);
        }
        return taskList;
    }

    @Override
    public LinkedList<Subtask> getAllSubtasks() {
        return new LinkedList<>(subtasks.values());
    }

    @Override
    public LinkedList<Epic> getAllEpics() {
        LinkedList<Epic> epicList = new LinkedList<>();
        for(Epic currentEpic : epics.values()) {
            epicList.add(currentEpic);
        }
        return epicList;
    }

    @Override
    public void deleteAllTasks() {
        tasks.clear();
    }

    @Override
    public void deleteAllSubtasks() {
        subtasks.clear();
        for(Epic epic: epics.values()) {
            epic.removeAllSubtasks();
        }
    }

    @Override
    public void deleteAllEpics() {
        epics.clear();
        subtasks.clear();
    }

    @Override
    public Task getTaskById(int id) {
        Task task = tasks.get(id);
        historyManager.add(task);
        return task;
    }

    @Override
    public Subtask getSubtaskById(int id) {
        Subtask subtask = subtasks.get(id);
        historyManager.add(subtask);
        return subtask;
    }

    @Override
    public Epic getEpicById(int id) {
        Epic epic = epics.get(id);
        historyManager.add(epic);
        return epic;
    }

    @Override
    public void createTask(Task task) throws IllegalArgumentException {
        try {
            if (task.getStatus().equals(Statuses.IN_PROGRESS)) {
                for (Task currentTask : getAllTasks()) {
                    if (!currentTask.getStatus().equals(Statuses.IN_PROGRESS)) continue;
                    if (task.getEndTime().compareTo(currentTask.getEndTime()) >= 0)
                        throw new IllegalArgumentException();
                }
            }
        }  catch (IllegalArgumentException illegalArgumentException) {
            System.out.println("Задачи не могут пересекаться по времени выполнения");
            return;
        }
        ++uniqueId;
        task.setId(uniqueId);
        tasks.put(uniqueId, task);
    }

    @Override
    public void createSubtask(Subtask subtask) throws IllegalArgumentException {
        try {
            if (subtask.getStatus().equals(Statuses.IN_PROGRESS)) {
                for (Subtask currentSubtask : getAllSubtasks()) {
                    if (!currentSubtask.getStatus().equals(Statuses.IN_PROGRESS)) continue;
                    if (subtask.getEndTime().compareTo(currentSubtask.getEndTime()) >= 0)
                        throw new IllegalArgumentException();
                }
            }
        } catch (IllegalArgumentException illegalArgumentException) {
            System.out.println("Подзадачи не могут пересекаться по времени выполнения");
            return;
        }
        ++uniqueId;
        subtask.setId(uniqueId);
        subtasks.put(uniqueId, subtask);
    }

    @Override
    public void createEpic(Epic epic) {
        ++uniqueId;
        epic.setId(uniqueId);
        for(Subtask currentSubtask : subtasks.values()) {
            if(currentSubtask.getEpic().equals(epic)) {
                epic.addSubtask(currentSubtask);
            }
        }
        epic.updateStatus();
        epics.put(uniqueId, epic);
    }

    @Override
    public void updateTask(Task task) {
        tasks.put(task.getId(), task);
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        subtask.getEpic().updateStatus();
        subtasks.put(subtask.getId(), subtask);
    }

    @Override
    public void updateEpic(Epic epic) {
        epic.updateStatus();
        epics.put(epic.getId(), epic);
    }

    @Override
    public void removeTaskById(int id) {
        tasks.remove(id);
        historyManager.remove(id);
    }

    @Override
    public void removeSubtaskById(int id) {
        Epic epic = subtasks.get(id).getEpic();
        epic.removeSubtask(subtasks.get(id));
        epic.updateStatus();
        historyManager.remove(id);
        subtasks.remove(id);
    }

    @Override
    public void removeEpicById(int id) {
        Epic epic = epics.get(id);
        for(Subtask currentSubtask : epic.getSubtasks()) {
            subtasks.remove(currentSubtask.getId());
            historyManager.remove(currentSubtask.getId());
        }
        historyManager.remove(id);
        epics.remove(id);
    }

    @Override
    public LinkedList<Subtask> getAllSubtaksOfEpic(Epic epic) {
        LinkedList<Subtask> subtasksList = new LinkedList<>();
        return subtasksList;
    }

    @Override
    public List<Task> getHistory() {
        //System.out.println(historyManager.getHistory());
        return historyManager.getHistory();
    }

    public static void increaseId() {++uniqueId;}

    @Override
    public LinkedList<Task> getPrioritizedTasks() {
        LinkedList<Task> allTask = getAllTasks();
        allTask.addAll(getAllSubtasks());
        Collections.sort(allTask);
        return allTask;
    }

}

