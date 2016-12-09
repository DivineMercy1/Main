
/**
 * Created by norse on 10/3/2016.
 */
public class StackClass<E> {
    public static final int CAPACITY = 1000; // default array capacity
    private E[] data; // generic array used for storage
    private int t = -1; // index of the top element in stack

    public StackClass() {
        this(CAPACITY);
    }// constructs stack with default capacity

    public StackClass(int capacity) { // constructs stack with given capacity
        data = (E[]) new Object[capacity]; // safe cast; compiler may give warning
    }

    public int size() { // return the size of the stack
        return (t + 1);
    }

    public boolean isEmpty() { // return whether or not the stack is empty
        return (t == -1);
    }

    public void push(E e) throws IllegalStateException { // add element to the stack
        if (size() == data.length) throw new IllegalStateException("Stack is full");
        data[++t] = e; // increment t before storing new item
    }

    public E top() { // return the data at the top of the stack
        if (isEmpty()) return null;
        return data[t];
    }

    public E pop() { // take off the top element and get the data off.
        if (isEmpty()) return null;
        E answer = data[t];
        data[t] = null; // dereference to help garbage collection
        t--;
        return answer;
    }
}
