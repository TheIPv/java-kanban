import java.net.URI;
import com.google.gson.Gson;

public class HTTPTaskManager extends FileBackedTasksManager {

    private static Gson gson = new Gson();
    private URI url;
    private static KVTaskClient kvTaskClient;
    static HTTPTaskManager httpTaskManager;
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
        httpTaskManager = new HTTPTaskManager("http://localhost:8078");
        try {
            httpTaskManager.createTask(gson.fromJson(kvTaskClient.load("task"), Task.class));
        } catch (NullPointerException nullPointerException) {
        }
        try {
            httpTaskManager.createEpic(gson.fromJson(kvTaskClient.load("epic"), Epic.class));
        } catch (NullPointerException nullPointerException) {
        }
        try {
            httpTaskManager.createSubtask(gson.fromJson(kvTaskClient.load("subtask"), Subtask.class));
        } catch (NullPointerException nullPointerException) {
        }
        try {
            historyFromString(kvTaskClient.load("history"));
        } catch (NullPointerException nullPointerException) {
        }
        return httpTaskManager;
    }
}
