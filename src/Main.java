public class Main {

    public static void main(String[] args) {

        Manager manager = new Manager();

        Task firstTask = new Task("Первая задача", "Описание", "NEW");
        manager.createTask(firstTask);

        Task secondTask = new Task("Вторая задача", "Описание", "NEW");
        manager.createTask(secondTask);

        Epic transfer = new Epic("Переезд", "Описание");

        Subtask firstTransferSubtask = new Subtask("Собрать коробки", "Описание", "NEW", transfer);
        manager.createSubtask(firstTransferSubtask);

        Subtask secondTransferSubtask = new Subtask("Упаковать кошку", "Описание", "NEW", transfer);
        manager.createSubtask(secondTransferSubtask);

        Subtask thirdTransferSubtask = new Subtask("Сказать слова прощания", "Описание", "NEW", transfer);
        manager.createSubtask(thirdTransferSubtask);

        manager.createEpic(transfer);

        Epic importantEpic = new Epic("Важный эпик 2", "Описание");

        Subtask importantEpicSubtask = new Subtask("Задача 1", "Описание", "NEW", importantEpic);
        manager.createSubtask(importantEpicSubtask);

        manager.createEpic(importantEpic);

        System.out.println(manager.getAllEpics());
        System.out.println(manager.getAllTasks());
        System.out.println(manager.getAllSubtasks());

        firstTask.status = "IN_PROGRESS";
        manager.updateTask(firstTask);
        System.out.println(manager.getAllTasks());

        firstTransferSubtask.status = "DONE";
        manager.updateSubtask(firstTransferSubtask);

        secondTransferSubtask.status = "DONE";
        manager.updateSubtask(secondTransferSubtask);

        thirdTransferSubtask.status = "IN_PROGRESS";
        manager.updateSubtask(thirdTransferSubtask);

        System.out.println(manager.getAllEpics());

        manager.removeTaskById(firstTask.getId());
        System.out.println(manager.getAllTasks());

        manager.removeEpicById(importantEpic.getId());
        manager.removeSubtaskById(thirdTransferSubtask.getId());

        System.out.println(manager.getAllEpics());
        System.out.println(manager.getAllSubtasks());

        manager.deleteAllSubtasks();
        System.out.println(manager.getAllEpics());
    }

}
