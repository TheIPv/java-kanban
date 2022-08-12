import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;


class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager> {
    public InMemoryTaskManagerTest() {
        putTaskManager(new InMemoryTaskManager());
    }
    void putTaskManager(InMemoryTaskManager inMemoryTaskManager) {
        super.putTaskManager(inMemoryTaskManager);
    }

    @Test
    void getPrioritizedTasks() {
        Epic transfer = new Epic("Переезд", "Описание");

        TaskManager taskManager = new InMemoryTaskManager();
        Subtask firstTransferSubtask = new Subtask("Собрать коробки", "Описание", Statuses.NEW, transfer,
                LocalDateTime.of(2021, 2, 14, 1, 2,3),
                Duration.ofDays(20));
        taskManager.createSubtask(firstTransferSubtask);

        Subtask secondTransferSubtask = new Subtask("Упаковать кошку", "Описание", Statuses.NEW, transfer,
                LocalDateTime.of(2020, 2, 14, 1, 2,3),
                Duration.ofDays(20));
        taskManager.createSubtask(secondTransferSubtask);

        Subtask thirdTransferSubtask = new Subtask("Сказать слова прощания", "Описание", Statuses.NEW, transfer,
                LocalDateTime.of(2022, 2, 14, 1, 2,3),
                Duration.ofDays(20));
        taskManager.createSubtask(thirdTransferSubtask);

        taskManager.createEpic(transfer);

        assertEquals(List.of(secondTransferSubtask,firstTransferSubtask,thirdTransferSubtask), taskManager.getPrioritizedTasks(), "");

        taskManager.deleteAllEpics();
    }
}