import java.io.IOException;

public class ManagerSaveException extends RuntimeException {

    public ManagerSaveException(String key) {
        System.out.println("Не удалось получить значение по ключу: "+key);
    }

}
