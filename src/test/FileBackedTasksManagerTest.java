import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileBackedTaskManagerTest extends TaskManagerTest<FileBackedTasksManager> {

    public FileBackedTaskManagerTest() {
        putTaskManager(new FileBackedTasksManager());
    }
    void putTaskManager(FileBackedTasksManager fileBackedTasksManager) {
        super.putTaskManager(fileBackedTasksManager);
    }

    @Test
    public void checkSave() {
        FileBackedTasksManager fileBackedTaskManager = new FileBackedTasksManager();
        HistoryManager historyManager = fileBackedTaskManager.historyManager;

        assertEquals(List.of(),fileBackedTaskManager.getAllTasks(), "Ошибка при пустом списке задач");
        assertEquals(List.of(),historyManager.getHistory(), "Ошибка при пустом списке истории");

        Epic epic = new Epic("Test checkSave", "Test checkSave description");
        fileBackedTaskManager.createEpic(epic);

        assertEquals(List.of(epic),fileBackedTaskManager.getAllEpics(), "Задачи не сохраняются");

        fileBackedTaskManager.deleteAllEpics();

    }

}