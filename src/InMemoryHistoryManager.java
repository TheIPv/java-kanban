import java.util.HashMap;
import java.util.LinkedList;

public class InMemoryHistoryManager implements HistoryManager{

    HashMap<Integer,Task> histroryOfView = new HashMap<>();

    private int idForHistoryView = 0;

    @Override
    public void add(Task task) {

        if(idForHistoryView == 10) idForHistoryView = 0;
        histroryOfView.put(idForHistoryView, task);
        ++idForHistoryView;
    }

    public LinkedList<Task> getHistory() {
        return new LinkedList<>(histroryOfView.values());
    }
}
