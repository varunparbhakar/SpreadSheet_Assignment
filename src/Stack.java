/**
 * This class is a substitute for a Java.util.Stack.
 */
public class Stack {
    /**
     * myStack holds all the Cells.
     */
    private java.util.Stack myStack;

    /**
     * Stack constructor.
     */
    public Stack() {
        myStack = new java.util.Stack();
    }

    /**
     * Checks if the myStack is empty.
     * @return (Boolean, if myStack is empty)
     */
    public boolean isEmpty(){return myStack.empty();}

    /**
     * Removes the item at the very top of myStack.
     */
    public void pop() {myStack.pop();}


    /**
     * Adds an item at the very top of  myStack.
     */
    public void push(Object theObject) {myStack.push(theObject);}

    /**
     * Removes and return the item at the top of myStack.
     * @return (Object, the removed item)
     */
    public Object topAndPop() {
        Object thePeek = myStack.peek();
        myStack.pop();
        return thePeek;
    }

    /**
     * Removes all the items in myStack.
     */
    public void makeEmpty(){

        myStack = new java.util.Stack();
    }

    /**
     * Returns the item at the top of myStack.
     * @return (Object, item at the top of myStack)
     */
    public Object top(){return myStack.peek();} //peek

    /**
     * Returns the size of myStack.
     * @return (int, size of myStack)
     */
    public int size(){
        return myStack.size();
    }

}
//END
