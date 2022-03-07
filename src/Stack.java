import java.io.ObjectStreamException;

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
    public Stack() {myStack = new java.util.Stack();
    }

    /**
     * Checks if the myStack is empty.
     * @return (Boolean, if myStack is empty)
     */
    public boolean isEmpty(){
        return myStack.isEmpty();}

    /**
     * Removes the item at the very top of myStack.
     */
    public void pop() {myStack.pop();}


    /**
     * Adds an item at the very top of  myStack.
     */
    public void push(Object theObject) { myStack.push(theObject);}

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
    public Stack copy() {
        Object[] copyArray = myStack.toArray();
        Stack copyStack = new Stack();
        for (Object obj: copyArray) {
            copyStack.push(obj);

        }
        return copyStack;
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

    @Override
    public String toString() {
        return myStack.toString();
    }

    /**
     * This function return a string array equivalent of myStack
     * @return (Array equivalent of myStack)
     */
    public Object[] toArray() {
        Object[] myArray = myStack.toArray();
        return myArray;
    }

    /**
     * This method fetches the last two numbers in a given array,
     * this method extracts the numbers from the given list and returns
     * them in a list back to the user.
     * @param theArray (myStack's array implementation)
     * @param startingIndex (int, Starting Index)
     * @return (int[], Array size 2 with the last 2 numbers)
     */
    public static boolean fetchLast2Values(Object[] theArray, int startingIndex) {
        //Counter for the numberArray
        int numberArrayCounter = 1;
        boolean foundMinus = false;
        if(theArray[startingIndex].toString().equals("-")) {
            foundMinus = true;
        }

        //Make sure the operator get taken out of the array first

        for (int i = startingIndex-1; i >= 0 ; i--) {
            if(!theArray[i].equals("X")) {
                if(foundMinus) {
                    numberArrayCounter--;
                    numberArrayCounter--;
                } else {
                    numberArrayCounter--;
                }
                theArray[i] = "X";

                if(numberArrayCounter == -1) {
                    break;}
            }
        }
        theArray[startingIndex] = "C";
        if(numberArrayCounter != -1) {
            System.out.println("two values were not found");
            return false;
        }
        return true;
    }


}

//END

