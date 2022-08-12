import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

abstract class TaskManagerTest<T extends TaskManager> {
    T taskManager;

    void putTaskManager(T taskManager) {
        this.taskManager = taskManager;
    }


    @Test
    void getAllTasks() {
        Task task = new Task("Test getAllTasks", "Test getAllTasks description", Statuses.NEW);
        taskManager.createTask(task);
        final List<Task> tasks = taskManager.getAllTasks();

        assertNotNull(tasks, "Задачи не возвращаются.");
        assertEquals(1, tasks.size(), "Неверное количество задач.");
        assertEquals(task, tasks.get(0), "Задачи не совпадают.");

        taskManager.deleteAllTasks();
    }

    @Test
    void getAllSubtasks() {
        Subtask subtask = new Subtask("Test getAllSubtasks", "Test getAllSubtasks description", Statuses.NEW, new Epic("Test epic", "Test descr"));
        taskManager.createSubtask(subtask);
        final LinkedList<Subtask> subtasks = taskManager.getAllSubtasks();

        assertNotNull(subtask, "Подзадачи не возвращаются.");
        assertEquals(1, subtasks.size(), "Неверное количество подзадач.");
        assertEquals(subtask, subtasks.get(0), "Подзадачи не совпадают.");

        taskManager.deleteAllSubtasks();
    }

    @Test
    void getAllEpics() {
        Epic epic = new Epic("Test getAllEpics", "Test getAllEpics description");
        taskManager.createEpic(epic);
        final LinkedList<Epic> epics = taskManager.getAllEpics();

        assertNotNull(epic, "Эпики не возвращаются.");
        assertEquals(1, epics.size(), "Неверное количество эпиков.");
        assertEquals(epic, epics.get(0), "Эпики не совпадают.");

        taskManager.deleteAllEpics();
    }

    @Test
    void deleteAllTasks() {
        Task firstTask = new Task("Test deleteAllTasks", "Test deleteAllTasks description", Statuses.NEW);
        Task secondTask = new Task("Test deleteAllTasks", "Test deleteAllTasks description", Statuses.DONE);
        taskManager.createTask(firstTask);
        taskManager.createTask(secondTask);

        taskManager.deleteAllTasks();

        final LinkedList<Task> tasks = taskManager.getAllTasks();

        assertNotNull(tasks, "Эпики не возвращаются.");
        assertEquals(0, tasks.size(), "Задачи не удаляются");
    }

    @Test
    void deleteAllSubtasks() {
        Epic epic = new Epic("Test deleteAllSubtasks", "Test deleteAllSubtasks description");
        Subtask firstSubtask = new Subtask("Test deleteAllSubtasks", "Test deleteAllSubtasks description", Statuses.NEW, epic);
        Subtask secondSubtask = new Subtask("Test deleteAllSubtasks", "Test deleteAllSubtasks description", Statuses.DONE, epic);

        taskManager.createSubtask(firstSubtask);
        taskManager.createSubtask(secondSubtask);
        taskManager.createEpic(epic);

        taskManager.deleteAllSubtasks();

        final LinkedList<Subtask> subtasks = taskManager.getAllSubtasks();

        assertNotNull(subtasks, "Подзадачи не возвращаются.");
        assertEquals(0, subtasks.size(), "Подзадачи не удаляются");
        assertEquals(new Epic("Epic without Subtasks",
                "Epic without Subtasks description").getSubtasks(),taskManager.getEpicById(epic.getId()).getSubtasks(),
                "Подзадачи не удаляются из эпиков");

        taskManager.deleteAllEpics();
    }

    @Test
    void deleteAllEpics() {
        Epic epic = new Epic("Test deleteAllEpics", "Test deleteAllEpics description");
        Subtask firstSubtask = new Subtask("Test deleteAllEpics", "Test deleteAllEpics description", Statuses.NEW, epic);
        Subtask secondSubtask = new Subtask("Test deleteAllEpics", "Test deleteAllEpics description", Statuses.DONE, epic);

        taskManager.createSubtask(firstSubtask);
        taskManager.createSubtask(secondSubtask);
        taskManager.createEpic(epic);

        taskManager.deleteAllEpics();

        final LinkedList<Epic> epics = taskManager.getAllEpics();

        assertNotNull(epics, "Эпики не возвращаются");
        assertEquals(0, epics.size(), "Эпики не удаляются");
        assertEquals(0, taskManager.getAllSubtasks().size(), "Подзадачи не удаляются");

    }

    @Test
    void createTaskAndGetTaskById() {
        Task task = new Task("Test addNewTask", "Test addNewTask description", Statuses.NEW);

        taskManager.createTask(task);

        final int taskId = task.getId();

        final Task savedTask = taskManager.getTaskById(taskId);

        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(task, savedTask, "Задачи не совпадают.");

        taskManager.deleteAllTasks();

    }

    @Test
    void createSubtaskAndGetSubtaskById() {

        Epic epic = new Epic("Test deleteAllEpics", "Test deleteAllEpics description");

        taskManager.createEpic(epic);

        final int epicId = epic.getId();

        final Epic savedEpic = taskManager.getEpicById(epicId);

        assertNotNull(savedEpic, "Эпик не найден.");
        assertEquals(epic, savedEpic, "Эпики не совпадают.");

        taskManager.deleteAllEpics();
    }

