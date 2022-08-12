import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class HistoryManagerTest {

    InMemoryHistoryManager inMemoryHistoryManager;
    @Test
    void add() {
        inMemoryHistoryManager = new InMemoryHistoryManager();
        assertEquals(List.of(), inMemoryHistoryManager.getHistory(), "Список задач изначально не пустой");
        Task task = new Task("HistoryManagerTest add", "HistoryManagerTest add description");
        task.setId(1);
        inMemoryHistoryManager.add(task);
        assertEquals(List.of(task), inMemoryHistoryManager.getHistory(), "Задача не создается");
        Task secondTask = new Task("HistoryManagerTest add", "HistoryManagerTest add description");
        secondTask.setId(2);
        inMemoryHistoryManager.add(secondTask);
        assertEquals(List.of(task, secondTask), inMemoryHistoryManager.getHistory(), "Ошибка при создании нескольких задач");
        inMemoryHistoryManager.add(task);
        assertEquals(List.of(secondTask, task), inMemoryHistoryManager.getHistory(), "Ошибка при дублировании");

    }

    @Test
    void remove() {
        inMemoryHistoryManager = new InMemoryHistoryManager();
        //inMemoryHistoryManager.remove(2);
        Task firstTask = new Task("1 HistoryManagerTest add", "HistoryManagerTest add description");
        firstTask.setId(1);
        Task secondTask = new Task("2 HistoryManagerTest add", "HistoryManagerTest add description");
        secondTask.setId(2);
        Task thirdTask = new Task("3 HistoryManagerTest add", "HistoryManagerTest add description");
        thirdTask.setId(3);

        inMemoryHistoryManager.add(firstTask);
        inMemoryHistoryManager.add(secondTask);
        inMemoryHistoryManager.add(thirdTask);
        assertEquals(List.of(firstTask, secondTask, thirdTask), inMemoryHistoryManager.getHistory(), "История просмотра сохраняется некорректно");

        inMemoryHistoryManager.remove(2);
        assertEquals(List.of(firstTask, thirdTask), inMemoryHistoryManager.getHistory(), "Программа работает некорректно при удалении истории просмотра из середины");

        inMemoryHistoryManager.remove(3);
        assertEquals(List.of(firstTask), inMemoryHistoryManager.getHistory(), "Программа работает некорректно при удалении истории просмотра из конца");

        inMemoryHistoryManager.remove(1);
        assertEquals(List.of(), inMemoryHistoryManager.getHistory(), "Программа работает некорректно при удалении истории просмотра из начала");
    }

}