public class Managers  {

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

    public static TaskManager getDefault() throws ManagerSaveException { return HTTPTaskManager.load(); }
}