    @Test
    void createEpicAndGetEpicById() {
        Subtask subtask = new Subtask("Test getAllSubtasks", "Test getAllSubtasks description", Statuses.NEW, new Epic("Test epic", "Test descr"));

        taskManager.createSubtask(subtask);

        final int subtaskId = subtask.getId();

        final Task savedSubtask = taskManager.getSubtaskById(subtaskId);

        assertNotNull(savedSubtask, "Подзадача не найдена.");
        assertEquals(subtask, savedSubtask, "Подзадачи не совпадают.");

        taskManager.deleteAllSubtasks();
    }

    @Test
    void updateTask() {
        Task task = new Task("Test updateTask", "Test updateTask description", Statuses.NEW);

        taskManager.createTask(task);
        final int taskId = task.getId();

        assertEquals(task, taskManager.getTaskById(taskId), "Задачи не возвращаются.");

        Task updatedTask = new Task("Updated Task", "descr.", Statuses.DONE);
        updatedTask.setId(taskId);

        taskManager.updateTask(updatedTask);
        final Task savedTask = taskManager.getTaskById(taskId);

        assertNotEquals(task, savedTask, "Задачи не обновляются");
        assertEquals(updatedTask, savedTask, "Задачи обновляются некорректно");

        taskManager.deleteAllTasks();

    }

    @Test
    void updateEpic() {
        Epic epic = new Epic("Test updateEpic", "Test updateEpic description");

        taskManager.createEpic(epic);
        final int epicId = epic.getId();

        assertEquals(epic, taskManager.getEpicById(epicId), "Эпики не возвращаются.");

        Epic updatedEpic = new Epic("Updated Epic", "descr.");
        updatedEpic.setId(epicId);

        taskManager.updateEpic(updatedEpic);
        final Epic savedEpic = taskManager.getEpicById(epicId);

        assertNotEquals(epic, savedEpic, "Эпики не обновляются");
        assertEquals(updatedEpic, savedEpic, "Эпики обновляются некорректно");

    }

    @Test
    void updateSubtask() {
        Subtask subtask = new Subtask("Test updateSubtask", "Test updateSubtask description", Statuses.DONE, new Epic("1", "1"));

        taskManager.createSubtask(subtask);
        final int subtaskId = subtask.getId();

        assertEquals(subtask, taskManager.getSubtaskById(subtaskId), "Подзадачи не возвращаются.");

        Subtask updatedSubtask = new Subtask("Test updateSubtask", "Test updateSubtask description", Statuses.DONE, new Epic("1", "1"));
        updatedSubtask.setId(subtaskId);

        taskManager.updateSubtask(updatedSubtask);
        final Subtask savedSubtask = taskManager.getSubtaskById(subtaskId);

        assertNotEquals(subtask, savedSubtask, "Подзадачи не обновляются");
        assertEquals(updatedSubtask, savedSubtask, "Подзадачи обновляются некорректно");

        taskManager.deleteAllSubtasks();

    }

    @Test
    void removeTaskById() {
        Task task = new Task("Test removeTaskById", "Test removeTaskById description", Statuses.NEW);
        Task secondTask = new Task("Test removeTaskById", "Test removeTaskById description", Statuses.NEW);

        taskManager.createTask(task);
        taskManager.createTask(secondTask);

        final int taskId = task.getId();

        taskManager.removeTaskById(taskId);

        assertEquals(secondTask,taskManager.getTaskById(secondTask.getId()), "Удалены лишние задачи");
        assertEquals(1,taskManager.getAllTasks().size(), "Задачи не удаляются");

        taskManager.deleteAllTasks();
    }

    @Test
    void removeSubtaskById() {
        Subtask subtask = new Subtask("Test removeSubtaskById", "Test removeSubtaskById description", Statuses.NEW, new Epic("",""));
        Subtask secondSubtask = new Subtask("Test removeSubtaskById", "Test removeSubtaskById description", Statuses.NEW, new Epic("",""));

        taskManager.createSubtask(subtask);
        taskManager.createSubtask(secondSubtask);

        final int taskId = subtask.getId();

        taskManager.removeSubtaskById(taskId);

        assertEquals(secondSubtask,taskManager.getSubtaskById(secondSubtask.getId()), "Удалены лишние подзадачи");
        assertEquals(1,taskManager.getAllSubtasks().size(), "Задачи не удаляются");

        taskManager.deleteAllSubtasks();
    }

    @Test
    void removeEpicById() {
        Epic epic = new Epic("Test removeEpicById", "Test removeEpicById description");
        Epic secondEpic = new Epic("Test removeEpicById", "Test removeEpicById description");

        taskManager.createEpic(epic);
        taskManager.createEpic(secondEpic);

        final int epicId = epic.getId();

        taskManager.removeEpicById(epicId);

        assertEquals(secondEpic,taskManager.getEpicById(secondEpic.getId()), "Удалены лишние подзадачи");
        assertEquals(1,taskManager.getAllEpics().size(), "Задачи не удаляются");

        taskManager.deleteAllEpics();
    }
}