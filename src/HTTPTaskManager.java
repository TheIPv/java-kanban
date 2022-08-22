import java.lang.reflect.Type;
import java.net.URI;
import java.util.HashMap;

import com.google.gson.Gson;
public class HTTPTaskManager extends FileBackedTasksManager {

    private static Gson gson = new Gson();
    private URI url;
    private static KVTaskClient kvTaskClient;
    static final HTTPTaskManager httpTaskManager = new HTTPTaskManager("http://localhost:8078");;
    public HTTPTaskManager(String url) {
        this.url = URI.create(url);
        kvTaskClient = new KVTaskClient(url);
    }

    @Override
    public void save() {
        kvTaskClient.put("task", gson.toJson(getAllTasks()));
        kvTaskClient.put("epic", gson.toJson(getAllEpics()));
        kvTaskClient.put("subtask", gson.toJson(getAllSubtasks()));
        kvTaskClient.put("history", historyToString(historyManager));
    }

    static HTTPTaskManager load() throws ManagerSaveException {
        try {
            httpTaskManager.createTask(gson.fromJson(kvTaskClient.load("task"), Task.class));
        } catch (ManagerSaveException managerSaveException) {
            //Сообщение об ошибке выводится в методе load KVTaskClient
        } finally {
            try {
                httpTaskManager.createTask(gson.fromJson(kvTaskClient.load("epic"), Epic.class));
            } catch (ManagerSaveException managerSaveException) {
                //Сообщение об ошибке выводится в методе load KVTaskClient
            } finally {
                try {
                    httpTaskManager.createSubtask(gson.fromJson(kvTaskClient.load("subtask"), Subtask.class));
                } catch (ManagerSaveException managerSaveException) {
                    //Сообщение об ошибке выводится в методе load KVTaskClient
                } finally {
                    try {
                        historyFromString(kvTaskClient.load("history"));
                    } catch (ManagerSaveException managerSaveException) {
                        //Сообщение об ошибке выводится в методе load KVTaskClient
                    }
                }
            }
        }
        return httpTaskManager;
    }

}
