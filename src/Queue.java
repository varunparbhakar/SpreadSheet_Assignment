import java.util.LinkedList;

/**
 * This class is custom implementation of java.util.Queue.
 * @version 03/07/2022
 * @author Varun Parbhakar, Andrew Dibble, and Minh Trung Le.
 */
public class Queue {

    private final java.util.Queue myQueue;

    /**
     * Constructor for Queue class
     */
    public Queue() {
        myQueue = new LinkedList();
    }

    /**
     * Empties the queue.
     */
    public void makeEmpty() {
        myQueue.removeAll(myQueue);
    }

    /**
     * Puts an item in the queue.
     */
    public void enqueue(Cell cell) {
        myQueue.add(cell);
    }

    /**
     * Releases the next item in the queue.
     */
    public Cell dequeue() {
        return (Cell) myQueue.remove();
    }

    /**
     * Returns if the queue is empty or not.
     * @return
     */
    public boolean isEmpty() {
        return myQueue.isEmpty();
    }

    /**
     * Returns the size of queue.
     * @return
     */
    public int size() {
        return myQueue.size();
    }


}
