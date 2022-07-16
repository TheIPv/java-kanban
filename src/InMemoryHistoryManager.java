import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class InMemoryHistoryManager implements HistoryManager{

    private Node head;
    public Node linkLast(Task task) {
        Node newNode = new Node(task);
        if(this.head == null) {
            head = newNode;
            return head;
        }
        else {
            Node currentNode = head;
            while(currentNode.getNextNode() != null) {
                currentNode = currentNode.getNextNode();
            }
            currentNode.setNextNode(newNode);
            newNode.setPrevNode(currentNode);
        }
        return newNode;
    }

    public ArrayList getTasks() {
        Node currentNode = head;
        ArrayList<Task> tasks = new ArrayList<>();
        while(currentNode != null) {
            tasks.add(currentNode.getData());
            currentNode = currentNode.getNextNode();
        }
        return tasks;
    }

    public void removeNode(Node node){
        if (node == null) {
            return;
        }
        if (node.getPrevNode() != null) {
            node.getPrevNode().setNextNode(node.getPrevNode().getNextNode());
        } else if (node.getPrevNode() == null) {
            head = node.getNextNode();
        }
        if (node.getNextNode() != null) {
            node.getNextNode().setPrevNode(node.getPrevNode());
        } else if (node.getNextNode() == null && node.getPrevNode() != null) {
            node.getPrevNode().setNextNode(null);
        }
    }

    HashMap<Integer,Node> histroryOfView = new HashMap<>();

    @Override
    public void add(Task task) {
        if(histroryOfView.containsKey(task.getId())) {
            remove(task.id);
        }
        histroryOfView.put(task.getId(), linkLast(task));
    }
    @Override
    public void remove(int id) {
        removeNode(histroryOfView.get(id));
        histroryOfView.remove(id);
    }

    @Override
    public ArrayList<Task> getHistory() {
        /*ArrayList<Task> tasks = new ArrayList<>();
        for(Node curNode: histroryOfView.values()) {
            tasks.add(curNode.getData());
        }*/
        return getTasks();
    }
}
