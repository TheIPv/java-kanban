public class  Main {

    public static void main(String[] args) {

        TaskManager manager = Managers.getDefault();

        Task firstTask = new Task("Первая задача", "Описание", Statuses.NEW);
        manager.createTask(firstTask);

        Task secondTask = new Task("Вторая задача", "Описание", Statuses.NEW);
        manager.createTask(secondTask);

        Epic transfer = new Epic("Переезд", "Описание");

        Subtask firstTransferSubtask = new Subtask("Собрать коробки", "Описание", Statuses.NEW, transfer);
        manager.createSubtask(firstTransferSubtask);

        Subtask secondTransferSubtask = new Subtask("Упаковать кошку", "Описание", Statuses.NEW, transfer);
        manager.createSubtask(secondTransferSubtask);

        Subtask thirdTransferSubtask = new Subtask("Сказать слова прощания", "Описание", Statuses.NEW, transfer);
        manager.createSubtask(thirdTransferSubtask);

        manager.createEpic(transfer);

        Epic importantEpic = new Epic("Важный эпик 2", "Описание");

        Subtask importantEpicSubtask = new Subtask("Задача 1", "Описание", Statuses.NEW, importantEpic);
        manager.createSubtask(importantEpicSubtask);

        manager.createEpic(importantEpic);

        manager.getTaskById(1);
        manager.getTaskById(2);
        manager.getTaskById(1);
        manager.getTaskById(1);
        manager.getTaskById(1);
        manager.getTaskById(1);
        manager.getTaskById(2);

        //manager.getSubtaskById(4);

        //manager.getEpicById(6);

        //manager.removeEpicById(6);

        manager.getHistory();


    }

}
