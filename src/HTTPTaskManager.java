import java.lang.reflect.Type;
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
        String[] types = {"task","epic","subtask"};
        Type[] classes = {Task.class, Epic.class, Subtask.class};
        int i = 0;
        httpTaskManager = new HTTPTaskManager("http://localhost:8078");
        while(i < 4) {
            try {
                if(i == 3) {
                    historyFromString(kvTaskClient.load("history"));
                } else {
                    httpTaskManager.createTask(gson.fromJson(kvTaskClient.load(types[i]), classes[i]));
                }
            } catch (ManagerSaveException managerSaveException) {
            } finally {
                ++i;
            }
        }
        return httpTaskManager;
    }
}
