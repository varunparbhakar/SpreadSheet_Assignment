import java.util.LinkedList;

public class Queue {

    private final java.util.Queue myQueue;

    public Queue() {
        myQueue = new LinkedList();
    }

    public void makeEmpty() {
        myQueue.removeAll(myQueue);
    }

    public void enqueue(Cell cell) {
        myQueue.add(cell);
    }

    public Cell dequeue() {
        return (Cell) myQueue.remove();
    }

    public boolean isEmpty() {
        return myQueue.isEmpty();
    }

    public int size() {
        return myQueue.size();
    }


}
