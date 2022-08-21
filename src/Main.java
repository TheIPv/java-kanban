
import java.io.IOException;

public class  Main {

    public static void main(String[] args) throws IOException {
        new KVServer().start();
        TaskManager manager = Managers.getDefault();
       // manager.updateTask(manager.getTaskById(0));
        new HttpTaskServer(manager);
    }

}
