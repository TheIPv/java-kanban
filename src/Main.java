
import java.io.IOException;

public class  Main {

    public static void main(String[] args) throws IOException {
        new KVServer().start();
        TaskManager manager = Managers.getDefault();
        new HttpTaskServer(manager);
    }

}
