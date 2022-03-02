import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Test.*;


class StackTest {
    Stack myTestStack;
    @Test
    void isEmpty() {
        myTestStack = new Stack();
        myTestStack.push(42);
        myTestStack.push(446);
        assertFalse(myTestStack.isEmpty());
    }
    @Test
    void isEmpty2() {
        myTestStack = new Stack();
        myTestStack.push(42);
        myTestStack.push(446);
        myTestStack.makeEmpty();
        assertTrue(myTestStack.isEmpty());
    }

    @Test
    void pop() {
        myTestStack = new Stack();
        myTestStack.push(42);
        myTestStack.push(446);
        myTestStack.pop();
        assertEquals(myTestStack.top(), 42);
    }

    @Test
    void push() {
        myTestStack = new Stack();
        myTestStack.push(42);
        myTestStack.push(446);
        myTestStack.push(9954);
        assertEquals(myTestStack.top(), 9954);
    }

    @Test
    void topAndPop() {
        myTestStack = new Stack();
        myTestStack.push(42);
        myTestStack.push(446);
        myTestStack.push(9954);
        int sizeBeforePop = myTestStack.size();


        assertEquals(myTestStack.topAndPop(), 9954);
        assertEquals(myTestStack.size(), sizeBeforePop-1);
    }

    @Test
    void makeEmpty() {
        myTestStack = new Stack();
        myTestStack.makeEmpty();
        assertEquals(myTestStack.size(), 0);

        myTestStack.push(42);
        myTestStack.push(446);
        myTestStack.push(9954);
        myTestStack.makeEmpty();



        assertEquals(myTestStack.size(), 0);
    }

    @Test
    void top() {
        myTestStack = new Stack();
        myTestStack.push(42);
        myTestStack.push(446);
        myTestStack.push(9954);
        assertEquals(myTestStack.top(), 9954);
    }

    @Test
    void size() {
        myTestStack = new Stack();
        myTestStack.push(42);
        myTestStack.push(446);
        myTestStack.push(9954);
        assertEquals(myTestStack.size(), 3);
    }
}