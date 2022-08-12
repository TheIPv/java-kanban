import java.io.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class FileBackedTasksManager extends InMemoryTaskManager implements TaskManager{

    public static void main(String[] args) throws ManagerSaveException {

        FileBackedTasksManager fileBackedTasksManager = loadFromFile(new File("kanban.csv"));

        Epic transfer = new Epic("Переезд", "Описание");

        Subtask firstTransferSubtask = new Subtask("Собрать коробки", "Описание", Statuses.IN_PROGRESS, transfer,
                LocalDateTime.of(2021, 2, 14, 1, 2,3),
                Duration.ofDays(20));
        fileBackedTasksManager.createSubtask(firstTransferSubtask);

        Subtask secondTransferSubtask = new Subtask("Упаковать кошку", "Описание", Statuses.IN_PROGRESS, transfer,
                LocalDateTime.of(2021, 2, 15, 1, 2,3),
                Duration.ofDays(20));
        fileBackedTasksManager.createSubtask(secondTransferSubtask);

        Subtask thirdTransferSubtask = new Subtask("Сказать слова прощания", "Описание", Statuses.IN_PROGRESS, transfer,
                LocalDateTime.of(2021, 2, 16, 1, 2,3),
                Duration.ofDays(20));
        fileBackedTasksManager.createSubtask(thirdTransferSubtask);

        fileBackedTasksManager.createEpic(transfer);

        /*Task firstTask = new Task("Первая задача", "Описание", Statuses.NEW);
        fileBackedTasksManager.createTask(firstTask);

        Task secondTask = new Task("Вторая задача", "Описание", Statuses.NEW);
        fileBackedTasksManager.createTask(secondTask);

        Epic transfer = new Epic("Переезд", "Описание");

        Subtask firstTransferSubtask = new Subtask("Собрать коробки", "Описание", Statuses.NEW, transfer);
        fileBackedTasksManager.createSubtask(firstTransferSubtask);

        Subtask secondTransferSubtask = new Subtask("Упаковать кошку", "Описание", Statuses.NEW, transfer);
        fileBackedTasksManager.createSubtask(secondTransferSubtask);

        Subtask thirdTransferSubtask = new Subtask("Сказать слова прощания", "Описание", Statuses.NEW, transfer);
        fileBackedTasksManager.createSubtask(thirdTransferSubtask);

        fileBackedTasksManager.createEpic(transfer);

        Epic importantEpic = new Epic("Важный эпик 2", "Описание");

        Subtask importantEpicSubtask = new Subtask("Задача 1", "Описание", Statuses.NEW, importantEpic);
        fileBackedTasksManager.createSubtask(importantEpicSubtask);

        fileBackedTasksManager.createEpic(importantEpic);

        fileBackedTasksManager.getTaskById(1);
        fileBackedTasksManager.getTaskById(2);
        fileBackedTasksManager.getTaskById(1);
        fileBackedTasksManager.getTaskById(1);
        fileBackedTasksManager.getTaskById(1);
        fileBackedTasksManager.getTaskById(1);
        fileBackedTasksManager.getTaskById(2);*/

        //manager.getSubtaskById(4);

        //manager.getEpicById(6);

        //manager.removeEpicById(6);

        //fileBackedTasksManager.createTask(new Task("Задача 3", "Описание", Statuses.NEW));

        //fileBackedTasksManager.getTaskById(9);

        fileBackedTasksManager.getHistory();

    }



    @Override
    public void createSubtask(Subtask subtask) {
        super.createSubtask(subtask);
        save();
    }

    @Override
    public void createTask(Task task) {
        super.createTask(task);
        save();
        super.tasks.put(task.getId(), task);
    }

    @Override
    public void createEpic(Epic epic) {
        super.createEpic(epic);
        save();
    }

    @Override
    public void getHistory() {
        System.out.println(historyManager.getHistory());
        save();
    }
    public void save() {
        try (FileWriter writer = new FileWriter("kanban.csv", false)) {
            writer.write("id,type,name,status,description,epic,startTime,duration\n");
            for(Task currentTask: super.getAllTasks()) {
                writer.write(toString(currentTask));
            }
            for(Epic currentEpic: super.getAllEpics()) {
                writer.write(String.join(",",Integer.toString(currentEpic.getId()),Types.EPIC.toString(),
                        currentEpic.getName(), currentEpic.getStatus().toString(),
                        currentEpic.getDescription(), "-1", currentEpic.getStartTime().toString(), currentEpic.getEndTime().toMinutes() + "\n"));
            }
            for(Subtask currentSubtask: super.getAllSubtasks()) {
                writer.write(String.join(",",Integer.toString(currentSubtask.getId()),Types.SUBTASK.toString(),
                        currentSubtask.getName(), currentSubtask.getStatus().toString(),
                        currentSubtask.getDescription(), Integer.toString(currentSubtask.getEpic().getId()),
                        Integer.toString(0), currentSubtask.getStartTime().toString(), currentSubtask.getDuration().toMinutes() + "\n"));
            }
            writer.write("\n"+ historyToString(historyManager));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String toString(Task task) {
        return String.join(",",Integer.toString(task.getId()),Types.TASK.toString(),
                task.getName(), task.getStatus().toString(), task.getDescription(), Integer.toString(0), task.getStartTime().toString(), task.getDuration().toMinutes() + "\n");
    }

    Task fromString(String value) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        String[] words = value.split(",");
        Task task = new Task(words[2], words[4],StingToStatus(words[3]), LocalDateTime.parse(words[6],dateTimeFormatter), Duration.ofMinutes(Long.valueOf(words[7])));
        task.setId(Integer.valueOf(words[0]));
        return task;
    }


    static String historyToString(HistoryManager manager) {
        StringBuilder sb = new StringBuilder();
        for(Task currentTask : manager.getHistory()) {
            sb.append(currentTask.getId() + ",");
        }
        if(sb.length() > 1) sb.deleteCharAt(sb.length()-1);
        return sb.toString();
    }

    static List<Integer> historyFromString(String value) {
        List<Integer> list = new LinkedList<>();
        String[] history = value.split(",");
        for(String currentElement: history) {
            list.add(Integer.valueOf(currentElement));
        }
        return list;
    }

    public static Types StingToTypes(String value) {
        switch (value) {
            case "EPIC":
                return Types.EPIC;
            case "SUBTASK":
                return Types.SUBTASK;
            case "TASK":
                return Types.TASK;
            default:
                throw new IllegalArgumentException();
        }
    }
    public static Statuses StingToStatus(String value) {
        switch (value) {
            case "NEW":
                return Statuses.NEW;
            case "DONE":
                return Statuses.DONE;
            case "IN_PROGRESS":
                return Statuses.IN_PROGRESS;
            default:
                throw new IllegalArgumentException();
        }
    }


    static FileBackedTasksManager loadFromFile(File file) throws ManagerSaveException {
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager();
        try(BufferedReader fileReader = new BufferedReader(new FileReader(file))) {
            fileReader.readLine();
            while(fileReader.ready()) {
                String readLine = fileReader.readLine();
                if(readLine.isEmpty()) {
                    if(fileReader.ready()) readLine = fileReader.readLine();
                    if(!readLine.isEmpty()) {
                        for(Integer id: historyFromString(readLine)) {
                            if(subtasks.get(id) != null) fileBackedTasksManager.historyManager.add(subtasks.get(id));
                            else if(epics.get(id) != null) fileBackedTasksManager.historyManager.add(epics.get(id));
                            else if(tasks.get(id) != null) fileBackedTasksManager.historyManager.add(tasks.get(id));
                        }
                    }
                    break;
                }
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
                String[] words = readLine.split(",");
                switch(StingToTypes(words[1])) {
                    case EPIC:
                        Epic epic = new Epic(words[2], words[4]);
                        epic.setId(Integer.valueOf(words[0]));
                        increaseId();
                        epics.put(epic.getId(), epic);
                        break;
                    case SUBTASK:

                        Subtask subtask = new Subtask(words[2], words[4], StingToStatus(words[3]),
                                epics.get(Integer.valueOf(words[5])), LocalDateTime.parse(words[7],dateTimeFormatter), Duration.ofMinutes(Long.valueOf(words[8])));
                        subtask.setId(Integer.valueOf(words[0]));
                        subtasks.put(subtask.getId(), subtask);
                        increaseId();
                        break;
                    case TASK:
                        Task task = fileBackedTasksManager.fromString(String.join(",",words[0],words[1],words[2],words[3],words[4],words[5],words[6],words[7]));
                        tasks.put(task.getId(), task);
                        increaseId();
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + StingToTypes(words[1]));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Файлы сохранений отсутствуют. Создаю новый файл...");
        } catch (IOException e) {
            throw new ManagerSaveException();
        }
        return fileBackedTasksManager;
    }
}


