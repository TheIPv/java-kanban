public class Node<T extends Task> {
    public T data;
    public Node<T> next;
    public Node<T> prev;

    public Node(T data) {
        this.data = data;
    }
    public Node getNextNode() {
        return next;
    }
    public void setNextNode(Node nextNode) {
        this.next = nextNode;
    }

    public Node getPrevNode() {
        return prev;
    }

    public void setPrevNode(Node prevNode) {
        this.prev = prevNode;
    }
    
    public T getData() {
        return data;
    }

}
