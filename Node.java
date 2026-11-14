import java.io.Serializable;

// Node class for the linked list structure holding reservations
public class Node implements Serializable {
    private Reservation data;
    private Node next;

    // Constructor to initialize node data
    public Node(Reservation data) {
        this.data = data;
        this.next = null;
    }

    // Get reservation data stored in this node
    public Reservation getData() {
        return data;
    }

    // Set reservation data for this node
    public void setData(Reservation data) {
        this.data = data;
    }

    // Get the next node reference
    public Node getNext() {
        return next;
    }

    // Set the next node reference
    public void setNext(Node next) {
        this.next = next;
    }
}
