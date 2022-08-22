
public class ManagerSaveException extends RuntimeException {

    private String key;
    public ManagerSaveException(String key) {
        this.key = key;
        System.out.println("Не удалось получить значение по ключу: " + key);
    }

}
