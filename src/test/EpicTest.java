import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EpicTest {
    @Test
    void shouldReturnDoneForEpicWithoutSubtasks() {
        Epic epicWithoutSubtasks = new Epic("Эпик без подзадач", "и без описания");
        assertEquals(Statuses.DONE, epicWithoutSubtasks.getStatus());
    }
    @Test
    void shouldReturnNewForEpicWithAllSubtasksWithStatusNew() {
        Epic epicWithNewSubtasks = new Epic("Эпик с подзадачами NEW", "и без описания");
        epicWithNewSubtasks.addSubtask(new Subtask("Подзадача 1 NEW", "и без описания", Statuses.NEW, epicWithNewSubtasks));
        epicWithNewSubtasks.addSubtask(new Subtask("Подзадача 2 NEW", "и без описания", Statuses.NEW, epicWithNewSubtasks));
        assertEquals(Statuses.NEW, epicWithNewSubtasks.getStatus());
    }

    @Test
    void shouldReturnDoneForEpicWithAllSubtasksWithStatusDone() {
        Epic epicWithNewSubtasks = new Epic("Эпик с подзадачами DONE", "и без описания");
        epicWithNewSubtasks.addSubtask(new Subtask("Подзадача 1 DONE", "и без описания", Statuses.DONE, epicWithNewSubtasks));
        epicWithNewSubtasks.addSubtask(new Subtask("Подзадача 2 DONE", "и без описания", Statuses.DONE, epicWithNewSubtasks));
        assertEquals(Statuses.DONE, epicWithNewSubtasks.getStatus());
    }

    @Test
    void shouldReturnNewForEpicWithSubtasksWithStatusNewAndDone() {
        Epic epicWithNewSubtasks = new Epic("Эпик с подзадачами NEW и DONE", "и без описания");
        epicWithNewSubtasks.addSubtask(new Subtask("Подзадача 1 NEW", "и без описания", Statuses.NEW, epicWithNewSubtasks));
        epicWithNewSubtasks.addSubtask(new Subtask("Подзадача 2 DONE", "и без описания", Statuses.DONE, epicWithNewSubtasks));
        assertEquals(Statuses.NEW, epicWithNewSubtasks.getStatus());
    }

    @Test
    void shouldReturnInProgressForEpicWithSubtasksWithStatusInProgress() {
        Epic epicWithNewSubtasks = new Epic("Эпик с подзадачами IN PROGRESS", "и без описания");
        epicWithNewSubtasks.addSubtask(new Subtask("Подзадача 1 IN PROGRESS", "и без описания", Statuses.IN_PROGRESS, epicWithNewSubtasks));
        epicWithNewSubtasks.addSubtask(new Subtask("Подзадача 2 IN PROGRESS", "и без описания", Statuses.IN_PROGRESS, epicWithNewSubtasks));
        assertEquals(Statuses.IN_PROGRESS, epicWithNewSubtasks.getStatus());
    }
}