import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;

public class  Main {

    public static void main(String[] args) {

        TaskManager manager = Managers.getDefault();

    //    Task firstTask = new Task("Первая задача", "Описание", Statuses.NEW);
      //  manager.createTask(firstTask);

      //  Task secondTask = new Task("Вторая задача", "Описание", Statuses.NEW);
     //   manager.createTask(secondTask);

        Epic transfer = new Epic("Переезд", "Описание");

        Subtask firstTransferSubtask = new Subtask("Собрать коробки", "Описание", Statuses.IN_PROGRESS, transfer,
                LocalDateTime.of(2021, 2, 14, 1, 2,3),
                Duration.ofDays(20));
        manager.createSubtask(firstTransferSubtask);

        Subtask secondTransferSubtask = new Subtask("Упаковать кошку", "Описание", Statuses.IN_PROGRESS, transfer,
                LocalDateTime.of(2021, 2, 15, 1, 2,3),
                Duration.ofDays(20));
        manager.createSubtask(secondTransferSubtask);

        Subtask thirdTransferSubtask = new Subtask("Сказать слова прощания", "Описание", Statuses.IN_PROGRESS, transfer,
                LocalDateTime.of(2021, 2, 16, 1, 2,3),
                Duration.ofDays(20));
        manager.createSubtask(thirdTransferSubtask);

        manager.createEpic(transfer);

      //  Epic importantEpic = new Epic("Важный эпик 2", "Описание");

      //  Subtask importantEpicSubtask = new Subtask("Задача 1", "Описание", Statuses.NEW, importantEpic);
      //  manager.createSubtask(importantEpicSubtask);

      //  manager.createEpic(importantEpic);

    /*    manager.getTaskById(1);
        manager.getTaskById(2);
        manager.getTaskById(1);
        manager.getTaskById(1);
        manager.getTaskById(1);
        manager.getTaskById(1);
        manager.getTaskById(2);*/

        //manager.getSubtaskById(4);

        //manager.getEpicById(6);

        //manager.removeEpicById(6);

        System.out.println(manager.getPrioritizedTasks());

    }

}
